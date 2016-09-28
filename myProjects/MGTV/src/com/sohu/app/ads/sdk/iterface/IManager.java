package com.sohu.app.ads.sdk.iterface;

import com.sohu.app.ads.sdk.model.AdsResponse;
import java.util.ArrayList;

public abstract interface IManager
{
  public abstract void destroy();

  public abstract ArrayList<AdsResponse> getAdsResponseList();

  public abstract void init(IAdEventListener paramIAdEventListener);

  public abstract void removeAdEventListener(IAdEventListener paramIAdEventListener);

  public abstract void resume();

  public abstract void start();
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.app.ads.sdk.iterface.IManager
 * JD-Core Version:    0.6.2
 */