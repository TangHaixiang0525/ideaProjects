package com.sohu.app.ads.sdk.core;

import android.os.Handler;
import android.os.Message;
import com.sohu.app.ads.sdk.model.emu.AdEventType;

class g extends Handler
{
  g(AdsManager paramAdsManager)
  {
  }

  public void handleMessage(Message paramMessage)
  {
    switch (paramMessage.what)
    {
    default:
      return;
    case 4:
      AdsManager.a(this.a, AdEventType.ALL_ADS_COMPLETED);
      return;
    case 1:
      AdsManager.a(this.a, AdEventType.LOADED);
      return;
    case 2:
      AdsManager.a(this.a, AdEventType.STARTED);
      return;
    case 3:
      AdsManager.a(this.a, AdEventType.END);
      return;
    case 9:
      AdsManager.a(this.a, AdEventType.RESUMED);
      return;
    case 5:
      AdsManager.a(this.a, AdEventType.PLAYTIMEOUT);
      return;
    case 6:
      AdsManager.a(this.a, AdEventType.ERROR);
      return;
    case 7:
      AdsManager.a(this.a, AdEventType.BUFFERCOMPLETE);
      return;
    case 8:
    }
    AdsManager.a(this.a, AdEventType.PAUSED);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.app.ads.sdk.core.g
 * JD-Core Version:    0.6.2
 */