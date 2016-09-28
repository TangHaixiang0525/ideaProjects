package com.sohutv.tv.player.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

public class a
{
  public static int a(Context paramContext, int paramInt)
  {
    DisplayMetrics localDisplayMetrics = paramContext.getApplicationContext().getResources().getDisplayMetrics();
    float f = localDisplayMetrics.density;
    int i = localDisplayMetrics.widthPixels;
    int j;
    if (i > 1600)
    {
      j = (int)(paramInt / f);
      if (paramInt == 0)
        break label102;
      if (j != 0);
    }
    do
    {
      do
      {
        return 1;
        return j;
        if (i <= 1200)
          break;
        j = (int)(paramInt / (1.5D / f));
        if (paramInt == 0)
          break label102;
      }
      while (j == 0);
      return j;
      j = (int)(paramInt / f);
      if (paramInt == 0)
        break;
    }
    while (j == 0);
    return j;
    label102: return j;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohutv.tv.player.util.a
 * JD-Core Version:    0.6.2
 */