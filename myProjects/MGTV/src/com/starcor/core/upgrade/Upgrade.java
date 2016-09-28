package com.starcor.core.upgrade;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.os.StatFs;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import com.starcor.core.domain.AppVersion;
import com.starcor.core.domain.Version;
import com.starcor.core.utils.AppKiller;
import com.starcor.core.utils.CacheUtils;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.App;
import com.starcor.hunan.MainActivityV2;
import com.starcor.hunan.widget.XULDialog;
import com.starcor.sccms.api.SccmsApiCheckUpdateTask.ISccmsApiCheckUpdateTaskListener;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiManager;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.ui.UITools;
import com.starcor.xul.Wrapper.XulSliderAreaWrapper;
import com.starcor.xul.XulPendingInputStream;
import com.starcor.xul.XulView;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlSerializer;

public class Upgrade
{
  public static String CAN_NOT_UPGRADE_TIP = "磁盘空间不足，将无法升级最新版本，请清除部分apk或缓存后再试。";
  private static int MIN_DISK_SPACE_DOWNLOAD = 0;
  private static int MIN_DISK_SPACE_INSTALL = 0;
  private static final String TAG = "Upgrade";
  final int MAX_RETRY_COUNTER = 3;
  private File _download_file;
  FileDownloader _downloader;
  private int _retry_counter;
  private Context context;
  int currentScrollRange;
  private boolean isForceUpgrade = false;
  int maxScrollRange;
  int scrollOffset = App.Operation(80.0F);
  private String tmpPath = "tmp.apk";
  private XULDialog upgradeDialog;
  private XulPendingInputStream upgradeStream = new XulPendingInputStream();
  private Version version;

  public static void clearCacheData()
  {
    new Thread(new Runnable()
    {
      public void run()
      {
        CacheUtils.clearPic();
        CacheUtils.clearMetaData();
      }
    }).start();
  }

  private void clickOnUpgrade()
  {
    clearCacheData();
    if (this.isForceUpgrade)
    {
      if (!isApkCanDown())
      {
        UITools.ShowCustomToast(this.context, CAN_NOT_UPGRADE_TIP);
        return;
      }
      this.upgradeDialog.dismiss();
      startUpgradeActivity(this.version.appVersion.url);
      return;
    }
    if (!isApkCanInstall())
    {
      UITools.ShowCustomToast(this.context, CAN_NOT_UPGRADE_TIP);
      return;
    }
    this.upgradeDialog.dismiss();
    installApkSilent();
  }

  private void dealUpgradeLogic()
  {
    Logger.i("Upgrade", "dealUpgradeLogic isForceUpgrade=" + this.isForceUpgrade);
    if (this.isForceUpgrade)
      showUpgradeDialog();
    while (!(this.context instanceof MainActivityV2))
      return;
    Logger.e("Upgrade", "dealUpgradeLogic");
    boolean bool = isApkCanDown();
    if (!bool)
    {
      clearCacheData();
      bool = isApkCanDown();
    }
    if (!bool)
    {
      Logger.e("Upgrade", CAN_NOT_UPGRADE_TIP);
      UITools.ShowCustomToast(this.context, CAN_NOT_UPGRADE_TIP);
      return;
    }
    Logger.e("Upgrade", "download");
    download();
  }

  private void download()
  {
    for (File localFile : this.context.getFilesDir().listFiles())
      if (localFile.isFile())
      {
        String str1 = localFile.getName();
        if (str1.toLowerCase().endsWith(".apk"))
        {
          Logger.i("Upgrade", "发现apk后缀的包: " + str1);
          try
          {
            String str2 = localFile.getAbsolutePath();
            PackageManager localPackageManager = this.context.getPackageManager();
            PackageInfo localPackageInfo = localPackageManager.getPackageArchiveInfo(str2, 1);
            if (installationStatus(localPackageManager, localPackageInfo.packageName, localPackageInfo.versionCode))
            {
              onDownloadFinished();
              return;
            }
          }
          catch (Exception localException)
          {
            localException.printStackTrace();
            Logger.i("Upgrade", "发现apk后缀的包 解析出错 再次下载" + localException, localException);
            startDownload();
            return;
          }
        }
      }
    startDownload();
  }

  public static long getAvailableInternalMemorySize()
  {
    StatFs localStatFs = new StatFs(Environment.getDataDirectory().getPath());
    return localStatFs.getBlockSize() * localStatFs.getAvailableBlocks() >> 10 >> 10;
  }

  // ERROR //
  private java.io.ByteArrayInputStream getUpgradeStream(Version paramVersion)
  {
    // Byte code:
    //   0: invokestatic 290	org/xmlpull/v1/XmlPullParserFactory:newInstance	()Lorg/xmlpull/v1/XmlPullParserFactory;
    //   3: invokevirtual 294	org/xmlpull/v1/XmlPullParserFactory:newSerializer	()Lorg/xmlpull/v1/XmlSerializer;
    //   6: astore_3
    //   7: new 296	java/io/StringWriter
    //   10: dup
    //   11: invokespecial 297	java/io/StringWriter:<init>	()V
    //   14: astore 4
    //   16: aload_3
    //   17: aload 4
    //   19: invokeinterface 303 2 0
    //   24: aload_3
    //   25: ldc_w 305
    //   28: iconst_1
    //   29: invokestatic 311	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
    //   32: invokeinterface 315 3 0
    //   37: aload_3
    //   38: aconst_null
    //   39: ldc_w 317
    //   42: invokeinterface 321 3 0
    //   47: pop
    //   48: aload_3
    //   49: aconst_null
    //   50: ldc_w 323
    //   53: invokeinterface 321 3 0
    //   58: pop
    //   59: aload_0
    //   60: aload_3
    //   61: ldc_w 325
    //   64: aload_1
    //   65: getfield 136	com/starcor/core/domain/Version:appVersion	Lcom/starcor/core/domain/AppVersion;
    //   68: getfield 328	com/starcor/core/domain/AppVersion:ver	Ljava/lang/String;
    //   71: invokespecial 332	com/starcor/core/upgrade/Upgrade:writeNodeValue	(Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;Ljava/lang/String;)V
    //   74: new 334	java/io/BufferedReader
    //   77: dup
    //   78: new 336	java/io/StringReader
    //   81: dup
    //   82: aload_1
    //   83: getfield 136	com/starcor/core/domain/Version:appVersion	Lcom/starcor/core/domain/AppVersion;
    //   86: getfield 339	com/starcor/core/domain/AppVersion:desc	Ljava/lang/String;
    //   89: invokespecial 340	java/io/StringReader:<init>	(Ljava/lang/String;)V
    //   92: invokespecial 343	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   95: astore 7
    //   97: new 345	java/util/ArrayList
    //   100: dup
    //   101: invokespecial 346	java/util/ArrayList:<init>	()V
    //   104: astore 8
    //   106: iconst_0
    //   107: istore 9
    //   109: aconst_null
    //   110: astore 10
    //   112: aload 7
    //   114: invokevirtual 349	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   117: astore 24
    //   119: aload 24
    //   121: ifnull +171 -> 292
    //   124: ldc_w 351
    //   127: aload 24
    //   129: invokevirtual 355	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   132: ifeq +9 -> 141
    //   135: iconst_0
    //   136: istore 9
    //   138: goto -26 -> 112
    //   141: iload 9
    //   143: ifne +38 -> 181
    //   146: new 357	com/starcor/core/upgrade/Upgrade$DescribeInfo
    //   149: dup
    //   150: aload_0
    //   151: invokespecial 359	com/starcor/core/upgrade/Upgrade$DescribeInfo:<init>	(Lcom/starcor/core/upgrade/Upgrade;)V
    //   154: astore 25
    //   156: aload 25
    //   158: aload 24
    //   160: putfield 362	com/starcor/core/upgrade/Upgrade$DescribeInfo:title	Ljava/lang/String;
    //   163: aload 8
    //   165: aload 25
    //   167: invokevirtual 365	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   170: pop
    //   171: iinc 9 1
    //   174: aload 25
    //   176: astore 10
    //   178: goto -66 -> 112
    //   181: aload 24
    //   183: bipush 32
    //   185: invokevirtual 369	java/lang/String:indexOf	(I)I
    //   188: istore 27
    //   190: ldc_w 351
    //   193: astore 28
    //   195: iload 27
    //   197: ifle +88 -> 285
    //   200: aload 24
    //   202: iconst_0
    //   203: iload 27
    //   205: invokevirtual 373	java/lang/String:substring	(II)Ljava/lang/String;
    //   208: astore 29
    //   210: aload 24
    //   212: iload 27
    //   214: iconst_1
    //   215: iadd
    //   216: invokevirtual 376	java/lang/String:substring	(I)Ljava/lang/String;
    //   219: astore 28
    //   221: new 357	com/starcor/core/upgrade/Upgrade$DescribeInfo
    //   224: dup
    //   225: aload_0
    //   226: invokespecial 359	com/starcor/core/upgrade/Upgrade$DescribeInfo:<init>	(Lcom/starcor/core/upgrade/Upgrade;)V
    //   229: astore 30
    //   231: aload 30
    //   233: invokevirtual 380	java/lang/Object:getClass	()Ljava/lang/Class;
    //   236: pop
    //   237: new 382	com/starcor/core/upgrade/Upgrade$DescribeInfo$SubDescribe
    //   240: dup
    //   241: aload 30
    //   243: invokespecial 385	com/starcor/core/upgrade/Upgrade$DescribeInfo$SubDescribe:<init>	(Lcom/starcor/core/upgrade/Upgrade$DescribeInfo;)V
    //   246: astore 32
    //   248: aload 32
    //   250: aload 29
    //   252: putfield 386	com/starcor/core/upgrade/Upgrade$DescribeInfo$SubDescribe:title	Ljava/lang/String;
    //   255: aload 32
    //   257: aload 28
    //   259: putfield 389	com/starcor/core/upgrade/Upgrade$DescribeInfo$SubDescribe:content	Ljava/lang/String;
    //   262: aload 10
    //   264: ifnull +14 -> 278
    //   267: aload 10
    //   269: getfield 393	com/starcor/core/upgrade/Upgrade$DescribeInfo:subDescribes	Ljava/util/ArrayList;
    //   272: aload 32
    //   274: invokevirtual 365	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   277: pop
    //   278: aload 10
    //   280: astore 25
    //   282: goto -111 -> 171
    //   285: aload 24
    //   287: astore 29
    //   289: goto -68 -> 221
    //   292: aload 10
    //   294: pop
    //   295: aload 8
    //   297: invokevirtual 397	java/util/ArrayList:iterator	()Ljava/util/Iterator;
    //   300: astore 13
    //   302: aload 13
    //   304: invokeinterface 402 1 0
    //   309: ifeq +156 -> 465
    //   312: aload 13
    //   314: invokeinterface 406 1 0
    //   319: checkcast 357	com/starcor/core/upgrade/Upgrade$DescribeInfo
    //   322: astore 17
    //   324: aload_3
    //   325: aconst_null
    //   326: ldc_w 408
    //   329: invokeinterface 321 3 0
    //   334: pop
    //   335: aload_0
    //   336: aload_3
    //   337: ldc_w 409
    //   340: aload 17
    //   342: getfield 362	com/starcor/core/upgrade/Upgrade$DescribeInfo:title	Ljava/lang/String;
    //   345: invokespecial 332	com/starcor/core/upgrade/Upgrade:writeNodeValue	(Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;Ljava/lang/String;)V
    //   348: aload 17
    //   350: getfield 393	com/starcor/core/upgrade/Upgrade$DescribeInfo:subDescribes	Ljava/util/ArrayList;
    //   353: invokevirtual 397	java/util/ArrayList:iterator	()Ljava/util/Iterator;
    //   356: astore 19
    //   358: aload 19
    //   360: invokeinterface 402 1 0
    //   365: ifeq +86 -> 451
    //   368: aload 19
    //   370: invokeinterface 406 1 0
    //   375: checkcast 382	com/starcor/core/upgrade/Upgrade$DescribeInfo$SubDescribe
    //   378: astore 21
    //   380: aload_3
    //   381: aconst_null
    //   382: ldc_w 411
    //   385: invokeinterface 321 3 0
    //   390: pop
    //   391: aload_0
    //   392: aload_3
    //   393: ldc_w 413
    //   396: aload 21
    //   398: getfield 386	com/starcor/core/upgrade/Upgrade$DescribeInfo$SubDescribe:title	Ljava/lang/String;
    //   401: invokespecial 332	com/starcor/core/upgrade/Upgrade:writeNodeValue	(Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;Ljava/lang/String;)V
    //   404: aload_0
    //   405: aload_3
    //   406: ldc_w 415
    //   409: aload 21
    //   411: getfield 389	com/starcor/core/upgrade/Upgrade$DescribeInfo$SubDescribe:content	Ljava/lang/String;
    //   414: invokespecial 332	com/starcor/core/upgrade/Upgrade:writeNodeValue	(Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;Ljava/lang/String;)V
    //   417: aload_3
    //   418: aconst_null
    //   419: ldc_w 411
    //   422: invokeinterface 418 3 0
    //   427: pop
    //   428: goto -70 -> 358
    //   431: astore_2
    //   432: aload_2
    //   433: invokevirtual 249	java/lang/Exception:printStackTrace	()V
    //   436: aconst_null
    //   437: areturn
    //   438: astore 11
    //   440: aload 10
    //   442: pop
    //   443: aload 11
    //   445: invokevirtual 419	java/io/IOException:printStackTrace	()V
    //   448: goto -153 -> 295
    //   451: aload_3
    //   452: aconst_null
    //   453: ldc_w 408
    //   456: invokeinterface 418 3 0
    //   461: pop
    //   462: goto -160 -> 302
    //   465: aload_3
    //   466: aconst_null
    //   467: ldc_w 323
    //   470: invokeinterface 418 3 0
    //   475: pop
    //   476: aload_3
    //   477: aconst_null
    //   478: ldc_w 317
    //   481: invokeinterface 418 3 0
    //   486: pop
    //   487: aload_3
    //   488: invokeinterface 422 1 0
    //   493: aload_3
    //   494: invokeinterface 425 1 0
    //   499: ldc 13
    //   501: aload 4
    //   503: invokevirtual 426	java/io/StringWriter:toString	()Ljava/lang/String;
    //   506: invokestatic 173	com/starcor/core/utils/Logger:i	(Ljava/lang/String;Ljava/lang/String;)V
    //   509: new 428	java/io/ByteArrayInputStream
    //   512: dup
    //   513: aload 4
    //   515: invokevirtual 426	java/io/StringWriter:toString	()Ljava/lang/String;
    //   518: ldc_w 305
    //   521: invokevirtual 432	java/lang/String:getBytes	(Ljava/lang/String;)[B
    //   524: invokespecial 435	java/io/ByteArrayInputStream:<init>	([B)V
    //   527: astore 16
    //   529: aload 16
    //   531: areturn
    //   532: astore 11
    //   534: goto -91 -> 443
    //
    // Exception table:
    //   from	to	target	type
    //   0	106	431	java/lang/Exception
    //   112	119	431	java/lang/Exception
    //   124	135	431	java/lang/Exception
    //   146	156	431	java/lang/Exception
    //   156	171	431	java/lang/Exception
    //   181	190	431	java/lang/Exception
    //   200	221	431	java/lang/Exception
    //   221	262	431	java/lang/Exception
    //   267	278	431	java/lang/Exception
    //   295	302	431	java/lang/Exception
    //   302	358	431	java/lang/Exception
    //   358	428	431	java/lang/Exception
    //   443	448	431	java/lang/Exception
    //   451	462	431	java/lang/Exception
    //   465	529	431	java/lang/Exception
    //   112	119	438	java/io/IOException
    //   124	135	438	java/io/IOException
    //   146	156	438	java/io/IOException
    //   181	190	438	java/io/IOException
    //   200	221	438	java/io/IOException
    //   221	262	438	java/io/IOException
    //   267	278	438	java/io/IOException
    //   156	171	532	java/io/IOException
  }

  private boolean installationStatus(PackageManager paramPackageManager, String paramString, int paramInt)
  {
    Iterator localIterator = paramPackageManager.getInstalledPackages(8192).iterator();
    while (localIterator.hasNext())
    {
      PackageInfo localPackageInfo = (PackageInfo)localIterator.next();
      String str = localPackageInfo.packageName;
      int i = localPackageInfo.versionCode;
      if (paramString.endsWith(str))
      {
        if (paramInt == i)
        {
          Log.i("Upgrade", "已经安装，不用更新，可以卸载该应用");
          return false;
        }
        if (paramInt > i)
        {
          Log.i("Upgrade", "已经安装，有更新");
          return true;
        }
      }
    }
    Log.i("Upgrade", "未安装该应用，可以安装");
    return true;
  }

  private static boolean isApkCanDown()
  {
    int i = (int)getAvailableInternalMemorySize();
    Logger.i("Upgrade", "free Size=" + i + "MB");
    return MIN_DISK_SPACE_DOWNLOAD < i;
  }

  public static boolean isApkCanInstall()
  {
    int i = (int)getAvailableInternalMemorySize();
    return MIN_DISK_SPACE_INSTALL < i;
  }

  private void onDownloadFinished()
  {
    Logger.d("Upgrade", "onDownloadFinished..");
    showUpgradeDialog();
  }

  private void startDownload()
  {
    this._retry_counter = 0;
    String str1 = this.version.appVersion.url.trim();
    if (TextUtils.isEmpty(str1))
      return;
    while (true)
    {
      try
      {
        String str2 = new HttpGet(str1).getURI().getPath();
        MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
        localMessageDigest.update(str2.getBytes());
        byte[] arrayOfByte = localMessageDigest.digest();
        String str3 = "";
        int i = arrayOfByte.length;
        int j = 0;
        if (j < i)
        {
          int k = arrayOfByte[j];
          str3 = str3 + Integer.toHexString(k & 0xFF);
          j++;
          continue;
        }
        File localFile1 = this.context.getFilesDir();
        File[] arrayOfFile = localFile1.listFiles();
        int m = arrayOfFile.length;
        int n = 0;
        if (n < m)
        {
          File localFile2 = arrayOfFile[n];
          boolean bool = localFile2.delete();
          StringBuilder localStringBuilder = new StringBuilder().append("delete ").append(localFile2.getName());
          if (!bool)
            break label321;
          str4 = " success";
          Logger.d("Upgrade", str4);
          n++;
          continue;
        }
        File localFile3 = new File(localFile1, str3);
        this._download_file = localFile3;
        this._downloader = new FileDownloader(this.context);
        this._downloader.start(str1, this._download_file, true, new Handler(new Handler.Callback()
        {
          public boolean handleMessage(Message paramAnonymousMessage)
          {
            Upgrade.this.onHttpMessage(paramAnonymousMessage);
            return false;
          }
        }));
        return;
      }
      catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
      {
        Logger.i("Upgrade", "内部错误(无法创建临时文件), 更新失败!");
        localNoSuchAlgorithmException.printStackTrace();
        return;
      }
      catch (Exception localException)
      {
        Logger.i("Upgrade", "内部错误, 更新失败!");
        localException.printStackTrace();
        return;
      }
      label321: String str4 = "fail";
    }
  }

  private boolean startUpgradeActivity(String paramString)
  {
    if (TextUtils.isEmpty(paramString))
    {
      Logger.i("Upgrade", "startUpgradeActivity url is empty!");
      return false;
    }
    Logger.i("Upgrade", "startUpgradeActivity url:" + paramString);
    try
    {
      Intent localIntent = new Intent(this.context, UpgradeActivity.class);
      Logger.i("Upgrade", "startUpgradeActivity UpgradeActivity.class");
      localIntent.putExtra("url", new String[] { paramString });
      localIntent.setFlags(8388608);
      localIntent.putExtra("upgrade_silently", true);
      localIntent.putExtra("error_start_package", this.context.getPackageName());
      this.context.startActivity(localIntent);
      return true;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      Logger.e("Upgrade", "startUpgradeActivity Exception");
    }
    return false;
  }

  private void writeNodeValue(XmlSerializer paramXmlSerializer, String paramString1, String paramString2)
    throws IOException
  {
    if (paramString2 == null)
      paramString2 = "";
    paramXmlSerializer.startTag(null, paramString1);
    paramXmlSerializer.text(paramString2);
    paramXmlSerializer.endTag(null, paramString1);
  }

  public void checkUpgrade(Context paramContext)
  {
    this.context = paramContext;
    ServerApiManager.i().APICheckUpdate(new SccmsApiCheckUpdateTask.ISccmsApiCheckUpdateTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        Logger.i("Upgrade", "请求检查版本升级接口失败");
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, Version paramAnonymousVersion)
      {
        if ((paramAnonymousVersion == null) || (paramAnonymousVersion.appVersion == null) || (paramAnonymousVersion.appVersion.state != 0))
        {
          Logger.i("Upgrade", "请求检查版本升级接口数据有误");
          return;
        }
        Upgrade.access$002(Upgrade.this, paramAnonymousVersion);
        Upgrade.this.upgradeStream.setBaseStream(Upgrade.this.getUpgradeStream(Upgrade.this.version));
        if ("force".equals(Upgrade.this.version.appVersion.type))
          Upgrade.access$302(Upgrade.this, true);
        Upgrade.this.dealUpgradeLogic();
      }
    });
  }

  public boolean installApkSilent()
  {
    clearCacheData();
    Uri localUri = Uri.fromFile(new File(this.context.getFilesDir() + File.separator + this.tmpPath));
    Logger.d("upgradeActivity", "install_apk_silent:" + this.context.getFilesDir() + File.separator + this.tmpPath);
    Intent localIntent = new Intent("android.intent.action.VIEW");
    localIntent.setDataAndType(localUri, "application/vnd.android.package-archive");
    localIntent.addFlags(268435456);
    this.context.startActivity(localIntent);
    return false;
  }

  protected void onHttpMessage(Message paramMessage)
  {
    switch (paramMessage.what)
    {
    default:
      this._downloader.stop();
    case 1:
    case 4:
    case 5:
      return;
    case 3:
      onDownloadFinished();
      return;
    case 2:
    }
    this._retry_counter = (1 + this._retry_counter);
    if (this._retry_counter < 3)
    {
      Logger.i("Upgrade", "文件下载失败!! 正在重试...");
      this._downloader.resume();
      return;
    }
    Logger.i("Upgrade", "更新失败 - 文件下载错误!");
    this._downloader.stop();
  }

  public void showUpgradeDialog()
  {
    Logger.i("Upgrade", "showUpgradeDialog");
    JSONObject localJSONObject = new JSONObject();
    try
    {
      localJSONObject.put("type", this.version.appVersion.type);
      this.upgradeDialog = new XULDialog(this.context, "Upgrade", localJSONObject)
      {
        public boolean dispatchKeyEvent(KeyEvent paramAnonymousKeyEvent)
        {
          if (paramAnonymousKeyEvent.getAction() == 0)
          {
            XulView localXulView = xulFindViewById("desc_area");
            if (localXulView != null)
            {
              XulSliderAreaWrapper localXulSliderAreaWrapper = XulSliderAreaWrapper.fromXulView(localXulView);
              Upgrade.this.maxScrollRange = localXulSliderAreaWrapper.getScrollRange();
              Log.i("onKeyDonw", "range" + Upgrade.this.maxScrollRange + ",event.getKeyCode()=" + paramAnonymousKeyEvent.getKeyCode());
              if (paramAnonymousKeyEvent.getKeyCode() == 19)
              {
                Upgrade localUpgrade2 = Upgrade.this;
                localUpgrade2.currentScrollRange -= Upgrade.this.scrollOffset;
                if (Upgrade.this.currentScrollRange < 0)
                  Upgrade.this.currentScrollRange = 0;
                localXulSliderAreaWrapper.scrollTo(Upgrade.this.currentScrollRange);
                localXulSliderAreaWrapper.activateScrollBar();
                return true;
              }
              if (paramAnonymousKeyEvent.getKeyCode() == 20)
              {
                Upgrade localUpgrade1 = Upgrade.this;
                localUpgrade1.currentScrollRange += Upgrade.this.scrollOffset;
                if (Upgrade.this.currentScrollRange > Upgrade.this.maxScrollRange)
                  Upgrade.this.currentScrollRange = Upgrade.this.maxScrollRange;
                localXulSliderAreaWrapper.scrollTo(Upgrade.this.currentScrollRange);
                localXulSliderAreaWrapper.activateScrollBar();
                return true;
              }
              if (paramAnonymousKeyEvent.getKeyCode() == 4)
              {
                if (!Upgrade.this.isForceUpgrade)
                  break label273;
                Logger.i("Upgrade", "_ExitDialog->xulDoAction->killApp");
                Upgrade.this.upgradeDialog.dismiss();
                AppKiller.getInstance().killApp();
              }
            }
          }
          while (true)
          {
            return super.dispatchKeyEvent(paramAnonymousKeyEvent);
            label273: dismiss();
          }
        }

        protected boolean xulDoAction(XulView paramAnonymousXulView, String paramAnonymousString1, String paramAnonymousString2, String paramAnonymousString3, Object paramAnonymousObject)
        {
          if (paramAnonymousString2.equals("immediately_upgrade"))
            Upgrade.this.clickOnUpgrade();
          while (true)
          {
            return super.xulDoAction(paramAnonymousXulView, paramAnonymousString1, paramAnonymousString2, paramAnonymousString3, paramAnonymousObject);
            if (paramAnonymousString2.equals("next_upgrade"))
              Upgrade.this.upgradeDialog.dismiss();
          }
        }

        protected InputStream xulGetAppData(String paramAnonymousString)
        {
          if ("api/get_upgrade_data".equals(paramAnonymousString))
          {
            if (Upgrade.this.upgradeStream.isReady())
              return Upgrade.this.upgradeStream;
            return null;
          }
          return super.xulGetAppData(paramAnonymousString);
        }

        protected void xulOnRenderIsReady()
        {
          super.xulOnRenderIsReady();
        }
      };
      this.upgradeDialog.setCancelable(false);
      this.upgradeDialog.show();
      return;
    }
    catch (JSONException localJSONException)
    {
      while (true)
        localJSONException.printStackTrace();
    }
  }

  class DescribeInfo
  {
    ArrayList<SubDescribe> subDescribes = new ArrayList();
    String title;

    DescribeInfo()
    {
    }

    class SubDescribe
    {
      String content;
      String title;

      SubDescribe()
      {
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.upgrade.Upgrade
 * JD-Core Version:    0.6.2
 */