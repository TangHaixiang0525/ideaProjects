package com.sohutv.tv.player.interfaces;

import android.media.MediaPlayer;
import com.tianci.framework.player.kernel.jni.SkyBjPlayer;

public abstract interface ISkyworthPlayerCallback extends PlayerCallback
{
  public abstract void onBufferingUpdate(MediaPlayer paramMediaPlayer, int paramInt);

  public abstract void onCompletion(SkyBjPlayer paramSkyBjPlayer);

  public abstract boolean onError(SkyBjPlayer paramSkyBjPlayer, int paramInt1, int paramInt2);

  public abstract boolean onInfo(SkyBjPlayer paramSkyBjPlayer, int paramInt1, int paramInt2);

  public abstract void onPrepared(SkyBjPlayer paramSkyBjPlayer);

  public abstract void onVideoSizeChanged(MediaPlayer paramMediaPlayer, int paramInt1, int paramInt2);
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohutv.tv.player.interfaces.ISkyworthPlayerCallback
 * JD-Core Version:    0.6.2
 */