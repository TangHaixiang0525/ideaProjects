package com.sohu.app.ads.sdk.b;

import com.sohu.app.ads.sdk.iterface.IAdEvent;
import com.sohu.app.ads.sdk.model.AdsResponse;
import com.sohu.app.ads.sdk.model.emu.AdEventType;

public class a
  implements IAdEvent
{
  private AdEventType a;
  private AdsResponse b;

  public a(AdEventType paramAdEventType, AdsResponse paramAdsResponse)
  {
    this.a = paramAdEventType;
    this.b = paramAdsResponse;
  }

  public AdsResponse getAd()
  {
    return this.b;
  }

  public AdEventType getType()
  {
    return this.a;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.app.ads.sdk.b.a
 * JD-Core Version:    0.6.2
 */