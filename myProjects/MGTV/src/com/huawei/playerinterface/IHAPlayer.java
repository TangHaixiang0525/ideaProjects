package com.huawei.playerinterface;

import android.content.Context;
import android.view.SurfaceView;
import com.huawei.playerinterface.parameter.HAGetParam;
import com.huawei.playerinterface.parameter.HASetParam;
import java.io.IOException;

public abstract interface IHAPlayer
{
  public abstract int getCurrentPosition();

  public abstract int getDuration();

  public abstract Object getProperties(HAGetParam paramHAGetParam);

  public abstract int getVideoHeight();

  public abstract int getVideoWidth();

  public abstract boolean isPlaying();

  public abstract void pause();

  public abstract void prepare();

  public abstract void prepareAsync();

  public abstract void release();

  public abstract void resume();

  public abstract void resume(int paramInt);

  public abstract void seekTo(int paramInt);

  public abstract void seekTo(int paramInt1, int paramInt2);

  public abstract void setDataSource(Context paramContext, String paramString)
    throws IOException, IllegalArgumentException, SecurityException, IllegalStateException;

  public abstract void setDisplay(SurfaceView paramSurfaceView);

  public abstract void setOnBufferingUpdateListener(OnBufferingUpdateListener paramOnBufferingUpdateListener);

  public abstract void setOnCompletionListener(OnCompletionListener paramOnCompletionListener);

  public abstract void setOnErrorListener(OnErrorListener paramOnErrorListener);

  public abstract void setOnInfoListener(OnInfoListener paramOnInfoListener);

  public abstract void setOnPreparedListener(OnPreparedListener paramOnPreparedListener);

  public abstract void setOnSeekCompleteListener(OnSeekCompleteListener paramOnSeekCompleteListener);

  public abstract void setOnVideoSizeChangedListener(OnVideoSizeChangedListener paramOnVideoSizeChangedListener);

  public abstract int setProperties(HASetParam paramHASetParam, Object paramObject);

  public abstract void start();

  public abstract void start(int paramInt);

  public abstract void stop();

  public abstract void suspend();

  public static abstract interface OnBufferingUpdateListener
  {
    public abstract void onBufferingUpdate(IHAPlayer paramIHAPlayer, int paramInt);
  }

  public static abstract interface OnCompletionListener
  {
    public abstract void onCompletion(IHAPlayer paramIHAPlayer);
  }

  public static abstract interface OnErrorListener
  {
    public abstract boolean onError(IHAPlayer paramIHAPlayer, int paramInt1, int paramInt2);
  }

  public static abstract interface OnInfoListener
  {
    public abstract boolean onInfo(IHAPlayer paramIHAPlayer, int paramInt1, int paramInt2);
  }

  public static abstract interface OnPreparedListener
  {
    public abstract void onPrepared(IHAPlayer paramIHAPlayer);
  }

  public static abstract interface OnSeekCompleteListener
  {
    public abstract void onSeekComplete(IHAPlayer paramIHAPlayer);
  }

  public static abstract interface OnVideoSizeChangedListener
  {
    public abstract void onVideoSizeChanged(IHAPlayer paramIHAPlayer, int paramInt1, int paramInt2);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.huawei.playerinterface.IHAPlayer
 * JD-Core Version:    0.6.2
 */