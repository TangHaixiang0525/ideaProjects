package com.sohu.app.ads.sdk.core;

import android.view.ViewGroup;
import android.widget.PopupWindow;

public class r
{
  private static PopupWindow a;
  private static r b;
  private static boolean c = false;

  public static r a()
  {
    if (b == null)
      b = new r();
    return b;
  }

  public static boolean c()
  {
    return c;
  }

  public void a(ViewGroup paramViewGroup, n paramn, int[] paramArrayOfInt)
  {
    try
    {
      if (!c)
      {
        a = new PopupWindow(paramn, paramArrayOfInt[0], paramArrayOfInt[1]);
        a.showAtLocation(paramViewGroup, 0, paramArrayOfInt[2], paramArrayOfInt[3]);
        c = true;
      }
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public void b()
  {
    if ((a != null) && (c))
    {
      a.dismiss();
      a = null;
      c = false;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.app.ads.sdk.core.r
 * JD-Core Version:    0.6.2
 */