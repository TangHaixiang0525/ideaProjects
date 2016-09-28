package com.starcor.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppManagerService extends Service
{
  AppManageService _manageService = new AppManageService(null);

  public IBinder onBind(Intent paramIntent)
  {
    return this._manageService.asBinder();
  }

  private class AppManageService extends IAppManager.Stub
  {
    String _last_error = "";

    static
    {
      if (!AppManagerService.class.desiredAssertionStatus());
      for (boolean bool = true; ; bool = false)
      {
        $assertionsDisabled = bool;
        return;
      }
    }

    private AppManageService()
    {
    }

    int execCommand(String paramString)
    {
      Process localProcess = null;
      try
      {
        localProcess = Runtime.getRuntime().exec(paramString);
        localProcess.waitFor();
        int i = localProcess.exitValue();
        if (localProcess != null);
        try
        {
          localProcess.destroy();
          return i;
        }
        catch (Exception localException4)
        {
          localException4.printStackTrace();
          return i;
        }
      }
      catch (IOException localIOException)
      {
        localIOException.printStackTrace();
        if (localProcess != null);
        try
        {
          localProcess.destroy();
          return -1;
        }
        catch (Exception localException3)
        {
          while (true)
            localException3.printStackTrace();
        }
      }
      catch (InterruptedException localInterruptedException)
      {
        while (true)
        {
          localInterruptedException.printStackTrace();
          if (localProcess != null)
            try
            {
              localProcess.destroy();
            }
            catch (Exception localException2)
            {
              localException2.printStackTrace();
            }
        }
      }
      finally
      {
        if (localProcess == null);
      }
      try
      {
        localProcess.destroy();
        throw localObject;
      }
      catch (Exception localException1)
      {
        while (true)
          localException1.printStackTrace();
      }
    }

    public int forceStopApp(String paramString)
      throws RemoteException
    {
      return execCommand("am force-stop " + paramString);
    }

    public ApplicationInfo getAppInfo(String paramString)
      throws RemoteException
    {
      return ApplicationInfo.fromPkgName(paramString);
    }

    public String[] getAppList()
      throws RemoteException
    {
      PackageManager localPackageManager = AppManagerService.this.getApplicationContext().getPackageManager();
      assert (localPackageManager != null);
      Intent localIntent = new Intent("android.intent.action.MAIN");
      localIntent.addCategory("android.intent.category.LAUNCHER");
      List localList = localPackageManager.queryIntentActivities(localIntent, 0);
      if ((localList == null) || (localList.isEmpty()))
        return null;
      HashSet localHashSet = new HashSet();
      for (int i = 0; i < localList.size(); i++)
        localHashSet.add(((ResolveInfo)localList.get(i)).activityInfo.packageName);
      String[] arrayOfString = new String[localHashSet.size()];
      localHashSet.toArray(arrayOfString);
      return arrayOfString;
    }

    public int installApp(String paramString)
      throws RemoteException
    {
      if (install_apk(paramString))
        return 0;
      return -1;
    }

    boolean install_apk(String paramString)
      throws RemoteException
    {
      this._last_error = "";
      Intent localIntent = new Intent("android.intent.action.VIEW");
      localIntent.addFlags(268435456);
      localIntent.setDataAndType(Uri.parse("file://" + paramString), "application/vnd.android.package-archive");
      try
      {
        AppManagerService.this.startActivity(localIntent);
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
      throws RemoteException
    {
      this._last_error = "";
      PackageInfo localPackageInfo = AppManagerService.this.getPackageManager().getPackageArchiveInfo(paramString, 5);
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

    public int removeApp(String paramString)
      throws RemoteException
    {
      if (uninstall_apk(paramString))
        return 0;
      return -1;
    }

    public int resetApp(String paramString)
      throws RemoteException
    {
      return execCommand("pm clear " + paramString);
    }

    public int setEventListener(IAppEventListener paramIAppEventListener)
      throws RemoteException
    {
      return 0;
    }

    public int startApp(String paramString)
      throws RemoteException
    {
      Intent localIntent = AppManagerService.this.getPackageManager().getLaunchIntentForPackage(paramString);
      if (localIntent == null)
        return -1;
      localIntent.addFlags(268435456);
      try
      {
        AppManagerService.this.startActivity(localIntent);
        return 0;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
      return -1;
    }

    public int stopApp(String paramString)
      throws RemoteException
    {
      return execCommand("am kill " + paramString);
    }

    boolean uninstall_apk(String paramString)
      throws RemoteException
    {
      this._last_error = "";
      Intent localIntent = new Intent("android.intent.action.DELETE", Uri.parse("package:" + paramString));
      localIntent.addFlags(268435456);
      try
      {
        AppManagerService.this.startActivity(localIntent);
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
      throws RemoteException
    {
      this._last_error = "";
      PackageManager localPackageManager = AppManagerService.this.getPackageManager();
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
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.service.AppManagerService
 * JD-Core Version:    0.6.2
 */