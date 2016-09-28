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
import java.io.IOException;

public class TCLRT2995MediaPlayerAdapter extends MediaPlayerAdapter
{
  int _currentPosition = 0;
  int _druation = 0;
  MediaPlayer mediaPlayer;

  private TCLRT2995MediaPlayerAdapter(MediaPlayer paramMediaPlayer)
  {
    this.mediaPlayer = paramMediaPlayer;
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
        return new TCLRT2995MediaPlayerAdapter(localMediaPlayer1, null);
      }

      public boolean is_class(Class<?> paramAnonymousClass)
      {
        return paramAnonymousClass.getName().equals(TCLRT2995MediaPlayerAdapter.class.getName());
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
    this._currentPosition = this.mediaPlayer.getCurrentPosition();
    return this._currentPosition;
  }

  public int getDuration()
  {
    this._druation = this.mediaPlayer.getDuration();
    return this._druation;
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
    this._currentPosition = paramInt;
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
        int i = TCLRT2995MediaPlayerAdapter.this._druation;
        int j = 0;
        if (i > 0)
        {
          int k = TCLRT2995MediaPlayerAdapter.this._druation;
          int m = TCLRT2995MediaPlayerAdapter.this._currentPosition;
          j = 0;
          if (k > m)
            j = 100 * TCLRT2995MediaPlayerAdapter.this._currentPosition / TCLRT2995MediaPlayerAdapter.this._druation;
        }
        paramOnBufferingUpdateListener.onBufferingUpdate(TCLRT2995MediaPlayerAdapter.this, j + paramAnonymousInt);
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
        paramOnCompletionListener.onCompletion(TCLRT2995MediaPlayerAdapter.this);
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
        return paramOnErrorListener.onError(TCLRT2995MediaPlayerAdapter.this, paramAnonymousInt1, paramAnonymousInt2);
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
        return paramOnInfoListener.onInfo(TCLRT2995MediaPlayerAdapter.this, paramAnonymousInt1, paramAnonymousInt2);
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
        TCLRT2995MediaPlayerAdapter.this._druation = paramAnonymousMediaPlayer.getDuration();
        TCLRT2995MediaPlayerAdapter.this._currentPosition = paramAnonymousMediaPlayer.getCurrentPosition();
        paramOnPreparedListener.onPrepared(TCLRT2995MediaPlayerAdapter.this);
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
        TCLRT2995MediaPlayerAdapter.this._currentPosition = paramAnonymousMediaPlayer.getCurrentPosition();
        paramOnSeekCompleteListener.onSeekComplete(TCLRT2995MediaPlayerAdapter.this);
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
        paramOnVideoSizeChangedListener.onVideoSizeChanged(TCLRT2995MediaPlayerAdapter.this, paramAnonymousInt1, paramAnonymousInt2);
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
 * Qualified Name:     com.starcor.player.TCLRT2995MediaPlayerAdapter
 * JD-Core Version:    0.6.2
 */