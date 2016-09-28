package com.huawei.dmpbase;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.os.Build.VERSION;
import android.util.DisplayMetrics;
import android.util.Log;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class DmpBase
{
  public static final int DMP_ERR = -1;
  public static final int DMP_OK = 0;
  static final String LIBDMPBASE = "dmpbase";
  static final String TAG = "HAPlayer_DmpBase";
  static int ref = 0;

  static
  {
    try
    {
      ref = -1;
      System.loadLibrary("dmpbase");
      ref = 0;
      Log.i("HAPlayer_DmpBase", "Succeed to load library dmpbase.");
      return;
    }
    catch (UnsatisfiedLinkError localUnsatisfiedLinkError)
    {
      Log.e("HAPlayer_DmpBase", "Failed to load library dmpbase!" + localUnsatisfiedLinkError);
    }
  }

  public static void Close()
  {
    try
    {
      ref = -1 + ref;
      if (ref == 0)
        nativeOnDestruct();
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public static String GetDevUid()
  {
    try
    {
      if (ref > 0)
      {
        String str2 = nativeGetDevUid();
        str1 = str2;
        return str1;
      }
      String str1 = null;
    }
    finally
    {
    }
  }

  public static String GetDmpBaseVer()
  {
    try
    {
      if (ref > 0)
      {
        String str2 = nativeGetDmpBaseVer();
        str1 = str2;
        return str1;
      }
      String str1 = "not support";
    }
    finally
    {
    }
  }

  public static int IsRooted()
  {
    try
    {
      if (ref > 0)
      {
        int j = nativeIsRooted();
        i = j;
        return i;
      }
      int i = 0;
    }
    finally
    {
    }
  }

  public static int IsUnderDebug()
  {
    try
    {
      if (ref > 0)
      {
        int j = nativeIsUnderDebug();
        i = j;
        return i;
      }
      int i = 0;
    }
    finally
    {
    }
  }

  public static int Open(Context paramContext)
  {
    int i = -1;
    while (true)
    {
      try
      {
        Log.i("HAPlayer_DmpBase", "DmpBase.Open()");
        Thread.setDefaultUncaughtExceptionHandler(new DmpExceptionHandler(paramContext));
        if (ref < 0)
        {
          Log.e("HAPlayer_DmpBase", "Load library failed, do not call open pls.");
          return i;
        }
        if (ref > 0)
        {
          Log.i("HAPlayer_DmpBase", "DmpBase.Open() already called.");
          ref = 1 + ref;
          i = 0;
          continue;
        }
        if (nativeOnConstruct() != 0)
        {
          Log.e("HAPlayer_DmpBase", "Native construct failed!");
          continue;
        }
      }
      finally
      {
      }
      ref = 1 + ref;
      i = 0;
    }
  }

  public static String ReadDiagTrace()
  {
    try
    {
      if (ref > 0)
      {
        String str2 = nativeReadDiagTrace();
        str1 = str2;
        return str1;
      }
      String str1 = null;
    }
    finally
    {
    }
  }

  public static void SetLogLevel(int paramInt)
  {
    try
    {
      if (ref == 0)
        Log.e("HAPlayer_DmpBase", "Call DmpBase.Open(this) before DmpBase.SetLogLevel() please!");
      while (true)
      {
        return;
        if (ref > 0)
          nativeSetLogLevel(paramInt);
      }
    }
    finally
    {
    }
  }

  public static int StartDebugAgent(String paramString)
  {
    int i = -1;
    try
    {
      if (ref == 0)
        Log.e("HAPlayer_DmpBase", "Call DmpBase.Open(this) before DmpBase.StartDebugAgent() please!");
      while (true)
      {
        return i;
        if (ref > 0)
        {
          int j = nativeStartDebugAgent(paramString);
          i = j;
        }
      }
    }
    finally
    {
    }
  }

  public static int StartDiagTrace()
  {
    try
    {
      if (ref > 0)
      {
        int j = nativeStartDiagTrace();
        i = j;
        return i;
      }
      int i = 0;
    }
    finally
    {
    }
  }

  public static void StopDebugAgent()
  {
    try
    {
      if (ref > 0)
        nativeStopDebugAgent();
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public static void StopDiagTrace()
  {
    try
    {
      if (ref > 0)
        nativeStopDiagTrace();
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public static void WriteDiagTrace(int paramInt, String paramString)
  {
    try
    {
      if (ref > 0)
        nativeWriteDiagTrace(paramInt, paramString);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public static void WriteLog(int paramInt, String paramString1, String paramString2)
  {
    try
    {
      if (ref > 0)
      {
        StackTraceElement localStackTraceElement = Thread.currentThread().getStackTrace()[4];
        nativeWriteLog(paramInt, paramString1, localStackTraceElement.getFileName(), localStackTraceElement.getLineNumber(), paramString2);
      }
      while (true)
      {
        return;
        int i = paramInt + 3;
        Log.println(i, paramString1, paramString2);
      }
    }
    finally
    {
    }
  }

  protected static native void nativeCloseLocalFileLog();

  protected static native void nativeCloseLogCatLog();

  protected static native String nativeGetDevUid();

  protected static native String nativeGetDmpBaseVer();

  protected static native int nativeIsRooted();

  protected static native int nativeIsUnderDebug();

  private static native int nativeOnConstruct();

  private static native void nativeOnDestruct();

  protected static native void nativeOpenLocalFileLog(String paramString);

  protected static native void nativeOpenLogCatLog();

  protected static native String nativeReadDiagTrace();

  protected static native void nativeSetLogLevel(int paramInt);

  protected static native int nativeStartDebugAgent(String paramString);

  protected static native int nativeStartDiagTrace();

  protected static native void nativeStopDebugAgent();

  protected static native void nativeStopDiagTrace();

  protected static native void nativeWriteCrashLog(String paramString);

  protected static native void nativeWriteDiagTrace(int paramInt, String paramString);

  protected static native void nativeWriteLog(int paramInt1, String paramString1, String paramString2, int paramInt2, String paramString3);

  public static class DmpExceptionHandler
    implements Thread.UncaughtExceptionHandler
  {
    private String app_name;
    private String app_ver;
    private String board;
    private String brand;
    private String dmp_uid;
    private String dmp_ver;
    private int height = 0;
    private Thread.UncaughtExceptionHandler mDefaultHandler = null;
    private String model;
    private String os_sdk;
    private String os_ver;
    private String pkg_name;
    private String revision;
    private int width = 0;

    public DmpExceptionHandler(Context paramContext)
    {
      try
      {
        this.brand = Build.BRAND;
        this.model = Build.MODEL;
        this.board = Build.BOARD;
        this.revision = Build.VERSION.INCREMENTAL;
        this.os_ver = Build.VERSION.RELEASE;
        this.os_sdk = Build.VERSION.SDK;
        DisplayMetrics localDisplayMetrics = paramContext.getResources().getDisplayMetrics();
        this.width = localDisplayMetrics.widthPixels;
        this.height = localDisplayMetrics.heightPixels;
        this.pkg_name = paramContext.getPackageName();
        PackageManager localPackageManager = paramContext.getPackageManager();
        PackageInfo localPackageInfo = localPackageManager.getPackageInfo(this.pkg_name, 0);
        this.app_name = localPackageManager.getApplicationLabel(localPackageInfo.applicationInfo).toString();
        this.app_ver = localPackageInfo.versionName;
        this.dmp_ver = DmpBase.nativeGetDmpBaseVer();
        this.dmp_uid = DmpBase.nativeGetDevUid();
        this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        return;
      }
      catch (Exception localException)
      {
        while (true)
        {
          Log.e("HAPlayer_DmpBase", localException.getMessage().toString());
          Log.e("HAPlayer_DmpBase", localException.getCause().toString());
        }
      }
    }

    public void uncaughtException(Thread paramThread, Throwable paramThrowable)
    {
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append("[DEVICE]\r\n");
      localStringBuffer.append("Brand:").append(this.brand).append("\r\n");
      localStringBuffer.append("Model:").append(this.model).append("\r\n");
      localStringBuffer.append("Board:").append(this.board).append("\r\n");
      localStringBuffer.append("Revision:").append(this.revision).append("\r\n");
      localStringBuffer.append("Android Version:").append(this.os_ver).append("\r\n");
      localStringBuffer.append("SDK Level:").append(this.os_sdk).append("\r\n");
      localStringBuffer.append("Screen Metrics:").append(Integer.toString(this.height)).append("x").append(Integer.toString(this.width)).append("\r\n\r\n");
      localStringBuffer.append("[APPLICATION]\r\n");
      localStringBuffer.append("Package Name:").append(this.pkg_name).append("\r\n");
      localStringBuffer.append("App Name:").append(this.app_name).append("\r\n");
      localStringBuffer.append("App Version:").append(this.app_ver).append("\r\n");
      localStringBuffer.append("Platform Version:").append(this.dmp_ver).append("\r\n");
      localStringBuffer.append("Unique Identifier:").append(this.dmp_uid).append("\r\n\r\n");
      localStringBuffer.append("[EXCEPTION]\r\n");
      localStringBuffer.append("Name:").append(paramThrowable.getMessage()).append("\r\n");
      Throwable localThrowable = paramThrowable.getCause();
      if (localThrowable != null)
      {
        localStringBuffer.append("Reason:").append(localThrowable.toString()).append("\r\n");
        DecimalFormat localDecimalFormat = new DecimalFormat("#0");
        StackTraceElement[] arrayOfStackTraceElement = localThrowable.getStackTrace();
        for (int i = 0; i < arrayOfStackTraceElement.length; i++)
          localStringBuffer.append("Call Stack:").append(localDecimalFormat.format(i)).append("\t\t").append(arrayOfStackTraceElement[i].toString()).append("\r\n");
      }
      localStringBuffer.append("\r\n");
      DmpBase.nativeWriteCrashLog(localStringBuffer.toString());
      if (this.mDefaultHandler != null)
        this.mDefaultHandler.uncaughtException(paramThread, paramThrowable);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.huawei.dmpbase.DmpBase
 * JD-Core Version:    0.6.2
 */