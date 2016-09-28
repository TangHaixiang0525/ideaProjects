package com.starcor.player;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.SurfaceHolder;
import com.sohutv.tv.player.interfaces.ISohuTVPlayerCallback;
import com.sohutv.tv.player.partner.SohuTvPlayer;
import com.starcor.core.utils.Logger;
import java.io.IOException;

public class SohuPlayerAdapter extends MediaPlayerAdapter
{
  private static final String TAG = "SohuPlayerAdapter";
  MediaPlayerAdapter.OnBufferingUpdateListener _bufferingListener;
  MediaPlayerAdapter.OnCompletionListener _completionListener;
  MediaPlayerAdapter.OnErrorListener _errorListener;
  MediaPlayerAdapter.OnInfoListener _infoListener;
  MediaPlayerAdapter.OnPreparedListener _prepareListener;
  MediaPlayerAdapter.OnSeekCompleteListener _seekCompleteListener;
  SohuTvPlayer _sohuPlayer;
  String _url;
  MediaPlayerAdapter.OnVideoSizeChangedListener _videoSizeChangedListener;
  private boolean isFirstSeekListener = true;

  public SohuPlayerAdapter(SohuTvPlayer paramSohuTvPlayer)
  {
    this._sohuPlayer = paramSohuTvPlayer;
    this._sohuPlayer.setDimensionVideoBigest();
    this._sohuPlayer.setPlayerCallback(new ISohuTVPlayerCallback()
    {
      public void OnSeekCompleteListener()
      {
        MediaPlayerAdapter.OnSeekCompleteListener localOnSeekCompleteListener = SohuPlayerAdapter.this._seekCompleteListener;
        if (localOnSeekCompleteListener == null)
          return;
        if (SohuPlayerAdapter.this.isFirstSeekListener)
        {
          Logger.i("SohuPlayerAdapter", "搜狐播放器设置地址成功之后，会默认回调一次onSeekComplete，所以第一次不处理这个回调。");
          return;
        }
        localOnSeekCompleteListener.onSeekComplete(SohuPlayerAdapter.this);
      }

      public void adRemainTime(int paramAnonymousInt)
      {
      }

      public void onBufferingUpdate(MediaPlayer paramAnonymousMediaPlayer, int paramAnonymousInt)
      {
        MediaPlayerAdapter.OnBufferingUpdateListener localOnBufferingUpdateListener = SohuPlayerAdapter.this._bufferingListener;
        if (localOnBufferingUpdateListener == null)
          return;
        localOnBufferingUpdateListener.onBufferingUpdate(SohuPlayerAdapter.this, paramAnonymousInt);
      }

      public void onCompletion(MediaPlayer paramAnonymousMediaPlayer)
      {
        MediaPlayerAdapter.OnCompletionListener localOnCompletionListener = SohuPlayerAdapter.this._completionListener;
        if (localOnCompletionListener == null)
          return;
        localOnCompletionListener.onCompletion(SohuPlayerAdapter.this);
      }

      public boolean onError(MediaPlayer paramAnonymousMediaPlayer, int paramAnonymousInt1, int paramAnonymousInt2)
      {
        MediaPlayerAdapter.OnErrorListener localOnErrorListener = SohuPlayerAdapter.this._errorListener;
        if (localOnErrorListener == null)
          return false;
        return localOnErrorListener.onError(SohuPlayerAdapter.this, paramAnonymousInt1, paramAnonymousInt2);
      }

      public boolean onInfo(MediaPlayer paramAnonymousMediaPlayer, int paramAnonymousInt1, int paramAnonymousInt2)
      {
        MediaPlayerAdapter.OnInfoListener localOnInfoListener = SohuPlayerAdapter.this._infoListener;
        if (localOnInfoListener == null)
          return false;
        return localOnInfoListener.onInfo(SohuPlayerAdapter.this, paramAnonymousInt1, paramAnonymousInt2);
      }

      public void onPrepared(MediaPlayer paramAnonymousMediaPlayer)
      {
        MediaPlayerAdapter.OnPreparedListener localOnPreparedListener = SohuPlayerAdapter.this._prepareListener;
        if (localOnPreparedListener == null)
          return;
        localOnPreparedListener.onPrepared(SohuPlayerAdapter.this);
      }

      public void onVideoSizeChanged(MediaPlayer paramAnonymousMediaPlayer, int paramAnonymousInt1, int paramAnonymousInt2)
      {
        MediaPlayerAdapter.OnVideoSizeChangedListener localOnVideoSizeChangedListener = SohuPlayerAdapter.this._videoSizeChangedListener;
        if (localOnVideoSizeChangedListener == null)
          return;
        localOnVideoSizeChangedListener.onVideoSizeChanged(SohuPlayerAdapter.this, paramAnonymousInt1, paramAnonymousInt2);
      }

      public void surfaceChanged(SurfaceHolder paramAnonymousSurfaceHolder, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
      {
        Logger.i("SohuPlayerAdapter", "SohuPlayer   surfaceChanged ");
      }

      public void surfaceCreated(SurfaceHolder paramAnonymousSurfaceHolder)
      {
        Logger.i("SohuPlayerAdapter", "SohuPlayer   surfaceCreated ");
      }

      public void surfaceDestroyed(SurfaceHolder paramAnonymousSurfaceHolder)
      {
        Logger.i("SohuPlayerAdapter", "SohuPlayer   surfaceDestroyed ");
      }

      public void throwableCallBack(Throwable paramAnonymousThrowable)
      {
      }
    });
  }

  public static void registerFactory()
  {
    MediaPlayerAdapter.registerAdapterFactory(new MediaPlayerAdapter.AdapterFactory()
    {
      public MediaPlayerAdapter create(MediaPlayerSurfaceSite paramAnonymousMediaPlayerSurfaceSite)
      {
        SohuTvPlayer localSohuTvPlayer1;
        try
        {
          SohuTvPlayer localSohuTvPlayer2 = SohuSurfaceAdapter.SohuPlayerFromSurfaceSite(paramAnonymousMediaPlayerSurfaceSite);
          localSohuTvPlayer1 = localSohuTvPlayer2;
          if (localSohuTvPlayer1 == null)
            return null;
        }
        catch (Exception localException)
        {
          while (true)
          {
            localException.printStackTrace();
            localSohuTvPlayer1 = null;
          }
        }
        return new SohuPlayerAdapter(localSohuTvPlayer1);
      }

      public boolean is_class(Class<?> paramAnonymousClass)
      {
        return paramAnonymousClass.getName().equals(SohuPlayerAdapter.class.getName());
      }

      public void prepareSurface(MediaPlayerSurfaceSite paramAnonymousMediaPlayerSurfaceSite)
      {
        SohuSurfaceAdapter.SohuPlayerFromSurfaceSite(paramAnonymousMediaPlayerSurfaceSite);
      }
    });
  }

  public String[] getAudioTrackData()
  {
    return new String[0];
  }

  public int getCurrentPosition()
  {
    return this._sohuPlayer.getCurrentPosition();
  }

  public int getDuration()
  {
    return this._sohuPlayer.getDuration();
  }

  public String[] getSubtitles()
  {
    return new String[0];
  }

  public int getVideoHeight()
  {
    return this._sohuPlayer.getVideoHeight();
  }

  public int getVideoWidth()
  {
    return this._sohuPlayer.getVideoWidth();
  }

  public boolean isLooping()
  {
    return false;
  }

  public boolean isPlaying()
  {
    return this._sohuPlayer.isPlaying();
  }

  public void pause()
    throws IllegalStateException
  {
    this._sohuPlayer.pause();
  }

  public void prepare()
    throws IOException, IllegalStateException
  {
  }

  public void prepareAsync()
    throws IllegalStateException
  {
  }

  public void release()
  {
    this._sohuPlayer.release();
  }

  public void reset()
  {
    this._sohuPlayer.stop();
  }

  public void seekTo(int paramInt)
    throws IllegalStateException
  {
    this.isFirstSeekListener = false;
    this._sohuPlayer.seekTo(paramInt);
  }

  public void setAudioStreamType(int paramInt)
  {
  }

  public void setAudioTrack(int paramInt)
  {
  }

  public void setDataSource(Context paramContext, String paramString)
    throws IOException, IllegalArgumentException, SecurityException, IllegalStateException
  {
    this._url = paramString;
    this.isFirstSeekListener = true;
    this._sohuPlayer.setVideoParam(this._url);
    Logger.i("laladin", "setDataSource _url:" + this._url);
  }

  public void setDisplay(MediaPlayerSurfaceAdapter paramMediaPlayerSurfaceAdapter)
  {
  }

  public void setLooping(boolean paramBoolean)
  {
  }

  public void setOnBufferingUpdateListener(MediaPlayerAdapter.OnBufferingUpdateListener paramOnBufferingUpdateListener)
  {
    this._bufferingListener = paramOnBufferingUpdateListener;
  }

  public void setOnCompletionListener(MediaPlayerAdapter.OnCompletionListener paramOnCompletionListener)
  {
    this._completionListener = paramOnCompletionListener;
  }

  public void setOnErrorListener(MediaPlayerAdapter.OnErrorListener paramOnErrorListener)
  {
    this._errorListener = paramOnErrorListener;
  }

  public void setOnInfoListener(MediaPlayerAdapter.OnInfoListener paramOnInfoListener)
  {
    this._infoListener = paramOnInfoListener;
  }

  public void setOnPreparedListener(MediaPlayerAdapter.OnPreparedListener paramOnPreparedListener)
  {
    this._prepareListener = paramOnPreparedListener;
  }

  public void setOnSeekCompleteListener(MediaPlayerAdapter.OnSeekCompleteListener paramOnSeekCompleteListener)
  {
    this._seekCompleteListener = paramOnSeekCompleteListener;
  }

  public void setOnVideoSizeChangedListener(MediaPlayerAdapter.OnVideoSizeChangedListener paramOnVideoSizeChangedListener)
  {
    this._videoSizeChangedListener = paramOnVideoSizeChangedListener;
  }

  public void setScreenOnWhilePlaying(boolean paramBoolean)
  {
  }

  public void setSoundTrack(int paramInt)
  {
  }

  public void setSubtitle(int paramInt)
  {
  }

  public void setVolume(float paramFloat1, float paramFloat2)
  {
  }

  public void start(boolean paramBoolean)
    throws IllegalStateException
  {
    Logger.i("laladin", "sohu start() ");
    if (paramBoolean)
    {
      this._sohuPlayer.resume();
      return;
    }
    this._sohuPlayer.start();
  }

  public void stop()
    throws IllegalStateException
  {
    this._sohuPlayer.stop();
  }

  public static class SohuPlayParams
  {
    private int catecode;
    private int cid;
    private int definition;
    private String position;
    private int sid;
    private int vid;

    public int getCatecode()
    {
      return this.catecode;
    }

    public int getCid()
    {
      return this.cid;
    }

    public int getDefinition()
    {
      return this.definition;
    }

    public String getPosition()
    {
      return this.position;
    }

    public int getSid()
    {
      return this.sid;
    }

    public int getVid()
    {
      return this.vid;
    }

    public void setCatecode(int paramInt)
    {
      this.catecode = paramInt;
    }

    public void setCid(int paramInt)
    {
      this.cid = paramInt;
    }

    public void setDefinition(int paramInt)
    {
      this.definition = paramInt;
    }

    public void setPosition(String paramString)
    {
      this.position = paramString;
    }

    public void setSid(int paramInt)
    {
      this.sid = paramInt;
    }

    public void setVid(int paramInt)
    {
      this.vid = paramInt;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.player.SohuPlayerAdapter
 * JD-Core Version:    0.6.2
 */