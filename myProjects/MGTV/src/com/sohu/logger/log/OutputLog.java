package com.sohu.logger.log;

import android.os.Environment;
import android.util.Log;
import java.io.File;

public class OutputLog
{
  private static final String TAG = "com.sohu.logger";
  public static OutputLog logInstance = new OutputLog();
  private boolean DEBUG = false;

  private OutputLog()
  {
    if (Environment.getExternalStorageState().equals("mounted"))
      try
      {
        if (new File(Environment.getExternalStorageDirectory() + File.separator + "sohusdkdebug.txt").exists())
        {
          this.DEBUG = true;
          return;
        }
        this.DEBUG = false;
        return;
      }
      catch (Exception localException)
      {
        this.DEBUG = false;
        localException.printStackTrace();
        return;
      }
    this.DEBUG = false;
  }

  public static boolean canShow()
  {
    return logInstance.DEBUG;
  }

  public static void d(String paramString)
  {
    if ((canShow()) && (paramString != null));
    try
    {
      Log.d("com.sohu.logger", paramString);
      return;
    }
    catch (Exception localException)
    {
      printStackTrace(localException);
      return;
    }
    catch (Error localError)
    {
      printStackTrace(localError);
    }
  }

  public static void d(String paramString1, String paramString2)
  {
    if ((canShow()) && (paramString2 != null));
    try
    {
      Log.d(paramString1, paramString2);
      return;
    }
    catch (Exception localException)
    {
      printStackTrace(localException);
      return;
    }
    catch (Error localError)
    {
      printStackTrace(localError);
    }
  }

  public static void e(String paramString)
  {
    if ((canShow()) && (paramString != null));
    try
    {
      Log.e("com.sohu.logger", paramString);
      return;
    }
    catch (Exception localException)
    {
      printStackTrace(localException);
      return;
    }
    catch (Error localError)
    {
      printStackTrace(localError);
    }
  }

  public static void e(String paramString1, String paramString2)
  {
    if ((canShow()) && (paramString2 != null));
    try
    {
      Log.e(paramString1, paramString2);
      return;
    }
    catch (Exception localException)
    {
      printStackTrace(localException);
      return;
    }
    catch (Error localError)
    {
      printStackTrace(localError);
    }
  }

  public static void e(String paramString1, String paramString2, Throwable paramThrowable)
  {
    if ((canShow()) && (paramString2 != null));
    try
    {
      Log.e(paramString1, paramString2, paramThrowable);
      return;
    }
    catch (Exception localException)
    {
      printStackTrace(localException);
      return;
    }
    catch (Error localError)
    {
      printStackTrace(localError);
    }
  }

  public static void i(String paramString)
  {
    if ((canShow()) && (paramString != null));
    try
    {
      Log.i("com.sohu.logger", paramString);
      return;
    }
    catch (Exception localException)
    {
      printStackTrace(localException);
      return;
    }
    catch (Error localError)
    {
      printStackTrace(localError);
    }
  }

  public static void i(String paramString1, String paramString2)
  {
    if ((canShow()) && (paramString2 != null));
    try
    {
      Log.i(paramString1, paramString2);
      return;
    }
    catch (Exception localException)
    {
      printStackTrace(localException);
      return;
    }
    catch (Error localError)
    {
      printStackTrace(localError);
    }
  }

  public static void printStackTrace(Error paramError)
  {
    paramError.printStackTrace();
  }

  public static void printStackTrace(Exception paramException)
  {
    paramException.printStackTrace();
  }

  public static void v(String paramString)
  {
    if ((canShow()) && (paramString != null));
    try
    {
      Log.v("com.sohu.logger", paramString);
      return;
    }
    catch (Exception localException)
    {
      printStackTrace(localException);
      return;
    }
    catch (Error localError)
    {
      printStackTrace(localError);
    }
  }

  public static void v(String paramString1, String paramString2)
  {
    if ((canShow()) && (paramString2 != null));
    try
    {
      Log.v(paramString1, paramString2);
      return;
    }
    catch (Exception localException)
    {
      printStackTrace(localException);
      return;
    }
    catch (Error localError)
    {
      printStackTrace(localError);
    }
  }

  public static void w(String paramString)
  {
    if ((canShow()) && (paramString != null));
    try
    {
      Log.w("com.sohu.logger", paramString);
      return;
    }
    catch (Exception localException)
    {
      printStackTrace(localException);
      return;
    }
    catch (Error localError)
    {
      printStackTrace(localError);
    }
  }

  public static void w(String paramString1, String paramString2)
  {
    if ((canShow()) && (paramString2 != null));
    try
    {
      Log.w(paramString1, paramString2);
      return;
    }
    catch (Exception localException)
    {
      printStackTrace(localException);
      return;
    }
    catch (Error localError)
    {
      printStackTrace(localError);
    }
  }

  public static void w(String paramString1, String paramString2, Throwable paramThrowable)
  {
    if ((canShow()) && (paramString2 != null));
    try
    {
      Log.w(paramString1, paramString2, paramThrowable);
      return;
    }
    catch (Exception localException)
    {
      printStackTrace(localException);
      return;
    }
    catch (Error localError)
    {
      printStackTrace(localError);
    }
  }

  public static void w(String paramString, Throwable paramThrowable)
  {
    if ((canShow()) && (paramString != null));
    try
    {
      Log.w("com.sohu.logger", paramString, paramThrowable);
      return;
    }
    catch (Exception localException)
    {
      printStackTrace(localException);
      return;
    }
    catch (Error localError)
    {
      printStackTrace(localError);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.logger.log.OutputLog
 * JD-Core Version:    0.6.2
 */