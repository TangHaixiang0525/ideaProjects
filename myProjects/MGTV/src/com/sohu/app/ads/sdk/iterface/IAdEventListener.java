package com.sohu.app.ads.sdk.iterface;

public abstract interface IAdEventListener
{
  public abstract void onAdClickEvent(String paramString);

  public abstract void onAdEvent(IAdEvent paramIAdEvent);

  public abstract void onAdPlayTime(int paramInt);

  public abstract void onImpressEvent(boolean paramBoolean, String paramString);
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.app.ads.sdk.iterface.IAdEventListener
 * JD-Core Version:    0.6.2
 */