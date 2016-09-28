package com.starcor.log.tools;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Environment;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Log;
import com.starcor.config.AppFuncCfg;
import com.starcor.config.DeviceInfo;
import com.starcor.config.MgtvUrl;
import com.starcor.core.domain.UserInfo;
import com.starcor.core.logic.GlobalEnv;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.App;
import com.starcor.hunan.opendownload.logupload.CompressString;
import com.starcor.hunan.service.apidetect.task.ApiProcessDetectTask;
import com.starcor.hunan.service.apidetect.task.ApiProcessDetectTask.APiProcessTypes;
import com.starcor.hunan.service.apidetect.task.ApiProcessDetectTask.SavingWays;
import com.starcor.hunan.service.apidetect.task.ApiProcessDetectTask.TimeshiftProcessCallback;
import com.starcor.hunan.service.apidetect.task.ApiProcessDetectTask.UserCenterCallback;
import com.starcor.hunan.service.apidetect.task.ApiProcessDetectTask.VodProcessCallback;
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
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONException;
import org.json.JSONObject;

public class LogCache
{
  public static final String IGNORE = "IGNORE";
  private static final String TAG = LogCache.class.getSimpleName();
  private static LogCache logCache;
  private String logPath = App.getInstance().getDir("logs_cache", 0).toString();
  private ContentManager mAppCache = new ContentManager();
  private ContentDiskWriter mDiskWriter;
  private ErrorBussines mErrorBussines;
  private UploadManager mUploader;
  private ExecutorService pool;
  private String sysLogTmpPathName = "systemTmp.log";
  private String timeShiftProcessPath = "/data/data/com.starcor.hunan/cache/timeshift_process_result.txt";
  private String usercenterProcessPath = "/data/data/com.starcor.hunan/cache/usercenter_process_result.txt";
  private String vodProcessPath = "/data/data/com.starcor.hunan/cache/vod_process_result.txt";

  private LogCache()
  {
    this.mAppCache.setContentListener(new ContentManager.ContentProcessListener()
    {
      public void onContentLimit()
      {
        LogCache.this.pool.submit(new Runnable()
        {
          public void run()
          {
            try
            {
              LogCache.this.mAppCache.saveContents();
              return;
            }
            catch (IOException localIOException)
            {
              localIOException.printStackTrace();
            }
          }
        });
      }
    });
    this.mDiskWriter = new ContentDiskWriter(null);
    this.mErrorBussines = new ErrorBussines(null);
    this.mUploader = new UploadManager();
    this.pool = Executors.newSingleThreadExecutor();
    this.logPath = getInnerSDCardPath();
  }

  private boolean checkMsgValid(String paramString)
  {
    return (AppFuncCfg.FUNCTION_ENABLE_LOGUPLOAD) && (!TextUtils.isEmpty(paramString));
  }

  private File getCacheFile(String paramString)
    throws IOException
  {
    File localFile1 = new File(this.logPath);
    if (!localFile1.exists())
      localFile1.mkdir();
    File localFile2 = new File(this.logPath, paramString);
    if (!localFile2.exists())
      localFile2.createNewFile();
    return localFile2;
  }

  private String getInnerSDCardPath()
  {
    File localFile = new File(Environment.getExternalStorageDirectory().getPath(), "log_cache");
    if (!localFile.exists())
      localFile.mkdir();
    return localFile.getAbsolutePath();
  }

  public static LogCache getInstance()
  {
    if (logCache == null)
      logCache = new LogCache();
    return logCache;
  }

  private void getSystemLogs()
  {
    try
    {
      localPattern = Pattern.compile("^(.+?)\\s+(\\d+)\\s+1\\s+.+[A-Z]\\s+logcat$");
      java.lang.Process localProcess = Runtime.getRuntime().exec("ps");
      BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(localProcess.getInputStream()));
      str = localBufferedReader.readLine();
      if (str == null)
        localProcess.destroy();
    }
    catch (Exception localException1)
    {
      try
      {
        while (true)
        {
          Pattern localPattern;
          String str;
          Log.i(TAG, "start get System log!");
          Runtime.getRuntime().exec("logcat -d -t 5000 -v time -f " + getCacheFile(this.sysLogTmpPathName));
          return;
          Matcher localMatcher = localPattern.matcher(str);
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
            continue;
            localException1 = localException1;
            localException1.printStackTrace();
          }
        }
      }
      catch (IOException localIOException)
      {
        localIOException.printStackTrace();
      }
    }
  }

  private void startUploadLogs(final String paramString1, final Map<String, String> paramMap, final List<String> paramList, final String paramString2, final UploadCallBack paramUploadCallBack)
  {
    this.pool.submit(new Runnable()
    {
      public void run()
      {
        ArrayList localArrayList = new ArrayList();
        if (paramList != null)
        {
          Iterator localIterator = paramList.iterator();
          while (localIterator.hasNext())
            localArrayList.add(new File((String)localIterator.next()));
        }
        long l1 = System.currentTimeMillis();
        LogCache.this.getSystemLogs();
        long l2 = System.currentTimeMillis();
        Logger.i(LogCache.TAG, "系统日志读取完成, 耗时：" + (l2 - l1));
        LogCache.this.mAppCache.saveAllContents();
        LogCache.this.mDiskWriter.writeLogToFile(paramString2, null, localArrayList, true);
        long l3 = System.currentTimeMillis();
        Logger.i(LogCache.TAG, "日志写文件完成, 耗时：" + (l3 - l2));
        LogCache.this.mUploader.upload(paramString2, paramString1, paramMap, paramUploadCallBack);
        long l4 = System.currentTimeMillis();
        Logger.i(LogCache.TAG, "日志上传完成, 耗时：" + (l4 - l3));
      }
    });
  }

  public void addAppLog(String paramString)
  {
    if (checkMsgValid(paramString))
      this.mAppCache.addContent(paramString);
  }

  public void notifyUploadLogs(ErrorType paramErrorType, String paramString)
  {
    if (!AppFuncCfg.FUNCTION_ENABLE_LOGUPLOAD)
      return;
    this.mErrorBussines.setResultCallback(new ResultCallBack()
    {
      public void onSuccess()
      {
        ArrayList localArrayList = new ArrayList();
        if (LogCache.this.mErrorBussines.getErrorType() == LogCache.ErrorType.ERROR)
        {
          localArrayList.add(LogCache.this.vodProcessPath);
          localArrayList.add(LogCache.this.timeShiftProcessPath);
          localArrayList.add(LogCache.this.usercenterProcessPath);
        }
        if (LogCache.this.mErrorBussines.getErrorType() != LogCache.ErrorType.NORMAL)
        {
          LogCache.this.mErrorBussines.getInfoObject();
          localArrayList.add("/data/anr/traces.txt");
        }
        if (LogCache.this.mErrorBussines.getErrorType() == LogCache.ErrorType.TEST_SPEED);
        LogCache.this.startUploadLogs(MgtvUrl.getReportTerminalStatus(), null, localArrayList, LogCache.this.logPath, null);
      }
    });
    this.mErrorBussines.startErrorProcess(paramErrorType, paramString);
  }

  public void notifyUploadLogs(String paramString1, Map<String, String> paramMap, String paramString2, UploadCallBack paramUploadCallBack)
  {
    startUploadLogs(paramString1, paramMap, null, paramString2, paramUploadCallBack);
  }

  private class ContentDiskWriter
  {
    private String commonPattern = "yyyyMMdd";
    private String exactPattern = "yyyyMMddHHmmss";

    private ContentDiskWriter()
    {
    }

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

    private String getNowDate(String paramString)
    {
      Date localDate = new Date();
      return new SimpleDateFormat(paramString).format(localDate);
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

    private InputStream string2Stream(String paramString)
    {
      if (paramString != null);
      for (byte[] arrayOfByte = paramString.getBytes(); ; arrayOfByte = new byte[0])
        return new ByteArrayInputStream(arrayOfByte);
    }

    public void writeLogToFile(String paramString, JSONObject paramJSONObject, List<File> paramList, boolean paramBoolean)
    {
      Logger.i(LogCache.TAG, "writeLogFiles");
      String str1 = getNowDate(this.exactPattern);
      String str2 = MD5(GlobalLogic.getInstance().getLogSecretPrefix() + getNowDate(this.commonPattern));
      File localFile1 = new File(paramString);
      if (!localFile1.exists())
        localFile1.mkdir();
      Logger.i(LogCache.TAG, "password: " + str2);
      ZIPFileUtils localZIPFileUtils = new ZIPFileUtils(paramString + File.separator + str1 + localFile1.getName() + ".zip", str2);
      if (paramJSONObject != null)
        localZIPFileUtils.putEntry("info.json", string2Stream(paramJSONObject.toString()));
      if (paramBoolean);
      try
      {
        File localFile3 = new File(LogCache.this.logPath, LogCache.this.sysLogTmpPathName);
        localZIPFileUtils.putEntry("system.log", new FileInputStream(localFile3));
        localFile3.delete();
        try
        {
          label224: localZIPFileUtils.putEntry("apk.log", LogCache.this.mAppCache.getOrderedDiskCacheFiles());
          label242: if ((paramList != null) && (!paramList.isEmpty()))
          {
            Iterator localIterator = paramList.iterator();
            while (localIterator.hasNext())
            {
              File localFile2 = (File)localIterator.next();
              try
              {
                localZIPFileUtils.putEntry(localFile2.getName(), new FileInputStream(localFile2));
                localFile2.delete();
              }
              catch (FileNotFoundException localFileNotFoundException)
              {
                localFileNotFoundException.printStackTrace();
              }
            }
          }
          localZIPFileUtils.closeZip();
          Logger.i(LogCache.TAG, "写完zip");
          return;
        }
        catch (Exception localException2)
        {
          break label242;
        }
      }
      catch (Exception localException1)
      {
        break label224;
      }
    }
  }

  private class ErrorBussines
  {
    String errorCode = "";
    private LogCache.ErrorType errorType;
    private LogCache.ResultCallBack mCallBack;
    private JSONObject object;
    private long oldTime = System.currentTimeMillis();
    private boolean timeshiftProcessFinish = false;
    private boolean userCenterProcessFinish = false;
    private boolean vodProcessFinish = false;
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
          LogCache.ErrorBussines.access$1502(LogCache.ErrorBussines.this, new JSONObject());
          JSONObject localJSONObject1 = new JSONObject();
          JSONObject localJSONObject2 = new JSONObject();
          JSONObject localJSONObject3 = new JSONObject();
          LogCache.ErrorBussines.this.object.put("system", localJSONObject1);
          LogCache.ErrorBussines.this.object.put("app", localJSONObject2);
          LogCache.ErrorBussines.this.object.put("user", localJSONObject3);
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

    private ErrorBussines()
    {
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
          Logger.i(LogCache.TAG, "vod process finishes!");
          LogCache.ErrorBussines.access$1602(LogCache.ErrorBussines.this, true);
          LogCache.ErrorBussines.this.onProcessFinish();
        }
      });
      localApiProcessDetectTask.setTimeshiftProcessCallback(new ApiProcessDetectTask.TimeshiftProcessCallback()
      {
        public void finish()
        {
          Logger.i(LogCache.TAG, "timeshift process finishes!");
          LogCache.ErrorBussines.access$1802(LogCache.ErrorBussines.this, true);
          LogCache.ErrorBussines.this.onProcessFinish();
        }
      });
      localApiProcessDetectTask.setUserCenterProcessCallback(new ApiProcessDetectTask.UserCenterCallback()
      {
        public void finish()
        {
          Logger.i(LogCache.TAG, "usercenter process finishes!");
          LogCache.ErrorBussines.access$1902(LogCache.ErrorBussines.this, true);
          LogCache.ErrorBussines.this.onProcessFinish();
        }
      });
      localApiProcessDetectTask.setSavingWay(ApiProcessDetectTask.SavingWays.SAVING_WAY_INTERAL_STORAGE);
      localApiProcessDetectTask.runTasks();
    }

    private boolean checkErrorContinue(String paramString)
    {
      Logger.i(LogCache.TAG, "传入的错误码" + paramString);
      if ("IGNORE".equals(paramString))
      {
        Logger.i(LogCache.TAG, "此错误为忽略");
        return true;
      }
      if (TextUtils.isEmpty(paramString))
      {
        Logger.i(LogCache.TAG, "请求码为空");
        return true;
      }
      long l = (System.currentTimeMillis() - this.oldTime) / 60000L;
      Logger.i(LogCache.TAG, "interval " + l);
      if (l >= 20L)
      {
        Logger.i(LogCache.TAG, "间隔大于预设值");
        return true;
      }
      if (paramString.equals(this.errorCode))
      {
        Logger.i(LogCache.TAG, "2次错误码相同");
        return false;
      }
      Logger.i(LogCache.TAG, "2次错误码不同");
      return true;
    }

    private void errorProcessFinish()
    {
      this.writeUserInfoApi.run();
      if (this.mCallBack != null)
        this.mCallBack.onSuccess();
    }

    private void onProcessFinish()
    {
      if ((this.vodProcessFinish) && (this.timeshiftProcessFinish) && (this.userCenterProcessFinish))
      {
        Logger.i(LogCache.TAG, "isNext2ZipFile");
        this.vodProcessFinish = false;
        this.timeshiftProcessFinish = false;
        this.userCenterProcessFinish = false;
        errorProcessFinish();
      }
    }

    public LogCache.ErrorType getErrorType()
    {
      return this.errorType;
    }

    public JSONObject getInfoObject()
    {
      return this.object;
    }

    public void setResultCallback(LogCache.ResultCallBack paramResultCallBack)
    {
      this.mCallBack = paramResultCallBack;
    }

    public void startErrorProcess(LogCache.ErrorType paramErrorType, String paramString)
    {
      this.errorType = paramErrorType;
      if (!checkErrorContinue(paramString))
        return;
      this.errorCode = paramString;
      this.oldTime = System.currentTimeMillis();
      if (this.errorType == LogCache.ErrorType.ERROR)
      {
        Logger.i(LogCache.TAG, "错误接");
        apiProcessDetect();
        return;
      }
      Logger.i(LogCache.TAG, "开始写crash日志");
      errorProcessFinish();
    }
  }

  public static enum ErrorType
  {
    static
    {
      ERROR = new ErrorType("ERROR", 1);
      CRASH = new ErrorType("CRASH", 2);
      TEST_SPEED = new ErrorType("TEST_SPEED", 3);
      ErrorType[] arrayOfErrorType = new ErrorType[4];
      arrayOfErrorType[0] = NORMAL;
      arrayOfErrorType[1] = ERROR;
      arrayOfErrorType[2] = CRASH;
      arrayOfErrorType[3] = TEST_SPEED;
    }
  }

  static abstract interface ResultCallBack
  {
    public abstract void onSuccess();
  }

  public static abstract interface UploadCallBack
  {
    public abstract void onError();

    public abstract void onSuccess(String paramString);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.log.tools.LogCache
 * JD-Core Version:    0.6.2
 */