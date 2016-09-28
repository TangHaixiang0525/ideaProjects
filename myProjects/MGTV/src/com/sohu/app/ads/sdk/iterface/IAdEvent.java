package com.sohu.app.ads.sdk.iterface;

import com.sohu.app.ads.sdk.model.AdsResponse;
import com.sohu.app.ads.sdk.model.emu.AdEventType;

public abstract interface IAdEvent
{
  public abstract AdsResponse getAd();

  public abstract AdEventType getType();
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.app.ads.sdk.iterface.IAdEvent
 * JD-Core Version:    0.6.2
 */