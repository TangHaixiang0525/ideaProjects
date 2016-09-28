package com.sohu.mma.tracking.c;

import android.util.Log;

public final class g
{
  public static boolean a = false;
  public static String b = "mmachina";

  public static void a(String paramString)
  {
    if ((a) && (paramString != null) && (!paramString.equals("")))
      Log.d(b, paramString);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.mma.tracking.c.g
 * JD-Core Version:    0.6.2
 */