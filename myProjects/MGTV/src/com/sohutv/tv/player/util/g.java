package com.sohutv.tv.player.util;

import android.content.Context;
import android.util.Log;
import com.sohu.app.ads.sdk.SdkFactory;
import com.sohu.logger.SohuLoggerAgent;
import com.sohu.logger.bean.LogInfo;
import com.sohu.logger.common.AppConstants;
import com.sohu.logger.log.OutputLog;
import com.sohutv.tv.player.ad.AdRequestParams;
import com.sohutv.tv.player.ad.b;
import com.sohutv.tv.player.entity.PlayInfo;
import com.sohutv.tv.player.entity.SystemConstantEntity;
import com.sohutv.tv.player.util.a.c;

public class g
{
  private Context a;
  private i b;

  public g(Context paramContext, a parama)
  {
    this.a = paramContext;
    SohuLoggerAgent.getInstance().init(this.a, 0, c.k, c.m, c.h, c.l, c.i, "", "", 3600);
    if (AppConstants.getInstance().getmProjectType() == 0)
      SohuLoggerAgent.getInstance().onAppStart(this.a);
    SdkFactory.getInstance().prepare(this.a, c.l);
    if (parama != null)
    {
      this.b = i.a();
      this.b.a(parama);
    }
    c();
  }

  private void c()
  {
    String str = c.a();
    OutputLog.i("Sohuplayer", "getSystemConstant httpurl = " + str);
    HttpAPI.get(str, SystemConstantEntity.class, new HttpAPI.SuccessListener()
    {
      public void a(SystemConstantEntity paramAnonymousSystemConstantEntity)
      {
        if (paramAnonymousSystemConstantEntity != null)
        {
          Log.i("Sohuplayer", "getSystemConstant :" + paramAnonymousSystemConstantEntity.getAd_main_switch() + " " + paramAnonymousSystemConstantEntity.getAd_oad_switch());
          com.sohutv.tv.player.ad.a.a(g.a(g.this), "0".equals(paramAnonymousSystemConstantEntity.getAd_main_switch()));
          com.sohutv.tv.player.ad.a.b(g.a(g.this), "1".equals(paramAnonymousSystemConstantEntity.getAd_vip_show_switch()));
          com.sohutv.tv.player.ad.a.c(g.a(g.this), "1".equals(paramAnonymousSystemConstantEntity.getAd_oad_switch()));
          b.a(g.a(g.this), paramAnonymousSystemConstantEntity.getOpenAdShowingTime());
        }
      }
    }
    , new HttpAPI.ErrorListener()
    {
      public void onErrorResponse(int paramAnonymousInt, Throwable paramAnonymousThrowable)
      {
        if (paramAnonymousThrowable == null)
          paramAnonymousThrowable = new Throwable("网络请求错误");
        Log.e("Sohuplayer", "广告控制参数请求错误 ");
        paramAnonymousThrowable.printStackTrace();
      }
    });
  }

  public AdRequestParams a()
  {
    if (com.sohutv.tv.player.util.a.a.f != null)
    {
      AdRequestParams localAdRequestParams = new AdRequestParams();
      localAdRequestParams.setAg(com.sohutv.tv.player.util.a.a.f.getAg());
      localAdRequestParams.setSt(com.sohutv.tv.player.util.a.a.f.getSt());
      localAdRequestParams.setAdSource(com.sohutv.tv.player.util.a.a.f.getAdSource());
      localAdRequestParams.setVc(com.sohutv.tv.player.util.a.a.f.getVc());
      localAdRequestParams.setAlbumId(com.sohutv.tv.player.util.a.a.f.getAlbumID());
      localAdRequestParams.setDuration(com.sohutv.tv.player.util.a.a.f.getDu());
      localAdRequestParams.setArea(com.sohutv.tv.player.util.a.a.f.getAr());
      localAdRequestParams.setVideoId(com.sohutv.tv.player.util.a.a.f.getVideoID());
      localAdRequestParams.setLid(com.sohutv.tv.player.util.a.a.f.getLid());
      localAdRequestParams.setVu(null);
      return localAdRequestParams;
    }
    return null;
  }

  public LogInfo b()
  {
    if (com.sohutv.tv.player.util.a.a.f == null)
      return null;
    LogInfo localLogInfo = new LogInfo();
    localLogInfo.setAlbumID(com.sohutv.tv.player.util.a.a.f.getAlbumID());
    localLogInfo.setArea(com.sohutv.tv.player.util.a.a.f.getArea());
    localLogInfo.setCateCode(com.sohutv.tv.player.util.a.a.f.getCateCode());
    localLogInfo.setCategoryID(com.sohutv.tv.player.util.a.a.f.getCategoryID());
    localLogInfo.setChanneled(com.sohutv.tv.player.util.a.a.f.getChanneled());
    localLogInfo.setCompany(com.sohutv.tv.player.util.a.a.f.getCompany());
    localLogInfo.setCurrentDefinition(com.sohutv.tv.player.util.a.a.f.getCurrentDefinition());
    localLogInfo.setCurrentDefinitionType(com.sohutv.tv.player.util.a.a.f.getCurrentDefinitionType());
    localLogInfo.setCurrentUrl(com.sohutv.tv.player.util.a.a.f.getCurrentUrl());
    localLogInfo.setEnterID(com.sohutv.tv.player.util.a.a.f.getEnterID());
    localLogInfo.setFee(com.sohutv.tv.player.util.a.a.f.isFee());
    localLogInfo.setIsEdit(com.sohutv.tv.player.util.a.a.f.getIsEdit());
    localLogInfo.setIsSohu(com.sohutv.tv.player.util.a.a.f.getIsSohu());
    localLogInfo.setLanguage(com.sohutv.tv.player.util.a.a.f.getLanguage());
    localLogInfo.setLiveChannelID(com.sohutv.tv.player.util.a.a.f.getLiveChannelID());
    localLogInfo.setLiveStreamID(com.sohutv.tv.player.util.a.a.f.getLiveStreamID());
    localLogInfo.setPassport(com.sohutv.tv.player.util.a.a.f.getPassport());
    localLogInfo.setSiteName(com.sohutv.tv.player.util.a.a.f.getSiteName());
    localLogInfo.setStatCode(com.sohutv.tv.player.util.a.a.f.getStatCode());
    localLogInfo.setSubCategoryID(com.sohutv.tv.player.util.a.a.f.getSubCategoryID());
    localLogInfo.setTvID(com.sohutv.tv.player.util.a.a.f.getTvID());
    localLogInfo.setType(com.sohutv.tv.player.util.a.a.f.getType());
    localLogInfo.setVideoID(com.sohutv.tv.player.util.a.a.f.getVideoID());
    localLogInfo.setWatchType(com.sohutv.tv.player.util.a.a.f.getWatchType());
    return localLogInfo;
  }

  public static abstract interface a
  {
    public abstract void a(String paramString);

    public abstract void a(Throwable paramThrowable);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohutv.tv.player.util.g
 * JD-Core Version:    0.6.2
 */