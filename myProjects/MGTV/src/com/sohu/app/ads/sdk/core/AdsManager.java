package com.sohu.app.ads.sdk.core;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.sohu.app.ads.sdk.iterface.IAdEventListener;
import com.sohu.app.ads.sdk.iterface.IManager;
import com.sohu.app.ads.sdk.iterface.IVideoAdPlayer;
import com.sohu.app.ads.sdk.model.AdsResponse;
import com.sohu.app.ads.sdk.model.emu.AdEventType;
import com.sohu.app.ads.sdk.res.Const;
import com.sohu.mobile.a.a.e;
import com.sohu.mobile.tracing.plugin.b;
import com.sohu.mobile.tracing.plugin.expose.Plugin_ExposeAction;
import com.sohu.mobile.tracing.plugin.expose.Plugin_ExposeAdBoby;
import com.sohu.mobile.tracing.plugin.expose.Plugin_VastTag;
import java.io.File;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AdsManager
  implements View.OnClickListener, IManager
{
  private int a = 0;
  private Context b;
  private IVideoAdPlayer c;
  private ViewGroup d;
  private s e;
  private AdsResponse f;
  private AdsResponse g;
  private ArrayList<AdsResponse> h = null;
  private ArrayList<AdsResponse> i = null;
  private ArrayList<IAdEventListener> j = null;
  private h k;
  private int l = 0;
  private final int m = 1;
  private final int n = 2;
  private final int o = 3;
  private final int p = 4;
  private final int q = 5;
  private final int r = 6;
  private final int s = 7;
  private final int t = 8;
  private final int u = 9;
  private Handler v = new g(this);

  public AdsManager(Context paramContext, IVideoAdPlayer paramIVideoAdPlayer, ViewGroup paramViewGroup, ArrayList<AdsResponse> paramArrayList)
  {
    this.b = paramContext;
    this.c = paramIVideoAdPlayer;
    this.d = paramViewGroup;
    this.h = paramArrayList;
    if (this.h != null)
      this.i = ((ArrayList)this.h.clone());
  }

  public AdsManager(Context paramContext, IVideoAdPlayer paramIVideoAdPlayer, ViewGroup paramViewGroup, ArrayList<AdsResponse> paramArrayList, LinearLayout paramLinearLayout)
  {
    this.b = paramContext;
    this.c = paramIVideoAdPlayer;
    this.d = paramViewGroup;
    this.h = paramArrayList;
  }

  private void a(Context paramContext, String paramString)
  {
    Intent localIntent = new Intent("android.intent.action.VIEW", Uri.parse(paramString));
    localIntent.addFlags(268435456);
    paramContext.startActivity(localIntent);
  }

  private void a(AdsResponse paramAdsResponse)
  {
    this.f = paramAdsResponse;
  }

  private void a(AdEventType paramAdEventType)
  {
    try
    {
      if (this.j == null)
        break label100;
      synchronized (this.j)
      {
        com.sohu.app.ads.sdk.b.a locala = new com.sohu.app.ads.sdk.b.a(paramAdEventType, c());
        if ((this.j != null) && (this.j.size() > 0))
        {
          Iterator localIterator = this.j.iterator();
          if (localIterator.hasNext())
            ((IAdEventListener)localIterator.next()).onAdEvent(locala);
        }
      }
    }
    finally
    {
    }
    label100:
  }

  private void a(String paramString)
  {
    if (com.sohu.app.ads.sdk.f.c.a() == 1)
    {
      Intent localIntent = new Intent(d(), PadDetailsActivity.class);
      localIntent.addFlags(268435456);
      localIntent.putExtra("url", paramString);
      d().startActivity(localIntent);
    }
  }

  private void f()
  {
    this.l = 0;
    for (int i1 = 0; i1 < this.h.size(); i1++)
      this.l = (((AdsResponse)this.h.get(i1)).getDuration() + this.l);
    com.sohu.app.ads.sdk.c.a.a("广告总时间更新为:" + this.l + "秒");
  }

  private void g()
  {
    String str1;
    if ((this.c != null) && (this.c.playing()) && (this.f != null))
      if (Const.isForward)
      {
        if ((this.j != null) && (this.j.size() > 0))
        {
          Iterator localIterator2 = this.j.iterator();
          while (localIterator2.hasNext())
            ((IAdEventListener)localIterator2.next()).onAdClickEvent(this.f.getClickThrough());
        }
      }
      else
      {
        str1 = this.f.getClickThrough();
        if ((com.sohu.app.ads.sdk.f.d.a()) && (com.sohu.app.ads.sdk.f.d.a(str1)) && (this.b != null))
          b.b().a(Plugin_ExposeAdBoby.OAD, this.f.getClickTracking(), Plugin_VastTag.VAST_CLICK, Plugin_ExposeAction.EXPOSE_CLICK);
      }
    while (true)
    {
      String str2;
      String str3;
      try
      {
        ArrayList localArrayList = this.f.getSdkClickTracking();
        if (localArrayList != null)
        {
          Iterator localIterator1 = localArrayList.iterator();
          if (localIterator1.hasNext())
          {
            com.sohu.app.ads.sdk.model.c localc = (com.sohu.app.ads.sdk.model.c)localIterator1.next();
            str2 = localc.a();
            str3 = localc.b();
            if ((!com.sohu.app.ads.sdk.f.d.a(str2)) || (!com.sohu.app.ads.sdk.f.d.a(str3)))
              continue;
            if (!"admaster".equalsIgnoreCase(str2))
              break label270;
            b.b().a(Plugin_ExposeAdBoby.OAD, str3, Plugin_VastTag.ADMASTER, Plugin_ExposeAction.EXPOSE_CLICK);
            continue;
          }
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        if (com.sohu.app.ads.sdk.f.d.a(str1))
          parserAction(this.b, str1);
      }
      return;
      label270: if ("miaozhen".equalsIgnoreCase(str2))
      {
        com.sohu.app.ads.sdk.c.a.c("AdsManager秒针展示曝光");
        b.b().a(Plugin_ExposeAdBoby.OAD, str3, Plugin_VastTag.MIAOZHEN, Plugin_ExposeAction.EXPOSE_CLICK);
      }
      else if ("sohutv".equalsIgnoreCase(str2))
      {
        b.b().a(Plugin_ExposeAdBoby.OAD, str3, Plugin_VastTag.SOHUSDK, Plugin_ExposeAction.EXPOSE_CLICK);
      }
    }
  }

  public void SendTime(int paramInt)
  {
    while (true)
    {
      IAdEventListener localIAdEventListener;
      try
      {
        synchronized (this.j)
        {
          if ((this.j == null) || (this.j.size() <= 0))
            break;
          Iterator localIterator = this.j.iterator();
          if (!localIterator.hasNext())
            break;
          localIAdEventListener = (IAdEventListener)localIterator.next();
          if (paramInt >= 0)
            localIAdEventListener.onAdPlayTime(paramInt);
        }
      }
      finally
      {
      }
      localIAdEventListener.onAdPlayTime(0);
    }
  }

  public void SendTrackingEvent(boolean paramBoolean, List<String> paramList)
  {
    for (int i1 = 0; i1 < paramList.size(); i1++)
    {
      String str = (String)paramList.get(i1);
      if ((com.sohu.app.ads.sdk.f.d.a(str)) && (str.contains("sohu.com")) && (str.contains("p=oad")) && (this.j != null) && (this.j.size() > 0))
      {
        Iterator localIterator = this.j.iterator();
        while (localIterator.hasNext())
          ((IAdEventListener)localIterator.next()).onImpressEvent(paramBoolean, str);
      }
    }
  }

  protected int a()
  {
    return this.l;
  }

  protected void a(int paramInt)
  {
    if (this.c != null)
    {
      com.sohu.app.ads.sdk.c.a.a("removeCallback");
      this.c.removeCallback(this.k);
    }
    if (this.v != null)
      this.v.sendEmptyMessage(paramInt);
  }

  protected void b()
  {
    com.sohu.app.ads.sdk.e.a.a().a(this.b);
    e.c().a("oadPlayTimeout");
    a(5);
  }

  protected AdsResponse c()
  {
    return this.f;
  }

  protected Context d()
  {
    return this.b;
  }

  public void destroy()
  {
    com.sohu.app.ads.sdk.c.a.a("销毁AdsManager");
    this.g = null;
    if (this.e != null)
      this.e.a();
    this.e = null;
    this.h = null;
    this.i = null;
    this.j = null;
    this.f = null;
    if (this.c != null)
    {
      this.c.removeCallback(this.k);
      com.sohu.app.ads.sdk.c.a.a("removeCallback");
      this.c = null;
    }
    this.d = null;
    this.b = null;
    this.k = null;
    this.v = null;
  }

  protected IVideoAdPlayer e()
  {
    return this.c;
  }

  public ArrayList<AdsResponse> getAdsResponseList()
  {
    return this.i;
  }

  public ArrayList<AdsResponse> getPlayerList()
  {
    return this.h;
  }

  public void init(IAdEventListener paramIAdEventListener)
  {
    try
    {
      this.j = new ArrayList();
      this.k = new h(this);
      if (this.c == null)
        com.sohu.app.ads.sdk.c.a.c("出现致命问题,mPlayer=null");
      this.c.addCallback(this.k);
      this.e = new s(this);
      this.j.add(this.e);
      this.j.add(paramIAdEventListener);
      com.sohu.app.ads.sdk.c.a.a("addCallback complete...");
      if (this.v != null)
        this.v.sendEmptyMessage(1);
      com.sohu.app.ads.sdk.f.c.a("default_volume", Integer.valueOf(((AudioManager)this.b.getSystemService("audio")).getStreamVolume(3)));
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public void onClick(View paramView)
  {
    if ((this.f == null) || (!com.sohu.app.ads.sdk.f.d.a()))
      return;
    switch (paramView.getId())
    {
    case 1000:
    default:
      return;
    case 1001:
    }
    if (!this.c.playing())
      resume();
    r.a().b();
  }

  public void parserAction(Context paramContext, String paramString)
  {
    int i2;
    String str2;
    try
    {
      if ((!com.sohu.app.ads.sdk.f.d.a(paramString)) || (!paramString.startsWith("sv://")))
        break label234;
      String str1 = paramString.substring(1 + paramString.indexOf("?"));
      com.sohu.app.ads.sdk.c.a.c(str1);
      String[] arrayOfString1 = str1.split("&");
      HashMap localHashMap = new HashMap();
      for (int i1 = 0; i1 < arrayOfString1.length; i1++)
        if (com.sohu.app.ads.sdk.f.d.a(arrayOfString1[i1]))
        {
          String[] arrayOfString2 = arrayOfString1[i1].split("=");
          if (arrayOfString2.length > 1)
            localHashMap.put(arrayOfString2[0], arrayOfString2[1]);
        }
      try
      {
        int i3 = Integer.valueOf((String)localHashMap.get("action")).intValue();
        i2 = i3;
        str2 = (String)localHashMap.get("url");
        if (!com.sohu.app.ads.sdk.f.d.a(str2))
          break label240;
        str2 = URLDecoder.decode(str2);
      }
      catch (NumberFormatException localNumberFormatException)
      {
        while (true)
        {
          localNumberFormatException.printStackTrace();
          i2 = 0;
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      com.sohu.app.ads.sdk.c.a.a("跳转链接异常" + paramString);
      return;
    }
    a(str2);
    return;
    a(paramContext, str2);
    return;
    label234: a(paramString);
    return;
    label240: switch (i2)
    {
    case 2:
    case 3:
    }
  }

  public void removeAdEventListener(IAdEventListener paramIAdEventListener)
  {
    synchronized (this.j)
    {
      this.j.remove(paramIAdEventListener);
      return;
    }
  }

  public void resume()
  {
    this.c.loadAd(this.f.getMediaFile(), this.a);
    this.c.playAd();
  }

  public void setmNextAd(AdsResponse paramAdsResponse)
  {
    this.g = paramAdsResponse;
  }

  public void start()
  {
    AdsResponse localAdsResponse;
    try
    {
      synchronized (this.h)
      {
        if ((this.h == null) || (this.h.size() <= 0))
          break label410;
        f();
        localAdsResponse = (AdsResponse)this.h.remove(0);
        if ((localAdsResponse.getDuration() > 0) && (com.sohu.app.ads.sdk.f.d.a(localAdsResponse.getMediaFile())))
          break label197;
        ArrayList localArrayList2 = localAdsResponse.getImpression();
        if ((localArrayList2 != null) && (localArrayList2.size() > 0))
        {
          SendTrackingEvent(true, localArrayList2);
          Iterator localIterator = localArrayList2.iterator();
          while (localIterator.hasNext())
          {
            String str1 = (String)localIterator.next();
            if (com.sohu.app.ads.sdk.f.d.a(str1))
              b.b().a(Plugin_ExposeAdBoby.OAD, str1, Plugin_VastTag.VAST_NULL, Plugin_ExposeAction.EXPOSE_SHOW);
          }
        }
      }
    }
    catch (Exception localException)
    {
      com.sohu.app.ads.sdk.c.a.a("广告播放过程中出现的异常");
      if (this.v != null)
        this.v.sendEmptyMessage(6);
      localException.printStackTrace();
      return;
    }
    com.sohu.app.ads.sdk.f.d.a(AdEventType.END, localAdsResponse);
    start();
    return;
    label197: String str2 = localAdsResponse.getMediaFile();
    if (com.sohu.app.ads.sdk.f.d.a())
    {
      if (com.sohu.app.ads.sdk.e.a.a().b(this.b, str2))
        localAdsResponse.setMediaFile(com.sohu.app.ads.sdk.f.d.i().getPath() + "/" + com.sohu.app.ads.sdk.f.d.b(str2));
      a(localAdsResponse);
      com.sohu.app.ads.sdk.c.a.a("开始播放第" + localAdsResponse.getAdSequence() + "贴广告,剩余广告贴数:" + this.h.size());
      com.sohu.app.ads.sdk.c.a.a("当前播放广告地址:" + this.f.getMediaFile());
      this.c.loadAd(this.f.getMediaFile());
      this.c.playAd();
      if (this.v != null)
        this.v.sendEmptyMessage(2);
    }
    while (true)
    {
      return;
      if (!new File(str2).exists())
        break;
      localAdsResponse.setMediaFile(str2);
      break;
      label410: com.sohu.app.ads.sdk.c.a.c("获取到的广告列表为空");
      a(4);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.app.ads.sdk.core.AdsManager
 * JD-Core Version:    0.6.2
 */