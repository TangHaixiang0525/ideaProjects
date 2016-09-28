package com.sohutv.tv.player.interfaces;

import android.media.MediaPlayer;

public abstract interface ISohuTVPlayerCallback extends PlayerCallback
{
  public abstract void onBufferingUpdate(MediaPlayer paramMediaPlayer, int paramInt);

  public abstract void onCompletion(MediaPlayer paramMediaPlayer);

  public abstract boolean onError(MediaPlayer paramMediaPlayer, int paramInt1, int paramInt2);

  public abstract boolean onInfo(MediaPlayer paramMediaPlayer, int paramInt1, int paramInt2);

  public abstract void onPrepared(MediaPlayer paramMediaPlayer);

  public abstract void onVideoSizeChanged(MediaPlayer paramMediaPlayer, int paramInt1, int paramInt2);
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohutv.tv.player.interfaces.ISohuTVPlayerCallback
 * JD-Core Version:    0.6.2
 */