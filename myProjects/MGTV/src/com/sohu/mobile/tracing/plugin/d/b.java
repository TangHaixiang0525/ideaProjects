package com.sohu.mobile.tracing.plugin.d;

import android.util.Log;

public class b
{
  public static boolean a = false;

  public static void a(String paramString)
  {
    if (!a)
      Log.i("TrackingPlugin", paramString);
  }

  public static void b(String paramString)
  {
    if (!a)
      Log.e("TrackingPlugin", paramString);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.mobile.tracing.plugin.d.b
 * JD-Core Version:    0.6.2
 */