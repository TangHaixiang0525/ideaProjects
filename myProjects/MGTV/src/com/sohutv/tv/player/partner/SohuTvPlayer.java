package com.sohutv.tv.player.partner;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceView;
import com.sohutv.tv.player.entity.PlayInfo;
import com.sohutv.tv.player.interfaces.ISohuMediaControllerCallback;
import com.sohutv.tv.player.interfaces.PlayerCallback;
import com.sohutv.tv.player.interfacesimpl.SohuPlayer;
import com.sohutv.tv.player.play.SohuMediaController;

public final class SohuTvPlayer extends SohuPlayer
{
  public SohuTvPlayer(Context paramContext)
  {
    super(paramContext);
  }

  public SohuTvPlayer(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public SohuTvPlayer(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  public int getCurrentPosition()
  {
    return super.getCurrentPosition();
  }

  public int getDuration()
  {
    return super.getDuration();
  }

  public PlayInfo getPLayInfo()
  {
    return super.getPLayInfo();
  }

  public SurfaceView getSurfaceView()
  {
    return super.getSurfaceView();
  }

  public int getVideoHeight()
  {
    return super.getVideoHeight();
  }

  public int getVideoWidth()
  {
    return super.getVideoWidth();
  }

  public boolean isAdPlaying()
  {
    return super.isAdPlaying();
  }

  public boolean isIgnoreSurfaceDestory()
  {
    return super.isIgnoreSurfaceDestory();
  }

  public boolean isPlaying()
  {
    return super.isPlaying();
  }

  @Deprecated
  public void onActivityResume(Activity paramActivity)
  {
    super.onActivityResume(paramActivity);
  }

  @Deprecated
  public void onActivityStop(Activity paramActivity)
  {
    super.onActivityStop(paramActivity);
  }

  public void onLogResume(Activity paramActivity)
  {
    super.onActivityResume(paramActivity);
  }

  public void onLogStop(Activity paramActivity)
  {
    super.onActivityStop(paramActivity);
  }

  public void pause()
  {
    super.pause();
  }

  public void release()
  {
    super.stop();
  }

  public void resume()
  {
    super.resume();
  }

  public void seekTo(int paramInt)
  {
    super.seekTo(paramInt);
  }

  public void selectTrack(int paramInt)
  {
    super.selectTrack(paramInt);
  }

  public void setDefinition(int paramInt1, int paramInt2)
  {
    super.setDefinition(paramInt1, paramInt2);
  }

  public void setDimension(int paramInt1, int paramInt2)
  {
    super.setDimension(paramInt1, paramInt2);
  }

  public void setDimensionFullScreen()
  {
    super.setDimensionFullScreen();
  }

  public void setDimensionOriginal()
  {
    super.setDimensionOriginal();
  }

  public void setDimensionVideoBigest()
  {
    setDimensionOriginal();
  }

  public void setDimension_16_9()
  {
    super.setDimension_16_9();
  }

  public void setDimension_4_3()
  {
    super.setDimension_4_3();
  }

  public void setDimesionAuto()
  {
    super.setDimesionAuto();
  }

  public void setIgnoreSurfaceDestory(boolean paramBoolean)
  {
    super.setIgnoreSurfaceDestory(paramBoolean);
  }

  public void setMediaController(SohuMediaController paramSohuMediaController, ISohuMediaControllerCallback paramISohuMediaControllerCallback)
  {
    super.setMediaController(paramSohuMediaController, paramISohuMediaControllerCallback);
  }

  public void setPauseADTopMarginPercent(int paramInt)
  {
    super.setPauseADTopMarginPercent(paramInt);
  }

  public void setPlayerCallback(PlayerCallback paramPlayerCallback)
  {
    super.setPlayerCallback(paramPlayerCallback);
  }

  public void setVideoParam(String paramString)
  {
    super.setVideoParam(paramString);
  }

  public void start()
  {
    super.start();
  }

  public void stop()
  {
    super.stop();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohutv.tv.player.partner.SohuTvPlayer
 * JD-Core Version:    0.6.2
 */