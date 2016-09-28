package com.starcor.core.exception;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import com.starcor.config.AppFuncCfg;
import com.starcor.core.logic.GlobalEnv;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.opendownload.logupload.LogCacheManger;
import com.starcor.hunan.opendownload.logupload.LogCacheManger.ErrorType;
import com.starcor.message.MessageHandler;
import com.starcor.report.Column.ErrorColumn.Builder;
import com.starcor.report.ReportMessage;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class CrashHandler
  implements Thread.UncaughtExceptionHandler
{
  private static CrashHandler INSTANCE = new CrashHandler();
  public static final String TAG = "CrashHandler";
  private static String errorCodeName = "错误码：";
  private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
  private Map<String, String> infos = new HashMap();
  private boolean isReport = false;
  private Context mContext;
  private Thread.UncaughtExceptionHandler mDefaultHandler;

  public static CrashHandler getInstance()
  {
    return INSTANCE;
  }

  private boolean handleException(Throwable paramThrowable)
  {
    return paramThrowable != null;
  }

  public static void initErrorCodeName(String paramString)
  {
    errorCodeName = paramString;
  }

  private void showDialog()
  {
    if (this.mContext == null);
    Bundle localBundle;
    do
    {
      return;
      localBundle = new Bundle();
      localBundle.putString("Message", (String)ApplicationException.appExceptionMap.get("1002002") + "[" + errorCodeName + "1002002" + "]");
    }
    while (((Activity)this.mContext).isFinishing());
    ((Activity)this.mContext).showDialog(10, localBundle);
  }

  protected void collectDeviceInfo(Context paramContext)
  {
    if (this.infos == null);
    while (true)
    {
      return;
      try
      {
        localPackageInfo = paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(), 1);
        if (localPackageInfo != null)
        {
          if (localPackageInfo.versionName == null)
          {
            str1 = "null";
            String str2 = localPackageInfo.versionCode + "";
            this.infos.put("versionName", str1);
            this.infos.put("versionCode", str2);
          }
        }
        else
        {
          Field[] arrayOfField = Build.class.getDeclaredFields();
          int i = arrayOfField.length;
          j = 0;
          if (j >= i)
            continue;
          localField = arrayOfField[j];
        }
      }
      catch (PackageManager.NameNotFoundException localNameNotFoundException)
      {
        try
        {
          while (true)
          {
            PackageInfo localPackageInfo;
            int j;
            Field localField;
            localField.setAccessible(true);
            this.infos.put(localField.getName(), localField.get(null).toString());
            Log.d("CrashHandler", localField.getName() + " : " + localField.get(null));
            j++;
            continue;
            String str1 = localPackageInfo.versionName;
          }
          localNameNotFoundException = localNameNotFoundException;
          Log.e("CrashHandler", "an error occured when collect package info", localNameNotFoundException);
        }
        catch (Exception localException)
        {
          while (true)
            Log.e("CrashHandler", "an error occured when collect crash info", localException);
        }
      }
    }
  }

  public void init(Context paramContext)
  {
    this.mContext = paramContext;
    this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
    Thread.setDefaultUncaughtExceptionHandler(this);
  }

  public void postErrorInfo(Throwable paramThrowable)
  {
    if (!AppFuncCfg.FUNCTION_ENABLE_REPORT)
      return;
    StringBuffer localStringBuffer = new StringBuffer();
    for (int i = 0; i < paramThrowable.getStackTrace().length; i++)
    {
      localStringBuffer.append(paramThrowable.getStackTrace()[i].toString());
      localStringBuffer.append("@");
    }
    ReportMessage localReportMessage = new ReportMessage();
    localReportMessage.mExtData = new ErrorColumn.Builder("1002002", localStringBuffer.toString()).build();
    localReportMessage.setDesc("运行时错误信息数据事件上报");
    MessageHandler.instance().doNotify(localReportMessage);
  }

  protected String saveCrashInfo2File(Throwable paramThrowable)
  {
    if (this.infos == null)
      return "";
    StringBuffer localStringBuffer = new StringBuffer();
    Iterator localIterator = this.infos.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      String str3 = (String)localEntry.getKey();
      String str4 = (String)localEntry.getValue();
      localStringBuffer.append(str3).append("=").append(str4).append("\n");
    }
    StringWriter localStringWriter = new StringWriter();
    PrintWriter localPrintWriter = new PrintWriter(localStringWriter);
    paramThrowable.printStackTrace(localPrintWriter);
    for (Throwable localThrowable = paramThrowable.getCause(); localThrowable != null; localThrowable = localThrowable.getCause())
      localThrowable.printStackTrace(localPrintWriter);
    localPrintWriter.close();
    localStringBuffer.append(localStringWriter.toString());
    try
    {
      long l = System.currentTimeMillis();
      String str1 = this.formatter.format(new Date());
      String str2 = "crash-" + str1 + "-" + l + ".log";
      FileOutputStream localFileOutputStream = new FileOutputStream(GlobalEnv.getInstance().getLogPath() + str2);
      localFileOutputStream.write(localStringBuffer.toString().getBytes());
      localFileOutputStream.close();
      return str2;
    }
    catch (Exception localException)
    {
      Log.e("CrashHandler", "an error occured while writing file...", localException);
    }
    return null;
  }

  public void uncaughtException(Thread paramThread, final Throwable paramThrowable)
  {
    Logger.e("CrashHandler", "FATAL EXCEPTION: ", paramThrowable);
    if ((!handleException(paramThrowable)) && (this.mDefaultHandler != null))
      this.mDefaultHandler.uncaughtException(paramThread, paramThrowable);
    while (this.isReport)
      return;
    this.isReport = true;
    LogCacheManger.getInstance().notifyWriteFile(LogCacheManger.ErrorType.CRASH, LogCacheManger.IGNORE);
    new Thread()
    {
      public void run()
      {
        CrashHandler.this.postErrorInfo(paramThrowable);
        Looper.prepare();
        CrashHandler.this.showDialog();
        Looper.loop();
      }
    }
    .start();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.exception.CrashHandler
 * JD-Core Version:    0.6.2
 */