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
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class SystemMediaPlayerAsynAdapter extends MediaPlayerAdapter
{
  public static final boolean IS_SUPPORT_AUDIO_TRACK_SETTING = false;
  public static final int KEY_PARAMETER_AML_PLAYER_SWITCH_AUDIO_TRACK = 2004;
  public static final int KEY_PARAMETER_AML_PLAYER_SWITCH_SOUND_TRACK = 2003;
  private static final String TAG = "SystemMediaPlayerAsynAdapter";
  public static final int THREAD_TIMER_INTERVAL = 50;
  private Class<?> audioInfoClazz = null;
  private Field audioInfoField = null;
  private Field audioInfoIndexField = null;
  private Object[] audioInfos = null;
  private int audioTrackSize = 0;
  private Field audioTrackSizeField = null;
  private Context context;
  private Method getMediaInfoMethod = null;
  private Object mediaInfo = null;
  private Class<?> mediaInfoClazz = null;
  MediaPlayer mediaPlayer;
  private Class<?> mediaPlayerClazz = null;
  private Uri playUrl;
  private Integer setDataSourceCmd = Integer.valueOf(0);
  private Method setParameterMethod = null;
  private boolean threadExitFlag = false;

  static
  {
    if (hasMethod(MediaPlayer.class, "getMediaInfo", new Class[0]))
    {
      Class[] arrayOfClass = new Class[2];
      arrayOfClass[0] = Integer.TYPE;
      arrayOfClass[1] = String.class;
      if (!hasMethod(MediaPlayer.class, "setParameter", arrayOfClass));
    }
    for (boolean bool = true; ; bool = false)
    {
      IS_SUPPORT_AUDIO_TRACK_SETTING = bool;
      return;
    }
  }

  private SystemMediaPlayerAsynAdapter(final MediaPlayer paramMediaPlayer)
  {
    this.mediaPlayer = paramMediaPlayer;
    if (this.mediaPlayer == null)
    {
      Logger.i("SystemMediaPlayerAsynAdapter", "SystemMediaPlayerAsynAdapter() mediaPlayer is null");
      return;
    }
    Logger.i("SystemMediaPlayerAsynAdapter", "IS_SUPPORT_AUDIO_TRACK_SETTING = " + IS_SUPPORT_AUDIO_TRACK_SETTING);
    if (IS_SUPPORT_AUDIO_TRACK_SETTING);
    try
    {
      this.mediaPlayerClazz = paramMediaPlayer.getClass();
      this.mediaInfoClazz = Class.forName("android.media.MediaPlayer$MediaInfo");
      this.audioInfoClazz = Class.forName("android.media.MediaPlayer$AudioInfo");
      this.getMediaInfoMethod = this.mediaPlayerClazz.getMethod("getMediaInfo", new Class[0]);
      Class localClass = this.mediaPlayerClazz;
      Class[] arrayOfClass = new Class[2];
      arrayOfClass[0] = Integer.TYPE;
      arrayOfClass[1] = String.class;
      this.setParameterMethod = localClass.getMethod("setParameter", arrayOfClass);
      this.audioTrackSizeField = this.mediaInfoClazz.getDeclaredField("total_audio_num");
      this.audioInfoField = this.mediaInfoClazz.getDeclaredField("audioInfo");
      this.audioInfoIndexField = this.audioInfoClazz.getDeclaredField("index");
      Logger.i("SystemMediaPlayerAsynAdapter", "SystemMediaPlayerAsynAdapter() start thread");
      new Thread()
      {
        public void run()
        {
          while (!SystemMediaPlayerAsynAdapter.this.threadExitFlag)
            try
            {
              if (1 == SystemMediaPlayerAsynAdapter.this.setDataSourceCmd.intValue())
                Logger.i("SystemMediaPlayerAsynAdapter", "Thread run setDataSourceCmd:" + SystemMediaPlayerAsynAdapter.this.setDataSourceCmd);
            }
            catch (InterruptedException localInterruptedException)
            {
              synchronized (SystemMediaPlayerAsynAdapter.this.setDataSourceCmd)
              {
                if (paramMediaPlayer != null)
                {
                  paramMediaPlayer.setDataSource(SystemMediaPlayerAsynAdapter.this.context, SystemMediaPlayerAsynAdapter.this.playUrl);
                  paramMediaPlayer.prepareAsync();
                }
                SystemMediaPlayerAsynAdapter.access$102(SystemMediaPlayerAsynAdapter.this, Integer.valueOf(0));
                Thread.sleep(50L);
                continue;
                localInterruptedException = localInterruptedException;
                localInterruptedException.printStackTrace();
              }
            }
            catch (IOException localIOException)
            {
              localIOException.printStackTrace();
            }
          Logger.i("SystemMediaPlayerAsynAdapter", "Thread end threadExitFlag:" + SystemMediaPlayerAsynAdapter.this.threadExitFlag);
          if (paramMediaPlayer != null);
          try
          {
            paramMediaPlayer.release();
            return;
          }
          catch (Exception localException)
          {
            localException.printStackTrace();
          }
        }
      }
      .start();
      return;
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      while (true)
        localNoSuchMethodException.printStackTrace();
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      while (true)
        localClassNotFoundException.printStackTrace();
    }
    catch (NoSuchFieldException localNoSuchFieldException)
    {
      while (true)
        localNoSuchFieldException.printStackTrace();
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
        return new SystemMediaPlayerAsynAdapter(localMediaPlayer1, null);
      }

      public boolean is_class(Class<?> paramAnonymousClass)
      {
        return paramAnonymousClass.getName().equals(SystemMediaPlayerAsynAdapter.class.getName());
      }

      public void prepareSurface(MediaPlayerSurfaceSite paramAnonymousMediaPlayerSurfaceSite)
      {
        CommonSurfaceAdapter.SurfaceHolderFromSurfaceSite(paramAnonymousMediaPlayerSurfaceSite);
      }
    });
  }

  private void setMediaPlayerParameter(Object[] paramArrayOfObject)
  {
    try
    {
      this.setParameterMethod.invoke(this.mediaPlayer, paramArrayOfObject);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public String[] getAudioTrackData()
  {
    String[] arrayOfString;
    if (this.mediaInfo == null)
    {
      Logger.e("SystemMediaPlayerAsynAdapter", "mediaInfo == null ");
      arrayOfString = null;
    }
    while (true)
    {
      return arrayOfString;
      Logger.i("SystemMediaPlayerAsynAdapter", "The audio track total num of current media is: " + this.audioTrackSize);
      int i = this.audioTrackSize;
      arrayOfString = null;
      if (i > 0)
      {
        arrayOfString = new String[this.audioTrackSize];
        for (int j = 0; j < this.audioTrackSize; j++)
          arrayOfString[j] = ("音轨 " + (j + 1));
      }
    }
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
    Logger.i("SystemMediaPlayerAsynAdapter", "prepareAsync()");
  }

  public void release()
  {
    this.threadExitFlag = true;
    Logger.i("SystemMediaPlayerAsynAdapter", "release");
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
    if ((this.mediaPlayer != null) && (this.mediaInfo != null) && (this.audioTrackSize > 0));
    try
    {
      this.audioInfos = ((Object[])this.audioInfoField.get(this.mediaInfo));
      int i = this.audioInfoIndexField.getInt(this.audioInfos[paramInt]);
      Logger.i("SystemMediaPlayerAsynAdapter", "setAudioTrack : " + i);
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = Integer.valueOf(2004);
      arrayOfObject[1] = ("aid:" + i);
      setMediaPlayerParameter(arrayOfObject);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public void setDataSource(Context paramContext, String paramString)
    throws IOException, IllegalArgumentException, SecurityException, IllegalStateException
  {
    Logger.i("SystemMediaPlayerAsynAdapter", "setDataSource playUrl:" + paramString);
    synchronized (this.setDataSourceCmd)
    {
      this.context = paramContext;
      this.playUrl = Uri.parse(paramString);
      this.setDataSourceCmd = Integer.valueOf(1);
      return;
    }
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
        paramOnBufferingUpdateListener.onBufferingUpdate(SystemMediaPlayerAsynAdapter.this, paramAnonymousInt);
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
        paramOnCompletionListener.onCompletion(SystemMediaPlayerAsynAdapter.this);
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
        return paramOnErrorListener.onError(SystemMediaPlayerAsynAdapter.this, paramAnonymousInt1, paramAnonymousInt2);
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
        return paramOnInfoListener.onInfo(SystemMediaPlayerAsynAdapter.this, paramAnonymousInt1, paramAnonymousInt2);
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
        try
        {
          SystemMediaPlayerAsynAdapter.access$502(SystemMediaPlayerAsynAdapter.this, SystemMediaPlayerAsynAdapter.this.getMediaInfoMethod.invoke(paramAnonymousMediaPlayer, new Object[0]));
          SystemMediaPlayerAsynAdapter.access$702(SystemMediaPlayerAsynAdapter.this, SystemMediaPlayerAsynAdapter.this.audioTrackSizeField.getInt(SystemMediaPlayerAsynAdapter.this.mediaInfo));
          paramOnPreparedListener.onPrepared(SystemMediaPlayerAsynAdapter.this);
          return;
        }
        catch (Exception localException)
        {
          while (true)
          {
            localException.printStackTrace();
            SystemMediaPlayerAsynAdapter.access$502(SystemMediaPlayerAsynAdapter.this, null);
            SystemMediaPlayerAsynAdapter.access$702(SystemMediaPlayerAsynAdapter.this, 0);
          }
        }
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
        paramOnSeekCompleteListener.onSeekComplete(SystemMediaPlayerAsynAdapter.this);
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
        paramOnVideoSizeChangedListener.onVideoSizeChanged(SystemMediaPlayerAsynAdapter.this, paramAnonymousInt1, paramAnonymousInt2);
      }
    });
  }

  public void setScreenOnWhilePlaying(boolean paramBoolean)
  {
    this.mediaPlayer.setScreenOnWhilePlaying(paramBoolean);
  }

  public void setSoundTrack(int paramInt)
  {
    String str;
    if (this.mediaPlayer != null)
    {
      str = "stereo";
      if (paramInt != 0)
        break label68;
      str = "stereo";
    }
    while (true)
    {
      Logger.i("SystemMediaPlayerAsynAdapter", "setSoundTrack : " + str);
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = Integer.valueOf(2003);
      arrayOfObject[1] = str;
      setMediaPlayerParameter(arrayOfObject);
      return;
      label68: if (paramInt == 1)
        str = "lmono";
      else if (paramInt == 2)
        str = "rmono";
      else if (paramInt == 3)
        str = "lrmix";
    }
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
 * Qualified Name:     com.starcor.player.SystemMediaPlayerAsynAdapter
 * JD-Core Version:    0.6.2
 */