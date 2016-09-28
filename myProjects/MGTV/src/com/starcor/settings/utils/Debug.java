package com.starcor.settings.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import java.io.PrintStream;

public class Debug
{
  private static final String DD = "DD";
  public static boolean DEBUG = false;
  private static final String EE = "EE";
  private static final String II = "II";
  private static final String VV = "VV";
  private static final String WW = "WW";

  public static void D_printStackTrace(Object paramObject)
  {
    if (!DEBUG);
    while (true)
    {
      return;
      StackTraceElement[] arrayOfStackTraceElement = Thread.currentThread().getStackTrace();
      Log.e("DD", "--------------" + paramObject.toString() + "--------------");
      int i = arrayOfStackTraceElement.length;
      for (int j = 0; j < i; j++)
        Log.d("DD", arrayOfStackTraceElement[j].toString());
    }
  }

  public static void E_printStackTrace(Object paramObject)
  {
    if (!DEBUG);
    while (true)
    {
      return;
      StackTraceElement[] arrayOfStackTraceElement = Thread.currentThread().getStackTrace();
      Log.d("EE", "--------------" + paramObject.toString() + "--------------");
      int i = arrayOfStackTraceElement.length;
      for (int j = 0; j < i; j++)
        Log.e("EE", arrayOfStackTraceElement[j].toString());
    }
  }

  public static void I_printStackTrace(Object paramObject)
  {
    if (!DEBUG);
    while (true)
    {
      return;
      StackTraceElement[] arrayOfStackTraceElement = Thread.currentThread().getStackTrace();
      Log.e("II", "--------------" + paramObject.toString() + "--------------");
      int i = arrayOfStackTraceElement.length;
      for (int j = 0; j < i; j++)
        Log.i("II", arrayOfStackTraceElement[j].toString());
    }
  }

  public static void V_printStackTrace(Object paramObject)
  {
    if (!DEBUG);
    while (true)
    {
      return;
      StackTraceElement[] arrayOfStackTraceElement = Thread.currentThread().getStackTrace();
      Log.e("VV", "--------------" + paramObject.toString() + "--------------");
      int i = arrayOfStackTraceElement.length;
      for (int j = 0; j < i; j++)
        Log.v("VV", arrayOfStackTraceElement[j].toString());
    }
  }

  public static void W_printStackTrace(Object paramObject)
  {
    if (!DEBUG);
    while (true)
    {
      return;
      StackTraceElement[] arrayOfStackTraceElement = Thread.currentThread().getStackTrace();
      Log.e("WW", "--------------" + paramObject.toString() + "--------------");
      int i = arrayOfStackTraceElement.length;
      for (int j = 0; j < i; j++)
        Log.w("WW", arrayOfStackTraceElement[j].toString());
    }
  }

  public static int d(String paramString)
  {
    boolean bool = DEBUG;
    int i = 0;
    if (bool)
    {
      String[] arrayOfString = wrapInfo(paramString);
      i = Log.d(arrayOfString[0], arrayOfString[1]);
    }
    return i;
  }

  public static int d(String paramString1, String paramString2)
  {
    if (DEBUG)
      return Log.d(paramString1, paramString2);
    return 0;
  }

  public static int d(String paramString1, String paramString2, Throwable paramThrowable)
  {
    if (DEBUG)
      return Log.d(paramString1, paramString2, paramThrowable);
    return 0;
  }

  public static int d(String paramString, Throwable paramThrowable)
  {
    boolean bool = DEBUG;
    int i = 0;
    if (bool)
    {
      String[] arrayOfString = wrapInfo(paramString);
      i = Log.d(arrayOfString[0], arrayOfString[1], paramThrowable);
    }
    return i;
  }

  public static int e(String paramString)
  {
    boolean bool = DEBUG;
    int i = 0;
    if (bool)
    {
      String[] arrayOfString = wrapInfo(paramString);
      i = Log.e(arrayOfString[0], arrayOfString[1]);
    }
    return i;
  }

  public static int e(String paramString1, String paramString2)
  {
    if (DEBUG)
      return Log.e(paramString1, paramString2);
    return 0;
  }

  public static int e(String paramString1, String paramString2, Throwable paramThrowable)
  {
    if (DEBUG)
      return Log.e(paramString1, paramString2, paramThrowable);
    return 0;
  }

  public static int e(String paramString, Throwable paramThrowable)
  {
    boolean bool = DEBUG;
    int i = 0;
    if (bool)
    {
      String[] arrayOfString = wrapInfo(paramString);
      i = Log.e(arrayOfString[0], arrayOfString[1], paramThrowable);
    }
    return i;
  }

  public static int i(String paramString)
  {
    boolean bool = DEBUG;
    int i = 0;
    if (bool)
    {
      String[] arrayOfString = wrapInfo(paramString);
      i = Log.i(arrayOfString[0], arrayOfString[1]);
    }
    return i;
  }

  public static int i(String paramString1, String paramString2)
  {
    if (DEBUG)
      return Log.i(paramString1, paramString2);
    return 0;
  }

  public static int i(String paramString1, String paramString2, Throwable paramThrowable)
  {
    if (DEBUG)
      return Log.i(paramString1, paramString2, paramThrowable);
    return 0;
  }

  public static int i(String paramString, Throwable paramThrowable)
  {
    boolean bool = DEBUG;
    int i = 0;
    if (bool)
    {
      String[] arrayOfString = wrapInfo(paramString);
      i = Log.i(arrayOfString[0], arrayOfString[1], paramThrowable);
    }
    return i;
  }

  public static void init(Context paramContext)
  {
    boolean bool = true;
    DEBUG = bool;
    PackageManager localPackageManager = paramContext.getPackageManager();
    try
    {
      if ((0x2 & localPackageManager.getPackageInfo(paramContext.getPackageName(), 0).applicationInfo.flags) != 0);
      while (true)
      {
        DEBUG = bool;
        return;
        bool = false;
      }
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
    }
  }

  public static void printStackTrace(Object paramObject)
  {
    if (!DEBUG);
    while (true)
    {
      return;
      StackTraceElement[] arrayOfStackTraceElement = Thread.currentThread().getStackTrace();
      Log.e("System.out", "--------------" + paramObject.toString() + "--------------");
      int i = arrayOfStackTraceElement.length;
      for (int j = 0; j < i; j++)
      {
        StackTraceElement localStackTraceElement = arrayOfStackTraceElement[j];
        System.out.println(localStackTraceElement);
      }
    }
  }

  public static int println(int paramInt, String paramString)
  {
    boolean bool = DEBUG;
    int i = 0;
    if (bool)
    {
      String[] arrayOfString = wrapInfo(paramString);
      i = Log.println(paramInt, arrayOfString[0], arrayOfString[1]);
    }
    return i;
  }

  public static int println(int paramInt, String paramString1, String paramString2)
  {
    if (DEBUG)
      return Log.println(paramInt, paramString1, paramString2);
    return 0;
  }

  public static int v(String paramString)
  {
    boolean bool = DEBUG;
    int i = 0;
    if (bool)
    {
      String[] arrayOfString = wrapInfo(paramString);
      i = Log.v(arrayOfString[0], arrayOfString[1]);
    }
    return i;
  }

  public static int v(String paramString1, String paramString2)
  {
    if (DEBUG)
      return Log.v(paramString1, paramString2);
    return 0;
  }

  public static int v(String paramString1, String paramString2, Throwable paramThrowable)
  {
    if (DEBUG)
      return Log.d(paramString1, paramString2, paramThrowable);
    return 0;
  }

  public static int v(String paramString, Throwable paramThrowable)
  {
    boolean bool = DEBUG;
    int i = 0;
    if (bool)
    {
      String[] arrayOfString = wrapInfo(paramString);
      i = Log.v(arrayOfString[0], arrayOfString[1], paramThrowable);
    }
    return i;
  }

  public static int w(String paramString)
  {
    boolean bool = DEBUG;
    int i = 0;
    if (bool)
    {
      String[] arrayOfString = wrapInfo(paramString);
      i = Log.w(arrayOfString[0], arrayOfString[1]);
    }
    return i;
  }

  public static int w(String paramString1, String paramString2)
  {
    if (DEBUG)
      return Log.w(paramString1, paramString2);
    return 0;
  }

  public static int w(String paramString1, String paramString2, Throwable paramThrowable)
  {
    if (DEBUG)
      return Log.w(paramString1, paramString2, paramThrowable);
    return 0;
  }

  public static int w(String paramString, Throwable paramThrowable)
  {
    if (DEBUG)
      return Log.w(paramString, paramThrowable);
    return 0;
  }

  public static int wMsg(String paramString, Throwable paramThrowable)
  {
    boolean bool = DEBUG;
    int i = 0;
    if (bool)
    {
      String[] arrayOfString = wrapInfo(paramString);
      i = Log.w(arrayOfString[0], arrayOfString[1], paramThrowable);
    }
    return i;
  }

  private static String[] wrapInfo(String paramString)
  {
    StackTraceElement localStackTraceElement = new Throwable().getStackTrace()[2];
    String[] arrayOfString = new String[2];
    arrayOfString[0] = localStackTraceElement.getClassName();
    arrayOfString[1] = ("[" + localStackTraceElement.getMethodName() + ":" + localStackTraceElement.getLineNumber() + "]" + paramString);
    return arrayOfString;
  }

  public static int wtf(String paramString)
  {
    boolean bool = DEBUG;
    int i = 0;
    if (bool)
    {
      String[] arrayOfString = wrapInfo(paramString);
      i = Log.wtf(arrayOfString[0], arrayOfString[1]);
    }
    return i;
  }

  public static int wtf(String paramString1, String paramString2)
  {
    if (DEBUG)
      return Log.wtf(paramString1, paramString2);
    return 0;
  }

  public static int wtf(String paramString1, String paramString2, Throwable paramThrowable)
  {
    if (DEBUG)
      return Log.wtf(paramString1, paramString2, paramThrowable);
    return 0;
  }

  public static int wtf(String paramString, Throwable paramThrowable)
  {
    if (DEBUG)
      return Log.wtf(paramString, paramThrowable);
    return 0;
  }

  public static int wtfMsg(String paramString, Throwable paramThrowable)
  {
    boolean bool = DEBUG;
    int i = 0;
    if (bool)
    {
      String[] arrayOfString = wrapInfo(paramString);
      i = Log.wtf(arrayOfString[0], arrayOfString[1], paramThrowable);
    }
    return i;
  }

  public boolean isDebugable()
  {
    return DEBUG;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.settings.utils.Debug
 * JD-Core Version:    0.6.2
 */