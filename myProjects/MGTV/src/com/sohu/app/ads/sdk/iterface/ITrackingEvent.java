package com.sohu.app.ads.sdk.iterface;

import com.sohu.mobile.tracing.plugin.expose.Plugin_VastTag;

public abstract interface ITrackingEvent
{
  public abstract String getAdUrl();

  public abstract Plugin_VastTag getTrackingType();

  public abstract String getTrackingUrl();
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.app.ads.sdk.iterface.ITrackingEvent
 * JD-Core Version:    0.6.2
 */