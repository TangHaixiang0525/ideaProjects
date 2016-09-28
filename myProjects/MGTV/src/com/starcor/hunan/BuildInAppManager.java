package com.starcor.hunan;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import com.starcor.core.domain.AppDownloadUrl;
import com.starcor.core.domain.PreInstallList;
import com.starcor.core.domain.PreInstallList.Apps;
import com.starcor.core.utils.GeneralUtils;
import com.starcor.core.utils.Logger;
import com.starcor.sccms.api.SccmsApiGetAppDownloadUrlTask.ISccmsApiGetAppDownloadUrlTaskListener;
import com.starcor.sccms.api.SccmsApiGetPreInstallListTask.ISccmsApiGetPreInstallListTaskListener;
import com.starcor.sccms.api.SccmsApiGetUninstallListTask.ISccmsApiGetUninstallListTaskListener;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiManager;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BuildInAppManager
{
  static final int CONCURRENT_LIMIT = 1;
  private static final String TAG = BuildInAppManager.class.getSimpleName();
  static BuildInAppManager _instance = new BuildInAppManager();
  final ArrayList<_Command> _cmd_queue = new ArrayList();
  AtomicInteger _download_counter = new AtomicInteger(0);
  AtomicInteger _fetch_url_counter = new AtomicInteger(0);
  AtomicInteger _install_counter = new AtomicInteger(0);
  private String _last_error;

  BuildInAppManager()
  {
    new Thread()
    {
      public void run()
      {
        BuildInAppManager.this._run();
      }
    }
    .start();
  }

  private void _activite_cmd_queue()
  {
    synchronized (this._cmd_queue)
    {
      this._cmd_queue.notify();
      return;
    }
  }

  private void _do_download(_Command param_Command, String paramString)
  {
    String str1 = GeneralUtils.MD5(paramString) + ".apk";
    String str2 = App.getInstance().getFilesDir().getAbsolutePath() + "/" + str1;
    Logger.d(TAG, "_do_download start, urlStr=" + paramString);
    try
    {
      localInputStream = new URL(paramString).openConnection().getInputStream();
      byte[] arrayOfByte = new byte[4096];
      FileOutputStream localFileOutputStream = App.getInstance().openFileOutput(str1, 1);
      while (true)
      {
        int i = localInputStream.read(arrayOfByte);
        if (i == -1)
          break;
        localFileOutputStream.write(arrayOfByte, 0, i);
      }
    }
    catch (MalformedURLException localMalformedURLException)
    {
      Logger.d(TAG, "_do_download error, urlStr=" + paramString + "\n " + localMalformedURLException.getMessage());
      localMalformedURLException.printStackTrace();
      Logger.d(TAG, "_do_download end, urlStr=" + paramString);
    }
    catch (IOException localInterruptedException)
    {
      try
      {
        Thread.sleep(500L);
        param_Command.op = BuildInAppManager._Command.Type.INSTALL;
        param_Command.path = str2;
      }
      catch (InterruptedException localInterruptedException)
      {
        synchronized (this._cmd_queue)
        {
          while (true)
          {
            InputStream localInputStream;
            this._cmd_queue.add(param_Command);
            _activite_cmd_queue();
            return;
            localInputStream.close();
            continue;
            localIOException = localIOException;
            Logger.d(TAG, "_do_download error, urlStr=" + paramString + "\n " + localIOException.getMessage());
            localIOException.printStackTrace();
          }
          localInterruptedException = localInterruptedException;
          localInterruptedException.printStackTrace();
        }
      }
    }
  }

  private void _op_download(final _Command param_Command, final String paramString)
  {
    if (this._download_counter.getAndIncrement() >= 1)
    {
      this._download_counter.decrementAndGet();
      synchronized (this._cmd_queue)
      {
        this._cmd_queue.add(param_Command);
        return;
      }
    }
    new Thread()
    {
      public void run()
      {
        try
        {
          BuildInAppManager.this._do_download(param_Command, paramString);
          BuildInAppManager.this._download_counter.decrementAndGet();
          BuildInAppManager.this._activite_cmd_queue();
          return;
        }
        catch (Exception localException)
        {
          while (true)
            localException.printStackTrace();
        }
      }
    }
    .start();
  }

  private void _op_fetch_url(final _Command param_Command, String paramString)
  {
    if (this._fetch_url_counter.getAndIncrement() >= 1)
    {
      this._fetch_url_counter.decrementAndGet();
      synchronized (this._cmd_queue)
      {
        this._cmd_queue.add(param_Command);
        return;
      }
    }
    ServerApiManager.i().APIGetAppDownloadUrl(paramString, new SccmsApiGetAppDownloadUrlTask.ISccmsApiGetAppDownloadUrlTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        BuildInAppManager.this._fetch_url_counter.decrementAndGet();
        BuildInAppManager.this._activite_cmd_queue();
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, AppDownloadUrl paramAnonymousAppDownloadUrl)
      {
        BuildInAppManager.this._fetch_url_counter.decrementAndGet();
        param_Command.op = BuildInAppManager._Command.Type.DOWNLOAD;
        param_Command.url = paramAnonymousAppDownloadUrl.url;
        synchronized (BuildInAppManager.this._cmd_queue)
        {
          BuildInAppManager.this._cmd_queue.add(0, param_Command);
          BuildInAppManager.this._activite_cmd_queue();
          return;
        }
      }
    });
  }

  private void _op_install(_Command param_Command, final String paramString)
  {
    if (this._install_counter.getAndIncrement() >= 1)
    {
      this._install_counter.decrementAndGet();
      synchronized (this._cmd_queue)
      {
        this._cmd_queue.add(param_Command);
        return;
      }
    }
    new Thread()
    {
      public void run()
      {
        try
        {
          boolean bool = BuildInAppManager.this.install_apk_silent(paramString);
          Logger.d(BuildInAppManager.TAG, "_op_install, install_apk_silent=" + bool);
          if (!bool)
            BuildInAppManager.this.install_apk(paramString);
          BuildInAppManager.this._install_counter.decrementAndGet();
          BuildInAppManager.this._activite_cmd_queue();
          return;
        }
        catch (Exception localException)
        {
          while (true)
            localException.printStackTrace();
        }
      }
    }
    .start();
  }

  private void _op_uninstall(_Command param_Command, final String paramString)
  {
    if (this._install_counter.getAndIncrement() >= 1)
    {
      this._install_counter.decrementAndGet();
      synchronized (this._cmd_queue)
      {
        this._cmd_queue.add(param_Command);
        return;
      }
    }
    new Thread()
    {
      public void run()
      {
        try
        {
          boolean bool = BuildInAppManager.this.uninstall_apk_silent(paramString);
          Logger.d(BuildInAppManager.TAG, "_op_uninstall, uninstall_apk_silent=" + bool);
          BuildInAppManager.this._install_counter.decrementAndGet();
          BuildInAppManager.this._activite_cmd_queue();
          return;
        }
        catch (Exception localException)
        {
          while (true)
            localException.printStackTrace();
        }
      }
    }
    .start();
  }

  private void _run()
  {
    while (true)
    {
      _Command local_Command = null;
      while (true)
      {
        try
        {
          synchronized (this._cmd_queue)
          {
            this._cmd_queue.wait(1000L);
            boolean bool = this._cmd_queue.isEmpty();
            local_Command = null;
            if (!bool)
              break label107;
          }
        }
        catch (InterruptedException localInterruptedException)
        {
          localInterruptedException.printStackTrace();
          switch (8.$SwitchMap$com$starcor$hunan$BuildInAppManager$_Command$Type[local_Command.op.ordinal()])
          {
          default:
            break;
          case 1:
            _op_refresh_app_lists(local_Command);
          case 2:
          case 3:
          case 4:
          case 5:
          }
        }
        break;
        label107: local_Command = (_Command)this._cmd_queue.remove(0);
      }
      _op_fetch_url(local_Command, local_Command.id);
      continue;
      _op_download(local_Command, local_Command.url);
      continue;
      _op_install(local_Command, local_Command.path);
      continue;
      _op_uninstall(local_Command, local_Command.package_name);
    }
  }

  private PackageManager getPackageManager()
  {
    return App.getAppContext().getPackageManager();
  }

  private void refresh_app_list()
  {
    _Command local_Command = new _Command();
    local_Command.op = BuildInAppManager._Command.Type.REFRESH_APP_LIST;
    synchronized (this._cmd_queue)
    {
      this._cmd_queue.add(local_Command);
      this._cmd_queue.notify();
      return;
    }
  }

  public static void run()
  {
  }

  private void startActivity(Intent paramIntent)
  {
    App.getAppContext().startActivity(paramIntent);
  }

  void _op_refresh_app_lists(_Command param_Command)
  {
    final PackageManager localPackageManager = getPackageManager();
    if (localPackageManager == null)
    {
      Logger.e(TAG, "Can not obtain PackageManager instance!");
      return;
    }
    ServerApiManager.i().APIGetPreInstallList(new SccmsApiGetPreInstallListTask.ISccmsApiGetPreInstallListTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        Logger.w(BuildInAppManager.TAG, "Get pre-installed app list failed! " + paramAnonymousServerApiCommonError.toString());
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, PreInstallList paramAnonymousPreInstallList)
      {
        Logger.w(BuildInAppManager.TAG, "Get pre-installed app list success!");
        label37: Iterator localIterator;
        if ((paramAnonymousPreInstallList == null) || (paramAnonymousPreInstallList.lists == null) || (paramAnonymousPreInstallList.lists.isEmpty()))
        {
          Logger.w(BuildInAppManager.TAG, "empty app list!");
          return;
        }
        else
        {
          localIterator = paramAnonymousPreInstallList.lists.iterator();
        }
        while (true)
        {
          if (!localIterator.hasNext())
            break label37;
          PreInstallList.Apps localApps = (PreInstallList.Apps)localIterator.next();
          if (localApps == null)
            break;
          String str1 = localApps.packageName;
          String str2 = localApps.versionId;
          String str3 = localApps.version;
          if ((TextUtils.isEmpty(str1)) || (TextUtils.isEmpty(str2)) || (TextUtils.isEmpty(str3)) || (!TextUtils.isDigitsOnly(str3)))
            break;
          int i = Integer.parseInt(str3);
          try
          {
            int j = localPackageManager.getPackageInfo(str1, 0).versionCode;
            if (j >= i)
              continue;
            local_Command = new BuildInAppManager._Command();
            local_Command.op = BuildInAppManager._Command.Type.FETCH_URL;
            local_Command.package_name = str1;
            local_Command.id = str2;
          }
          catch (PackageManager.NameNotFoundException localNameNotFoundException)
          {
            synchronized (BuildInAppManager.this._cmd_queue)
            {
              BuildInAppManager._Command local_Command;
              BuildInAppManager.this._cmd_queue.add(local_Command);
              BuildInAppManager.this._activite_cmd_queue();
              continue;
              localNameNotFoundException = localNameNotFoundException;
              localNameNotFoundException.printStackTrace();
            }
          }
        }
      }
    });
    ServerApiManager.i().APIGetUninstallList(new SccmsApiGetUninstallListTask.ISccmsApiGetUninstallListTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        Logger.w(BuildInAppManager.TAG, "Get forbidden app list failed! " + paramAnonymousServerApiCommonError.toString());
      }

      // ERROR //
      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, com.starcor.core.domain.UninstallList paramAnonymousUninstallList)
      {
        // Byte code:
        //   0: invokestatic 30	com/starcor/hunan/BuildInAppManager:access$100	()Ljava/lang/String;
        //   3: ldc 57
        //   5: invokestatic 51	com/starcor/core/utils/Logger:w	(Ljava/lang/String;Ljava/lang/String;)V
        //   8: aload_2
        //   9: ifnull +20 -> 29
        //   12: aload_2
        //   13: getfield 63	com/starcor/core/domain/UninstallList:lists	Ljava/util/ArrayList;
        //   16: ifnull +13 -> 29
        //   19: aload_2
        //   20: getfield 63	com/starcor/core/domain/UninstallList:lists	Ljava/util/ArrayList;
        //   23: invokevirtual 69	java/util/ArrayList:isEmpty	()Z
        //   26: ifeq +12 -> 38
        //   29: invokestatic 30	com/starcor/hunan/BuildInAppManager:access$100	()Ljava/lang/String;
        //   32: ldc 71
        //   34: invokestatic 51	com/starcor/core/utils/Logger:w	(Ljava/lang/String;Ljava/lang/String;)V
        //   37: return
        //   38: aload_2
        //   39: getfield 63	com/starcor/core/domain/UninstallList:lists	Ljava/util/ArrayList;
        //   42: invokevirtual 75	java/util/ArrayList:iterator	()Ljava/util/Iterator;
        //   45: astore_3
        //   46: aload_3
        //   47: invokeinterface 80 1 0
        //   52: ifeq -15 -> 37
        //   55: aload_3
        //   56: invokeinterface 84 1 0
        //   61: checkcast 86	com/starcor/core/domain/UninstallList$Apps
        //   64: astore 4
        //   66: aload 4
        //   68: ifnull -22 -> 46
        //   71: aload 4
        //   73: getfield 90	com/starcor/core/domain/UninstallList$Apps:packageName	Ljava/lang/String;
        //   76: astore 5
        //   78: aload 5
        //   80: invokestatic 95	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
        //   83: ifne -37 -> 46
        //   86: aload_0
        //   87: getfield 21	com/starcor/hunan/BuildInAppManager$3:val$packageManager	Landroid/content/pm/PackageManager;
        //   90: aload 5
        //   92: iconst_0
        //   93: invokevirtual 101	android/content/pm/PackageManager:getPackageInfo	(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;
        //   96: pop
        //   97: new 103	com/starcor/hunan/BuildInAppManager$_Command
        //   100: dup
        //   101: invokespecial 104	com/starcor/hunan/BuildInAppManager$_Command:<init>	()V
        //   104: astore 8
        //   106: aload 8
        //   108: getstatic 110	com/starcor/hunan/BuildInAppManager$_Command$Type:UNINSTALL	Lcom/starcor/hunan/BuildInAppManager$_Command$Type;
        //   111: putfield 113	com/starcor/hunan/BuildInAppManager$_Command:op	Lcom/starcor/hunan/BuildInAppManager$_Command$Type;
        //   114: aload 8
        //   116: aload 5
        //   118: putfield 116	com/starcor/hunan/BuildInAppManager$_Command:package_name	Ljava/lang/String;
        //   121: aload_0
        //   122: getfield 19	com/starcor/hunan/BuildInAppManager$3:this$0	Lcom/starcor/hunan/BuildInAppManager;
        //   125: getfield 119	com/starcor/hunan/BuildInAppManager:_cmd_queue	Ljava/util/ArrayList;
        //   128: astore 9
        //   130: aload 9
        //   132: monitorenter
        //   133: aload_0
        //   134: getfield 19	com/starcor/hunan/BuildInAppManager$3:this$0	Lcom/starcor/hunan/BuildInAppManager;
        //   137: getfield 119	com/starcor/hunan/BuildInAppManager:_cmd_queue	Ljava/util/ArrayList;
        //   140: aload 8
        //   142: invokevirtual 123	java/util/ArrayList:add	(Ljava/lang/Object;)Z
        //   145: pop
        //   146: aload 9
        //   148: monitorexit
        //   149: aload_0
        //   150: getfield 19	com/starcor/hunan/BuildInAppManager$3:this$0	Lcom/starcor/hunan/BuildInAppManager;
        //   153: invokestatic 127	com/starcor/hunan/BuildInAppManager:access$200	(Lcom/starcor/hunan/BuildInAppManager;)V
        //   156: goto -110 -> 46
        //   159: astore 6
        //   161: aload 6
        //   163: invokevirtual 130	android/content/pm/PackageManager$NameNotFoundException:printStackTrace	()V
        //   166: goto -120 -> 46
        //   169: astore 10
        //   171: aload 9
        //   173: monitorexit
        //   174: aload 10
        //   176: athrow
        //
        // Exception table:
        //   from	to	target	type
        //   86	133	159	android/content/pm/PackageManager$NameNotFoundException
        //   149	156	159	android/content/pm/PackageManager$NameNotFoundException
        //   174	177	159	android/content/pm/PackageManager$NameNotFoundException
        //   133	149	169	finally
        //   171	174	169	finally
      }
    });
  }

  boolean install_apk(String paramString)
  {
    this._last_error = "";
    Intent localIntent = new Intent("android.intent.action.VIEW");
    localIntent.addFlags(268435456);
    localIntent.setDataAndType(Uri.parse("file://" + paramString), "application/vnd.android.package-archive");
    try
    {
      startActivity(localIntent);
      Log.d("install_apk", paramString);
      return true;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }

  boolean install_apk_silent(String paramString)
  {
    this._last_error = "";
    PackageInfo localPackageInfo = getPackageManager().getPackageArchiveInfo(paramString, 5);
    if (localPackageInfo == null)
    {
      this._last_error = "INSTALL_PACKAGE_NOT_FOUND";
      return false;
    }
    try
    {
      Process localProcess = Runtime.getRuntime().exec("pm install -r " + paramString);
      new DataOutputStream(localProcess.getOutputStream());
      BufferedReader localBufferedReader1 = new BufferedReader(new InputStreamReader(localProcess.getInputStream()));
      localBufferedReader2 = new BufferedReader(new InputStreamReader(localProcess.getErrorStream()));
      try
      {
        localProcess.waitFor();
        String str2;
        for (localObject2 = ""; localBufferedReader1.ready(); localObject2 = str2)
          str2 = (String)localObject2 + localBufferedReader1.readLine() + "\n";
      }
      finally
      {
      }
    }
    catch (IOException localIOException)
    {
      BufferedReader localBufferedReader2;
      Object localObject2;
      localIOException.printStackTrace();
      this._last_error = "INSTALL_IO_EXCEPTION";
      while (true)
      {
        return false;
        for (String str1 = ""; localBufferedReader2.ready(); str1 = str1 + localBufferedReader2.readLine() + "\n");
        Log.d("install_apk_slient", paramString + " : " + localPackageInfo.packageName);
        Log.d("install_apk_slient", "result:\r\n" + (String)localObject2);
        Log.d("install_apk_slient", "err:\r\n" + str1);
        if (((String)localObject2).toLowerCase().contains("success"))
          return true;
        Matcher localMatcher = Pattern.compile("Failure\\s*\\[(.+)\\]\\s*", 10).matcher(str1);
        this._last_error = "INSTALL_UNKNOWN_ERROR";
        if (localMatcher.find())
          this._last_error = localMatcher.group(1);
      }
    }
    catch (InterruptedException localInterruptedException)
    {
      while (true)
      {
        localInterruptedException.printStackTrace();
        this._last_error = "INSTALL_INTERRUPT_EXCEPTION";
      }
    }
  }

  boolean uninstall_apk(String paramString)
  {
    this._last_error = "";
    Intent localIntent = new Intent("android.intent.action.DELETE", Uri.parse("package:" + paramString));
    localIntent.addFlags(268435456);
    try
    {
      startActivity(localIntent);
      Log.d("uninstall_apk", paramString);
      return true;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }

  boolean uninstall_apk_silent(String paramString)
  {
    this._last_error = "";
    PackageManager localPackageManager = getPackageManager();
    BufferedReader localBufferedReader2;
    Object localObject2;
    try
    {
      PackageInfo localPackageInfo = localPackageManager.getPackageInfo(paramString, 5);
      if (localPackageInfo == null)
      {
        Log.d("uninstall_apk", "package info not found:" + paramString);
        this._last_error = "UNINSTALL_PACKAGE_NOT_FOUND";
        return false;
      }
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      Log.d("uninstall_apk", "package not found:" + paramString);
      this._last_error = "UNINSTALL_PACKAGE_NAME_FOUND";
      return false;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
      this._last_error = "UNINSTALL_IO_EXCEPTION";
      return false;
      Process localProcess = Runtime.getRuntime().exec("pm uninstall -k " + paramString + "\n");
      new DataOutputStream(localProcess.getOutputStream());
      BufferedReader localBufferedReader1 = new BufferedReader(new InputStreamReader(localProcess.getInputStream()));
      localBufferedReader2 = new BufferedReader(new InputStreamReader(localProcess.getErrorStream()));
      try
      {
        localProcess.waitFor();
        String str2;
        for (localObject2 = ""; localBufferedReader1.ready(); localObject2 = str2)
          str2 = (String)localObject2 + localBufferedReader1.readLine() + "\n";
      }
      finally
      {
      }
    }
    catch (InterruptedException localInterruptedException)
    {
      localInterruptedException.printStackTrace();
      this._last_error = "UNINSTALL_INTERRUPT_EXCEPTION";
      return false;
    }
    for (String str1 = ""; localBufferedReader2.ready(); str1 = str1 + localBufferedReader2.readLine() + "\n");
    Log.d("uninstall_apk", paramString);
    Log.d("uninstall_apk", "result:\r\n" + (String)localObject2);
    Log.d("uninstall_apk", "err:\r\n" + str1);
    return true;
  }

  static class _Command
  {
    String id;
    Type op;
    String package_name;
    String path;
    String url;

    static enum Type
    {
      static
      {
        FETCH_URL = new Type("FETCH_URL", 1);
        DOWNLOAD = new Type("DOWNLOAD", 2);
        INSTALL = new Type("INSTALL", 3);
        UNINSTALL = new Type("UNINSTALL", 4);
        Type[] arrayOfType = new Type[5];
        arrayOfType[0] = REFRESH_APP_LIST;
        arrayOfType[1] = FETCH_URL;
        arrayOfType[2] = DOWNLOAD;
        arrayOfType[3] = INSTALL;
        arrayOfType[4] = UNINSTALL;
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.BuildInAppManager
 * JD-Core Version:    0.6.2
 */