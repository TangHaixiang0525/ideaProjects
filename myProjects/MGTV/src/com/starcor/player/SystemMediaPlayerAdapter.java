package com.starcor.player;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.net.Uri;
import com.starcor.core.utils.Logger;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class SystemMediaPlayerAdapter extends MediaPlayerAdapter
{
  public static final boolean IS_SUPPORT_AUDIO_TRACK_SETTING = false;
  private static final String TAG = "SystemMediaPlayerAdapter";
  List<String> audioTrackData = new ArrayList();
  private Method getAudioTracksMethod = null;
  private Method getSubtitlesMethod = null;
  MediaPlayer mediaPlayer;
  private Class<?> mediaPlayerClazz = null;
  private Method setAudioTrackMethod = null;
  private Method setSubtitleMethod = null;
  String[] subtitleData = null;

  static
  {
    if ((hasMethod(MediaPlayer.class, "getSubtitles", new Class[0])) && (hasMethod(MediaPlayer.class, "getSoundtracks", new Class[0])));
    for (boolean bool = true; ; bool = false)
    {
      IS_SUPPORT_AUDIO_TRACK_SETTING = bool;
      return;
    }
  }

  private SystemMediaPlayerAdapter(MediaPlayer paramMediaPlayer)
  {
    this.mediaPlayer = paramMediaPlayer;
    if (this.mediaPlayer == null)
      Logger.i("SystemMediaPlayerAdapter", "SystemMediaPlayerAdapter() mediaPlayer is null");
    do
    {
      return;
      Logger.i("SystemMediaPlayerAdapter", "IS_SUPPORT_AUDIO_TRACK_SETTING = " + IS_SUPPORT_AUDIO_TRACK_SETTING);
    }
    while (!IS_SUPPORT_AUDIO_TRACK_SETTING);
    try
    {
      this.mediaPlayerClazz = paramMediaPlayer.getClass();
      this.getAudioTracksMethod = this.mediaPlayerClazz.getMethod("getSoundtracks", new Class[0]);
      this.setAudioTrackMethod = this.mediaPlayerClazz.getMethod("setSoundtrack", new Class[] { String.class });
      this.getSubtitlesMethod = this.mediaPlayerClazz.getMethod("getSubtitles", new Class[0]);
      this.setSubtitleMethod = this.mediaPlayerClazz.getMethod("setSubtitle", new Class[] { String.class });
      return;
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      localNoSuchMethodException.printStackTrace();
    }
  }

  private static boolean hasMethod(Class<?> paramClass, String paramString, Class<?>[] paramArrayOfClass)
  {
    try
    {
      paramClass.getDeclaredMethod(paramString, paramArrayOfClass);
      return true;
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
    }
    return false;
  }

  public static void registerFactory()
  {
    MediaPlayerAdapter.registerAdapterFactory(new MediaPlayerAdapter.AdapterFactory()
    {
      public MediaPlayerAdapter create(MediaPlayerSurfaceSite paramAnonymousMediaPlayerSurfaceSite)
      {
        MediaPlayer localMediaPlayer1;
        try
        {
          CommonSurfaceAdapter.SurfaceHolderFromSurfaceSite(paramAnonymousMediaPlayerSurfaceSite);
          MediaPlayer localMediaPlayer2 = new MediaPlayer();
          localMediaPlayer1 = localMediaPlayer2;
          if (localMediaPlayer1 == null)
            return null;
        }
        catch (Exception localException)
        {
          while (true)
          {
            localException.printStackTrace();
            localMediaPlayer1 = null;
          }
        }
        return new SystemMediaPlayerAdapter(localMediaPlayer1, null);
      }

      public boolean is_class(Class<?> paramAnonymousClass)
      {
        return paramAnonymousClass.getName().equals(SystemMediaPlayerAdapter.class.getName());
      }

      public void prepareSurface(MediaPlayerSurfaceSite paramAnonymousMediaPlayerSurfaceSite)
      {
        CommonSurfaceAdapter.SurfaceHolderFromSurfaceSite(paramAnonymousMediaPlayerSurfaceSite);
      }
    });
  }

  public String[] getAudioTrackData()
  {
    return null;
  }

  public int getCurrentPosition()
  {
    return this.mediaPlayer.getCurrentPosition();
  }

  public int getDuration()
  {
    return this.mediaPlayer.getDuration();
  }

  public String[] getSubtitles()
  {
    return null;
  }

  public int getVideoHeight()
  {
    return this.mediaPlayer.getVideoHeight();
  }

  public int getVideoWidth()
  {
    return this.mediaPlayer.getVideoWidth();
  }

  public boolean isLooping()
  {
    return this.mediaPlayer.isLooping();
  }

  public boolean isPlaying()
  {
    return this.mediaPlayer.isPlaying();
  }

  public void pause()
    throws IllegalStateException
  {
    this.mediaPlayer.pause();
  }

  public void prepare()
    throws IOException, IllegalStateException
  {
    this.mediaPlayer.prepare();
  }

  public void prepareAsync()
    throws IllegalStateException
  {
    this.mediaPlayer.prepareAsync();
  }

  public void release()
  {
    this.mediaPlayer.release();
  }

  public void reset()
  {
    this.mediaPlayer.reset();
  }

  public void seekTo(int paramInt)
    throws IllegalStateException
  {
    this.mediaPlayer.seekTo(paramInt);
  }

  public void setAudioStreamType(int paramInt)
  {
    this.mediaPlayer.setAudioStreamType(paramInt);
  }

  public void setAudioTrack(int paramInt)
  {
  }

  public void setDataSource(Context paramContext, String paramString)
    throws IOException, IllegalArgumentException, SecurityException, IllegalStateException
  {
    this.mediaPlayer.setDataSource(paramContext, Uri.parse(paramString));
  }

  public void setDisplay(MediaPlayerSurfaceAdapter paramMediaPlayerSurfaceAdapter)
  {
    CommonSurfaceAdapter localCommonSurfaceAdapter = (CommonSurfaceAdapter)paramMediaPlayerSurfaceAdapter;
    this.mediaPlayer.setDisplay(localCommonSurfaceAdapter.getSurfaceHolder());
  }

  public void setLooping(boolean paramBoolean)
  {
    this.mediaPlayer.setLooping(paramBoolean);
  }

  public void setOnBufferingUpdateListener(final MediaPlayerAdapter.OnBufferingUpdateListener paramOnBufferingUpdateListener)
  {
    if (paramOnBufferingUpdateListener == null)
      this.mediaPlayer.setOnBufferingUpdateListener(null);
    this.mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener()
    {
      public void onBufferingUpdate(MediaPlayer paramAnonymousMediaPlayer, int paramAnonymousInt)
      {
        if (paramOnBufferingUpdateListener == null)
          return;
        paramOnBufferingUpdateListener.onBufferingUpdate(SystemMediaPlayerAdapter.this, paramAnonymousInt);
      }
    });
  }

  public void setOnCompletionListener(final MediaPlayerAdapter.OnCompletionListener paramOnCompletionListener)
  {
    if (paramOnCompletionListener == null)
      this.mediaPlayer.setOnCompletionListener(null);
    this.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
    {
      public void onCompletion(MediaPlayer paramAnonymousMediaPlayer)
      {
        if (paramOnCompletionListener == null)
          return;
        paramOnCompletionListener.onCompletion(SystemMediaPlayerAdapter.this);
      }
    });
  }

  public void setOnErrorListener(final MediaPlayerAdapter.OnErrorListener paramOnErrorListener)
  {
    if (paramOnErrorListener == null)
      this.mediaPlayer.setOnErrorListener(null);
    this.mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener()
    {
      public boolean onError(MediaPlayer paramAnonymousMediaPlayer, int paramAnonymousInt1, int paramAnonymousInt2)
      {
        if (paramOnErrorListener == null)
          return false;
        return paramOnErrorListener.onError(SystemMediaPlayerAdapter.this, paramAnonymousInt1, paramAnonymousInt2);
      }
    });
  }

  public void setOnInfoListener(final MediaPlayerAdapter.OnInfoListener paramOnInfoListener)
  {
    if (paramOnInfoListener == null)
      this.mediaPlayer.setOnInfoListener(null);
    this.mediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener()
    {
      public boolean onInfo(MediaPlayer paramAnonymousMediaPlayer, int paramAnonymousInt1, int paramAnonymousInt2)
      {
        if (paramOnInfoListener == null)
          return false;
        return paramOnInfoListener.onInfo(SystemMediaPlayerAdapter.this, paramAnonymousInt1, paramAnonymousInt2);
      }
    });
  }

  public void setOnPreparedListener(final MediaPlayerAdapter.OnPreparedListener paramOnPreparedListener)
  {
    if (paramOnPreparedListener == null)
      this.mediaPlayer.setOnPreparedListener(null);
    this.mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
    {
      public void onPrepared(MediaPlayer paramAnonymousMediaPlayer)
      {
        if (paramOnPreparedListener == null)
          return;
        paramOnPreparedListener.onPrepared(SystemMediaPlayerAdapter.this);
      }
    });
  }

  public void setOnSeekCompleteListener(final MediaPlayerAdapter.OnSeekCompleteListener paramOnSeekCompleteListener)
  {
    if (paramOnSeekCompleteListener == null)
      this.mediaPlayer.setOnSeekCompleteListener(null);
    this.mediaPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener()
    {
      public void onSeekComplete(MediaPlayer paramAnonymousMediaPlayer)
      {
        if (paramOnSeekCompleteListener == null)
          return;
        paramOnSeekCompleteListener.onSeekComplete(SystemMediaPlayerAdapter.this);
      }
    });
  }

  public void setOnVideoSizeChangedListener(final MediaPlayerAdapter.OnVideoSizeChangedListener paramOnVideoSizeChangedListener)
  {
    if (paramOnVideoSizeChangedListener == null)
      this.mediaPlayer.setOnVideoSizeChangedListener(null);
    this.mediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener()
    {
      public void onVideoSizeChanged(MediaPlayer paramAnonymousMediaPlayer, int paramAnonymousInt1, int paramAnonymousInt2)
      {
        if (paramOnVideoSizeChangedListener == null)
          return;
        paramOnVideoSizeChangedListener.onVideoSizeChanged(SystemMediaPlayerAdapter.this, paramAnonymousInt1, paramAnonymousInt2);
      }
    });
  }

  public void setScreenOnWhilePlaying(boolean paramBoolean)
  {
    this.mediaPlayer.setScreenOnWhilePlaying(paramBoolean);
  }

  public void setSoundTrack(int paramInt)
  {
  }

  public void setSubtitle(int paramInt)
  {
  }

  public void setVolume(float paramFloat1, float paramFloat2)
  {
    this.mediaPlayer.setVolume(paramFloat1, paramFloat2);
  }

  public void start(boolean paramBoolean)
    throws IllegalStateException
  {
    this.mediaPlayer.start();
  }

  public void stop()
    throws IllegalStateException
  {
    this.mediaPlayer.stop();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.player.SystemMediaPlayerAdapter
 * JD-Core Version:    0.6.2
 */