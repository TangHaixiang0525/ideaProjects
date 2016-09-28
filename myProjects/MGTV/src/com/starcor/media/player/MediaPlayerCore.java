package com.starcor.media.player;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.mstar.android.tvapi.common.PictureManager;
import com.mstar.android.tvapi.common.TvManager;
import com.mstar.android.tvapi.common.vo.EnumVideoArcType;
import com.starcor.config.DeviceInfo;
import com.starcor.config.MgtvVersion;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.App;
import com.starcor.player.HiMediaMediaPlayerAdapter;
import com.starcor.player.MediaPlayerAdapter;
import com.starcor.player.MediaPlayerAdapter.OnBufferingUpdateListener;
import com.starcor.player.MediaPlayerAdapter.OnCompletionListener;
import com.starcor.player.MediaPlayerAdapter.OnErrorListener;
import com.starcor.player.MediaPlayerAdapter.OnInfoListener;
import com.starcor.player.MediaPlayerAdapter.OnPreparedListener;
import com.starcor.player.MediaPlayerAdapter.OnSeekCompleteListener;
import com.starcor.player.MediaPlayerAdapter.OnVideoSizeChangedListener;
import com.starcor.player.MediaPlayerSurfaceAdapter;
import com.starcor.player.MediaPlayerSurfaceSite;
import com.starcor.player.SohuPlayerAdapter;
import com.starcor.player.SystemMediaPlayerAdapter;
import com.starcor.player.TCLRT2995MediaPlayerAdapter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class MediaPlayerCore extends RelativeLayout
  implements MediaPlayerSurfaceSite
{
  public static final int AFORMAT_AAC = 2;
  public static final int AFORMAT_AAC_LATM = 19;
  public static final int AFORMAT_AC3 = 3;
  public static final int AFORMAT_ADPCM = 11;
  public static final int AFORMAT_ALAC = 17;
  public static final int AFORMAT_ALAW = 4;
  public static final int AFORMAT_AMR = 12;
  public static final int AFORMAT_APE = 20;
  public static final int AFORMAT_COOK = 9;
  public static final int AFORMAT_DRA = 23;
  public static final int AFORMAT_DTS = 6;
  public static final int AFORMAT_EAC3 = 21;
  public static final int AFORMAT_FLAC = 8;
  public static final int AFORMAT_MAX = 28;
  public static final int AFORMAT_MPEG = 0;
  public static final int AFORMAT_MPEG1 = 25;
  public static final int AFORMAT_MPEG2 = 26;
  public static final int AFORMAT_MULAW = 5;
  public static final int AFORMAT_PCM_BLURAY = 16;
  public static final int AFORMAT_PCM_S16BE = 7;
  public static final int AFORMAT_PCM_S16LE = 1;
  public static final int AFORMAT_PCM_U8 = 10;
  public static final int AFORMAT_PCM_WIFIDISPLAY = 22;
  public static final int AFORMAT_RAAC = 13;
  public static final int AFORMAT_SIPR = 24;
  public static final int AFORMAT_UNKNOWN = -1;
  public static final int AFORMAT_UNSUPPORT = 27;
  public static final int AFORMAT_VORBIS = 18;
  public static final int AFORMAT_WMA = 14;
  public static final int AFORMAT_WMAPRO = 15;
  public static final int MEDIA_ERR_IA = 53;
  public static final int MEDIA_ERR_IO = 52;
  public static final int MEDIA_ERR_UNKNOW = 50;
  public static final int MEDIA_ERR_URL = 51;
  public static final int STATE_ERROR = -1;
  public static final int STATE_IDLE = 0;
  public static final int STATE_PAUSED = 4;
  public static final int STATE_PLAYBACK_COMPLETED = 5;
  public static final int STATE_PLAYING = 3;
  public static final int STATE_PREPARED = 2;
  public static final int STATE_PREPARING = 1;
  private static final String TAG = "MediaPlayerCore";
  public static final int VIDEO_SCALE_16v9 = 1;
  public static final int VIDEO_SCALE_4v3 = 2;
  MediaPlayerSurfaceAdapter _currentSurface;
  private boolean cachedIsPlaying = false;
  private int cachedPosition = 0;
  private MediaPlayerAdapter.OnCompletionListener controllerOnMpCompletionLsnr = null;
  private MediaPlayerAdapter.OnErrorListener controllerOnMpErrorLsnr = null;
  private MediaPlayerAdapter.OnInfoListener controllerOnMpInfoLsnr = null;
  private MediaPlayerAdapter.OnPreparedListener controllerOnMpPreparedLsnr = null;
  private MediaPlayerAdapter.OnSeekCompleteListener controllerOnMpSeekCompleteLsnr = null;
  private int currentBufferPercentage;
  private int currentState = 0;
  private int duration = -1;
  private boolean isFirstStart = true;
  boolean isSohuUrl = false;
  private long lastCallGetPositionTime = 0L;
  private long lastCallIsPlayingTime = 0L;
  private long lastSeekTime = 0L;
  private Context mContext = null;
  int mPos = 0;
  private int mPosAfterSeek = 0;
  private int mPosBeforSeek = -1;
  private int mVideoHeight = 0;
  private int mVideoWidth = 0;
  private Object mediaInfo = null;
  private MediaPlayerAdapter mediaPlayer = null;
  MediaPlayerAdapter.OnBufferingUpdateListener mpOnBufferingUpdateLsnr = new MediaPlayerAdapter.OnBufferingUpdateListener()
  {
    public void onBufferingUpdate(MediaPlayerAdapter paramAnonymousMediaPlayerAdapter, int paramAnonymousInt)
    {
      MediaPlayerCore.access$1002(MediaPlayerCore.this, paramAnonymousInt);
    }
  };
  MediaPlayerAdapter.OnCompletionListener mpOnCompletionLsnr = new MediaPlayerAdapter.OnCompletionListener()
  {
    public void onCompletion(MediaPlayerAdapter paramAnonymousMediaPlayerAdapter)
    {
      Logger.i("MediaPlayerCore", "OnCompletionListener.onCompletion()");
      MediaPlayerCore.access$202(MediaPlayerCore.this, 5);
      MediaPlayerCore.access$302(MediaPlayerCore.this, 5);
      if (MediaPlayerCore.this.controllerOnMpCompletionLsnr != null)
        MediaPlayerCore.this.controllerOnMpCompletionLsnr.onCompletion(MediaPlayerCore.this.mediaPlayer);
    }
  };
  MediaPlayerAdapter.OnErrorListener mpOnErrorLsnr = new MediaPlayerAdapter.OnErrorListener()
  {
    public boolean onError(MediaPlayerAdapter paramAnonymousMediaPlayerAdapter, int paramAnonymousInt1, int paramAnonymousInt2)
    {
      Logger.e("MediaPlayerCore", "OnErrorListener.onError() wath:" + paramAnonymousInt1 + ", extra:" + paramAnonymousInt2);
      MediaPlayerCore.access$202(MediaPlayerCore.this, -1);
      MediaPlayerCore.access$302(MediaPlayerCore.this, -1);
      if ((MediaPlayerCore.this.controllerOnMpErrorLsnr != null) && (MediaPlayerCore.this.controllerOnMpErrorLsnr.onError(paramAnonymousMediaPlayerAdapter, paramAnonymousInt1, paramAnonymousInt2)));
      return true;
    }
  };
  MediaPlayerAdapter.OnInfoListener mpOnInfoLsnr = new MediaPlayerAdapter.OnInfoListener()
  {
    public boolean onInfo(MediaPlayerAdapter paramAnonymousMediaPlayerAdapter, int paramAnonymousInt1, int paramAnonymousInt2)
    {
      if (MediaPlayerCore.this.controllerOnMpInfoLsnr != null)
        MediaPlayerCore.this.controllerOnMpInfoLsnr.onInfo(paramAnonymousMediaPlayerAdapter, paramAnonymousInt1, paramAnonymousInt2);
      return false;
    }
  };
  MediaPlayerAdapter.OnPreparedListener mpOnPreparedLsnr = new MediaPlayerAdapter.OnPreparedListener()
  {
    public void onPrepared(MediaPlayerAdapter paramAnonymousMediaPlayerAdapter)
    {
      MediaPlayerCore.access$202(MediaPlayerCore.this, 2);
      if (MediaPlayerCore.this.controllerOnMpPreparedLsnr != null)
        MediaPlayerCore.this.controllerOnMpPreparedLsnr.onPrepared(MediaPlayerCore.this.mediaPlayer);
    }
  };
  MediaPlayerAdapter.OnSeekCompleteListener mpOnSeekCompleteLsnr = new MediaPlayerAdapter.OnSeekCompleteListener()
  {
    public void onSeekComplete(MediaPlayerAdapter paramAnonymousMediaPlayerAdapter)
    {
      Logger.i("MediaPlayerCore", "OnSeekCompleteListener.onSeekComplete()");
      if (MediaPlayerCore.this.controllerOnMpSeekCompleteLsnr != null)
        MediaPlayerCore.this.controllerOnMpSeekCompleteLsnr.onSeekComplete(paramAnonymousMediaPlayerAdapter);
    }
  };
  MediaPlayerAdapter.OnVideoSizeChangedListener mpOnSizeChangedLsnr = new MediaPlayerAdapter.OnVideoSizeChangedListener()
  {
    public void onVideoSizeChanged(MediaPlayerAdapter paramAnonymousMediaPlayerAdapter, int paramAnonymousInt1, int paramAnonymousInt2)
    {
      MediaPlayerCore.access$002(MediaPlayerCore.this, paramAnonymousMediaPlayerAdapter.getVideoWidth());
      MediaPlayerCore.access$102(MediaPlayerCore.this, paramAnonymousMediaPlayerAdapter.getVideoHeight());
      if ((MediaPlayerCore.this.mVideoWidth != 0) && (MediaPlayerCore.this.mVideoHeight != 0))
        Logger.i("MediaPlayerCore", "OnVideoSizeChangedListener.onVideoSizeChanged() mVideoWidth:" + MediaPlayerCore.this.mVideoWidth + "  mVideoHeight:" + MediaPlayerCore.this.mVideoHeight);
    }
  };
  private String playUrl = null;
  private PictureManager pm;
  private MediaPlayer realPlayer = null;
  private boolean startFlag = false;
  boolean supportSohu = true;
  private View surfaceView;
  private int targetState = 0;
  private int videoScale = 1;

  static
  {
    SohuPlayerAdapter.registerFactory();
    if ((MgtvVersion.getFactoryCode() == 900010001) || (MgtvVersion.getFactoryCode() == 900010401))
      TCLRT2995MediaPlayerAdapter.registerFactory();
    if (DeviceInfo.isHMD())
      HiMediaMediaPlayerAdapter.registerFactory();
    SystemMediaPlayerAdapter.registerFactory();
  }

  public MediaPlayerCore(Context paramContext)
  {
    super(paramContext);
    this.mContext = paramContext;
    initVideoView();
  }

  public MediaPlayerCore(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mContext = paramContext;
    initVideoView();
  }

  public MediaPlayerCore(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    this.mContext = paramContext;
    initVideoView();
  }

  private void initVideoView()
  {
    this.mVideoWidth = 0;
    this.mVideoHeight = 0;
    this.currentState = 0;
    this.targetState = 0;
    MediaPlayerAdapter.prepareSurface(this, null, SohuPlayerAdapter.class);
  }

  private void openVideo()
  {
    Logger.i("MediaPlayerCore", "openVideo()" + this.playUrl);
    if (this.playUrl == null)
    {
      Logger.e("MediaPlayerCore", "openVideo() playUrl is null");
      return;
    }
    if (this.supportSohu)
      if (this.isSohuUrl)
      {
        MediaPlayerAdapter.prepareSurface(this, SohuPlayerAdapter.class, null);
        Logger.i("laladin", "use sohuPlayer");
      }
    while ((!this.isSohuUrl) && (!getCurrentSurface().isReady()))
    {
      Logger.i("laladin", "getCurrentSurface 未准备好");
      return;
      MediaPlayerAdapter.prepareSurface(this, null, SohuPlayerAdapter.class);
      continue;
      MediaPlayerAdapter.prepareSurface(this);
    }
    Intent localIntent = new Intent("com.android.music.musicservicecommand");
    localIntent.addFlags(32);
    localIntent.putExtra("command", "pause");
    this.mContext.sendBroadcast(localIntent);
    release();
    while (true)
    {
      try
      {
        this.currentBufferPercentage = 0;
        this.duration = -1;
        Logger.i("MediaPlayerCore", "openVideo() new MediaPlayerAdapter begin");
        if (this.supportSohu)
          if (this.isSohuUrl)
          {
            this.mediaPlayer = MediaPlayerAdapter.create(this, SohuPlayerAdapter.class, null);
            Logger.i("MediaPlayerCore", "openVideo() new MediaPlayerAdapter end");
            this.mediaPlayer.setOnPreparedListener(this.mpOnPreparedLsnr);
            this.mediaPlayer.setOnVideoSizeChangedListener(this.mpOnSizeChangedLsnr);
            this.mediaPlayer.setOnInfoListener(this.mpOnInfoLsnr);
            this.mediaPlayer.setOnSeekCompleteListener(this.mpOnSeekCompleteLsnr);
            this.mediaPlayer.setOnCompletionListener(this.mpOnCompletionLsnr);
            this.mediaPlayer.setOnErrorListener(this.mpOnErrorLsnr);
            this.mediaPlayer.setOnBufferingUpdateListener(this.mpOnBufferingUpdateLsnr);
            this.mediaPlayer.setDataSource(this.mContext, this.playUrl);
            this.mediaPlayer.setDisplay(getCurrentSurface());
            this.mediaPlayer.setAudioStreamType(3);
            this.mediaPlayer.setScreenOnWhilePlaying(true);
            Logger.i("MediaPlayerCore", "openVideo() call prepareAsync");
            this.mediaPlayer.prepareAsync();
            this.currentState = 1;
            return;
          }
      }
      catch (IOException localIOException)
      {
        Logger.e("MediaPlayerCore", "openVideo() IO异常 ");
        this.currentState = -1;
        this.targetState = -1;
        this.mpOnErrorLsnr.onError(this.mediaPlayer, 52, 0);
        return;
        this.mediaPlayer = MediaPlayerAdapter.create(this, null, SohuPlayerAdapter.class);
        continue;
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
        Logger.e("MediaPlayerCore", "openVideo() 播放状态异常 ");
        this.currentState = -1;
        this.targetState = -1;
        this.mpOnErrorLsnr.onError(this.mediaPlayer, 53, 0);
        return;
      }
      this.mediaPlayer = MediaPlayerAdapter.create(this);
    }
  }

  private void release()
  {
    Logger.i("MediaPlayerCore", "release()");
    this.lastCallGetPositionTime = 0L;
    this.cachedPosition = 0;
    this.lastCallIsPlayingTime = 0L;
    this.cachedIsPlaying = false;
    if (this.mediaPlayer == null)
      return;
    this.mediaPlayer.reset();
    this.mediaPlayer.release();
    this.mediaPlayer = null;
    this.currentState = 0;
  }

  private void releaseAsync()
  {
    Logger.i("MediaPlayerCore", "releaseAsync()");
    if (this.mediaPlayer == null)
    {
      Logger.i("MediaPlayerCore", "releaseAsync() mediaPlayer is null");
      return;
    }
    this.currentState = 0;
    this.targetState = 0;
    this.mediaPlayer.setOnPreparedListener(null);
    this.mediaPlayer.setOnVideoSizeChangedListener(null);
    this.mediaPlayer.setOnInfoListener(null);
    this.mediaPlayer.setOnSeekCompleteListener(null);
    this.mediaPlayer.setOnCompletionListener(null);
    this.mediaPlayer.setOnErrorListener(null);
    this.mediaPlayer.setOnBufferingUpdateListener(null);
    new Thread(new Runnable()
    {
      public void run()
      {
        MediaPlayerAdapter localMediaPlayerAdapter = MediaPlayerCore.this.mediaPlayer;
        boolean bool = MediaPlayerCore.this.startFlag;
        MediaPlayerCore.access$502(MediaPlayerCore.this, null);
        MediaPlayerCore.access$1402(MediaPlayerCore.this, false);
        if (localMediaPlayerAdapter.isPlaying())
        {
          Logger.i("MediaPlayerCore", "releaseAsync() call xmediaPlayer.stop() start");
          if (bool)
            localMediaPlayerAdapter.stop();
          Logger.i("MediaPlayerCore", "releaseAsync() call xmediaPlayer.stop() end");
        }
        Logger.i("MediaPlayerCore", "releaseAsync() call xmediaPlayer.release() start");
        localMediaPlayerAdapter.release();
        Logger.i("MediaPlayerCore", "releaseAsync() call xmediaPlayer.release() end");
      }
    }).start();
  }

  private void releaseSync()
  {
    Logger.i("MediaPlayerCore", "releaseSync()");
    this.duration = -1;
    if (this.mediaPlayer == null)
      return;
    if (this.startFlag)
    {
      this.startFlag = false;
      this.mediaPlayer.stop();
    }
    this.mediaPlayer.reset();
    this.mediaPlayer.release();
    this.mediaPlayer = null;
    this.currentState = 0;
    this.targetState = 0;
  }

  private void setMS801FullScreen()
  {
    if ((DeviceInfo.getFactory() != 900018002) && (DeviceInfo.getFactory() != 900018003))
    {
      Logger.i("MediaPlayerCore", "setMS801FullScreen version mismatching");
      return;
    }
    Logger.i("MediaPlayerCore", "setMS801FullScreen start");
    try
    {
      Class localClass1 = Class.forName("com.tvos.common.TvManager");
      Logger.i("MediaPlayerCore", "1.得到TvManger");
      Method localMethod1 = localClass1.getMethod("getPictureManager", new Class[0]);
      Logger.i("MediaPlayerCore", "2.得到tvManager.getpictureManager()方法");
      Object localObject1 = localMethod1.invoke(localClass1, new Object[0]);
      Logger.i("MediaPlayerCore", "3.执行方法得到PictureManager");
      Class localClass2 = Class.forName("com.tvos.common.PictureManager");
      Logger.i("MediaPlayerCore", "4.找到pictureManger的类");
      Logger.i("MediaPlayerCore", "5.找到pictureManager类的setAspectRatio方法：");
      Class localClass3 = Class.forName("com.tvos.common.vo.TvOsType$EnumVideoArcType");
      Logger.i("MediaPlayerCore", "5.1 找到方法的参数类");
      Method localMethod2 = localClass2.getMethod("setAspectRatio", new Class[] { localClass3 });
      Logger.i("MediaPlayerCore", "5.2 找到方法");
      Field localField = localClass3.getField("E_16x9");
      Logger.i("MediaPlayerCore", "6 找到方法执行时的参数对像 paramFiled=" + localField);
      Object localObject2 = localField.get(localClass3);
      Logger.i("MediaPlayerCore", "6.1得到参数实体 paramsObject=" + localObject2);
      localMethod2.invoke(localObject1, new Object[] { localObject2 });
      Logger.i("MediaPlayerCore", "7.执行方法完成");
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  private void setMstarFullScreen()
  {
    if (!DeviceInfo.isFactoryTCLMstarXX());
    do
    {
      return;
      Logger.i("MediaPlayerCore", "setMstarFullScreen isFirstStart:" + this.isFirstStart);
    }
    while (!this.isFirstStart);
    this.isFirstStart = false;
    try
    {
      if (this.pm == null)
        this.pm = TvManager.getInstance().getPictureManager();
      Logger.d("MediaPlayerCore", "setMstarFullScreen pre set 16:9");
      this.pm.setAspectRatio(EnumVideoArcType.E_16x9);
      Logger.d("MediaPlayerCore", "setMstarFullScreen after set 16:9");
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  private void updateSurfaceViewLayout(int paramInt1, int paramInt2)
  {
    if (this.surfaceView != null)
    {
      RelativeLayout.LayoutParams localLayoutParams = (RelativeLayout.LayoutParams)this.surfaceView.getLayoutParams();
      localLayoutParams.width = paramInt1;
      localLayoutParams.height = paramInt2;
      this.surfaceView.requestLayout();
      this.surfaceView.invalidate();
    }
  }

  public boolean addSurface(MediaPlayerSurfaceAdapter paramMediaPlayerSurfaceAdapter)
  {
    Logger.i("MediaPlayerCore", "addSurface surface = " + paramMediaPlayerSurfaceAdapter + ", view = " + paramMediaPlayerSurfaceAdapter.getView());
    this._currentSurface = paramMediaPlayerSurfaceAdapter;
    this.surfaceView = paramMediaPlayerSurfaceAdapter.getView();
    addView(this.surfaceView, -1, -1);
    return true;
  }

  public void changeVideoLayoutTo16v9()
  {
    RelativeLayout.LayoutParams localLayoutParams = (RelativeLayout.LayoutParams)getLayoutParams();
    localLayoutParams.width = App.Operation(1280.0F);
    localLayoutParams.height = App.Operation(720.0F);
    localLayoutParams.addRule(13);
    try
    {
      invalidate();
      Logger.i("MediaPlayerCore", "changeVideoLayoutTo16v9 " + localLayoutParams.width + "x" + localLayoutParams.height);
      updateSurfaceViewLayout(localLayoutParams.width, localLayoutParams.height);
      return;
    }
    catch (Exception localException)
    {
      while (true)
        Logger.w("MediaPlayerCore", "requestLayout Exception", localException);
    }
  }

  public void changeVideoLayoutTo2351()
  {
    RelativeLayout.LayoutParams localLayoutParams = (RelativeLayout.LayoutParams)getLayoutParams();
    localLayoutParams.width = getResources().getDisplayMetrics().widthPixels;
    localLayoutParams.height = ((int)(getResources().getDisplayMetrics().widthPixels / 2.35D));
    localLayoutParams.addRule(13);
    try
    {
      requestLayout();
      invalidate();
      Logger.i("MediaPlayerCore", "changeVideoLayoutTo2351 " + localLayoutParams.width + "x" + localLayoutParams.height);
      updateSurfaceViewLayout(localLayoutParams.width, localLayoutParams.height);
      return;
    }
    catch (Exception localException)
    {
      while (true)
        Logger.w("MediaPlayerCore", "requestLayout Exception", localException);
    }
  }

  public void changeVideoLayoutTo4v3()
  {
    RelativeLayout.LayoutParams localLayoutParams = (RelativeLayout.LayoutParams)getLayoutParams();
    localLayoutParams.width = App.Operation(960.0F);
    localLayoutParams.height = App.Operation(720.0F);
    localLayoutParams.addRule(13);
    try
    {
      requestLayout();
      invalidate();
      Logger.i("MediaPlayerCore", "changeVideoLayoutTo4v3 " + localLayoutParams.width + "x" + localLayoutParams.height);
      updateSurfaceViewLayout(localLayoutParams.width, localLayoutParams.height);
      return;
    }
    catch (Exception localException)
    {
      while (true)
        Logger.w("MediaPlayerCore", "requestLayout Exception", localException);
    }
  }

  public void changeVideoLayoutToDefault()
  {
    RelativeLayout.LayoutParams localLayoutParams = (RelativeLayout.LayoutParams)getLayoutParams();
    localLayoutParams.width = getResources().getDisplayMetrics().widthPixels;
    if ((this.mVideoWidth != 0) && (this.mVideoHeight != 0))
      localLayoutParams.height = ((int)(getResources().getDisplayMetrics().widthPixels * (1.0F * this.mVideoWidth / this.mVideoHeight)));
    while (true)
    {
      localLayoutParams.addRule(13);
      try
      {
        requestLayout();
        invalidate();
        Logger.i("MediaPlayerCore", "changeVideoLayoutToDefault " + localLayoutParams.width + "x" + localLayoutParams.height);
        updateSurfaceViewLayout(localLayoutParams.width, localLayoutParams.height);
        return;
        localLayoutParams.height = getResources().getDisplayMetrics().heightPixels;
      }
      catch (Exception localException)
      {
        while (true)
          Logger.w("MediaPlayerCore", "requestLayout Exception", localException);
      }
    }
  }

  public void changeVideoLayoutToFullScreen()
  {
    RelativeLayout.LayoutParams localLayoutParams = (RelativeLayout.LayoutParams)getLayoutParams();
    localLayoutParams.width = App.Operation(App.SCREEN_WIDTH);
    localLayoutParams.height = App.Operation(App.SCREEN_HEIGHT);
    localLayoutParams.addRule(13);
    try
    {
      requestLayout();
      invalidate();
      Logger.i("MediaPlayerCore", "changeVideoLayoutToFullScreen " + localLayoutParams.width + "x" + localLayoutParams.height);
      updateSurfaceViewLayout(localLayoutParams.width, localLayoutParams.height);
      return;
    }
    catch (Exception localException)
    {
      while (true)
        Logger.w("MediaPlayerCore", "requestLayout Exception", localException);
    }
  }

  public String[] getAudioTrackData()
  {
    if (this.mediaPlayer != null)
      return this.mediaPlayer.getAudioTrackData();
    return null;
  }

  public int getCurrentPosition()
  {
    if (!isInPlayState())
      return 0;
    if ((System.currentTimeMillis() >= this.lastCallGetPositionTime) && (System.currentTimeMillis() < 50L + this.lastCallGetPositionTime));
    for (int i = this.cachedPosition; (this.mPosBeforSeek >= 0) && (i >= this.mPosBeforSeek) && (i < 1500 + this.mPosBeforSeek) && (System.currentTimeMillis() - this.lastSeekTime < 1500L); i = this.cachedPosition)
    {
      Logger.i("MediaPlayerCore", "CurrentPosition1 bfSeek:" + this.mPosBeforSeek + ", afSeek:" + this.mPosAfterSeek + ", now:" + this.mediaPlayer.getCurrentPosition());
      return this.mPosAfterSeek;
      this.lastCallGetPositionTime = System.currentTimeMillis();
      this.cachedPosition = this.mediaPlayer.getCurrentPosition();
      Logger.i("MediaPlayerCore", "getCurrentPosition : " + this.cachedPosition);
    }
    if (i == 0)
    {
      Logger.i("MediaPlayerCore", "CurrentPosition2 bfSeek:" + this.mPosBeforSeek + ", afSeek:" + this.mPosAfterSeek + ", now:" + this.mediaPlayer.getCurrentPosition());
      return 0;
    }
    this.mPos = i;
    Logger.i("MediaPlayerCore", "CurrentPosition3 bfSeek:" + this.mPosBeforSeek + ", afSeek:" + this.mPosAfterSeek + ", now:" + this.mediaPlayer.getCurrentPosition());
    return this.mPos;
  }

  public MediaPlayerSurfaceAdapter getCurrentSurface()
  {
    return this._currentSurface;
  }

  public int getDuration()
  {
    if (this.mediaPlayer == null)
      return -1;
    if (this.duration > 0)
      return this.duration;
    this.duration = this.mediaPlayer.getDuration();
    Logger.i("MediaPlayerCore", "getDuration() Duration:" + this.duration);
    return this.duration;
  }

  public int getPlayState()
  {
    return this.currentState;
  }

  public Context getSiteContext()
  {
    return this.mContext;
  }

  public String[] getSubtitles()
  {
    if (this.mediaPlayer != null)
      return this.mediaPlayer.getSubtitles();
    return null;
  }

  public int getVideoHeight()
  {
    return this.mVideoHeight;
  }

  public int getVideoWidth()
  {
    return this.mVideoWidth;
  }

  public boolean isInPlayState()
  {
    return (this.mediaPlayer != null) && (this.currentState != -1) && (this.currentState != 0) && (this.currentState != 1);
  }

  public boolean isPlaying()
  {
    if ((System.currentTimeMillis() >= this.lastCallIsPlayingTime) && (System.currentTimeMillis() < 100L + this.lastCallIsPlayingTime))
      return this.cachedIsPlaying;
    this.lastCallIsPlayingTime = System.currentTimeMillis();
    if ((isInPlayState()) && (this.mediaPlayer.isPlaying()));
    for (boolean bool = true; ; bool = false)
    {
      this.cachedIsPlaying = bool;
      return this.cachedIsPlaying;
    }
  }

  public void onSurfaceChanged()
  {
    Logger.i("MediaPlayerCore", "surfaceChanged()");
    if (this.targetState == 3);
    for (int i = 1; ; i = 0)
    {
      if ((this.mediaPlayer != null) && (i != 0))
        start(false);
      return;
    }
  }

  public void onSurfaceCreated()
  {
    Logger.i("MediaPlayerCore", "surfaceCreated()");
    if ((DeviceInfo.isXiaoMi()) && (this.currentState == 4))
    {
      if (this.mediaPlayer != null)
        this.mediaPlayer.setDisplay(getCurrentSurface());
      return;
    }
    openVideo();
  }

  public void onSurfaceDestroy()
  {
    Logger.i("MediaPlayerCore", "surfaceDestroyed()");
    if ((DeviceInfo.isXiaoMi()) && (this.currentState == 4))
      return;
    releaseAsync();
    Logger.i("MediaPlayerCore", "surfaceDestroyed end");
  }

  public void pause()
  {
    if (this.mediaPlayer == null)
      return;
    this.lastCallGetPositionTime = 0L;
    this.lastCallIsPlayingTime = 0L;
    if ((isInPlayState()) && (this.mediaPlayer.isPlaying()))
    {
      this.mediaPlayer.pause();
      this.currentState = 4;
    }
    this.targetState = 4;
  }

  public boolean removeSurface(MediaPlayerSurfaceAdapter paramMediaPlayerSurfaceAdapter)
  {
    removeView(paramMediaPlayerSurfaceAdapter.getView());
    this._currentSurface = null;
    return true;
  }

  public void requestLayout()
  {
    super.requestLayout();
    if ((DeviceInfo.isHMDQ5With1Kernal()) && (this.mediaPlayer != null))
      this.mediaPlayer.resetVideoLayout();
  }

  public void reset()
  {
    this.lastCallGetPositionTime = 0L;
    this.lastCallIsPlayingTime = 0L;
    this.mPos = 0;
    this.mPosBeforSeek = -1;
    this.duration = -1;
    if (this.mediaPlayer != null)
    {
      this.mediaPlayer.reset();
      this.mediaPlayer = null;
      this.currentState = 0;
      this.targetState = 0;
    }
  }

  public void resetVideoLayout()
  {
    if (this.mediaPlayer != null)
      this.mediaPlayer.resetVideoLayout();
  }

  public void seekTo(int paramInt)
  {
    Logger.i("MediaPlayerCore", "seekTo() pos:" + paramInt);
    if (!isInPlayState())
      return;
    this.lastSeekTime = System.currentTimeMillis();
    this.mPosBeforSeek = this.mediaPlayer.getCurrentPosition();
    this.mPosAfterSeek = paramInt;
    this.mediaPlayer.seekTo(paramInt);
  }

  public void setAudioTrack(int paramInt)
  {
    if (this.mediaPlayer != null)
      this.mediaPlayer.setAudioTrack(paramInt);
  }

  public void setOnCompletionListener(MediaPlayerAdapter.OnCompletionListener paramOnCompletionListener)
  {
    this.controllerOnMpCompletionLsnr = paramOnCompletionListener;
  }

  public void setOnErrorListener(MediaPlayerAdapter.OnErrorListener paramOnErrorListener)
  {
    this.controllerOnMpErrorLsnr = paramOnErrorListener;
  }

  public void setOnInfoListener(MediaPlayerAdapter.OnInfoListener paramOnInfoListener)
  {
    this.controllerOnMpInfoLsnr = paramOnInfoListener;
  }

  public void setOnPreparedListener(MediaPlayerAdapter.OnPreparedListener paramOnPreparedListener)
  {
    this.controllerOnMpPreparedLsnr = paramOnPreparedListener;
  }

  public void setOnSeekCompleteListener(MediaPlayerAdapter.OnSeekCompleteListener paramOnSeekCompleteListener)
  {
    this.controllerOnMpSeekCompleteLsnr = paramOnSeekCompleteListener;
  }

  public void setSoundTrack(int paramInt)
  {
    if (this.mediaPlayer != null)
      this.mediaPlayer.setSoundTrack(paramInt);
  }

  public void setSubtitle(int paramInt)
  {
    if (this.mediaPlayer != null)
      this.mediaPlayer.setSubtitle(paramInt);
  }

  public void setVideoParamsForSohu(final String paramString1, String paramString2)
  {
    post(new Runnable()
    {
      public void run()
      {
        MediaPlayerCore.access$1102(MediaPlayerCore.this, paramString1);
        MediaPlayerCore.this.isSohuUrl = true;
        Logger.i("MediaPlayerCore", "setVideoParamsForSohu() sohuParams:" + MediaPlayerCore.this.playUrl);
        MediaPlayerCore.this.openVideo();
        MediaPlayerCore.this.requestLayout();
        MediaPlayerCore.this.invalidate();
      }
    });
  }

  public void setVideoScale(int paramInt)
  {
    this.videoScale = paramInt;
  }

  public void setVideoURI(final String paramString)
  {
    post(new Runnable()
    {
      public void run()
      {
        MediaPlayerCore.access$1102(MediaPlayerCore.this, paramString);
        MediaPlayerCore.this.isSohuUrl = false;
        Logger.i("MediaPlayerCore", "setVideoURI() uri:" + paramString);
        MediaPlayerCore.this.openVideo();
        MediaPlayerCore.this.requestLayout();
        MediaPlayerCore.this.invalidate();
      }
    });
  }

  public void setVisibility(int paramInt)
  {
    super.setVisibility(paramInt);
    if (this.surfaceView != null)
      this.surfaceView.setVisibility(paramInt);
  }

  public void start()
  {
    start(false);
  }

  public void start(boolean paramBoolean)
  {
    this.lastCallGetPositionTime = 0L;
    this.lastCallIsPlayingTime = 0L;
    if (isInPlayState())
    {
      this.mediaPlayer.start(paramBoolean);
      this.startFlag = true;
      this.currentState = 3;
    }
    this.targetState = 3;
    setMstarFullScreen();
    postDelayed(new Runnable()
    {
      public void run()
      {
        MediaPlayerCore.this.setMS801FullScreen();
      }
    }
    , 300L);
  }

  public void stop()
  {
    this.lastCallGetPositionTime = 0L;
    this.lastCallIsPlayingTime = 0L;
    this.mPos = 0;
    this.mPosBeforSeek = -1;
    releaseSync();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.media.player.MediaPlayerCore
 * JD-Core Version:    0.6.2
 */