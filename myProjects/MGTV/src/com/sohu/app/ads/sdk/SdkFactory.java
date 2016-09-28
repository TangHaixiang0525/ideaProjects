package com.sohu.app.ads.sdk;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import com.miaozhen.mzmonitor.MZMonitor;
import com.sohu.app.ads.sdk.core.j;
import com.sohu.app.ads.sdk.iterface.IDisplayLoader;
import com.sohu.app.ads.sdk.iterface.ILoader;
import com.sohu.app.ads.sdk.res.Const;
import com.sohu.mobile.a.a.e;
import com.sohu.mobile.tracing.plugin.expose.Plugin_ExposeAction;
import com.sohu.mobile.tracing.plugin.expose.Plugin_ExposeAdBoby;
import com.sohu.mobile.tracing.plugin.expose.Plugin_VastTag;
import java.io.File;

public class SdkFactory
{
  private static SdkFactory a = null;
  private Context b = null;

  private void a(Context paramContext)
  {
    if (paramContext == null);
    String str2;
    do
    {
      return;
      e.c().a(paramContext, "2.1.13");
      e.c().a(false);
      String str1 = e.c().b();
      com.sohu.app.ads.sdk.c.a.a("MQS=" + str1);
      if (!TextUtils.isEmpty(str1))
      {
        com.sohu.mobile.tracing.plugin.b.b().a(Plugin_ExposeAdBoby.MQS, str1, Plugin_VastTag.UNKNOWN, Plugin_ExposeAction.EXPOSE_SHOW);
        e.c().a();
      }
      com.sohu.mobile.a.a.b.a().a(paramContext, "2.1.13", Const.AppChannel);
      com.sohu.mobile.a.a.b.a().a(false);
      str2 = com.sohu.mobile.a.a.b.a().c();
      com.sohu.app.ads.sdk.c.a.a("Dtqs=" + str2);
    }
    while (TextUtils.isEmpty(str2));
    com.sohu.mobile.tracing.plugin.b.b().a(Plugin_ExposeAdBoby.MQS, str2, Plugin_VastTag.UNKNOWN, Plugin_ExposeAction.EXPOSE_SHOW);
    com.sohu.mobile.a.a.b.a().b();
  }

  public static SdkFactory getInstance()
  {
    if (a == null)
      a = new SdkFactory();
    Log.i("version", "Adverst SDK Version:2.1.13");
    return a;
  }

  public void NetWorkChangeCallback(Context paramContext)
  {
    if (paramContext == null);
    while (true)
    {
      return;
      try
      {
        com.sohu.app.ads.sdk.c.a.a("NetWorkChangeCallback");
        this.b = paramContext;
        com.sohu.app.ads.sdk.f.d.a(paramContext);
        if (!com.sohu.app.ads.sdk.f.d.a())
          continue;
        com.sohu.mobile.tracing.plugin.b.b().a(this.b);
        com.sohu.mobile.tracing.plugin.b.b().a("2.1.13");
        com.sohu.mobile.tracing.plugin.b.b().a();
        a(this.b);
        MZMonitor.retryCachedRequests(this.b);
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
      finally
      {
      }
    }
  }

  public ILoader createAdsLoader(Context paramContext)
  {
    return new com.sohu.app.ads.sdk.core.a(paramContext);
  }

  public IDisplayLoader createDisplayLoader(Context paramContext)
  {
    return new j(paramContext);
  }

  public void destory()
  {
    this.b = null;
  }

  public void prepare(Context paramContext, String paramString)
  {
    this.b = paramContext;
    Const.AppChannel = paramString;
    try
    {
      com.sohu.app.ads.sdk.c.a.a("prepare");
      com.sohu.app.ads.sdk.f.d.a(this.b);
      com.sohu.app.ads.sdk.f.d.a(com.sohu.app.ads.sdk.f.d.i(), 30);
      com.sohu.app.ads.sdk.e.a.a().a(paramContext, com.sohu.app.ads.sdk.f.d.i());
      com.sohu.app.ads.sdk.e.a.a().b(paramContext);
      com.sohu.app.ads.sdk.f.c.a(this.b);
      com.sohu.mobile.tracing.plugin.b.b().a(this.b);
      com.sohu.mobile.tracing.plugin.b.b().a("2.1.13");
      a(this.b);
      if (!new File(Environment.getExternalStorageDirectory(), "debug9.txt").exists())
      {
        com.sohu.app.ads.sdk.c.a.a();
        com.sohu.mobile.tracing.plugin.b.b().a(true);
      }
      com.sohu.mma.tracking.a.b.a().a(this.b, "");
      MZMonitor.retryCachedRequests(this.b);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.app.ads.sdk.SdkFactory
 * JD-Core Version:    0.6.2
 */