package com.sohu.app.ads.sdk.core;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import com.miaozhen.mzmonitor.MZMonitor;
import com.sohu.app.ads.sdk.d.h;
import com.sohu.app.ads.sdk.exception.SdkException;
import com.sohu.app.ads.sdk.iterface.IAdErrorEventListener;
import com.sohu.app.ads.sdk.iterface.IAdsLoadedListener;
import com.sohu.app.ads.sdk.iterface.ILoader;
import com.sohu.app.ads.sdk.iterface.INetCallback;
import com.sohu.app.ads.sdk.iterface.IVideoAdPlayer;
import com.sohu.app.ads.sdk.iterface.PopWindowCallback;
import com.sohu.app.ads.sdk.iterface.StartPictureCallBack;
import com.sohu.app.ads.sdk.model.AdsResponse;
import com.sohu.app.ads.sdk.model.RequestComponent;
import com.sohu.app.ads.sdk.model.emu.ErrorType;
import com.sohu.app.ads.sdk.res.AdType;
import com.sohu.app.ads.sdk.res.Const;
import com.sohu.mobile.tracing.plugin.expose.Plugin_ExposeAction;
import com.sohu.mobile.tracing.plugin.expose.Plugin_ExposeAdBoby;
import com.sohu.mobile.tracing.plugin.expose.Plugin_VastTag;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class a
  implements View.OnClickListener, ILoader, INetCallback
{
  private static n g = null;
  private IAdsLoadedListener a;
  private IAdErrorEventListener b;
  private RequestComponent c;
  private Context d;
  private com.sohu.app.ads.sdk.d.a e = null;
  private File f = null;

  public a(Context paramContext)
  {
    if (paramContext == null)
      throw new SdkException("Context实例为空");
    this.d = paramContext;
    com.sohu.app.ads.sdk.c.a.a("构造 AdsLoader()");
    com.sohu.app.ads.sdk.f.d.a(this.d);
    com.sohu.app.ads.sdk.f.c.a(this.d);
    com.sohu.mobile.a.a.e.c().a(this.d, "2.1.13");
    com.sohu.mobile.tracing.plugin.b.b().a(this.d);
    com.sohu.mobile.tracing.plugin.b.b().a("2.1.13");
    try
    {
      com.sohu.mma.tracking.a.b.a().a(this.d, "");
      MZMonitor.retryCachedRequests(this.d);
      if (com.sohu.app.ads.sdk.f.d.e())
      {
        com.sohu.app.ads.sdk.c.a.a("SD卡可以使用...");
        this.f = paramContext.getApplicationContext().getExternalFilesDir("PAUSECACHE");
        com.sohu.mobile.tracing.plugin.b.b().a();
        this.e = new com.sohu.app.ads.sdk.d.a();
        return;
      }
    }
    catch (Exception localException)
    {
      while (true)
      {
        localException.printStackTrace();
        continue;
        com.sohu.app.ads.sdk.c.a.a("SD卡不可以使用...");
        this.f = paramContext.getCacheDir();
      }
    }
  }

  private void a(Context paramContext, ViewGroup paramViewGroup, String paramString1, String paramString2, String paramString3, PopWindowCallback paramPopWindowCallback, int paramInt)
  {
    com.sohu.app.ads.sdk.c.a.a("PauseAd VID=" + paramString3);
    removePauseAd();
    int[] arrayOfInt = com.sohu.app.ads.sdk.f.d.a(com.sohu.app.ads.sdk.f.c.a(), paramInt);
    g = new n(paramContext, null, this);
    if (com.sohu.app.ads.sdk.f.d.a())
      this.e.a(paramString1, paramString2, new c(this, paramViewGroup, arrayOfInt, paramPopWindowCallback), 2);
    com.sohu.app.ads.sdk.model.a locala;
    while (true)
    {
      return;
      if (com.sohu.app.ads.sdk.f.d.a(paramString3))
      {
        locala = new com.sohu.app.ads.sdk.a.d(paramContext).b(paramString3);
        if (locala == null)
          break label230;
        if (com.sohu.app.ads.sdk.f.d.a(locala.e()))
          break label191;
        ArrayList localArrayList = locala.a();
        if ((localArrayList == null) || (localArrayList.size() <= 0))
          break;
        Iterator localIterator = localArrayList.iterator();
        while (localIterator.hasNext())
        {
          String str = (String)localIterator.next();
          com.sohu.mobile.tracing.plugin.b.b().a(Plugin_ExposeAdBoby.PAD, str, Plugin_VastTag.VAST_IMPRESSION, Plugin_ExposeAction.EXPOSE_SHOW);
        }
      }
    }
    label191: g.b(locala, new e(this, locala));
    r.a().a(paramViewGroup, g, arrayOfInt);
    paramPopWindowCallback.onOpenResult(true);
    return;
    label230: paramPopWindowCallback.onOpenResult(false);
  }

  private void a(Context paramContext, String paramString1, String paramString2, String paramString3, StartPictureCallBack paramStartPictureCallBack)
  {
    com.sohu.app.ads.sdk.c.a.a("startPictureAd");
    new b(this, paramString3, paramString1, paramString2).start();
    com.sohu.app.ads.sdk.model.a locala = new com.sohu.app.ads.sdk.a.e(paramContext).a(paramString3);
    com.sohu.app.ads.sdk.c.a.a("查询本地开机数据");
    if (locala != null)
    {
      com.sohu.app.ads.sdk.c.a.a("查询本地开机数据不为空");
      String str = locala.e();
      if (!com.sohu.app.ads.sdk.f.d.a(str))
      {
        com.sohu.app.ads.sdk.c.a.a("获取到本地开机数据图片为空");
        paramStartPictureCallBack.callBack(null, false);
        b(locala);
      }
      File localFile = new File(str);
      if (localFile.exists())
      {
        HashMap localHashMap = new HashMap();
        localHashMap.put("imageUrl", locala.e());
        localHashMap.put("impression", locala.a());
        localHashMap.put("tracking", locala.b());
        localHashMap.put("clickUrl", locala.d());
        localHashMap.put("clickTrack", locala.c());
        b(locala);
        paramStartPictureCallBack.callBack(localHashMap, true);
        return;
      }
      com.sohu.app.ads.sdk.c.a.a(localFile.getAbsolutePath() + "文件不存在");
      paramStartPictureCallBack.callBack(null, false);
      return;
    }
    paramStartPictureCallBack.callBack(null, false);
  }

  private void a(com.sohu.app.ads.sdk.model.a parama)
  {
    try
    {
      ArrayList localArrayList1 = parama.a();
      if ((localArrayList1 != null) && (localArrayList1.size() > 0))
      {
        Iterator localIterator2 = localArrayList1.iterator();
        while (localIterator2.hasNext())
        {
          String str3 = (String)localIterator2.next();
          com.sohu.mobile.tracing.plugin.b.b().a(Plugin_ExposeAdBoby.PAD, str3, Plugin_VastTag.VAST_IMPRESSION, Plugin_ExposeAction.EXPOSE_SHOW);
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    while (true)
    {
      return;
      ArrayList localArrayList2 = parama.b();
      if ((localArrayList2 != null) && (localArrayList2.size() > 0))
      {
        Iterator localIterator1 = localArrayList2.iterator();
        while (localIterator1.hasNext())
        {
          com.sohu.app.ads.sdk.model.d locald = (com.sohu.app.ads.sdk.model.d)localIterator1.next();
          String str1 = locald.a();
          String str2 = locald.b();
          if ("admaster".equalsIgnoreCase(str1))
          {
            com.sohu.mobile.tracing.plugin.b.b().a(Plugin_ExposeAdBoby.PAD, str2, Plugin_VastTag.ADMASTER, Plugin_ExposeAction.EXPOSE_SHOW);
          }
          else if ("sohutv".equalsIgnoreCase(str1))
          {
            com.sohu.mobile.tracing.plugin.b.b().a(Plugin_ExposeAdBoby.PAD, str2, Plugin_VastTag.SOHUSDK, Plugin_ExposeAction.EXPOSE_SHOW);
          }
          else if ("miaozhen".equalsIgnoreCase(str1))
          {
            com.sohu.app.ads.sdk.c.a.c("AdsLoader-exposePad秒针展示曝光");
            com.sohu.mobile.tracing.plugin.b.b().a(Plugin_ExposeAdBoby.PAD, str2, Plugin_VastTag.MIAOZHEN, Plugin_ExposeAction.EXPOSE_SHOW);
          }
        }
      }
    }
  }

  private void a(String paramString1, String paramString2, String paramString3)
  {
    com.sohu.app.ads.sdk.c.a.a("requestRequestAd");
    if (this.f != null)
    {
      com.sohu.app.ads.sdk.a.e locale = new com.sohu.app.ads.sdk.a.e(this.d);
      com.sohu.app.ads.sdk.model.a locala = com.sohu.app.ads.sdk.f.b.a().b(paramString2, paramString3);
      if (locala != null)
      {
        com.sohu.app.ads.sdk.c.a.a("保存...(开机广告)数据");
        locale.a(paramString1, locala);
        String str1 = locala.e();
        if (com.sohu.app.ads.sdk.f.d.a(str1))
        {
          String str2 = com.sohu.app.ads.sdk.f.d.c(str1);
          if (com.sohu.app.ads.sdk.f.a.a().a(str1, this.f, str2))
          {
            String str3 = this.f.getPath() + "/" + str2;
            com.sohu.app.ads.sdk.c.a.a("数据库中的文件地址更新为:" + str3);
            locale.a(str1, str3);
            return;
          }
          com.sohu.app.ads.sdk.c.a.a("下载开机广告失败");
          return;
        }
        com.sohu.app.ads.sdk.c.a.a("无法下载，开机广告图片的地址为空");
        return;
      }
      com.sohu.app.ads.sdk.c.a.a("获取(开机广告)离线数据失败...");
      return;
    }
    com.sohu.app.ads.sdk.c.a.c("mOfflinePauseDir==null");
  }

  private void a(ArrayList<AdsResponse> paramArrayList)
  {
    if (paramArrayList != null);
    try
    {
      if ((paramArrayList.size() > 0) && (this.c != null) && (this.d != null))
      {
        com.sohu.app.ads.sdk.c.a.a("SendResult:response.size() > 0");
        ViewGroup localViewGroup = this.c.getContainer();
        IVideoAdPlayer localIVideoAdPlayer = this.c.getPlayer();
        com.sohu.app.ads.sdk.b.c localc = new com.sohu.app.ads.sdk.b.c(new AdsManager(this.d, localIVideoAdPlayer, localViewGroup, paramArrayList));
        if (this.a != null)
          this.a.onAdsManagerLoaded(localc);
      }
      else
      {
        com.sohu.app.ads.sdk.c.a.a("SendResult:response == null ");
        if (this.b != null)
        {
          this.b.onAdsLoadedError(new com.sohu.app.ads.sdk.b.b(ErrorType.REQUESTADDS_ERROR, "从网络上获取广告数据失败"));
          return;
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  private String[] a(AdType paramAdType, HashMap<String, String> paramHashMap, boolean paramBoolean)
  {
    HashMap localHashMap = (HashMap)paramHashMap.clone();
    switch (f.a[paramAdType.ordinal()])
    {
    default:
      if (paramBoolean)
      {
        localHashMap.put("offline", "1");
        label60: localHashMap.put("sysver", Build.VERSION.SDK);
        localHashMap.put("prot", "vast");
        localHashMap.put("protv", "3.0");
        localHashMap.put("density", com.sohu.app.ads.sdk.f.d.g());
        localHashMap.put("displayMetrics", com.sohu.app.ads.sdk.f.d.f());
        localHashMap.put("manufacturer", Build.MANUFACTURER);
        localHashMap.put("sdkVersion", "2.1.13");
        localHashMap.put("localAareaCode", com.sohu.app.ads.sdk.f.d.d() + "");
        localHashMap.put("imei", com.sohu.app.ads.sdk.f.d.b());
        localHashMap.put("imsi", com.sohu.app.ads.sdk.f.d.c());
        localHashMap.put("appid", "ott");
        localHashMap.put("UUID", com.sohu.app.ads.sdk.f.d.j());
        if (!localHashMap.containsKey("url"))
          break label430;
      }
      break;
    case 1:
    case 2:
    case 3:
    }
    label430: for (String str1 = (String)localHashMap.remove("url"); ; str1 = "http://m.aty.sohu.com/m")
    {
      StringBuilder localStringBuilder = new StringBuilder();
      Iterator localIterator = localHashMap.keySet().iterator();
      while (true)
        if (localIterator.hasNext())
        {
          String str3 = (String)localIterator.next();
          localStringBuilder.append(str3 + "=" + (String)localHashMap.get(str3) + "&");
          continue;
          localHashMap.put("pt", "oad");
          break;
          localHashMap.put("pt", "pad");
          break;
          localHashMap.put("pt", "open");
          break;
          localHashMap.put("offline", "0");
          break label60;
        }
      String str2 = localStringBuilder.toString();
      return new String[] { str1, str2.substring(0, -1 + str2.length()) };
    }
  }

  private void b(com.sohu.app.ads.sdk.model.a parama)
  {
    ArrayList localArrayList1 = parama.a();
    if ((localArrayList1 != null) && (localArrayList1.size() > 0))
    {
      Iterator localIterator2 = localArrayList1.iterator();
      while (localIterator2.hasNext())
      {
        String str3 = (String)localIterator2.next();
        com.sohu.mobile.tracing.plugin.b.b().a(Plugin_ExposeAdBoby.OPEN, str3, Plugin_VastTag.VAST_IMPRESSION, Plugin_ExposeAction.EXPOSE_SHOW);
      }
    }
    while (true)
    {
      String str1;
      String str2;
      try
      {
        ArrayList localArrayList2 = parama.b();
        if ((localArrayList2 != null) && (localArrayList2.size() > 0))
        {
          Iterator localIterator1 = localArrayList2.iterator();
          if (localIterator1.hasNext())
          {
            com.sohu.app.ads.sdk.model.d locald = (com.sohu.app.ads.sdk.model.d)localIterator1.next();
            str1 = locald.a();
            str2 = locald.b();
            if (!"admaster".equalsIgnoreCase(str1))
              break label167;
            com.sohu.mobile.tracing.plugin.b.b().a(Plugin_ExposeAdBoby.OPEN, str2, Plugin_VastTag.ADMASTER, Plugin_ExposeAction.EXPOSE_SHOW);
            continue;
          }
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
      return;
      label167: if ("sohutv".equalsIgnoreCase(str1))
        com.sohu.mobile.tracing.plugin.b.b().a(Plugin_ExposeAdBoby.OPEN, str2, Plugin_VastTag.SOHUSDK, Plugin_ExposeAction.EXPOSE_SHOW);
      else if ("miaozhen".equalsIgnoreCase(str1))
        com.sohu.mobile.tracing.plugin.b.b().a(Plugin_ExposeAdBoby.OPEN, str2, Plugin_VastTag.MIAOZHEN, Plugin_ExposeAction.EXPOSE_SHOW);
    }
  }

  public void OpenShareUrl(String paramString)
  {
    Intent localIntent = new Intent("android.intent.action.VIEW", Uri.parse(paramString));
    localIntent.addFlags(268435456);
    this.d.startActivity(localIntent);
  }

  public void addAdErrorListener(IAdErrorEventListener paramIAdErrorEventListener)
  {
    this.b = paramIAdErrorEventListener;
  }

  public void addAdsLoadedListener(IAdsLoadedListener paramIAdsLoadedListener)
  {
    this.a = paramIAdsLoadedListener;
  }

  public void destory()
  {
    com.sohu.app.ads.sdk.c.a.a("销毁AdsLoader...");
    this.d = null;
    this.c = null;
    this.a = null;
    this.b = null;
    this.e = null;
    if (r.c())
      r.a().b();
  }

  public void netCallback(int paramInt, Object paramObject)
  {
    Object localObject = null;
    switch (paramInt)
    {
    default:
      return;
    case 1000:
    }
    if (paramObject != null)
    {
      com.sohu.mobile.a.a.b.a().a("onlineOadhaveData");
      ArrayList localArrayList1 = (ArrayList)paramObject;
      com.sohu.mobile.a.a.b.a().a("onlineOadSize", localArrayList1.size());
      com.sohu.app.ads.sdk.c.a.a("记录:在线前贴接口返回数据sucess");
      com.sohu.app.ads.sdk.c.a.a("记录:在线前贴接口返回" + localArrayList1.size() + "贴广告");
      ArrayList localArrayList2 = new ArrayList();
      int i = 0;
      if (i < localArrayList1.size())
      {
        AdsResponse localAdsResponse = (AdsResponse)localArrayList1.get(i);
        if (localAdsResponse != null)
        {
          if (localAdsResponse.isVoiceAd())
            localArrayList2.add(localAdsResponse.getDisplayKeyword());
          if (i != 0)
            break label193;
          localObject = localAdsResponse;
          label163: if (!"".equals(localAdsResponse.getMediaFile()))
            break label211;
          com.sohu.mobile.a.a.b.a().a("onlineOadNullSize");
        }
        while (true)
        {
          i++;
          break;
          label193: com.sohu.app.ads.sdk.e.a.a().a(this.d, localAdsResponse.getMediaFile());
          break label163;
          label211: com.sohu.mobile.a.a.b.a().a("onlineOadNotNullSize");
        }
      }
      if (localObject != null)
        com.sohu.app.ads.sdk.e.a.a().a(this.d, localObject.getMediaFile());
      a(localArrayList1);
      return;
    }
    com.sohu.mobile.a.a.b.a().a("onlineOadNoData");
    com.sohu.app.ads.sdk.c.a.a("记录:在线前贴接口返回数据failed");
    a(null);
  }

  public void onClick(View paramView)
  {
    if ((paramView.getId() == 1001) && (r.c()))
    {
      r.a().b();
      g.a();
    }
  }

  public void onDownloadTaskDeleted(String paramString)
  {
    com.sohu.app.ads.sdk.c.a.c("request delete offline ad");
    if (!com.sohu.app.ads.sdk.f.d.a(paramString))
      return;
    new com.sohu.app.ads.sdk.e.c(this.d).d(paramString);
  }

  public void onDownloadTaskStarted(HashMap<String, String> paramHashMap)
  {
    com.sohu.app.ads.sdk.c.a.c("request download offline ad");
    if (paramHashMap == null)
      throw new SdkException("onDownloadTaskStarted mParams is null");
    if (!com.sohu.app.ads.sdk.f.d.e());
    String str1;
    do
    {
      return;
      str1 = (String)paramHashMap.get("vid");
    }
    while (!com.sohu.app.ads.sdk.f.d.a(str1));
    String[] arrayOfString1 = a(AdType.OAD, paramHashMap, true);
    String str2 = arrayOfString1[0];
    String str3 = arrayOfString1[1];
    String[] arrayOfString2 = a(AdType.PAD, paramHashMap, true);
    String str4 = arrayOfString2[0];
    String str5 = arrayOfString2[1];
    com.sohu.app.ads.sdk.e.c localc = new com.sohu.app.ads.sdk.e.c(this.d);
    localc.a(str2 + "?" + str3);
    localc.b(str4 + "?" + str5);
    localc.c(str1);
    Thread localThread = new Thread(localc);
    localThread.setName(str1);
    localThread.start();
  }

  public void removePauseAd()
  {
    if (r.c())
    {
      com.sohu.app.ads.sdk.c.a.c("request remove pad");
      r.a().b();
      g.a();
    }
  }

  public void requestAds(RequestComponent paramRequestComponent, HashMap<String, String> paramHashMap)
  {
    com.sohu.app.ads.sdk.c.a.c("request Ads");
    if (this.a == null)
      throw new SdkException("mLoadedListener is null");
    if (this.b == null)
      throw new SdkException("mErrorListener is null");
    if (paramRequestComponent == null)
      throw new SdkException("requestComponent is null");
    if (paramRequestComponent.getContainer() == null)
      throw new SdkException("getContainer is null");
    if (paramRequestComponent.getPlayer() == null)
      throw new SdkException("getPlayer is null");
    this.c = paramRequestComponent;
    if (paramHashMap == null)
      throw new SdkException("HashMap mParams is null");
    Const.TimeOutStart = System.currentTimeMillis();
    com.sohu.app.ads.sdk.c.a.a("开始超时计时" + Const.TimeOutStart);
    String str1 = (String)paramHashMap.get("adoriginal");
    if (!com.sohu.app.ads.sdk.f.d.a(str1))
      throw new SdkException("PARAM_ADORIGINAL is null");
    ArrayList localArrayList;
    if ("sohu".equalsIgnoreCase(str1))
      if (paramHashMap.containsKey("islocaltv"))
      {
        String str5 = (String)paramHashMap.get("islocaltv");
        com.sohu.app.ads.sdk.c.a.c("isloacltvString==" + str5);
        if ((!TextUtils.isEmpty(str5)) && ("1".equals(str5)))
        {
          com.sohu.mobile.a.a.b.a().a("offlineOadInterfaceReqTimes");
          com.sohu.app.ads.sdk.c.a.b("记录:请求离线前贴片接口");
          com.sohu.app.ads.sdk.c.a.c("request offline Ads");
          String str6 = (String)paramHashMap.get("vid");
          if (!com.sohu.app.ads.sdk.f.d.a(str6))
            break label600;
          com.sohu.app.ads.sdk.c.a.c("本地广告vid=" + str6);
          localArrayList = new com.sohu.app.ads.sdk.a.c(this.d).b(str6);
          if ((localArrayList != null) && (localArrayList.size() > 0))
          {
            com.sohu.mobile.a.a.b.a().a("offlineOadhaveData");
            com.sohu.app.ads.sdk.c.a.b("记录:离线前贴接口返回数据sucess");
            com.sohu.mobile.a.a.b.a().a("offlineOadSize", localArrayList.size());
            com.sohu.app.ads.sdk.c.a.b("记录:离线前贴接口返回" + localArrayList.size() + "贴广告");
          }
        }
      }
    while (true)
    {
      a(localArrayList);
      do
      {
        return;
        com.sohu.mobile.a.a.b.a().a("offlineOadNoData");
        com.sohu.app.ads.sdk.c.a.b("记录:离线前贴片接口返回数据failed");
        break;
        if (com.sohu.app.ads.sdk.f.d.a())
        {
          com.sohu.mobile.a.a.b.a().a("onlineOadInterfaceReqTimes");
          com.sohu.app.ads.sdk.c.a.a("记录:请求在线前贴片接口");
          String[] arrayOfString = a(AdType.OAD, paramHashMap, false);
          String str3 = arrayOfString[0];
          String str4 = arrayOfString[1];
          new h(this, 1000, null, false).execute(new Object[] { str3, str4 });
        }
      }
      while (!"third".equalsIgnoreCase(str1));
      String str2 = (String)paramHashMap.get("url");
      if (!com.sohu.app.ads.sdk.f.d.a(str2))
        throw new SdkException("thirdUri PARAM_URI is null");
      if (com.sohu.app.ads.sdk.f.d.a())
      {
        new h(this, 1000, null, false).execute(new Object[] { str2, null });
        return;
      }
      a(null);
      return;
      label600: localArrayList = null;
    }
  }

  public void requestPauseAd(Context paramContext, ViewGroup paramViewGroup, HashMap<String, String> paramHashMap, PopWindowCallback paramPopWindowCallback, int paramInt)
  {
    com.sohu.app.ads.sdk.c.a.c("request pad");
    if (paramContext == null)
      throw new SdkException("mContext is null");
    if (paramViewGroup == null)
      throw new SdkException("requestPause Ad ViewGroup is null");
    if (paramHashMap == null)
      throw new SdkException("mParams is null");
    if (paramPopWindowCallback == null)
      throw new SdkException("PopWindowCallback is null");
    String str1 = (String)paramHashMap.get("adoriginal");
    if (!com.sohu.app.ads.sdk.f.d.a(str1))
      throw new SdkException("requestPauseAd PARAM_ADORIGINAL is null");
    if ("sohu".equalsIgnoreCase(str1))
    {
      String[] arrayOfString = a(AdType.PAD, paramHashMap, false);
      a(paramContext, paramViewGroup, arrayOfString[0], arrayOfString[1], (String)paramHashMap.get("vid"), paramPopWindowCallback, paramInt);
    }
    String str2;
    if ("third".equalsIgnoreCase(str1))
    {
      str2 = (String)paramHashMap.get("url");
      if (!com.sohu.app.ads.sdk.f.d.a(str2))
        this.b.onAdsLoadedError(new com.sohu.app.ads.sdk.b.b(ErrorType.RequestParamsError, "请求的服务器URL为空"));
    }
    else
    {
      return;
    }
    a(paramContext, paramViewGroup, str2, null, null, paramPopWindowCallback, paramInt);
  }

  public void requestStartPicture(Context paramContext, HashMap<String, String> paramHashMap, StartPictureCallBack paramStartPictureCallBack)
  {
    if (paramContext == null)
    {
      com.sohu.app.ads.sdk.c.a.c("mContext is null");
      throw new SdkException("mContext is null");
    }
    if (paramHashMap == null)
    {
      com.sohu.app.ads.sdk.c.a.c("mParams is null");
      throw new SdkException("mParams is null");
    }
    if (paramStartPictureCallBack == null)
    {
      com.sohu.app.ads.sdk.c.a.c("PopWindowCallback is null");
      throw new SdkException("PopWindowCallback is null");
    }
    String str1 = (String)paramHashMap.get("adoriginal");
    if (!com.sohu.app.ads.sdk.f.d.a(str1))
    {
      com.sohu.app.ads.sdk.c.a.c("requestPauseAd PARAM_ADORIGINAL is null");
      throw new SdkException("requestPauseAd PARAM_ADORIGINAL is null");
    }
    com.sohu.app.ads.sdk.c.a.a("original:" + str1);
    if ("sohu".equalsIgnoreCase(str1))
    {
      arrayOfString = a(AdType.STARTIMG, paramHashMap, false);
      a(paramContext, arrayOfString[0], arrayOfString[1], (String)paramHashMap.get("vid"), paramStartPictureCallBack);
    }
    while (!"third".equalsIgnoreCase(str1))
    {
      String[] arrayOfString;
      return;
    }
    String str2 = (String)paramHashMap.get("url");
    if (!com.sohu.app.ads.sdk.f.d.a(str2))
    {
      this.b.onAdsLoadedError(new com.sohu.app.ads.sdk.b.b(ErrorType.RequestParamsError, "请求的服务器URL为空"));
      return;
    }
    a(paramContext, str2, null, "1", paramStartPictureCallBack);
  }

  public void setDeviceType(int paramInt)
  {
    com.sohu.app.ads.sdk.f.c.a("device", Integer.valueOf(paramInt));
    com.sohu.app.ads.sdk.c.a.c("设置设备类型 = " + paramInt);
  }

  public void setTimeOut(int paramInt)
  {
    Const.TimeOut = paramInt;
    com.sohu.app.ads.sdk.c.a.c("设置广告超时时间 = " + paramInt);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.app.ads.sdk.core.a
 * JD-Core Version:    0.6.2
 */