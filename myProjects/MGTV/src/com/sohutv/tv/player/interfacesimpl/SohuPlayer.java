package com.sohutv.tv.player.interfacesimpl;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceView;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.sohu.logger.SohuLoggerAgent;
import com.sohu.logger.bean.LogInfo;
import com.sohu.logger.common.AppConstants;
import com.sohu.logger.log.OutputLog;
import com.sohu.logger.model.SohuPlayerLogger.ISohuPlayerLogger;
import com.sohutv.tv.player.ad.AdRequestParams;
import com.sohutv.tv.player.ad.SohuTVAdvertise;
import com.sohutv.tv.player.ad.SohuTVAdvertise.PlaybackState;
import com.sohutv.tv.player.ad.SohuTVAdvertise.a;
import com.sohutv.tv.player.entity.PlayInfo;
import com.sohutv.tv.player.interfaces.ISohuMediaControllerCallback;
import com.sohutv.tv.player.interfaces.PlayerCallback;
import com.sohutv.tv.player.play.SohuMediaController;
import com.sohutv.tv.player.play.b;
import com.sohutv.tv.player.util.g;
import com.sohutv.tv.player.util.h;
import com.sohutv.tv.player.util.i;
import com.sohutv.tv.player.util.k;

public class SohuPlayer extends FrameLayout
  implements SohuPlayerLogger.ISohuPlayerLogger, SohuTVAdvertise.a
{
  private PlayerCallback iPlayerCallback;
  private boolean isActivityAlive = true;
  private com.sohutv.tv.player.ad.c mAdListener = new com.sohutv.tv.player.ad.c()
  {
    public void a()
    {
      if (SohuPlayer.this.mTVAdvertise != null)
      {
        if (SohuPlayer.this.isActivityAlive)
          SohuPlayer.this.mTVAdvertise.a(SohuPlayer.this);
        return;
      }
      Log.e("Sohuplayer", "requestAd() mTVAdvertise is null");
    }

    public void b()
    {
      SohuPlayer.this.playVideoContent(false);
    }
  };
  private Context mContext;
  private i mPlayerTool;
  private g mSohuOttAPI;
  private SohuTVAdvertise mTVAdvertise;
  private com.sohutv.tv.player.play.a mVideoView;

  public SohuPlayer(Context paramContext)
  {
    super(paramContext);
    initView(paramContext);
  }

  public SohuPlayer(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    initView(paramContext);
  }

  public SohuPlayer(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    initView(paramContext);
  }

  private void finishSohuAD()
  {
    if (this.mTVAdvertise != null)
      this.mTVAdvertise.g();
  }

  private void hidePauseAd()
  {
    if (this.mTVAdvertise != null)
      this.mTVAdvertise.c();
  }

  private void initView(Context paramContext)
  {
    this.mContext = paramContext;
    this.mSohuOttAPI = new g(this.mContext, null);
    this.mVideoView = k.a(this.mContext, com.sohutv.tv.player.util.a.c.l);
    this.mVideoView.setVideoListener(new a());
    FrameLayout.LayoutParams localLayoutParams = new FrameLayout.LayoutParams(-1, -1);
    localLayoutParams.gravity = 17;
    addView(this.mVideoView, localLayoutParams);
    this.mPlayerTool = i.a();
    this.mPlayerTool.a(this.mVideoView);
    if ((com.sohutv.tv.player.util.a.a.h.booleanValue()) || (com.sohutv.tv.player.util.a.a.i.booleanValue()))
      this.mTVAdvertise = new SohuTVAdvertise(this.mContext, this);
    i.a().a(this.mAdListener);
  }

  private void showAdRemainTimeView(boolean paramBoolean)
  {
    if ((!paramBoolean) && (this.iPlayerCallback != null))
      this.iPlayerCallback.adRemainTime(-1);
  }

  private void toStart()
  {
    if (this.mVideoView != null)
    {
      Log.e("Sohuplayer", "start play");
      this.mVideoView.startSPlay();
      return;
    }
    Log.e("Sohuplayer", "start() mVideoView is null");
  }

  public void exitNotify()
  {
    this.isActivityAlive = false;
  }

  public AdRequestParams getAdRequestParams()
  {
    return this.mSohuOttAPI.a();
  }

  public int getCurrentPosition()
  {
    if (this.mVideoView != null)
      return this.mVideoView.getSCurrentPosition();
    return -1000;
  }

  public int getDuration()
  {
    if (this.mVideoView != null)
      return this.mVideoView.getSDuration();
    return 0;
  }

  public int getICurrentPosition()
  {
    return getCurrentPosition();
  }

  public int getIDuration()
  {
    return getDuration();
  }

  protected PlayInfo getPLayInfo()
  {
    return com.sohutv.tv.player.util.a.a.f;
  }

  public LogInfo getPlayInfo()
  {
    return this.mSohuOttAPI.b();
  }

  protected SurfaceView getSurfaceView()
  {
    if (this.mVideoView != null)
      return this.mVideoView.getSurfaceView();
    return null;
  }

  protected int getVideoHeight()
  {
    if (this.mVideoView != null)
      return this.mVideoView.getSVideoHeight();
    return 0;
  }

  protected int getVideoWidth()
  {
    if (this.mVideoView != null)
      return this.mVideoView.getSVideoWidth();
    return 0;
  }

  protected boolean isAdPlaying()
  {
    return SohuTVAdvertise.b != SohuTVAdvertise.PlaybackState.INVIDEO;
  }

  public boolean isIPlaying()
  {
    return isPlaying();
  }

  protected boolean isIgnoreSurfaceDestory()
  {
    if (this.mVideoView != null)
      return ((b)this.mVideoView).b();
    return false;
  }

  public boolean isPlaying()
  {
    if (this.mVideoView != null)
      return this.mVideoView.isSPlaying();
    return false;
  }

  protected void onActivityResume(Activity paramActivity)
  {
    try
    {
      if (AppConstants.getInstance().getmProjectType() == 0)
        SohuLoggerAgent.getInstance().onResume(paramActivity);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  protected void onActivityStop(Activity paramActivity)
  {
    try
    {
      if (AppConstants.getInstance().getmProjectType() == 0)
      {
        SohuLoggerAgent.getInstance().exit();
        SohuLoggerAgent.getInstance().onPause(paramActivity);
      }
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public void onAdPlayTime(int paramInt)
  {
    if (this.iPlayerCallback != null)
      this.iPlayerCallback.adRemainTime(paramInt);
  }

  public void onVideoPlayComplete()
  {
    OutputLog.i("Sohuplayer", "onVideoPlayComplete()");
  }

  protected void pause()
  {
    if ((this.mVideoView != null) && (SohuTVAdvertise.b != SohuTVAdvertise.PlaybackState.ADPLAYING))
    {
      this.mVideoView.pauseSPlay();
      if (com.sohutv.tv.player.util.a.a.i.booleanValue())
      {
        SohuTVAdvertise.a = false;
        ((Activity)this.mContext).runOnUiThread(new Runnable()
        {
          public void run()
          {
            if (SohuPlayer.this.mTVAdvertise != null)
              SohuPlayer.this.mTVAdvertise.b(SohuPlayer.this);
          }
        });
      }
    }
    try
    {
      com.sohutv.tv.player.util.a.a.k = true;
      SohuLoggerAgent.getInstance().onLogPause();
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public void pauseAdPlay()
  {
    if (isAdPlaying())
      this.mTVAdvertise.i();
  }

  public void playAd(String paramString, int paramInt)
  {
    OutputLog.i("Sohuplayer", "playAd() url=" + paramString);
    com.sohutv.tv.player.util.a.a.d = paramInt;
    i.a().a(paramString);
  }

  public void playVideoContent(boolean paramBoolean)
  {
    OutputLog.i("Sohuplayer", "sohuplayer playVideoContent()");
    if (paramBoolean);
    try
    {
      if (com.sohutv.tv.player.util.a.a.j.contains("himedia"))
      {
        Log.i("lifeCycle", "himedia is true");
        removeView(this.mVideoView);
        FrameLayout.LayoutParams localLayoutParams = new FrameLayout.LayoutParams(-1, -1);
        localLayoutParams.gravity = 17;
        addView(this.mVideoView, localLayoutParams);
      }
      showAdRemainTimeView(false);
    }
    catch (Exception localException2)
    {
      try
      {
        SohuLoggerAgent.getInstance().onLogSetUrl(this.mContext, true, this);
        i.a().b();
        return;
        localException2 = localException2;
        localException2.printStackTrace();
      }
      catch (Exception localException1)
      {
        while (true)
          localException1.printStackTrace();
      }
    }
  }

  protected void resume()
  {
    if ((this.mVideoView != null) && (!isPlaying()))
    {
      toStart();
      if (com.sohutv.tv.player.util.a.a.i.booleanValue())
      {
        SohuTVAdvertise.a = true;
        hidePauseAd();
      }
    }
    if (com.sohutv.tv.player.util.a.a.k);
    try
    {
      com.sohutv.tv.player.util.a.a.k = false;
      SohuLoggerAgent.getInstance().onLogStart();
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public void resumePlayAd()
  {
    this.mTVAdvertise.h();
  }

  protected void seekTo(int paramInt)
  {
    if ((SohuTVAdvertise.b == SohuTVAdvertise.PlaybackState.INVIDEO) && (this.mVideoView != null))
      this.mVideoView.seekSPositionTo(paramInt);
  }

  protected void selectTrack(int paramInt)
  {
    if (this.mVideoView != null)
      this.mVideoView.selectTrack(paramInt);
  }

  protected void setDefinition(int paramInt1, int paramInt2)
  {
    if ((this.mPlayerTool != null) && (SohuTVAdvertise.b == SohuTVAdvertise.PlaybackState.INVIDEO))
    {
      SohuLoggerAgent.getInstance().bitrateChange();
      com.sohutv.tv.player.util.a.a.k = false;
      this.mPlayerTool.a(paramInt1, paramInt2, true);
    }
  }

  protected void setDimension(int paramInt1, int paramInt2)
  {
    if (this.mVideoView != null)
      this.mVideoView.setDimension(paramInt1, paramInt2);
  }

  protected void setDimensionFullScreen()
  {
    if (this.mVideoView != null)
      this.mVideoView.setDimensionFullScreen();
  }

  protected void setDimensionOriginal()
  {
    if (this.mVideoView != null)
      this.mVideoView.setDimensionOriginal();
  }

  protected void setDimension_16_9()
  {
    if (this.mVideoView != null)
      this.mVideoView.setDimension_16_9();
  }

  protected void setDimension_4_3()
  {
    if (this.mVideoView != null)
      this.mVideoView.setDimension_4_3();
  }

  protected void setDimesionAuto()
  {
    if (this.mVideoView != null)
      this.mVideoView.setDimensionAuto();
  }

  protected void setIgnoreSurfaceDestory(boolean paramBoolean)
  {
    if (this.mVideoView != null)
      ((b)this.mVideoView).b(paramBoolean);
  }

  protected void setMediaController(SohuMediaController paramSohuMediaController, ISohuMediaControllerCallback paramISohuMediaControllerCallback)
  {
    if (this.mVideoView != null)
      this.mVideoView.setSMediaController(paramSohuMediaController, paramISohuMediaControllerCallback);
  }

  protected void setPauseADTopMarginPercent(int paramInt)
  {
    if (this.mTVAdvertise != null)
      this.mTVAdvertise.a(paramInt);
  }

  protected void setPlayerCallback(PlayerCallback paramPlayerCallback)
  {
    if (this.mVideoView != null)
      this.mVideoView.setmIPlayerCallback(paramPlayerCallback);
    this.iPlayerCallback = paramPlayerCallback;
    h.a().a(paramPlayerCallback);
  }

  protected void setVideoParam(String paramString)
  {
    if (this.mTVAdvertise != null)
      this.mTVAdvertise.e();
    com.sohutv.tv.player.util.a.a.k = false;
    this.mPlayerTool.a(this.mContext, paramString, true);
  }

  protected void start()
  {
    if ((this.mVideoView != null) && (!isPlaying()))
      toStart();
    if (com.sohutv.tv.player.util.a.a.k);
    try
    {
      com.sohutv.tv.player.util.a.a.k = false;
      SohuLoggerAgent.getInstance().onLogStart();
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  protected void stop()
  {
    Log.i("Sohuplayer", "停止播放,释放资源");
    if (this.mVideoView != null)
      this.mVideoView.stopSPlay();
    try
    {
      if ((com.sohutv.tv.player.util.a.a.h.booleanValue()) || (com.sohutv.tv.player.util.a.a.i.booleanValue()))
        finishSohuAD();
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohutv.tv.player.interfacesimpl.SohuPlayer
 * JD-Core Version:    0.6.2
 */