package com.sohu.app.ads.sdk.core;

import android.content.Context;
import com.miaozhen.mzmonitor.MZMonitor;
import com.sohu.app.ads.sdk.f.c;
import com.sohu.mobile.a.a.e;

abstract class i
{
  public Context a;
  public com.sohu.app.ads.sdk.d.a b;

  i(Context paramContext)
  {
    this.a = paramContext;
    this.b = new com.sohu.app.ads.sdk.d.a();
    com.sohu.app.ads.sdk.f.d.a(this.a);
    c.a(this.a);
    e.c().a(this.a, "2.1.13");
    try
    {
      com.sohu.mobile.tracing.plugin.b.b().a(this.a);
      com.sohu.mobile.tracing.plugin.b.b().a("2.1.13");
      com.sohu.mobile.tracing.plugin.b.b().a();
      com.sohu.mma.tracking.a.b.a().a(this.a, "");
      MZMonitor.retryCachedRequests(this.a);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public void destory()
  {
    this.b = null;
    this.a = null;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.app.ads.sdk.core.i
 * JD-Core Version:    0.6.2
 */