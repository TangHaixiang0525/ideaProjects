package com.sohu.logger.model;

import android.content.Context;
import android.os.Looper;
import android.os.Process;
import com.sohu.logger.bean.LogItem;
import com.sohu.logger.util.CommonUtil;
import com.sohu.logger.util.NetUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import org.json.JSONException;
import org.json.JSONObject;

public class MyCrashHandler
  implements Thread.UncaughtExceptionHandler
{
  private static MyCrashHandler myCrashHandler;
  private String activities;
  private Context context;
  private String os_version;
  private Object stacktrace;
  private String time;

  private String getErrorInfo(Throwable paramThrowable)
  {
    StringWriter localStringWriter = new StringWriter();
    PrintWriter localPrintWriter = new PrintWriter(localStringWriter);
    paramThrowable.printStackTrace(localPrintWriter);
    localPrintWriter.close();
    return localStringWriter.toString();
  }

  private JSONObject getErrorInfoJSONString(Context paramContext)
  {
    JSONObject localJSONObject = new JSONObject();
    try
    {
      localJSONObject.put("stacktrace", this.stacktrace);
      localJSONObject.put("time", this.time);
      localJSONObject.put("version", CommonUtil.getVersion(paramContext));
      localJSONObject.put("activity", this.activities);
      localJSONObject.put("os_version", this.os_version);
      localJSONObject.put("deviceid", CommonUtil.getDeviceName());
      return localJSONObject;
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
    }
    return localJSONObject;
  }

  public static MyCrashHandler getInstance()
  {
    try
    {
      if (myCrashHandler != null);
      for (MyCrashHandler localMyCrashHandler = myCrashHandler; ; localMyCrashHandler = myCrashHandler)
      {
        return localMyCrashHandler;
        myCrashHandler = new MyCrashHandler();
      }
    }
    finally
    {
    }
  }

  private String getLogDir()
  {
    return this.context.getFilesDir() + File.separator + "logger";
  }

  public void init(Context paramContext)
  {
    this.context = paramContext;
  }

  public boolean makesureCreateFile(File paramFile)
  {
    if (paramFile == null)
      return false;
    if (paramFile.exists())
      return true;
    File localFile = paramFile.getParentFile();
    if ((localFile != null) && (!localFile.exists()))
      localFile.mkdirs();
    return paramFile.createNewFile();
  }

  public void uncaughtException(Thread paramThread, final Throwable paramThrowable)
  {
    new Thread()
    {
      public void run()
      {
        super.run();
        Looper.prepare();
        String str1 = MyCrashHandler.this.getErrorInfo(paramThrowable);
        String[] arrayOfString = str1.split("\n\t");
        String str2 = arrayOfString[0] + "\n\t" + arrayOfString[1] + "\n\t" + arrayOfString[2] + "\n\t";
        String str3 = str2 + str1;
        MyCrashHandler.access$102(MyCrashHandler.this, str3);
        MyCrashHandler.access$202(MyCrashHandler.this, CommonUtil.getActivityName(MyCrashHandler.this.context));
        MyCrashHandler.access$402(MyCrashHandler.this, CommonUtil.getTime());
        MyCrashHandler.access$502(MyCrashHandler.this, CommonUtil.getOsVersion(MyCrashHandler.this.context));
        JSONObject localJSONObject = MyCrashHandler.this.getErrorInfoJSONString(MyCrashHandler.this.context);
        LogItem localLogItem = new LogItem();
        localLogItem.setParamsMapItem("json", localJSONObject.toString());
        String str4;
        if ((localLogItem.needSendRealtime()) && (CommonUtil.isNetworkAvailable(MyCrashHandler.this.context)) && (!MyCrashHandler.this.stacktrace.equals("")) && (!NetUtils.Post(MyCrashHandler.this.context, localLogItem.getRetryNum(), localLogItem.toUrl())))
          str4 = MyCrashHandler.this.getLogDir() + File.separator + System.currentTimeMillis() + ".log";
        try
        {
          File localFile = new File(str4);
          if (MyCrashHandler.this.makesureCreateFile(localFile))
          {
            FileOutputStream localFileOutputStream = new FileOutputStream(localFile, false);
            localFileOutputStream.write(localJSONObject.toString().getBytes());
            localFileOutputStream.flush();
            localFileOutputStream.close();
          }
          label340: Process.killProcess(Process.myPid());
          Looper.loop();
          return;
        }
        catch (Exception localException)
        {
          break label340;
        }
      }
    }
    .start();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.logger.model.MyCrashHandler
 * JD-Core Version:    0.6.2
 */