package com.sohu.app.ads.sdk.iterface;

import com.sohu.app.ads.sdk.model.VideoProgressUpdate;

public abstract interface IVideoAdPlayer
{
  public abstract void addCallback(IVideoAdPlayerCallback paramIVideoAdPlayerCallback);

  public abstract int getCurrentPos();

  public abstract VideoProgressUpdate getProgress();

  public abstract void loadAd(String paramString);

  public abstract void loadAd(String paramString, int paramInt);

  public abstract void onBufferedComplete();

  public abstract void pauseAd();

  public abstract void playAd();

  public abstract boolean playing();

  public abstract void removeCallback(IVideoAdPlayerCallback paramIVideoAdPlayerCallback);

  public abstract void resumeAd();

  public abstract void stopAd();
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.app.ads.sdk.iterface.IVideoAdPlayer
 * JD-Core Version:    0.6.2
 */