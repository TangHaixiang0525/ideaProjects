package com.starcor.player;

import android.content.Context;
import android.net.Uri;
import com.starcor.core.utils.Logger;
import java.io.IOException;

public class HeartbeatMediaPlayerAdapter extends MediaPlayerAdapter
{
  private String TAG = HeartbeatMediaPlayerAdapter.class.getSimpleName();
  private boolean exitFlag;
  private HeartbeatThread heartbeatThread;
  private MediaPlayerAdapter mediaPlayerAdapter;
  private Uri uri;

  public HeartbeatMediaPlayerAdapter(MediaPlayerAdapter paramMediaPlayerAdapter)
  {
    if (paramMediaPlayerAdapter == null)
      throw new NullPointerException("Adapter not null");
    this.mediaPlayerAdapter = paramMediaPlayerAdapter;
  }

  // ERROR //
  private void httpUrlConnection(String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 27	com/starcor/player/HeartbeatMediaPlayerAdapter:TAG	Ljava/lang/String;
    //   4: new 49	java/lang/StringBuilder
    //   7: dup
    //   8: invokespecial 50	java/lang/StringBuilder:<init>	()V
    //   11: ldc 52
    //   13: invokevirtual 56	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   16: aload_0
    //   17: getfield 58	com/starcor/player/HeartbeatMediaPlayerAdapter:uri	Landroid/net/Uri;
    //   20: invokevirtual 63	android/net/Uri:toString	()Ljava/lang/String;
    //   23: invokevirtual 56	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   26: aload_1
    //   27: invokevirtual 56	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   30: invokevirtual 64	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   33: invokestatic 70	com/starcor/core/utils/Logger:i	(Ljava/lang/String;Ljava/lang/String;)V
    //   36: new 72	org/apache/http/client/methods/HttpGet
    //   39: dup
    //   40: new 49	java/lang/StringBuilder
    //   43: dup
    //   44: invokespecial 50	java/lang/StringBuilder:<init>	()V
    //   47: aload_0
    //   48: getfield 58	com/starcor/player/HeartbeatMediaPlayerAdapter:uri	Landroid/net/Uri;
    //   51: invokevirtual 63	android/net/Uri:toString	()Ljava/lang/String;
    //   54: invokevirtual 56	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   57: aload_1
    //   58: invokevirtual 56	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   61: invokevirtual 64	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   64: invokespecial 73	org/apache/http/client/methods/HttpGet:<init>	(Ljava/lang/String;)V
    //   67: astore_2
    //   68: new 75	org/apache/http/impl/client/DefaultHttpClient
    //   71: dup
    //   72: invokespecial 76	org/apache/http/impl/client/DefaultHttpClient:<init>	()V
    //   75: astore_3
    //   76: aconst_null
    //   77: astore 4
    //   79: aload_3
    //   80: aload_2
    //   81: invokeinterface 82 2 0
    //   86: astore 9
    //   88: aload_0
    //   89: getfield 27	com/starcor/player/HeartbeatMediaPlayerAdapter:TAG	Ljava/lang/String;
    //   92: new 49	java/lang/StringBuilder
    //   95: dup
    //   96: invokespecial 50	java/lang/StringBuilder:<init>	()V
    //   99: ldc 84
    //   101: invokevirtual 56	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   104: aload 9
    //   106: invokeinterface 90 1 0
    //   111: invokevirtual 93	java/lang/Object:toString	()Ljava/lang/String;
    //   114: invokevirtual 56	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   117: invokevirtual 64	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   120: invokestatic 70	com/starcor/core/utils/Logger:i	(Ljava/lang/String;Ljava/lang/String;)V
    //   123: aload 9
    //   125: invokeinterface 97 1 0
    //   130: invokeinterface 103 1 0
    //   135: astore 4
    //   137: aload 4
    //   139: iconst_1
    //   140: newarray byte
    //   142: invokevirtual 109	java/io/InputStream:read	([B)I
    //   145: pop
    //   146: aload 4
    //   148: ifnull +8 -> 156
    //   151: aload 4
    //   153: invokevirtual 112	java/io/InputStream:close	()V
    //   156: return
    //   157: astore 7
    //   159: aload 4
    //   161: ifnull -5 -> 156
    //   164: aload 4
    //   166: invokevirtual 112	java/io/InputStream:close	()V
    //   169: return
    //   170: astore 8
    //   172: return
    //   173: astore 5
    //   175: aload 4
    //   177: ifnull +8 -> 185
    //   180: aload 4
    //   182: invokevirtual 112	java/io/InputStream:close	()V
    //   185: aload 5
    //   187: athrow
    //   188: astore 11
    //   190: return
    //   191: astore 6
    //   193: goto -8 -> 185
    //
    // Exception table:
    //   from	to	target	type
    //   79	146	157	java/lang/Exception
    //   164	169	170	java/lang/Exception
    //   79	146	173	finally
    //   151	156	188	java/lang/Exception
    //   180	185	191	java/lang/Exception
  }

  private void startHeartBeat()
  {
    Logger.i(this.TAG, "startHeartBeat");
    if (this.heartbeatThread != null)
      return;
    this.exitFlag = false;
    this.heartbeatThread = new HeartbeatThread();
    this.heartbeatThread.start();
  }

  private void stopHeartBeat()
  {
    Logger.i(this.TAG, "stopHeartBeat");
    this.exitFlag = true;
    if (this.heartbeatThread != null);
    try
    {
      this.heartbeatThread.interrupt();
      this.heartbeatThread = null;
      return;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }

  public String[] getAudioTrackData()
  {
    return this.mediaPlayerAdapter.getAudioTrackData();
  }

  public int getCurrentPosition()
  {
    return this.mediaPlayerAdapter.getCurrentPosition();
  }

  public int getDuration()
  {
    return this.mediaPlayerAdapter.getDuration();
  }

  public String[] getSubtitles()
  {
    return null;
  }

  public int getVideoHeight()
  {
    return this.mediaPlayerAdapter.getVideoHeight();
  }

  public int getVideoWidth()
  {
    return this.mediaPlayerAdapter.getVideoWidth();
  }

  public boolean isLooping()
  {
    return this.mediaPlayerAdapter.isLooping();
  }

  public boolean isPlaying()
  {
    return this.mediaPlayerAdapter.isPlaying();
  }

  public void pause()
    throws IllegalStateException
  {
    this.mediaPlayerAdapter.pause();
  }

  public void prepare()
    throws IOException, IllegalStateException
  {
    this.mediaPlayerAdapter.prepare();
  }

  public void prepareAsync()
    throws IllegalStateException
  {
    this.mediaPlayerAdapter.prepareAsync();
  }

  public void release()
  {
    Logger.i(this.TAG, "release");
    stopHeartBeat();
    this.mediaPlayerAdapter.release();
  }

  public void reset()
  {
    this.mediaPlayerAdapter.reset();
  }

  public void seekTo(int paramInt)
    throws IllegalStateException
  {
    this.mediaPlayerAdapter.seekTo(paramInt);
  }

  public void setAudioStreamType(int paramInt)
  {
    this.mediaPlayerAdapter.setAudioStreamType(paramInt);
  }

  public void setAudioTrack(int paramInt)
  {
    this.mediaPlayerAdapter.setAudioTrack(paramInt);
  }

  public void setDataSource(Context paramContext, String paramString)
    throws IOException, IllegalArgumentException, SecurityException, IllegalStateException
  {
    Logger.i(this.TAG, "setDataSource");
    this.uri = Uri.parse(paramString);
    startHeartBeat();
    this.mediaPlayerAdapter.setDataSource(paramContext, paramString);
  }

  public void setDisplay(MediaPlayerSurfaceAdapter paramMediaPlayerSurfaceAdapter)
  {
    this.mediaPlayerAdapter.setDisplay(paramMediaPlayerSurfaceAdapter);
  }

  public void setLooping(boolean paramBoolean)
  {
    this.mediaPlayerAdapter.setLooping(paramBoolean);
  }

  public void setOnBufferingUpdateListener(MediaPlayerAdapter.OnBufferingUpdateListener paramOnBufferingUpdateListener)
  {
    this.mediaPlayerAdapter.setOnBufferingUpdateListener(paramOnBufferingUpdateListener);
  }

  public void setOnCompletionListener(MediaPlayerAdapter.OnCompletionListener paramOnCompletionListener)
  {
    this.mediaPlayerAdapter.setOnCompletionListener(paramOnCompletionListener);
  }

  public void setOnErrorListener(MediaPlayerAdapter.OnErrorListener paramOnErrorListener)
  {
    this.mediaPlayerAdapter.setOnErrorListener(paramOnErrorListener);
  }

  public void setOnInfoListener(MediaPlayerAdapter.OnInfoListener paramOnInfoListener)
  {
    this.mediaPlayerAdapter.setOnInfoListener(paramOnInfoListener);
  }

  public void setOnPreparedListener(MediaPlayerAdapter.OnPreparedListener paramOnPreparedListener)
  {
    this.mediaPlayerAdapter.setOnPreparedListener(paramOnPreparedListener);
  }

  public void setOnSeekCompleteListener(MediaPlayerAdapter.OnSeekCompleteListener paramOnSeekCompleteListener)
  {
    this.mediaPlayerAdapter.setOnSeekCompleteListener(paramOnSeekCompleteListener);
  }

  public void setOnVideoSizeChangedListener(MediaPlayerAdapter.OnVideoSizeChangedListener paramOnVideoSizeChangedListener)
  {
    this.mediaPlayerAdapter.setOnVideoSizeChangedListener(paramOnVideoSizeChangedListener);
  }

  public void setScreenOnWhilePlaying(boolean paramBoolean)
  {
    this.mediaPlayerAdapter.setScreenOnWhilePlaying(paramBoolean);
  }

  public void setSoundTrack(int paramInt)
  {
    this.mediaPlayerAdapter.setSoundTrack(paramInt);
  }

  public void setSubtitle(int paramInt)
  {
  }

  public void setVolume(float paramFloat1, float paramFloat2)
  {
    this.mediaPlayerAdapter.setVolume(paramFloat1, paramFloat2);
  }

  public void start(boolean paramBoolean)
    throws IllegalStateException
  {
    this.mediaPlayerAdapter.start(paramBoolean);
  }

  public void stop()
    throws IllegalStateException
  {
    this.mediaPlayerAdapter.stop();
  }

  class HeartbeatThread extends Thread
  {
    HeartbeatThread()
    {
    }

    public void run()
    {
      while (!HeartbeatMediaPlayerAdapter.this.exitFlag)
        try
        {
          sleep(60000L);
          HeartbeatMediaPlayerAdapter.this.httpUrlConnection(".hb");
        }
        catch (InterruptedException localInterruptedException)
        {
          localInterruptedException.printStackTrace();
        }
      HeartbeatMediaPlayerAdapter.this.httpUrlConnection(".sb");
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.player.HeartbeatMediaPlayerAdapter
 * JD-Core Version:    0.6.2
 */