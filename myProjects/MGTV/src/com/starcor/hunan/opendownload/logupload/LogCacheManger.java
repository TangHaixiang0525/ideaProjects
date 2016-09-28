package com.starcor.hunan.opendownload.logupload;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Log;
import com.starcor.config.AppFuncCfg;
import com.starcor.config.DeviceInfo;
import com.starcor.core.domain.UserInfo;
import com.starcor.core.logic.GlobalEnv;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.App;
import com.starcor.hunan.service.apidetect.task.ApiProcessDetectTask;
import com.starcor.hunan.service.apidetect.task.ApiProcessDetectTask.APiProcessTypes;
import com.starcor.hunan.service.apidetect.task.ApiProcessDetectTask.SavingWays;
import com.starcor.hunan.service.apidetect.task.ApiProcessDetectTask.TimeshiftProcessCallback;
import com.starcor.hunan.service.apidetect.task.ApiProcessDetectTask.UserCenterCallback;
import com.starcor.hunan.service.apidetect.task.ApiProcessDetectTask.VodProcessCallback;
import com.starcor.service.LogUploadService;
import com.starcor.xul.XulUtils;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONException;
import org.json.JSONObject;

public class LogCacheManger
{
  public static String IGNORE = "IGNORE";
  public static String logPath = App.getInstance().getDir("terminal_status_logs", 0).toString();
  private static LogCacheManger manger;
  private final int DEFAULT_CACHE_SIZE = 1048576;
  private String TAG = LogCacheManger.class.getSimpleName();
  private List<CompressString> appCache;
  private int appTotalSize;
  private boolean busy = false;
  private String commonPattern = "yyyyMMdd";
  private String errorCode = "";
  private ErrorType errorType;
  private String exactPattern = "yyyyMMddHHmmss";
  private boolean exitFlag;
  private boolean isWritingFile = false;
  private JSONObject object;
  private long oldTime = System.currentTimeMillis();
  private List<CompressString> systemCache;
  private int systemTotalSize;
  private String timeShiftProcessPath = "/data/data/com.starcor.hunan/cache/timeshift_process_result.txt";
  private boolean timeshiftProcessFinish = false;
  private boolean userCenterProcessFinish = false;
  private String usercenterProcessPath = "/data/data/com.starcor.hunan/cache/usercenter_process_result.txt";
  private boolean vodProcessFinish = false;
  private String vodProcessPath = "/data/data/com.starcor.hunan/cache/vod_process_result.txt";
  private Thread workThread;
  private boolean writeFile = false;
  private WriteSystemLogsThread writeSystemLogsThread;
  private Runnable writeUserInfoApi = new Runnable()
  {
    private JSONObject getPropertiesJson()
    {
      JSONObject localJSONObject = new JSONObject();
      Iterator localIterator = System.getProperties().entrySet().iterator();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        Object localObject1 = localEntry.getKey();
        Object localObject2 = localEntry.getValue();
        try
        {
          localJSONObject.put(String.valueOf(localObject1), localObject2);
        }
        catch (JSONException localJSONException)
        {
          localJSONException.printStackTrace();
        }
      }
      return localJSONObject;
    }

    private String getTotalMemory(Context paramAnonymousContext)
    {
      long l = 0L;
      try
      {
        BufferedReader localBufferedReader = new BufferedReader(new FileReader("/proc/meminfo"), 8192);
        l = 1024 * Integer.valueOf(localBufferedReader.readLine().split("\\s+")[1]).intValue();
        localBufferedReader.close();
        label52: return Formatter.formatFileSize(paramAnonymousContext, l);
      }
      catch (IOException localIOException)
      {
        break label52;
      }
    }

    private String getVersion(Context paramAnonymousContext)
    {
      PackageManager localPackageManager = paramAnonymousContext.getPackageManager();
      try
      {
        String str = localPackageManager.getPackageInfo(paramAnonymousContext.getPackageName(), 0).versionName;
        return str;
      }
      catch (PackageManager.NameNotFoundException localNameNotFoundException)
      {
        localNameNotFoundException.printStackTrace();
      }
      return "0";
    }

    public String getCpuInfo()
    {
      String[] arrayOfString1 = { "", "" };
      try
      {
        BufferedReader localBufferedReader = new BufferedReader(new FileReader("/proc/cpuinfo"), 8192);
        String[] arrayOfString2 = localBufferedReader.readLine().split("\\s+");
        for (int i = 2; i < arrayOfString2.length; i++)
          arrayOfString1[0] = (arrayOfString1[0] + arrayOfString2[i] + " ");
        String[] arrayOfString3 = localBufferedReader.readLine().split("\\s+");
        arrayOfString1[1] = (arrayOfString1[1] + arrayOfString3[2]);
        localBufferedReader.close();
        label136: return arrayOfString1[0];
      }
      catch (IOException localIOException)
      {
        break label136;
      }
    }

    public String getFS()
    {
      StringBuffer localStringBuffer;
      try
      {
        BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("df").getInputStream()));
        localStringBuffer = new StringBuffer();
        while (true)
        {
          String str1 = localBufferedReader.readLine();
          if (str1 == null)
            break;
          localStringBuffer.append(str1);
        }
      }
      catch (IOException localIOException)
      {
        localIOException.printStackTrace();
        return "";
      }
      String str2 = localStringBuffer.toString();
      return str2;
    }

    public String getSingInfo(Context paramAnonymousContext)
    {
      PackageManager localPackageManager = paramAnonymousContext.getPackageManager();
      try
      {
        String str = parseSignature(localPackageManager.getPackageInfo(paramAnonymousContext.getPackageName(), 64).signatures[0].toByteArray());
        return str;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
      return null;
    }

    public String parseSignature(byte[] paramAnonymousArrayOfByte)
    {
      try
      {
        X509Certificate localX509Certificate = (X509Certificate)CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(paramAnonymousArrayOfByte));
        String str1 = localX509Certificate.getPublicKey().toString();
        String str2 = localX509Certificate.getSerialNumber().toString();
        String str3 = "signName:" + localX509Certificate.getSigAlgName() + " pubKey:" + str1 + " signNumber:" + str2 + " subjectDN:" + localX509Certificate.getSubjectDN().toString();
        return str3;
      }
      catch (CertificateException localCertificateException)
      {
        localCertificateException.printStackTrace();
      }
      return "";
    }

    public void run()
    {
      String str1 = DeviceInfo.getMac();
      String str2 = GlobalEnv.getInstance().getAAANetIp();
      String str3 = GlobalLogic.getInstance().getDeviceId();
      String str4 = getVersion(App.getAppContext());
      String str5 = getCpuInfo();
      String str6 = getTotalMemory(App.getAppContext());
      String str7 = getFS();
      String str8 = GlobalEnv.getInstance().getAAALicense();
      String str9 = DeviceInfo.getMGTVVersion();
      String str10 = getSingInfo(App.getAppContext());
      String str11 = "";
      String str12 = "";
      String str13 = "";
      String str14 = "";
      String str15 = "";
      if (GlobalLogic.getInstance().isUserLogined())
      {
        UserInfo localUserInfo = GlobalLogic.getInstance().getUserInfo();
        str11 = localUserInfo.web_token;
        str12 = localUserInfo.account;
        str13 = localUserInfo.vip_id;
        str14 = localUserInfo.vip_end_date;
        str15 = localUserInfo.rtype;
      }
      try
      {
        LogCacheManger.access$002(LogCacheManger.this, new JSONObject());
        JSONObject localJSONObject1 = new JSONObject();
        JSONObject localJSONObject2 = new JSONObject();
        JSONObject localJSONObject3 = new JSONObject();
        LogCacheManger.this.object.put("system", localJSONObject1);
        LogCacheManger.this.object.put("app", localJSONObject2);
        LogCacheManger.this.object.put("user", localJSONObject3);
        localJSONObject1.put("mac", str1);
        localJSONObject1.put("ip", str2);
        localJSONObject1.put("deviceID", str3);
        localJSONObject1.put("version", str4);
        localJSONObject1.put("cpu", str5);
        localJSONObject1.put("mem", str6);
        localJSONObject1.put("fs", str7);
        localJSONObject1.put("props", getPropertiesJson());
        localJSONObject2.put("license", str8);
        localJSONObject2.put("version", str9);
        localJSONObject2.put("sign", str10);
        localJSONObject3.put("ticket", str11);
        localJSONObject3.put("account", str12);
        localJSONObject3.put("vip_id", str13);
        localJSONObject3.put("vip_end_date", str14);
        localJSONObject3.put("account_type", str15);
        return;
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
      }
    }
  };

  private String MD5(String paramString)
  {
    String str = "";
    try
    {
      MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
      localMessageDigest.update(paramString.getBytes());
      byte[] arrayOfByte = localMessageDigest.digest();
      StringBuffer localStringBuffer = new StringBuffer("");
      for (int i = 0; i < arrayOfByte.length; i++)
      {
        int j = arrayOfByte[i];
        if (j < 0)
          j += 256;
        if (j < 16)
          localStringBuffer.append("0");
        localStringBuffer.append(Integer.toHexString(j));
      }
      str = localStringBuffer.toString();
      Logger.i("md5--->" + str);
      return str;
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
    }
    return str;
  }

  private int addLog2Cache(List<CompressString> paramList, int paramInt, String paramString)
  {
    byte[] arrayOfByte;
    int i;
    try
    {
      arrayOfByte = paramString.getBytes("utf-8");
      i = arrayOfByte.length;
      if (i >= 1048576)
        return paramInt;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      localUnsupportedEncodingException.printStackTrace();
      return paramInt;
    }
    if (paramInt + i > 1048576)
    {
      int j = 0;
      while (j < i)
        if (paramList.size() > 0)
        {
          CompressString localCompressString = (CompressString)paramList.remove(0);
          if (localCompressString != null)
            j += localCompressString.getLength();
        }
      paramInt -= j;
    }
    paramList.add(new CompressString(arrayOfByte));
    return paramInt + i;
  }

  private void apiProcessDetect()
  {
    ApiProcessDetectTask localApiProcessDetectTask = new ApiProcessDetectTask();
    localApiProcessDetectTask.init();
    ApiProcessDetectTask.APiProcessTypes[] arrayOfAPiProcessTypes = new ApiProcessDetectTask.APiProcessTypes[1];
    arrayOfAPiProcessTypes[0] = ApiProcessDetectTask.APiProcessTypes.API_PROCESS_ALL;
    localApiProcessDetectTask.setCurrentProcessType(arrayOfAPiProcessTypes);
    localApiProcessDetectTask.setVodProcessCallback(new ApiProcessDetectTask.VodProcessCallback()
    {
      public void finish()
      {
        Logger.i(LogCacheManger.this.TAG, "vod process finishes!");
        LogCacheManger.access$202(LogCacheManger.this, true);
        LogCacheManger.this.isNext2ZipFile();
      }
    });
    localApiProcessDetectTask.setTimeshiftProcessCallback(new ApiProcessDetectTask.TimeshiftProcessCallback()
    {
      public void finish()
      {
        Logger.i(LogCacheManger.this.TAG, "timeshift process finishes!");
        LogCacheManger.access$402(LogCacheManger.this, true);
        LogCacheManger.this.isNext2ZipFile();
      }
    });
    localApiProcessDetectTask.setUserCenterProcessCallback(new ApiProcessDetectTask.UserCenterCallback()
    {
      public void finish()
      {
        Logger.i(LogCacheManger.this.TAG, "usercenter process finishes!");
        LogCacheManger.access$502(LogCacheManger.this, true);
        LogCacheManger.this.isNext2ZipFile();
      }
    });
    localApiProcessDetectTask.setSavingWay(ApiProcessDetectTask.SavingWays.SAVING_WAY_INTERAL_STORAGE);
    localApiProcessDetectTask.runTasks();
  }

  private void delApiPorcessDetectFiles()
  {
    Logger.i(this.TAG, "删除自检api文件");
    delFile(this.vodProcessPath);
    delFile(this.timeShiftProcessPath);
    delFile(this.usercenterProcessPath);
  }

  private void delFile(String paramString)
  {
    File localFile = new File(paramString);
    if (localFile.exists())
      localFile.delete();
  }

  public static LogCacheManger getInstance()
  {
    if (manger == null)
      manger = new LogCacheManger();
    return manger;
  }

  private String getNowDate(String paramString)
  {
    Date localDate = new Date();
    return new SimpleDateFormat(paramString).format(localDate);
  }

  private boolean isCanWrite(String paramString)
  {
    Logger.i(this.TAG, "传入的错误码" + paramString);
    if (IGNORE.equals(paramString))
    {
      Logger.i(this.TAG, "此错误为忽略");
      return true;
    }
    if (TextUtils.isEmpty(paramString))
    {
      Logger.i(this.TAG, "请求码为空");
      return true;
    }
    long l = (System.currentTimeMillis() - this.oldTime) / 60000L;
    Logger.i(this.TAG, "interval " + l);
    if (l >= 20L)
    {
      Logger.i(this.TAG, "间隔大于预设值");
      return true;
    }
    if (paramString.equals(this.errorCode))
    {
      Logger.i(this.TAG, "2次错误码相同");
      return false;
    }
    Logger.i(this.TAG, "2次错误码不同");
    return true;
  }

  private void isNext2ZipFile()
  {
    if ((this.vodProcessFinish) && (this.timeshiftProcessFinish) && (this.userCenterProcessFinish))
    {
      Logger.i(this.TAG, "isNext2ZipFile");
      this.vodProcessFinish = false;
      this.timeshiftProcessFinish = false;
      this.userCenterProcessFinish = false;
      writeCrashFile();
    }
  }

  private InputStream list2Stream(List<CompressString> paramList)
  {
    int i = 0;
    for (int j = 0; j < paramList.size(); j++)
      i += ((CompressString)paramList.get(j)).getLength();
    byte[] arrayOfByte = new byte[i];
    int k = 0;
    for (int m = 0; m < paramList.size(); m++)
    {
      int n = ((CompressString)paramList.get(m)).read(arrayOfByte, k, arrayOfByte.length - k);
      if (n > 0)
        k += n;
    }
    return new ByteArrayInputStream(arrayOfByte, 0, k);
  }

  private void readSystemLogs()
  {
    try
    {
      Pattern localPattern = Pattern.compile("^(.+?)\\s+(\\d+)\\s+1\\s+.+[A-Z]\\s+logcat$");
      java.lang.Process localProcess = Runtime.getRuntime().exec("ps");
      BufferedReader localBufferedReader2 = new BufferedReader(new InputStreamReader(localProcess.getInputStream()));
      while (true)
      {
        String str2 = localBufferedReader2.readLine();
        if (str2 == null)
        {
          localProcess.destroy();
          try
          {
            BufferedReader localBufferedReader1 = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("logcat -v time").getInputStream(), "utf-8"));
            while (!this.exitFlag)
            {
              String str1 = localBufferedReader1.readLine();
              if (str1 != null)
                LogCache.getInstance().addLog2Cache(LogRecord.LogType.SYSTEM, str1);
              try
              {
                Thread.sleep(20L);
              }
              catch (InterruptedException localInterruptedException)
              {
                localInterruptedException.printStackTrace();
              }
            }
          }
          catch (IOException localIOException)
          {
            localIOException.printStackTrace();
          }
          return;
        }
        Matcher localMatcher = localPattern.matcher(str2);
        if ((localMatcher != null) && (localMatcher.matches()))
        {
          int i = XulUtils.tryParseInt(localMatcher.group(2));
          try
          {
            android.os.Process.killProcess(i);
          }
          catch (Exception localException2)
          {
            localException2.printStackTrace();
          }
        }
      }
    }
    catch (Exception localException1)
    {
      while (true)
        localException1.printStackTrace();
    }
  }

  private InputStream string2Stream(String paramString)
  {
    if (paramString != null);
    for (byte[] arrayOfByte = paramString.getBytes(); ; arrayOfByte = new byte[0])
      return new ByteArrayInputStream(arrayOfByte);
  }

  private void writeCrashFile()
  {
    Logger.i(this.TAG, "writeCrashFile");
    this.isWritingFile = true;
    String str1 = getNowDate(this.exactPattern);
    String str2 = GlobalLogic.getInstance().getLogSecretPrefix() + getNowDate(this.commonPattern);
    ZIPFileUtils localZIPFileUtils = new ZIPFileUtils(logPath + File.separator + str1 + "zip_logs.zip", MD5(str2));
    if (this.errorType == ErrorType.ERROR);
    try
    {
      localZIPFileUtils.putEntry(str1 + "vod_process_result.txt", new FileInputStream(this.vodProcessPath));
      try
      {
        label146: localZIPFileUtils.putEntry(str1 + "timeshift_process_result.txt", new FileInputStream(this.timeShiftProcessPath));
        try
        {
          label182: localZIPFileUtils.putEntry(str1 + "usercenter_process_result.txt", new FileInputStream(this.usercenterProcessPath));
          label218: this.writeUserInfoApi.run();
          if (this.object != null)
          {
            InputStream localInputStream3 = string2Stream(this.object.toString());
            localZIPFileUtils.putEntry(str1 + "info.json", localInputStream3);
          }
          if ((this.errorType == ErrorType.ERROR) || (this.errorType == ErrorType.CRASH))
          {
            InputStream localInputStream1 = list2Stream(this.appCache);
            localZIPFileUtils.putEntry(str1 + "apk.log", localInputStream1);
          }
          try
          {
            localZIPFileUtils.putEntry(str1 + "trace.txt", new FileInputStream("/data/anr/traces.txt"));
            label366: InputStream localInputStream2 = list2Stream(this.systemCache);
            localZIPFileUtils.putEntry(str1 + "logcat.log", localInputStream2);
            localZIPFileUtils.closeZip();
            Logger.i(this.TAG, "写完zip");
            delApiPorcessDetectFiles();
            this.isWritingFile = false;
            this.busy = false;
            this.writeFile = false;
            return;
          }
          catch (FileNotFoundException localFileNotFoundException1)
          {
            break label366;
          }
        }
        catch (FileNotFoundException localFileNotFoundException4)
        {
          break label218;
        }
      }
      catch (FileNotFoundException localFileNotFoundException3)
      {
        break label182;
      }
    }
    catch (FileNotFoundException localFileNotFoundException2)
    {
      break label146;
    }
  }

  public void init(Context paramContext)
  {
    if (!AppFuncCfg.FUNCTION_ENABLE_LOGUPLOAD)
      return;
    if ((this.workThread != null) && (this.workThread.isAlive()))
    {
      Log.e(this.TAG, "多次启动日志上传任务");
      return;
    }
    paramContext.startService(new Intent(paramContext, LogUploadService.class));
    this.exitFlag = false;
    this.systemCache = new ArrayList();
    this.appCache = new ArrayList();
    this.writeSystemLogsThread = new WriteSystemLogsThread();
    this.writeSystemLogsThread.start();
    this.workThread = new WorkThread();
    this.workThread.start();
  }

  public boolean isWritingFile()
  {
    return this.isWritingFile;
  }

  public void notifyWriteFile(ErrorType paramErrorType, String paramString)
  {
    this.errorType = paramErrorType;
    if (this.errorType == ErrorType.TEST_SPEED)
    {
      this.writeFile = true;
      return;
    }
    this.writeFile = isCanWrite(paramString);
    this.errorCode = paramString;
    this.oldTime = System.currentTimeMillis();
  }

  public static enum ErrorType
  {
    static
    {
      CRASH = new ErrorType("CRASH", 1);
      TEST_SPEED = new ErrorType("TEST_SPEED", 2);
      ErrorType[] arrayOfErrorType = new ErrorType[3];
      arrayOfErrorType[0] = ERROR;
      arrayOfErrorType[1] = CRASH;
      arrayOfErrorType[2] = TEST_SPEED;
    }
  }

  class WorkThread extends Thread
  {
    WorkThread()
    {
    }

    public void run()
    {
      while (!LogCacheManger.this.exitFlag)
      {
        try
        {
          Thread.sleep(20L);
          if (LogCacheManger.this.busy)
            continue;
          if (!LogCacheManger.this.writeFile)
            break label110;
          LogCacheManger.access$702(LogCacheManger.this, true);
          if (LogCacheManger.this.errorType == LogCacheManger.ErrorType.ERROR)
          {
            Logger.i(LogCacheManger.this.TAG, "开始写错误日志");
            LogCacheManger.this.apiProcessDetect();
          }
        }
        catch (InterruptedException localInterruptedException)
        {
          while (true)
            localInterruptedException.printStackTrace();
          Logger.i(LogCacheManger.this.TAG, "开始写crash日志");
          LogCacheManger.this.writeCrashFile();
        }
        continue;
        label110: LogCacheManger.access$702(LogCacheManger.this, true);
        LogRecord localLogRecord = LogCache.getInstance().getCache();
        if (localLogRecord == null)
        {
          LogCacheManger.access$702(LogCacheManger.this, false);
        }
        else
        {
          switch (LogCacheManger.5.$SwitchMap$com$starcor$hunan$opendownload$logupload$LogRecord$LogType[localLogRecord.getType().ordinal()])
          {
          default:
          case 1:
          case 2:
          }
          while (true)
          {
            LogCacheManger.access$702(LogCacheManger.this, false);
            break;
            LogCacheManger.access$1202(LogCacheManger.this, LogCacheManger.this.addLog2Cache(LogCacheManger.this.systemCache, LogCacheManger.this.systemTotalSize, localLogRecord.getContent()));
            continue;
            LogCacheManger.access$1502(LogCacheManger.this, LogCacheManger.this.addLog2Cache(LogCacheManger.this.appCache, LogCacheManger.this.appTotalSize, localLogRecord.getContent()));
          }
        }
      }
    }
  }

  class WriteSystemLogsThread extends Thread
  {
    WriteSystemLogsThread()
    {
    }

    public void run()
    {
      LogCacheManger.this.readSystemLogs();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.opendownload.logupload.LogCacheManger
 * JD-Core Version:    0.6.2
 */