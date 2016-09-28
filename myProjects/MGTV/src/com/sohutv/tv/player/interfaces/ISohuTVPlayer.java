package com.sohutv.tv.player.interfaces;

import android.view.SurfaceView;
import com.sohutv.tv.player.play.SohuMediaController;

public abstract interface ISohuTVPlayer extends ISohuOttPlayer
{
  public abstract int getSCurrentPosition();

  public abstract int getSDuration();

  public abstract int getSVideoHeight();

  public abstract int getSVideoWidth();

  public abstract SurfaceView getSurfaceView();

  public abstract boolean isSPlaying();

  public abstract void pauseSPlay();

  public abstract void resumeSPlay();

  public abstract void selectTrack(int paramInt);

  public abstract void setDimension(int paramInt1, int paramInt2);

  public abstract void setDimensionAuto();

  public abstract void setDimensionFullScreen();

  public abstract void setDimensionOriginal();

  public abstract void setDimension_16_9();

  public abstract void setDimension_4_3();

  public abstract void setSMediaController(SohuMediaController paramSohuMediaController, ISohuMediaControllerCallback paramISohuMediaControllerCallback);

  public abstract void setVideoListener(IVideoListener paramIVideoListener);

  public abstract void setmIPlayerCallback(PlayerCallback paramPlayerCallback);

  public abstract void stopSPlay();
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohutv.tv.player.interfaces.ISohuTVPlayer
 * JD-Core Version:    0.6.2
 */