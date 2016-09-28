package com.sohutv.tv.player.interfaces;

import android.graphics.Bitmap;

public abstract interface ISohuStartAdCallback
{
  public abstract Bitmap getDefaultOpenBitmap();

  public abstract boolean hasDefaultOpenView();

  public abstract boolean isCustomedTimeView();

  public abstract boolean isDelayedDisappear();

  public abstract void onEnd();

  public abstract void remainTime(int paramInt);
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohutv.tv.player.interfaces.ISohuStartAdCallback
 * JD-Core Version:    0.6.2
 */