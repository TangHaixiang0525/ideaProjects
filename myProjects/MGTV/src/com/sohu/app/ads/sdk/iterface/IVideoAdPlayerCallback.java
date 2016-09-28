package com.sohu.app.ads.sdk.iterface;

import android.view.MotionEvent;
import android.view.View;

public abstract interface IVideoAdPlayerCallback
{
  public abstract void adClicked();

  public abstract void isPlaying();

  public abstract void onBufferedComplete();

  public abstract void onEnded();

  public abstract void onError();

  public abstract void onPause();

  public abstract void onPlay();

  public abstract void onResume();

  public abstract void onTouch(View paramView, MotionEvent paramMotionEvent);

  public abstract void onVolumeChanged(int paramInt);
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.app.ads.sdk.iterface.IVideoAdPlayerCallback
 * JD-Core Version:    0.6.2
 */