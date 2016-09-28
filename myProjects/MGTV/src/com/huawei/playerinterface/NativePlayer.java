package com.huawei.playerinterface;

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
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceView;
import com.huawei.dmpbase.DmpLog;
import com.huawei.playerinterface.parameter.HAGetParam;
import com.huawei.playerinterface.parameter.HASetParam;
import com.huawei.playerinterface.parameter.PlayerPara;
import com.huawei.playerinterface.parameter.ProxyPara;
import com.huawei.playerinterface.version.PlayerVersion;
import com.huawei.so.OTTProxy;
import java.io.IOException;
import java.util.Locale;

public class NativePlayer extends PlayerLogic
  implements IHAPlayer, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnSeekCompleteListener, MediaPlayer.OnErrorListener, MediaPlayer.OnInfoListener, MediaPlayer.OnVideoSizeChangedListener, MediaPlayer.OnPreparedListener
{
  public static final int ANDROID_NATIVE = 1;
  public static final int SHARK_NATIVE = 0;
  private static final String TAG = "HAPlayer_NativePlayer";
  private static final int UPDATE_TIME = 500;
  private static NativePlayer shark_play;
  public Handler handler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      if (NativePlayer.this.mediaPlayer == null);
      do
      {
        do
        {
          do
          {
            do
            {
              do
              {
                do
                {
                  do
                  {
                    return;
                    if (NativePlayer.this.playPara.errReportCnt > 1)
                    {
                      DmpLog.i("HAPlayer_NativePlayer", "handleMessage but errcnt > 1 so needn't send msg.what:" + paramAnonymousMessage.what + " arg1:" + paramAnonymousMessage.arg1 + "arg2: " + paramAnonymousMessage.arg2);
                      return;
                    }
                    switch (paramAnonymousMessage.what)
                    {
                    default:
                      return;
                    case 1:
                      DmpLog.i("HAPlayer_NativePlayer", "prepared message");
                    case 2:
                    case 3:
                    case 5:
                    case 200:
                    case 100:
                    case 4:
                    }
                  }
                  while (NativePlayer.this.mOnPreparedListener == null);
                  NativePlayer.this.mOnPreparedListener.onPrepared(NativePlayer.shark_play);
                  return;
                  DmpLog.i("HAPlayer_NativePlayer", "media complete");
                }
                while (NativePlayer.this.mOnCompletionListener == null);
                NativePlayer.this.mOnCompletionListener.onCompletion(NativePlayer.shark_play);
                return;
                DmpLog.i("HAPlayer_NativePlayer", "buf update");
              }
              while (NativePlayer.this.mOnBufferingUpdateListener == null);
              NativePlayer.this.mOnBufferingUpdateListener.onBufferingUpdate(NativePlayer.shark_play, paramAnonymousMessage.arg1);
              return;
              DmpLog.i("HAPlayer_NativePlayer", "video size change");
            }
            while (NativePlayer.this.mOnVideoSizeChangedListener == null);
            NativePlayer.this.mOnVideoSizeChangedListener.onVideoSizeChanged(NativePlayer.shark_play, paramAnonymousMessage.arg1, paramAnonymousMessage.arg2);
            return;
            DmpLog.i("HAPlayer_NativePlayer", "info type:" + paramAnonymousMessage.arg1 + " code:" + paramAnonymousMessage.arg2);
          }
          while (NativePlayer.this.mOnInfoListener == null);
          NativePlayer.this.mOnInfoListener.onInfo(NativePlayer.shark_play, paramAnonymousMessage.arg1, paramAnonymousMessage.arg2);
          return;
          DmpLog.i("HAPlayer_NativePlayer", "err type:" + paramAnonymousMessage.arg1 + " code:" + paramAnonymousMessage.arg2);
        }
        while (NativePlayer.this.mOnErrorListener == null);
        NativePlayer.this.mOnErrorListener.onError(NativePlayer.shark_play, paramAnonymousMessage.arg1, paramAnonymousMessage.arg2);
        return;
        DmpLog.i("HAPlayer_NativePlayer", "seek complete");
      }
      while (NativePlayer.this.mOnSeekCompleteListener == null);
      NativePlayer.this.mOnSeekCompleteListener.onSeekComplete(NativePlayer.shark_play);
    }
  };
  private boolean isPlaying = false;
  private boolean mIsPlayerReleased = false;
  private IHAPlayer.OnBufferingUpdateListener mOnBufferingUpdateListener;
  private IHAPlayer.OnCompletionListener mOnCompletionListener;
  private IHAPlayer.OnErrorListener mOnErrorListener;
  private IHAPlayer.OnInfoListener mOnInfoListener;
  private IHAPlayer.OnPreparedListener mOnPreparedListener;
  private IHAPlayer.OnSeekCompleteListener mOnSeekCompleteListener;
  private IHAPlayer.OnVideoSizeChangedListener mOnVideoSizeChangedListener;
  public MediaPlayer mediaPlayer;
  protected boolean playingNotBuffering = false;
  protected int postionRecorder = 0;
  private int updateCnt = 0;

  public NativePlayer()
  {
    this.playPara.isInternalStart = false;
    this.mIsPlayerReleased = false;
    this.playPara.historyPlayPoint = -1;
    this.playPara.errReportCnt = 0;
    this.context = null;
    this.mSurfaceView = null;
    this.playPara.inPlayState = true;
    this.playPara.mediaDuration = 0;
    this.mediaPlayer = new MediaPlayer();
    shark_play = this;
    this.useProxy = 0;
    DmpLog.i("HAPlayer_NativePlayer", "new MediaPlayer");
  }

  private void setMaxBitrate(int paramInt)
  {
    DmpLog.d("HAPlayer_NativePlayer", "PowerPlayer -> setMaxBitrate() : maxBitrate = " + paramInt);
  }

  private void setMediaPlayerListener()
  {
    if (this.mediaPlayer == null)
    {
      DmpLog.e("HAPlayer_NativePlayer", "setMediaPlayerListener fail mediaPlayer is null");
      return;
    }
    this.mediaPlayer.setOnCompletionListener(this);
    this.mediaPlayer.setOnPreparedListener(this);
    this.mediaPlayer.setOnErrorListener(this);
    this.mediaPlayer.setOnInfoListener(this);
    this.mediaPlayer.setOnVideoSizeChangedListener(this);
    this.mediaPlayer.setOnSeekCompleteListener(this);
  }

  private void setMinBitrate(int paramInt)
  {
    DmpLog.d("HAPlayer_NativePlayer", "PowerPlayer -> setMinBitrate() : minBitrate = " + paramInt);
  }

  public int getCurrentPosition()
  {
    if (this.mediaPlayer == null)
    {
      DmpLog.e("HAPlayer_NativePlayer", "sharkplayer getCurrentPosition fail mediaPlayer is null");
      return 0;
    }
    boolean bool = isPlaying();
    int i = 0;
    if (bool)
      i = this.mediaPlayer.getCurrentPosition();
    switch (this.playPara.contentType)
    {
    default:
    case 0:
    case 1:
    case 2:
    }
    while (true)
    {
      return this.playPara.playPosition;
      if (isPlaying())
      {
        this.playPara.playPosition = i;
        continue;
        this.playPara.playPosition = this.playPara.mediaDuration;
        continue;
        if (this.playPara.pauseTimeRecorder > 0L)
          this.playPara.playPosition = (this.playPara.mediaDuration - getTSTVSeekTime());
        else
          this.playPara.playPosition = (this.playPara.mediaDuration - this.playPara.tvSeekTime);
      }
    }
  }

  public int getDuration()
  {
    DmpLog.d("HAPlayer_NativePlayer", "getDuration() :playPara.contentType = " + this.playPara.contentType + " playPara.mediaDuration:" + this.playPara.mediaDuration);
    if (this.playPara.mediaDuration > 0)
      return this.playPara.mediaDuration;
    if (this.mediaPlayer == null)
    {
      DmpLog.e("HAPlayer_NativePlayer", "getDuration() : getDuration() failed, mediaPlayer is null");
      return 0;
    }
    switch (this.playPara.contentType)
    {
    default:
      DmpLog.w("HAPlayer_NativePlayer", "getDuration() failed, unknown mediatype");
      return 0;
    case 0:
    }
    if (this.isPlaying)
      this.playPara.mediaDuration = this.mediaPlayer.getDuration();
    DmpLog.i("HAPlayer_NativePlayer", "getDuration:" + this.playPara.mediaDuration);
    return this.playPara.mediaDuration;
  }

  public Object getProperties(HAGetParam paramHAGetParam)
  {
    switch (2.$SwitchMap$com$huawei$playerinterface$parameter$HAGetParam[paramHAGetParam.ordinal()])
    {
    case 1:
    case 2:
    default:
      return null;
    case 3:
    }
    return PlayerVersion.getVer();
  }

  public int getVideoHeight()
  {
    if (this.mediaPlayer == null)
    {
      DmpLog.e("HAPlayer_NativePlayer", "getVideoHeight fail mediaPlayer is null");
      return 0;
    }
    int i = this.mediaPlayer.getVideoHeight();
    DmpLog.i("HAPlayer_NativePlayer", "getVideoHeight:" + i);
    return i;
  }

  public int getVideoWidth()
  {
    if (this.mediaPlayer == null)
    {
      DmpLog.e("HAPlayer_NativePlayer", "getVideoWidth fail mediaPlayer is null");
      return 0;
    }
    int i = this.mediaPlayer.getVideoWidth();
    DmpLog.i("HAPlayer_NativePlayer", "sharkplayer getVideoWidth:" + i);
    return i;
  }

  public boolean isPlaying()
  {
    if (this.mediaPlayer == null);
    while (!this.isPlaying)
      return false;
    return this.mediaPlayer.isPlaying();
  }

  public void onBufferingUpdate(MediaPlayer paramMediaPlayer, int paramInt)
  {
    Message localMessage = new Message();
    localMessage.what = 3;
    localMessage.arg1 = paramInt;
    DmpLog.i("HAPlayer_NativePlayer", "onBufferingUpdate:" + paramInt);
    this.handler.sendMessage(localMessage);
  }

  public void onCompletion(MediaPlayer paramMediaPlayer)
  {
    DmpLog.i("HAPlayer_NativePlayer", "onCompletion");
    this.isPlaying = false;
    this.handler.sendEmptyMessage(2);
  }

  public boolean onError(MediaPlayer paramMediaPlayer, int paramInt1, int paramInt2)
  {
    PlayerPara localPlayerPara = this.playPara;
    localPlayerPara.errReportCnt = (1 + localPlayerPara.errReportCnt);
    DmpLog.e("HAPlayer_NativePlayer", "err- what:" + paramInt1 + " extra:" + paramInt2);
    if (this.mIsPlayerReleased)
    {
      DmpLog.i("HAPlayer_NativePlayer", "player is released(suspend), noneed send err msg");
      return true;
    }
    Message localMessage = new Message();
    localMessage.what = 100;
    localMessage.arg1 = paramInt1;
    localMessage.arg2 = paramInt2;
    this.handler.sendMessage(localMessage);
    return false;
  }

  public boolean onInfo(MediaPlayer paramMediaPlayer, int paramInt1, int paramInt2)
  {
    DmpLog.i("HAPlayer_NativePlayer", "onInfo what:" + paramInt1 + " extra:" + paramInt2);
    Message localMessage = new Message();
    localMessage.what = 200;
    localMessage.arg1 = paramInt1;
    localMessage.arg2 = paramInt2;
    this.handler.sendMessage(localMessage);
    return false;
  }

  public void onPrepared(MediaPlayer paramMediaPlayer)
  {
    if ((this.mediaPlayer != null) && (this.playPara.contentType == 0))
      this.playPara.mediaDuration = this.mediaPlayer.getDuration();
    if (this.playPara.isInternalStart)
    {
      DmpLog.i("HAPlayer_NativePlayer", "onPrepared internal start");
      if ((1 != this.playPara.contentType) && (this.playPara.playPosition > 0))
      {
        start(this.playPara.playPosition);
        return;
      }
      start();
      return;
    }
    DmpLog.i("HAPlayer_NativePlayer", "onPrepared send prepared message");
    this.handler.sendEmptyMessage(1);
    this.playPara.isInternalStart = true;
  }

  public void onSeekComplete(MediaPlayer paramMediaPlayer)
  {
    DmpLog.i("HAPlayer_NativePlayer", "onSeekComplete");
    this.handler.sendEmptyMessage(4);
  }

  public void onVideoSizeChanged(MediaPlayer paramMediaPlayer, int paramInt1, int paramInt2)
  {
    DmpLog.i("HAPlayer_NativePlayer", "video size change width:" + paramInt1 + " height:" + paramInt2);
    Message localMessage = new Message();
    localMessage.what = 5;
    localMessage.arg1 = paramInt1;
    localMessage.arg2 = paramInt2;
    this.handler.sendMessage(localMessage);
    setProperties(HASetParam.SCALE_MODE, Integer.valueOf(this.playPara.scaleMode));
  }

  public void pause()
  {
    this.playPara.inPlayState = false;
    DmpLog.i("HAPlayer_NativePlayer", "pause contentType" + this.playPara.contentType + "isplaying:" + isPlaying());
    if (this.mediaPlayer == null)
      DmpLog.e("HAPlayer_NativePlayer", "pause fail mediaPlayer is null");
    do
    {
      return;
      switch (this.playPara.contentType)
      {
      case 1:
      default:
        return;
      case 0:
      case 2:
      }
    }
    while (!isPlaying());
    this.mediaPlayer.pause();
    return;
    if (isPlaying())
      this.mediaPlayer.pause();
    this.playPara.pauseTimeRecorder = getPlayTimeTick();
  }

  public void prepare()
  {
    DmpLog.i("HAPlayer_NativePlayer", "prepare");
    if ((this.mediaPlayer == null) || (this.playPara.errReportCnt > 0))
    {
      DmpLog.e("HAPlayer_NativePlayer", "prepare fail mediaPlayer:" + this.mediaPlayer + " errcnt:" + this.playPara.errReportCnt);
      return;
    }
    this.postionRecorder = 0;
    this.playingNotBuffering = false;
    this.mediaPlayer.prepareAsync();
    super.logicStart();
  }

  public void prepareAsync()
  {
    DmpLog.i("HAPlayer_NativePlayer", "prepareAsync");
    prepare();
  }

  public void release()
  {
    super.logicRelease();
    DmpLog.i("HAPlayer_NativePlayer", "release");
    if (this.mediaPlayer != null)
    {
      DmpLog.i("HAPlayer_NativePlayer", "Release mediaPlayer...");
      this.mOnPreparedListener = null;
      this.mOnCompletionListener = null;
      this.mOnBufferingUpdateListener = null;
      this.mOnSeekCompleteListener = null;
      this.mOnVideoSizeChangedListener = null;
      this.mOnErrorListener = null;
      this.mOnInfoListener = null;
      reset();
      this.mediaPlayer.release();
      this.mediaPlayer = null;
      DmpLog.i("HAPlayer_NativePlayer", "Release mediaPlayer...OK");
    }
    DmpLog.d("HAPlayer_NativePlayer", "release(): playProxy.proxyClose()");
    if (this.playProxy != null)
    {
      setNeedUpdateProxyCode(false);
      this.playProxy.proxyClose();
      this.playProxy = null;
    }
    while (true)
    {
      this.handler = null;
      this.playPara = null;
      stopPlayTime();
      return;
      DmpLog.w("HAPlayer_NativePlayer", "release():  playProxy is null,not close proxy");
    }
  }

  public void reset()
  {
    this.playPara.errReportCnt = 0;
    if (this.mediaPlayer == null)
      return;
    DmpLog.i("HAPlayer_NativePlayer", "reset");
    this.isPlaying = false;
    this.playingNotBuffering = false;
    this.mediaPlayer.reset();
  }

  public void resume()
  {
    super.logicResume();
    DmpLog.i("HAPlayer_NativePlayer", "sharkplayer resume");
    this.playPara.inPlayState = true;
    resumeOnly();
  }

  public void resume(int paramInt)
  {
    super.logicResume();
    DmpLog.i("HAPlayer_NativePlayer", "resume play is" + paramInt);
    PlayerPara localPlayerPara;
    if (paramInt >= 0)
    {
      localPlayerPara = this.playPara;
      if (paramInt != 0)
        break label54;
    }
    label54: for (boolean bool = false; ; bool = true)
    {
      localPlayerPara.inPlayState = bool;
      resumeOnly();
      return;
    }
  }

  public void resumeOnly()
  {
    DmpLog.i("HAPlayer_NativePlayer", "resumeOnly");
    if (this.mediaPlayer == null)
    {
      DmpLog.e("HAPlayer_NativePlayer", "resumeOnly fail mediaPlayer is null");
      return;
    }
    this.mIsPlayerReleased = false;
    reset();
    setDataSource();
    this.mediaPlayer.setDisplay(this.mSurfaceView.getHolder());
    prepareAsync();
    super.logicResume();
  }

  public void seekTo(int paramInt)
  {
    DmpLog.i("HAPlayer_NativePlayer", "seekTo");
    seekToOnly(paramInt);
  }

  public void seekTo(int paramInt1, int paramInt2)
  {
    DmpLog.i("HAPlayer_NativePlayer", "seekTo play is" + paramInt2);
    seekToOnly(paramInt1);
  }

  public void seekToOnly(int paramInt)
  {
    DmpLog.i("HAPlayer_NativePlayer", "sharkplayer seekToOnly: " + paramInt + " playType:" + this.playPara.contentType);
    if (this.mediaPlayer == null)
      DmpLog.e("HAPlayer_NativePlayer", "seekToOnly fail mediaPlayer is null");
    do
    {
      return;
      if (this.playPara.mediaDuration == paramInt)
      {
        DmpLog.i("HAPlayer_NativePlayer", "Seek to end of file!");
        paramInt -= 1000;
      }
      switch (this.playPara.contentType)
      {
      case 1:
      default:
        return;
      case 0:
        if (this.mediaPlayer != null)
          this.mediaPlayer.seekTo(paramInt);
        this.playPara.playPosition = paramInt;
        return;
      case 2:
      }
      if (this.playProxy != null)
      {
        this.playPara.pauseTimeRecorder = 0L;
        this.playPara.tvSeekTime = (this.playPara.mediaDuration - paramInt);
        this.playProxy.proxySeek(this.playPara.tvSeekTime);
      }
      reset();
      DmpLog.i("HAPlayer_NativePlayer", "SharkPlayer -> tstv seek() player reset ");
      setDataSource();
      this.mediaPlayer.setDisplay(this.mSurfaceView.getHolder());
      prepareAsync();
      DmpLog.d("HAPlayer_NativePlayer", "seekTo():TSTV send seekcomplete to listener");
    }
    while (this.handler == null);
    this.handler.sendEmptyMessage(4);
  }

  protected void setDataSource()
  {
    if ((this.mediaPlayer == null) || (this.playPara.playUrl == null))
    {
      DmpLog.e("HAPlayer_NativePlayer", "setDataSource mediaPlayer:" + this.mediaPlayer + " playUrl:" + this.playPara.playUrl);
      onError(this.mediaPlayer, 100, 1);
      return;
    }
    Uri localUri = Uri.parse(this.playPara.playUrl);
    DmpLog.i("HAPlayer_NativePlayer", "setDataSource() final url:" + localUri);
    try
    {
      this.mediaPlayer.setDataSource(this.context, localUri);
      return;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      localIllegalArgumentException.printStackTrace();
      return;
    }
    catch (SecurityException localSecurityException)
    {
      localSecurityException.printStackTrace();
      return;
    }
    catch (IllegalStateException localIllegalStateException)
    {
      localIllegalStateException.printStackTrace();
      return;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
  }

  public void setDataSource(Context paramContext, String paramString)
  {
    this.context = paramContext;
    this.playPara.isInternalStart = false;
    this.playPara.errReportCnt = 0;
    setMediaPlayerListener();
    this.proxyPara.playUrl = paramString;
    String str = paramString.toLowerCase(Locale.getDefault());
    int i;
    if ((str.indexOf(".m3u8") != -1) && (str.indexOf("http://") != -1) && (this.useProxy == 1))
    {
      this.playProxy = new PlayerProxy();
      if (this.playProxy == null)
        break label237;
      this.playProxy.initProxy();
      this.proxyPara.playUrl = this.playProxy.proxyGetPlayUrl(paramString, this.playPara.contentType, this.playPara.mediaDuration, 10.0F);
      if (this.playPara.historyPlayPoint >= 0)
      {
        this.playPara.tvSeekTime = (this.playPara.mediaDuration - this.playPara.historyPlayPoint);
        PlayerPara localPlayerPara = this.playPara;
        if (this.playPara.tvSeekTime >= 0)
          break label225;
        i = 0;
        localPlayerPara.tvSeekTime = i;
        this.playProxy.proxySeek(this.playPara.tvSeekTime);
      }
      setNeedUpdateProxyCode(true);
    }
    while (true)
    {
      this.playPara.playUrl = this.proxyPara.playUrl;
      setDataSource();
      return;
      label225: i = this.playPara.tvSeekTime;
      break;
      label237: this.proxyPara.playUrl = this.playPara.playUrl;
    }
  }

  public void setDisplay(SurfaceView paramSurfaceView)
  {
    DmpLog.i("HAPlayer_NativePlayer", "setDisplay");
    if (this.mediaPlayer == null)
    {
      DmpLog.e("HAPlayer_NativePlayer", "setDisplay fail mediaPlayer is null");
      return;
    }
    this.mSurfaceView = paramSurfaceView;
    recordScreenSize();
    this.mediaPlayer.setDisplay(paramSurfaceView.getHolder());
    DmpLog.i("HAPlayer_NativePlayer", "mediaPlayer setDisplay");
    super.logicPrepare(this.handler);
  }

  public void setOnBufferingUpdateListener(IHAPlayer.OnBufferingUpdateListener paramOnBufferingUpdateListener)
  {
    this.mOnBufferingUpdateListener = paramOnBufferingUpdateListener;
  }

  public void setOnCompletionListener(IHAPlayer.OnCompletionListener paramOnCompletionListener)
  {
    this.mOnCompletionListener = paramOnCompletionListener;
  }

  public void setOnErrorListener(IHAPlayer.OnErrorListener paramOnErrorListener)
  {
    this.mOnErrorListener = paramOnErrorListener;
  }

  public void setOnInfoListener(IHAPlayer.OnInfoListener paramOnInfoListener)
  {
    this.mOnInfoListener = paramOnInfoListener;
  }

  public void setOnPreparedListener(IHAPlayer.OnPreparedListener paramOnPreparedListener)
  {
    this.mOnPreparedListener = paramOnPreparedListener;
  }

  public void setOnSeekCompleteListener(IHAPlayer.OnSeekCompleteListener paramOnSeekCompleteListener)
  {
    this.mOnSeekCompleteListener = paramOnSeekCompleteListener;
  }

  public void setOnVideoSizeChangedListener(IHAPlayer.OnVideoSizeChangedListener paramOnVideoSizeChangedListener)
  {
    this.mOnVideoSizeChangedListener = paramOnVideoSizeChangedListener;
  }

  public int setProperties(HASetParam paramHASetParam, Object paramObject)
  {
    DmpLog.i("HAPlayer_NativePlayer", "shark -> setProperties() : key = " + paramHASetParam);
    switch (2.$SwitchMap$com$huawei$playerinterface$parameter$HASetParam[paramHASetParam.ordinal()])
    {
    default:
      return super.setProperties(paramHASetParam, paramObject);
    case 1:
      if ((paramObject instanceof Integer))
      {
        this.playPara.contentType = ((Integer)paramObject).intValue();
        if (this.playPara.contentType == 2)
          this.useProxy = 1;
      }
      break;
    case 2:
    case 3:
    case 4:
    case 5:
    }
    while (true)
    {
      return 0;
      DmpLog.e("HAPlayer_NativePlayer", "shark -> setProperties() ->VIDEO_TYPE: failed,value is not Integer");
      continue;
      if ((paramObject instanceof Integer))
      {
        this.playPara.mediaDuration = ((Integer)paramObject).intValue();
      }
      else
      {
        DmpLog.e("HAPlayer_NativePlayer", "shark -> setProperties() ->TSTV_LENGTH: failed,value is not Integer");
        continue;
        if ((paramObject instanceof Integer))
        {
          switch (((Integer)paramObject).intValue())
          {
          default:
            break;
          case 0:
            setRatioScale();
            break;
          case 1:
            setFullScreen();
            break;
          }
        }
        else
        {
          DmpLog.e("HAPlayer_NativePlayer", "shark -> setProperties() ->SCALE_MODE: failed,value is not Integer");
          continue;
          if ((paramObject instanceof Integer))
          {
            this.playPara.historyPlayPoint = ((Integer)paramObject).intValue();
          }
          else
          {
            DmpLog.e("HAPlayer_NativePlayer", "shark -> setProperties() ->HISTORY_PLAY_POINT: failed,value is not Integer");
            continue;
            if (!(paramObject instanceof Integer))
              break;
            this.useProxy = ((Integer)paramObject).intValue();
          }
        }
      }
    }
    DmpLog.e("HAPlayer_NativePlayer", "shark -> setProperties() ->PROXY_ON: failed,value is not Integer");
    return -1;
  }

  protected void setRatioScale()
  {
    if (this.mediaPlayer == null)
    {
      DmpLog.e("HAPlayer_NativePlayer", "setRatioScale fail mediaPlayer is null");
      return;
    }
    this.playPara.scaleMode = 0;
    setAutoVideoScree(this.mediaPlayer.getVideoWidth(), this.mediaPlayer.getVideoHeight());
  }

  public void start()
  {
    DmpLog.i("HAPlayer_NativePlayer", "sharkplayer start");
    if (this.mediaPlayer == null)
    {
      DmpLog.e("HAPlayer_NativePlayer", "sharkplayer start fail mediaPlayer is null");
      return;
    }
    switch (this.playPara.contentType)
    {
    default:
    case 0:
    case 1:
    case 2:
    }
    while (true)
    {
      this.isPlaying = true;
      return;
      this.mediaPlayer.start();
      if (this.playPara.historyPlayPoint > 0)
      {
        this.mediaPlayer.seekTo(this.playPara.historyPlayPoint);
        this.playPara.historyPlayPoint = 0;
        continue;
        this.mediaPlayer.start();
      }
    }
  }

  public void start(int paramInt)
  {
    DmpLog.i("HAPlayer_NativePlayer", "nativeplayer start timestamp: " + paramInt);
    this.playPara.historyPlayPoint = paramInt;
    start();
  }

  public void stop()
  {
    super.logicStop();
    if (this.mediaPlayer != null)
    {
      DmpLog.i("HAPlayer_NativePlayer", "sharkplayer stop");
      this.mediaPlayer.stop();
    }
  }

  public void suspend()
  {
    super.logicSuspend();
    DmpLog.i("HAPlayer_NativePlayer", "sharkplayer suspend");
    this.mIsPlayerReleased = true;
    this.playPara.playPosition = getCurrentPosition();
    if (getDuration() == 0)
      this.playPara.playPosition = 0;
    if (this.playPara.contentType == 2)
      this.playPara.pauseTimeRecorder = getPlayTimeTick();
    reset();
  }

  protected void updateNativePlayerBuffering()
  {
    if (!isPlaying());
    do
    {
      int j;
      do
      {
        int i;
        do
        {
          return;
          i = 1 + this.updateCnt;
          this.updateCnt = i;
        }
        while (i < 500 / PlayerLogic.getPlayerSysTick());
        this.updateCnt = 0;
        j = this.mediaPlayer.getCurrentPosition();
        if ((this.postionRecorder == j) && (this.playPara.inPlayState == true) && (this.playingNotBuffering))
        {
          onBufferingUpdate(null, 0);
          this.playingNotBuffering = false;
          DmpLog.i("HAPlayer_NativePlayer", "send buffering");
        }
      }
      while ((j == this.postionRecorder) || (this.playPara.inPlayState != true));
      this.postionRecorder = j;
    }
    while (this.playingNotBuffering);
    onBufferingUpdate(null, 100);
    this.playingNotBuffering = true;
    DmpLog.i("HAPlayer_NativePlayer", "send playing");
  }

  protected void updateProxyCode()
  {
    if ((this.playProxy == null) || (this.mediaPlayer == null))
    {
      setNeedUpdateProxyCode(false);
      return;
    }
    int i = OTTProxy.native_proxy_get_callback();
    switch (i)
    {
    case 6:
    case 7:
    case 8:
    default:
      return;
    case 0:
      DmpLog.i("HAPlayer_NativePlayer", "getProxyCodeThread rcv code 0will return");
    case 1:
    case 3:
    case 5:
    case 2:
    case 4:
      while (true)
      {
        setNeedUpdateProxyCode(true);
        return;
        Message localMessage3 = new Message();
        DmpLog.e("HAPlayer_NativePlayer", "getProxyCodeThread rcv code " + i + " will return");
        localMessage3.what = 100;
        localMessage3.arg1 = 104;
        localMessage3.arg2 = 0;
        this.handler.sendMessage(localMessage3);
        continue;
        DmpLog.e("HAPlayer_NativePlayer", "getProxyCodeThread rcv code " + i + " will return");
        continue;
        Message localMessage2 = new Message();
        DmpLog.e("HAPlayer_NativePlayer", "getProxyCodeThread rcv code " + i + " will return");
        localMessage2.what = 100;
        localMessage2.arg1 = 105;
        localMessage2.arg2 = 400;
        this.handler.sendMessage(localMessage2);
      }
    case 9:
    }
    DmpLog.w("HAPlayer_NativePlayer", "getProxyCodeThread rcv code TS_LOWBANDWIDTH");
    Message localMessage1 = new Message();
    localMessage1.what = 200;
    localMessage1.arg1 = 2;
    localMessage1.arg2 = 0;
    this.handler.sendMessage(localMessage1);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.huawei.playerinterface.NativePlayer
 * JD-Core Version:    0.6.2
 */