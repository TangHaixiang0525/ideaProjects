package com.sohu.app.ads.sdk.core;

import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import com.sohu.app.ads.sdk.iterface.IVideoAdPlayer;
import com.sohu.app.ads.sdk.iterface.IVideoAdPlayerCallback;
import com.sohu.app.ads.sdk.model.AdsResponse;
import com.sohu.mobile.tracing.plugin.b;
import com.sohu.mobile.tracing.plugin.expose.Plugin_ExposeAction;
import com.sohu.mobile.tracing.plugin.expose.Plugin_ExposeAdBoby;
import com.sohu.mobile.tracing.plugin.expose.Plugin_VastTag;
import java.io.File;
import java.util.ArrayList;

class h
  implements IVideoAdPlayerCallback
{
  h(AdsManager paramAdsManager)
  {
  }

  public void adClicked()
  {
    com.sohu.app.ads.sdk.c.a.c("adClicked");
    AdsManager.f(this.a);
  }

  public void isPlaying()
  {
    com.sohu.app.ads.sdk.c.a.a("isPlaying");
  }

  public void onBufferedComplete()
  {
    if (AdsManager.a(this.a) != null)
      AdsManager.a(this.a).sendEmptyMessage(7);
  }

  public void onEnded()
  {
    if (AdsManager.a(this.a) != null)
      AdsManager.a(this.a).sendEmptyMessage(3);
    b.b().a(Plugin_ExposeAdBoby.OAD, AdsManager.b(this.a).getComplete(), Plugin_VastTag.VAST_COMPLETE, Plugin_ExposeAction.EXPOSE_SHOW);
    com.sohu.app.ads.sdk.c.a.a("onEnded");
    if ((AdsManager.c(this.a) != null) && (AdsManager.c(this.a).size() > 0))
    {
      this.a.start();
      return;
    }
    this.a.a(4);
  }

  public void onError()
  {
    com.sohu.app.ads.sdk.c.a.a("onError");
    if (AdsManager.b(this.a).getMediaFile().contains("OADCACHE"));
    try
    {
      new File(AdsManager.b(this.a).getMediaFile()).delete();
      if ((AdsManager.c(this.a) != null) && (AdsManager.c(this.a).size() > 0))
      {
        this.a.start();
        return;
      }
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
      this.a.a(6);
    }
  }

  public void onPause()
  {
    com.sohu.app.ads.sdk.c.a.a("onPause");
    AdsManager.a(this.a, AdsManager.d(this.a).getCurrentPos());
    com.sohu.app.ads.sdk.c.a.a("Save CurrentPositon=" + AdsManager.e(this.a));
    if (AdsManager.a(this.a) != null)
      AdsManager.a(this.a).sendEmptyMessage(8);
  }

  public void onPlay()
  {
    com.sohu.app.ads.sdk.c.a.a("onPlay~~");
  }

  public void onResume()
  {
    com.sohu.app.ads.sdk.c.a.a("onResume");
    if (AdsManager.a(this.a) != null)
      AdsManager.a(this.a).sendEmptyMessage(9);
    this.a.resume();
  }

  public void onTouch(View paramView, MotionEvent paramMotionEvent)
  {
  }

  public void onVolumeChanged(int paramInt)
  {
    com.sohu.app.ads.sdk.c.a.a("onVolumeChanged");
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.app.ads.sdk.core.h
 * JD-Core Version:    0.6.2
 */