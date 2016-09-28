package com.starcor.utils;

import android.util.Log;

public class Logger
{
  public static boolean DEBUG = false;
  public static final int PRINT_OUT_INNER = 2;
  public static final int PRINT_OUT_NORMAL = 1;
  private static final String TAG = "hunan_ott_lib_inner";
  public static int state = 2;

  public static void d(String paramString)
  {
    d("hunan_ott_lib_inner", paramString);
  }

  public static void d(String paramString1, String paramString2)
  {
    writeLog("D", paramString1, paramString2);
  }

  public static void e(Exception paramException)
  {
    if (paramException != null)
      e("hunan_ott_lib_inner", paramException.getMessage());
  }

  public static void e(String paramString1, String paramString2)
  {
    writeLog("E", paramString1, paramString2);
  }

  public static void i(String paramString)
  {
    i("hunan_ott_lib_inner", paramString);
  }

  public static void i(String paramString1, String paramString2)
  {
    writeLog("I", paramString1, paramString2);
  }

  public static void w(String paramString)
  {
    w("hunan_ott_lib_inner", paramString);
  }

  public static void w(String paramString1, String paramString2)
  {
    writeLog("W", paramString1, paramString2);
  }

  private static void writeLog(String paramString1, String paramString2, String paramString3)
  {
    if (state == 2);
    do
    {
      Log.i("hunan_ott_lib_inner", String.format("[%s][%s]: %s\n", new Object[] { paramString1, paramString2, paramString3 }));
      do
        return;
      while (state != 1);
      if ("D".equals(paramString1))
      {
        Log.d(paramString2, paramString3);
        return;
      }
      if ("I".equals(paramString1))
      {
        Log.i(paramString2, paramString3);
        return;
      }
      if ("W".equals(paramString1))
      {
        Log.w(paramString2, paramString3);
        return;
      }
    }
    while (!"E".equals(paramString1));
    Log.e(paramString2, paramString3);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.utils.Logger
 * JD-Core Version:    0.6.2
 */