package com.starcor.core.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil
{
  private static CharSequence oldMsg;
  private static long oneTime = 0L;
  protected static Toast toast = null;
  private static long twoTime = 0L;

  public static void showToast(Context paramContext, int paramInt)
  {
    showToast(paramContext, paramContext.getString(paramInt));
  }

  public static void showToast(Context paramContext, CharSequence paramCharSequence)
  {
    if (toast == null)
    {
      toast = Toast.makeText(paramContext, paramCharSequence, 0);
      toast.show();
      oneTime = System.currentTimeMillis();
    }
    while (true)
    {
      oneTime = twoTime;
      return;
      twoTime = System.currentTimeMillis();
      if (paramCharSequence.equals(oldMsg))
      {
        if (twoTime - oneTime > 0L)
          toast.show();
      }
      else
      {
        oldMsg = paramCharSequence;
        toast.setText(paramCharSequence);
        toast.show();
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.utils.ToastUtil
 * JD-Core Version:    0.6.2
 */