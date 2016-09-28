package com.sohu.app.ads.sdk.core;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.sohu.app.ads.sdk.f.c;
import com.sohu.app.ads.sdk.model.AdsResponse;
import com.sohu.app.ads.sdk.model.emu.AdEventType;
import com.sohu.app.ads.sdk.res.Const;
import com.sohu.mobile.tracing.plugin.b;
import com.sohu.mobile.tracing.plugin.expose.Plugin_ExposeAction;
import com.sohu.mobile.tracing.plugin.expose.Plugin_ExposeAdBoby;
import com.sohu.mobile.tracing.plugin.expose.Plugin_VastTag;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class t extends Handler
{
  t(s params)
  {
  }

  private void a(float paramFloat, int paramInt)
  {
    ArrayList localArrayList2;
    try
    {
      Const.TimeOutStart = System.currentTimeMillis();
      if (paramInt == 0)
      {
        com.sohu.app.ads.sdk.c.a.a("开始第0秒上报");
        if (s.b(this.a) != null)
        {
          b.b().a(Plugin_ExposeAdBoby.OAD, s.b(this.a).getCreativeView(), Plugin_VastTag.VAST_CREATIVEVIEW, Plugin_ExposeAction.EXPOSE_SHOW);
          b.b().a(Plugin_ExposeAdBoby.OAD, s.b(this.a).getStart(), Plugin_VastTag.VAST_START, Plugin_ExposeAction.EXPOSE_SHOW);
          com.sohu.app.ads.sdk.f.d.a(AdEventType.BUFFERCOMPLETE, s.b(this.a));
          localArrayList2 = s.b(this.a).getImpression();
          if (localArrayList2 != null)
          {
            Iterator localIterator = localArrayList2.iterator();
            while (localIterator.hasNext())
            {
              String str3 = (String)localIterator.next();
              if (com.sohu.app.ads.sdk.f.d.a(str3))
                b.b().a(Plugin_ExposeAdBoby.OAD, str3, Plugin_VastTag.VAST_IMPRESSION, Plugin_ExposeAction.EXPOSE_SHOW);
            }
          }
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    label530: label534: 
    while (true)
    {
      return;
      s.a(this.a).SendTrackingEvent(false, localArrayList2);
      ArrayList localArrayList1 = s.b(this.a).getSdkTracking();
      if (localArrayList1 != null)
      {
        int i = localArrayList1.size();
        int j = 0;
        if (i > 0)
          while (true)
          {
            if (j >= localArrayList1.size())
              break label534;
            com.sohu.app.ads.sdk.model.d locald = (com.sohu.app.ads.sdk.model.d)localArrayList1.get(j);
            int k = locald.c();
            String str1 = locald.a();
            String str2 = locald.b();
            if ((k >= 0) && (k == paramInt) && (com.sohu.app.ads.sdk.f.d.a(str1)) && (com.sohu.app.ads.sdk.f.d.a(str2)))
            {
              if ("admaster".equalsIgnoreCase(str1))
              {
                b.b().a(Plugin_ExposeAdBoby.OAD, str2, Plugin_VastTag.ADMASTER, Plugin_ExposeAction.EXPOSE_SHOW);
                break label530;
                if ((int)paramFloat == paramInt)
                {
                  b.b().a(Plugin_ExposeAdBoby.OAD, s.b(this.a).getCreativeView(), Plugin_VastTag.VAST_FIRSTQUARTILE, Plugin_ExposeAction.EXPOSE_SHOW);
                  break;
                }
                if ((int)(3.0F * paramFloat) == paramInt)
                {
                  b.b().a(Plugin_ExposeAdBoby.OAD, s.b(this.a).getThirdQuartile(), Plugin_VastTag.VAST_THIRDQUARTILE, Plugin_ExposeAction.EXPOSE_SHOW);
                  break;
                }
                if ((int)(2.0F * paramFloat) != paramInt)
                  break;
                b.b().a(Plugin_ExposeAdBoby.OAD, s.b(this.a).getMidpoint(), Plugin_VastTag.VAST_SECONDQUARTILE, Plugin_ExposeAction.EXPOSE_SHOW);
                break;
              }
              if ("miaozhen".equalsIgnoreCase(str1))
              {
                com.sohu.app.ads.sdk.c.a.c("秒针展示曝光");
                b.b().a(Plugin_ExposeAdBoby.OAD, str2, Plugin_VastTag.MIAOZHEN, Plugin_ExposeAction.EXPOSE_SHOW);
              }
              else if ("sohutv".equalsIgnoreCase(str1))
              {
                b.b().a(Plugin_ExposeAdBoby.OAD, str2.trim(), Plugin_VastTag.SOHUSDK, Plugin_ExposeAction.EXPOSE_SHOW);
              }
              else
              {
                b.b().a(Plugin_ExposeAdBoby.OAD, str2.trim(), Plugin_VastTag.SOHUSDK, Plugin_ExposeAction.EXPOSE_SHOW);
              }
            }
            j++;
          }
      }
    }
  }

  public void dispatchMessage(Message paramMessage)
  {
    if ((s.a(this.a) == null) || (s.b(this.a) == null));
    do
      while (true)
      {
        return;
        switch (paramMessage.what)
        {
        default:
          return;
        case 1:
          Bundle localBundle = paramMessage.getData();
          if ((localBundle != null) && (s.c(this.a) != null))
          {
            float f = s.b(this.a).getDuration() / 4;
            int i = com.sohu.app.ads.sdk.f.d.a(localBundle.getFloat("time"));
            s.a(this.a).SendTime(i);
            int j = -1;
            try
            {
              int k = com.sohu.app.ads.sdk.f.d.a(localBundle.getFloat("currentTime"));
              j = k;
              if ((s.c() != j) && (!s.c(this.a).contains(Integer.valueOf(j))))
              {
                s.c(this.a).add(Integer.valueOf(j));
                s.a(j);
                com.sohu.app.ads.sdk.c.a.a("SOHUSDK", "playTime==" + j + "/integerTime=" + i + ",语音广告=" + s.b(this.a).isVoiceAd() + ",语音初始化=" + c.b());
                a(f, j);
                if (j == 5)
                {
                  com.sohu.app.ads.sdk.e.a.a().a(s.a(this.a).d());
                  return;
                }
              }
            }
            catch (Exception localException)
            {
              while (true)
                localException.printStackTrace();
            }
          }
          break;
        case 2:
        }
      }
    while (Const.TimeOutStart + Const.TimeOut >= System.currentTimeMillis());
    com.sohu.app.ads.sdk.c.a.a("SOHUSDK", "ProgressTimer:播放超时");
    com.sohu.app.ads.sdk.f.d.a(AdEventType.PLAYTIMEOUT, s.b(this.a));
    s.a(this.a).b();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.app.ads.sdk.core.t
 * JD-Core Version:    0.6.2
 */