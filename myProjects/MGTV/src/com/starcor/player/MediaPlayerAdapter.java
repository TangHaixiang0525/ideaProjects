package com.starcor.player;

import android.content.Context;
import com.starcor.config.AppFuncCfg;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class MediaPlayerAdapter
{
  private static ArrayList<AdapterFactory> factories = new ArrayList();

  public static MediaPlayerAdapter create(MediaPlayerSurfaceSite paramMediaPlayerSurfaceSite)
  {
    return create(paramMediaPlayerSurfaceSite, null, null);
  }

  public static MediaPlayerAdapter create(MediaPlayerSurfaceSite paramMediaPlayerSurfaceSite, Class<?> paramClass1, Class<?> paramClass2)
  {
    Iterator localIterator = factories.iterator();
    while (localIterator.hasNext())
    {
      AdapterFactory localAdapterFactory = (AdapterFactory)localIterator.next();
      if (((paramClass1 == null) || (localAdapterFactory.is_class(paramClass1))) && ((paramClass2 == null) || (!localAdapterFactory.is_class(paramClass2))))
      {
        Object localObject = localAdapterFactory.create(paramMediaPlayerSurfaceSite);
        if (localObject != null)
        {
          if ((!AppFuncCfg.FUNCTION_OTTTV_PROXY) && (AppFuncCfg.FUNCTION_ENABLE_HEARTBEAT))
            localObject = new HeartbeatMediaPlayerAdapter((MediaPlayerAdapter)localObject);
          return localObject;
        }
      }
    }
    return null;
  }

  public static void prepareSurface(MediaPlayerSurfaceSite paramMediaPlayerSurfaceSite)
  {
    prepareSurface(paramMediaPlayerSurfaceSite, null, null);
  }

  public static void prepareSurface(MediaPlayerSurfaceSite paramMediaPlayerSurfaceSite, Class<?> paramClass1, Class<?> paramClass2)
  {
    Iterator localIterator = factories.iterator();
    while (localIterator.hasNext())
    {
      AdapterFactory localAdapterFactory = (AdapterFactory)localIterator.next();
      if (((paramClass1 == null) || (localAdapterFactory.is_class(paramClass1))) && ((paramClass2 == null) || (!localAdapterFactory.is_class(paramClass2))))
        localAdapterFactory.prepareSurface(paramMediaPlayerSurfaceSite);
    }
  }

  public static void registerAdapterFactory(AdapterFactory paramAdapterFactory)
  {
    factories.add(paramAdapterFactory);
  }

  public abstract String[] getAudioTrackData();

  public abstract int getCurrentPosition();

  public abstract int getDuration();

  public abstract String[] getSubtitles();

  public abstract int getVideoHeight();

  public abstract int getVideoWidth();

  public abstract boolean isLooping();

  public abstract boolean isPlaying();

  public abstract void pause()
    throws IllegalStateException;

  public abstract void prepare()
    throws IOException, IllegalStateException;

  public abstract void prepareAsync()
    throws IllegalStateException;

  public abstract void release();

  public abstract void reset();

  public void resetVideoLayout()
  {
  }

  public abstract void seekTo(int paramInt)
    throws IllegalStateException;

  public abstract void setAudioStreamType(int paramInt);

  public abstract void setAudioTrack(int paramInt);

  public abstract void setDataSource(Context paramContext, String paramString)
    throws IOException, IllegalArgumentException, SecurityException, IllegalStateException;

  public abstract void setDisplay(MediaPlayerSurfaceAdapter paramMediaPlayerSurfaceAdapter);

  public abstract void setLooping(boolean paramBoolean);

  public abstract void setOnBufferingUpdateListener(OnBufferingUpdateListener paramOnBufferingUpdateListener);

  public abstract void setOnCompletionListener(OnCompletionListener paramOnCompletionListener);

  public abstract void setOnErrorListener(OnErrorListener paramOnErrorListener);

  public abstract void setOnInfoListener(OnInfoListener paramOnInfoListener);

  public abstract void setOnPreparedListener(OnPreparedListener paramOnPreparedListener);

  public abstract void setOnSeekCompleteListener(OnSeekCompleteListener paramOnSeekCompleteListener);

  public abstract void setOnVideoSizeChangedListener(OnVideoSizeChangedListener paramOnVideoSizeChangedListener);

  public abstract void setScreenOnWhilePlaying(boolean paramBoolean);

  public abstract void setSoundTrack(int paramInt);

  public abstract void setSubtitle(int paramInt);

  public abstract void setVolume(float paramFloat1, float paramFloat2);

  public abstract void start(boolean paramBoolean)
    throws IllegalStateException;

  public abstract void stop()
    throws IllegalStateException;

  public static abstract interface AdapterFactory
  {
    public abstract MediaPlayerAdapter create(MediaPlayerSurfaceSite paramMediaPlayerSurfaceSite);

    public abstract boolean is_class(Class<?> paramClass);

    public abstract void prepareSurface(MediaPlayerSurfaceSite paramMediaPlayerSurfaceSite);
  }

  public static abstract interface OnBufferingUpdateListener
  {
    public abstract void onBufferingUpdate(MediaPlayerAdapter paramMediaPlayerAdapter, int paramInt);
  }

  public static abstract interface OnCompletionListener
  {
    public abstract void onCompletion(MediaPlayerAdapter paramMediaPlayerAdapter);
  }

  public static abstract interface OnErrorListener
  {
    public abstract boolean onError(MediaPlayerAdapter paramMediaPlayerAdapter, int paramInt1, int paramInt2);
  }

  public static abstract interface OnInfoListener
  {
    public abstract boolean onInfo(MediaPlayerAdapter paramMediaPlayerAdapter, int paramInt1, int paramInt2);
  }

  public static abstract interface OnPreparedListener
  {
    public abstract void onPrepared(MediaPlayerAdapter paramMediaPlayerAdapter);
  }

  public static abstract interface OnSeekCompleteListener
  {
    public abstract void onSeekComplete(MediaPlayerAdapter paramMediaPlayerAdapter);
  }

  public static abstract interface OnVideoSizeChangedListener
  {
    public abstract void onVideoSizeChanged(MediaPlayerAdapter paramMediaPlayerAdapter, int paramInt1, int paramInt2);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.player.MediaPlayerAdapter
 * JD-Core Version:    0.6.2
 */