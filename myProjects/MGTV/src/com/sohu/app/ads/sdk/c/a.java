package com.sohu.app.ads.sdk.c;

import android.util.Log;

public class a
{
  private static boolean a = true;

  public static void a()
  {
    a = false;
  }

  public static void a(String paramString)
  {
    if (a)
      Log.i("SOHUSDK", paramString);
  }

  public static void a(String paramString1, String paramString2)
  {
    if (a)
      Log.i(paramString1, paramString2);
  }

  public static void b(String paramString)
  {
    if (a)
      Log.d("SOHUSDK", paramString);
  }

  public static void b(String paramString1, String paramString2)
  {
    if (a)
      Log.e(paramString1, paramString2);
  }

  public static void c(String paramString)
  {
    if (a)
      Log.e("SOHUSDK", paramString);
  }

  public static void d(String paramString)
  {
    if (a)
      Log.i("DownloadThread", paramString);
  }

  public static void e(String paramString)
  {
    if (a)
      Log.e("DownloadThread", paramString);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.app.ads.sdk.c.a
 * JD-Core Version:    0.6.2
 */