package com.sohu.upload.a;

import android.util.Log;

public class a
{
  public static void a(String paramString)
  {
    if (com.sohu.upload.consts.a.a)
    {
      Log.i("APPLIST", paramString);
      b.a("i", "APPLIST", paramString);
    }
  }

  public static void a(String paramString1, String paramString2)
  {
    if (com.sohu.upload.consts.a.a)
    {
      Log.i(paramString1, paramString2);
      b.a("i", paramString1, paramString2);
    }
  }

  public static void b(String paramString)
  {
    if (com.sohu.upload.consts.a.a)
    {
      Log.e("APPLIST", paramString);
      b.a("e", "APPLIST", paramString);
    }
  }

  public static void b(String paramString1, String paramString2)
  {
    if (com.sohu.upload.consts.a.a)
    {
      Log.e(paramString1, paramString2);
      b.a("e", paramString1, paramString2);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.upload.a.a
 * JD-Core Version:    0.6.2
 */