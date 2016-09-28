package com.sohu.app.ads.sdk.core;

import android.os.Handler;
import com.sohu.app.ads.sdk.c.a;
import com.sohu.app.ads.sdk.f.d;
import com.sohu.app.ads.sdk.iterface.IAdEvent;
import com.sohu.app.ads.sdk.model.AdsResponse;
import com.sohu.app.ads.sdk.model.b;
import com.sohu.app.ads.sdk.model.emu.AdEventType;
import java.util.ArrayList;
import java.util.Timer;

public class s extends b
{
  private static boolean g = false;
  private static boolean h = true;
  private static int i = 200;
  private static int j = -1;
  private final String a = "SOHUSDK";
  private AdsManager b;
  private Handler c = null;
  private Timer d = null;
  private ArrayList<Integer> e = null;
  private AdsResponse f;

  public s()
  {
  }

  public s(AdsManager paramAdsManager)
  {
    this.b = paramAdsManager;
    this.d = new Timer();
    this.c = new t(this);
  }

  private void e()
  {
    if ((this.d != null) && (h))
    {
      h = false;
      a.a("SOHUSDK", "开启 schedule...");
      this.d.schedule(new u(this), 10L, i);
    }
  }

  protected void a()
  {
    if (this.d != null)
    {
      a.a("SOHUSDK", "暂停....schedule...");
      this.d.cancel();
      b();
    }
  }

  void b()
  {
    a.a("SOHUSDK", "ProgressTimer onDestory");
    this.c = null;
    this.d = null;
    this.e = null;
    h = true;
    this.b = null;
    this.d = null;
    this.f = null;
    this.c = null;
    if (this.e != null)
    {
      this.e.clear();
      this.e = null;
    }
  }

  public void onAdEvent(IAdEvent paramIAdEvent)
  {
    a.a("SOHUSDK", "ProgressTimer线程接收到的AdEvent...." + paramIAdEvent.getType());
    switch (v.a[paramIAdEvent.getType().ordinal()])
    {
    case 8:
    default:
      return;
    case 1:
      if (this.b != null)
        this.f = this.b.c();
      if (this.e == null)
        this.e = new ArrayList();
      e();
      return;
    case 2:
      d.a(AdEventType.ERROR, this.f);
    case 3:
    case 4:
      a.b("SOHUSDK", "接收到ERROR事件...修改变量:mPaused=true");
      g = false;
      a();
      return;
    case 5:
      if (this.e != null)
        this.e.clear();
      a.a("END...END....END...");
      return;
    case 6:
      a.b("SOHUSDK", "接收到PAUSED事件...修改变量:mPaused=true");
      g = true;
      return;
    case 7:
    }
    a.b("SOHUSDK", "接收到RESUMED事件...修改变量:mPaused=false");
    g = false;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.app.ads.sdk.core.s
 * JD-Core Version:    0.6.2
 */