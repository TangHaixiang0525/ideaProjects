package com.umeng.analytics;

import android.content.Context;
import android.text.TextUtils;

public class e
{
  private static String[] a = new String[2];

  public static void a(Context paramContext, String paramString1, String paramString2)
  {
    a[0] = paramString1;
    a[1] = paramString2;
    if (paramContext != null)
      h.a(paramContext).a(paramString1, paramString2);
  }

  public static String[] a(Context paramContext)
  {
    if ((!TextUtils.isEmpty(a[0])) && (!TextUtils.isEmpty(a[1])))
      return a;
    if (paramContext != null)
    {
      String[] arrayOfString = h.a(paramContext).a();
      if (arrayOfString != null)
      {
        a[0] = arrayOfString[0];
        a[1] = arrayOfString[1];
        return a;
      }
    }
    return null;
  }

  public static void b(Context paramContext)
  {
    a[0] = null;
    a[1] = null;
    if (paramContext != null)
      h.a(paramContext).b();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.umeng.analytics.e
 * JD-Core Version:    0.6.2
 */