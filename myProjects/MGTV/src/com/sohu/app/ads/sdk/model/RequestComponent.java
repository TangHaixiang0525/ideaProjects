package com.sohu.app.ads.sdk.model;

import android.view.ViewGroup;
import com.sohu.app.ads.sdk.iterface.IVideoAdPlayer;

public class RequestComponent
{
  private ViewGroup a;
  private IVideoAdPlayer b;

  public RequestComponent(ViewGroup paramViewGroup, IVideoAdPlayer paramIVideoAdPlayer)
  {
    this.a = paramViewGroup;
    this.b = paramIVideoAdPlayer;
  }

  public ViewGroup getContainer()
  {
    return this.a;
  }

  public IVideoAdPlayer getPlayer()
  {
    return this.b;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.app.ads.sdk.model.RequestComponent
 * JD-Core Version:    0.6.2
 */