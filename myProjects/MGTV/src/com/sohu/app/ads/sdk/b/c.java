package com.sohu.app.ads.sdk.b;

import com.sohu.app.ads.sdk.iterface.ILoadedEvent;
import com.sohu.app.ads.sdk.iterface.IManager;

public class c
  implements ILoadedEvent
{
  private IManager a;

  public c(IManager paramIManager)
  {
    this.a = paramIManager;
  }

  public IManager getAdsManager()
  {
    return this.a;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.app.ads.sdk.b.c
 * JD-Core Version:    0.6.2
 */