package com.sohutv.tv.player.ad;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.HandlerThread;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;
import com.sohu.app.ads.sdk.SdkFactory;
import com.sohu.app.ads.sdk.exception.SdkException;
import com.sohu.app.ads.sdk.iterface.IAdErrorEventListener;
import com.sohu.app.ads.sdk.iterface.IAdEvent;
import com.sohu.app.ads.sdk.iterface.IAdEventListener;
import com.sohu.app.ads.sdk.iterface.IAdsLoadedError;
import com.sohu.app.ads.sdk.iterface.IAdsLoadedListener;
import com.sohu.app.ads.sdk.iterface.ILoadedEvent;
import com.sohu.app.ads.sdk.iterface.ILoader;
import com.sohu.app.ads.sdk.iterface.IManager;
import com.sohu.app.ads.sdk.iterface.IVideoAdPlayer;
import com.sohu.app.ads.sdk.iterface.IVideoAdPlayerCallback;
import com.sohu.app.ads.sdk.iterface.PopWindowCallback;
import com.sohu.app.ads.sdk.model.RequestComponent;
import com.sohu.app.ads.sdk.model.VideoProgressUpdate;
import com.sohu.app.ads.sdk.model.emu.AdEventType;
import com.sohu.app.ads.sdk.model.emu.ErrorType;
import com.sohu.app.ads.sdk.res.AdType;
import com.sohu.logger.log.OutputLog;
import com.sohutv.tv.player.util.g.a;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SohuTVAdvertise
  implements IAdErrorEventListener, IAdEventListener, IAdsLoadedListener, IVideoAdPlayer
{
  public static boolean a = false;
  public static PlaybackState b = PlaybackState.IDLE;
  public static List<IVideoAdPlayerCallback> c = new ArrayList();
  private Context d;
  private SdkFactory e;
  private IManager f;
  private ILoader g;
  private a h;
  private HandlerThread i = null;
  private int j = 300000;
  private int k = 20;
  private ViewGroup l = null;
  private g.a m = null;
  private String n = "";
  private Handler o = null;

  public SohuTVAdvertise(Context paramContext, a parama)
  {
    this.d = paramContext;
    this.h = parama;
    j();
    b();
  }

  private void j()
  {
    try
    {
      this.i = new HandlerThread("CheckADLog");
      this.i.start();
      this.o = new Handler(this.i.getLooper(), new Handler.Callback()
      {
        public boolean handleMessage(Message paramAnonymousMessage)
        {
          switch (paramAnonymousMessage.what)
          {
          default:
          case 0:
          }
          while (true)
          {
            return false;
            SohuTVAdvertise.a(SohuTVAdvertise.this);
          }
        }
      });
      this.o.sendEmptyMessage(0);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  private void k()
  {
    d();
    if (this.m != null)
    {
      if (!TextUtils.isEmpty(this.n))
      {
        Log.i("Sohuplayer", "API for play video");
        this.m.a(this.n);
        return;
      }
      Log.e("Sohuplayer", "API inVideoUrl is null");
      return;
    }
    l();
  }

  private void l()
  {
    try
    {
      if (this.h != null)
      {
        this.h.playVideoContent(true);
        return;
      }
      Log.e("Sohuplayer", "mISohuTVAdvertise is null");
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  private d.a m()
  {
    if ((this.h != null) && (this.h.getAdRequestParams() != null))
    {
      AdRequestParams localAdRequestParams = this.h.getAdRequestParams();
      String str1 = localAdRequestParams.getAg();
      String str2 = localAdRequestParams.getSt();
      String str3 = localAdRequestParams.getAdSource();
      String str4 = localAdRequestParams.getVc();
      String str5 = localAdRequestParams.getAlbumID();
      String str6 = localAdRequestParams.getDu();
      String str7 = localAdRequestParams.getAr();
      String str8 = localAdRequestParams.getVideoID();
      String str9 = localAdRequestParams.getLid();
      return new d.a(str1, str2, localAdRequestParams.getVu(), str3, str4, str5, str6, str7, str8, str9);
    }
    return null;
  }

  private void n()
  {
    Log.i("Sohuplayer", "adLogCheck " + Thread.currentThread().getId());
    try
    {
      if (this.d != null)
        SdkFactory.getInstance().NetWorkChangeCallback(this.d);
      if (this.o != null)
        this.o.sendEmptyMessageDelayed(0, this.j);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public void a()
  {
    PlaybackState localPlaybackState = b;
    OutputLog.e("Sohuplayer", "startAd() oldstate : " + localPlaybackState);
    if ((b == PlaybackState.IDLE) || (b == PlaybackState.ADPAUSED) || (b == PlaybackState.ADSTOPPED))
      b = PlaybackState.ADPLAYING;
    switch (4.c[localPlaybackState.ordinal()])
    {
    default:
    case 1:
    case 2:
    case 3:
    }
    while (true)
    {
      return;
      Iterator localIterator2 = c.iterator();
      while (localIterator2.hasNext())
        ((IVideoAdPlayerCallback)localIterator2.next()).onPlay();
      continue;
      Iterator localIterator1 = c.iterator();
      while (localIterator1.hasNext())
        ((IVideoAdPlayerCallback)localIterator1.next()).onResume();
    }
  }

  public void a(int paramInt)
  {
    this.k = paramInt;
  }

  public void a(ViewGroup paramViewGroup)
  {
    if ((!a.a(this.d)) || (a.b(this.d)))
    {
      l();
      return;
    }
    d();
    if (this.g == null)
      b();
    try
    {
      if ((this.h != null) && (this.h.getAdRequestParams() != null))
      {
        this.g.requestAds(new RequestComponent(paramViewGroup, this), d.a(this.d, AdType.OAD, false, m()));
        return;
      }
    }
    catch (SdkException localSdkException)
    {
      localSdkException.printStackTrace();
      return;
    }
    l();
  }

  public void a(String paramString)
  {
    if ((!a.a(this.d)) || (a.b(this.d)))
    {
      l();
      return;
    }
    if (this.l == null)
    {
      Log.e("Sohuplayer", "viewOfPlay = null");
      return;
    }
    this.n = paramString;
    d();
    if (this.g == null)
      b();
    try
    {
      if ((this.h != null) && (this.h.getAdRequestParams() != null))
      {
        this.g.requestAds(new RequestComponent(this.l, this), d.a(this.d, AdType.OAD, false, m()));
        return;
      }
    }
    catch (SdkException localSdkException)
    {
      localSdkException.printStackTrace();
      return;
    }
    l();
  }

  public void addCallback(IVideoAdPlayerCallback paramIVideoAdPlayerCallback)
  {
    c.add(paramIVideoAdPlayerCallback);
  }

  public void b()
  {
    try
    {
      this.e = SdkFactory.getInstance();
      this.g = this.e.createAdsLoader(this.d.getApplicationContext());
      this.g.setTimeOut(7000);
      this.g.setDeviceType(2);
      this.g.addAdsLoadedListener(this);
      this.g.addAdErrorListener(this);
      return;
    }
    catch (SdkException localSdkException)
    {
      localSdkException.printStackTrace();
    }
  }

  public void b(ViewGroup paramViewGroup)
  {
    if (!a.a(this.d));
    while (true)
    {
      return;
      if (this.g == null)
        b();
      try
      {
        if ((this.h != null) && (this.h.getAdRequestParams() != null) && (!TextUtils.isEmpty(this.h.getAdRequestParams().getVideoID())))
        {
          this.g.requestPauseAd(this.d.getApplicationContext(), paramViewGroup, d.a(this.d.getApplicationContext(), AdType.PAD, false, m()), new PopWindowCallback()
          {
            public void onOpenResult(boolean paramAnonymousBoolean)
            {
              if ((paramAnonymousBoolean == true) && (SohuTVAdvertise.a))
                SohuTVAdvertise.this.c();
            }
          }
          , this.k);
          return;
        }
      }
      catch (SdkException localSdkException)
      {
        localSdkException.printStackTrace();
      }
    }
  }

  public void c()
  {
    Log.i("Sohuplayer", "hidePauseAd");
    if ((com.sohutv.tv.player.util.a.a.i.booleanValue()) && (this.g != null))
      ((Activity)this.d).runOnUiThread(new Runnable()
      {
        public void run()
        {
          try
          {
            SohuTVAdvertise.b(SohuTVAdvertise.this).removePauseAd();
            return;
          }
          catch (Exception localException)
          {
            Log.e("Sohuplayer", "hidePauseAd exception");
            localException.printStackTrace();
          }
        }
      });
  }

  public void d()
  {
    Log.i("Sohuplayer", "releaseAdLoader");
    this.e = null;
    try
    {
      if (this.g != null)
      {
        this.g.addAdErrorListener(null);
        this.g.addAdsLoadedListener(null);
        this.g.destory();
        this.g = null;
      }
      if (this.f != null)
      {
        this.f.destroy();
        this.f = null;
      }
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public void e()
  {
    b = PlaybackState.IDLE;
  }

  public void f()
  {
    Log.i("Sohuplayer", "releaseAdResource");
    try
    {
      if (this.o != null)
      {
        this.o.removeCallbacksAndMessages(null);
        this.o = null;
      }
      if (this.i != null)
        this.i.quit();
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public void g()
  {
    c();
    d();
    f();
  }

  public int getCurrentPos()
  {
    if (this.h != null)
      return this.h.getICurrentPosition();
    return 0;
  }

  public VideoProgressUpdate getProgress()
  {
    if ((this.h != null) && (this.h.isIPlaying()))
    {
      int i1 = this.h.getIDuration();
      if (i1 <= 0)
        return VideoProgressUpdate.VIDEO_TIME_NOT_READY;
      return new VideoProgressUpdate(this.h.getICurrentPosition(), i1);
    }
    return null;
  }

  public void h()
  {
    if ((this.f != null) && (b == PlaybackState.ADPAUSED))
      this.f.resume();
  }

  public void i()
  {
    b = PlaybackState.ADPAUSED;
  }

  public void loadAd(String paramString)
  {
    try
    {
      loadAd(paramString, 0);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public void loadAd(String paramString, int paramInt)
  {
    try
    {
      if (b == PlaybackState.ADPAUSED)
        return;
      if (this.h != null)
      {
        this.h.playAd(paramString, paramInt);
        return;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public void onAdClickEvent(String paramString)
  {
  }

  public void onAdEvent(IAdEvent paramIAdEvent)
  {
    OutputLog.i("Sohuplayer", "onAdEvent event.getType()=" + paramIAdEvent.getType());
    OutputLog.d("SohuTVAdvertise onAdEvent -->" + paramIAdEvent.getType());
    switch (4.a[paramIAdEvent.getType().ordinal()])
    {
    default:
      OutputLog.e("default");
    case 2:
    case 6:
    case 7:
      return;
    case 1:
      this.f.start();
      return;
    case 3:
      OutputLog.e("所有广告都播放完毕");
      k();
      return;
    case 4:
      OutputLog.e("Player PLAYTIMEOUT...");
      k();
      return;
    case 5:
      OutputLog.e("Player ERROR...");
      k();
      return;
    case 8:
      OutputLog.e("RESUMED");
      return;
    case 9:
    }
    OutputLog.e("PAUSED");
  }

  public void onAdPlayTime(int paramInt)
  {
    if (this.h != null)
      this.h.onAdPlayTime(paramInt);
  }

  public void onAdsLoadedError(IAdsLoadedError paramIAdsLoadedError)
  {
    OutputLog.i("Sohuplayer", "onAdsLoadedError event.getErrorType()=" + paramIAdsLoadedError.getErrorType());
    OutputLog.d("onAdsLoadedError ErrorType=" + paramIAdsLoadedError.getErrorType() + ",Error message=" + paramIAdsLoadedError.getErrorMessage());
    paramIAdsLoadedError.getErrorMessage();
    switch (4.b[paramIAdsLoadedError.getErrorType().ordinal()])
    {
    case 1:
    case 2:
    case 3:
    case 4:
    case 5:
    case 6:
    case 7:
    case 8:
    case 9:
    case 10:
    }
    l();
  }

  public void onAdsManagerLoaded(ILoadedEvent paramILoadedEvent)
  {
    OutputLog.i("Sohuplayer", "onAdEvent onAdsManagerLoaded");
    OutputLog.d("SohuTVAdvertise onAdsManagerLoaded()");
    try
    {
      this.f = paramILoadedEvent.getAdsManager();
      this.f.init(this);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public void onBufferedComplete()
  {
  }

  public void onImpressEvent(boolean paramBoolean, String paramString)
  {
  }

  public void pauseAd()
  {
  }

  public void playAd()
  {
    b = PlaybackState.ADSTOPPED;
    a();
  }

  public boolean playing()
  {
    if (this.h != null)
      return this.h.isIPlaying();
    return false;
  }

  public void removeCallback(IVideoAdPlayerCallback paramIVideoAdPlayerCallback)
  {
    c.remove(paramIVideoAdPlayerCallback);
  }

  public void resumeAd()
  {
  }

  public void stopAd()
  {
    b = PlaybackState.ADFINISHED;
  }

  public static enum PlaybackState
  {
    static
    {
      ADSTOPPED = new PlaybackState("ADSTOPPED", 1);
      ADPAUSED = new PlaybackState("ADPAUSED", 2);
      ADPLAYING = new PlaybackState("ADPLAYING", 3);
      ADFINISHED = new PlaybackState("ADFINISHED", 4);
      INVIDEO = new PlaybackState("INVIDEO", 5);
      PlaybackState[] arrayOfPlaybackState = new PlaybackState[6];
      arrayOfPlaybackState[0] = IDLE;
      arrayOfPlaybackState[1] = ADSTOPPED;
      arrayOfPlaybackState[2] = ADPAUSED;
      arrayOfPlaybackState[3] = ADPLAYING;
      arrayOfPlaybackState[4] = ADFINISHED;
      arrayOfPlaybackState[5] = INVIDEO;
    }
  }

  public static abstract interface a
  {
    public abstract AdRequestParams getAdRequestParams();

    public abstract int getICurrentPosition();

    public abstract int getIDuration();

    public abstract boolean isIPlaying();

    public abstract void onAdPlayTime(int paramInt);

    public abstract void playAd(String paramString, int paramInt);

    public abstract void playVideoContent(boolean paramBoolean);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohutv.tv.player.ad.SohuTVAdvertise
 * JD-Core Version:    0.6.2
 */