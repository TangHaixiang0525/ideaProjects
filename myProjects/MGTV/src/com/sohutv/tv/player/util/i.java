package com.sohutv.tv.player.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.util.Log;
import com.sohu.logger.SohuLoggerAgent;
import com.sohu.logger.common.AppConstants;
import com.sohu.logger.common.DeviceConstants;
import com.sohu.logger.log.OutputLog;
import com.sohutv.tv.player.ad.SohuTVAdvertise;
import com.sohutv.tv.player.ad.SohuTVAdvertise.PlaybackState;
import com.sohutv.tv.player.entity.FeeKeyData;
import com.sohutv.tv.player.entity.PlayInfo;
import com.sohutv.tv.player.entity.PlayUrl;
import com.sohutv.tv.player.entity.ResponsePlayInfo;
import com.sohutv.tv.player.interfaces.ISohuOttPlayer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

public class i
{
  private static i d = new i();
  private Context a;
  private ISohuOttPlayer b;
  private List<PlayUrl> c = new ArrayList();
  private String e = "";
  private com.sohutv.tv.player.ad.c f;
  private g.a g;
  private SohuTVAdvertise h = null;

  private PlayUrl a(int paramInt)
  {
    Iterator localIterator = this.c.iterator();
    while (localIterator.hasNext())
    {
      PlayUrl localPlayUrl = (PlayUrl)localIterator.next();
      if (localPlayUrl.getId() == paramInt)
        return localPlayUrl;
    }
    if (paramInt >= 32)
      a(31);
    while (true)
    {
      return null;
      if (paramInt == 31)
        a(21);
      else if (paramInt == 21)
        a(1);
      else if (paramInt == 2)
        a(1);
    }
  }

  public static i a()
  {
    return d;
  }

  private String a(ResponsePlayInfo paramResponsePlayInfo)
  {
    String str1;
    if (paramResponsePlayInfo.getPlayinfos() == null)
    {
      Log.e("Sohuplayer", "获取播放信息为空，不能播放");
      str1 = "";
    }
    String str2;
    do
    {
      return str1;
      if (this.c.size() > 0)
        this.c.clear();
      this.c.addAll(paramResponsePlayInfo.getPlayinfos());
      str1 = "";
      str2 = "";
      if (this.c.size() == 0)
      {
        Log.e("Sohuplayer", "获取播放地址列表为空，不能播放");
        return "";
      }
      Log.i("Sohuplayer", "请求清晰度 = " + com.sohutv.tv.player.util.a.a.b);
      PlayUrl localPlayUrl = a(com.sohutv.tv.player.util.a.a.b);
      if (localPlayUrl != null)
      {
        str1 = localPlayUrl.getUrl();
        str2 = localPlayUrl.getUrl_h265();
        com.sohutv.tv.player.util.a.a.b = localPlayUrl.getId();
        Log.i("Sohuplayer", "获得清晰度 = " + com.sohutv.tv.player.util.a.a.b);
      }
      if (str1.equals(""))
      {
        Log.i("Sohuplayer", "没找到清晰度 = " + com.sohutv.tv.player.util.a.a.b);
        String str3 = ((PlayUrl)this.c.get(0)).getUrl();
        if (TextUtils.isEmpty(str3))
        {
          Log.e("Sohuplayer", "获取播放地址为空,不能播放");
          return "";
        }
        if (((PlayUrl)this.c.get(0)).getId() == 51)
        {
          String str4 = ((PlayUrl)this.c.get(0)).getUrl_h265();
          if ((!TextUtils.isEmpty(str4)) && (e()))
          {
            Log.i("Sohuplayer", "4K清晰度,返回265地址");
            return str4;
          }
          Log.i("Sohuplayer", "4K清晰度,但不返回265地址");
          return str3;
        }
        return str3;
      }
    }
    while (com.sohutv.tv.player.util.a.a.b != 51);
    if ((!TextUtils.isEmpty(str2)) && (e()))
    {
      Log.i("Sohuplayer", "4K清晰度,返回265地址");
      return str2;
    }
    Log.i("Sohuplayer", "4K清晰度,但不返回265地址");
    return str1;
  }

  private void a(PlayInfo paramPlayInfo, ResponsePlayInfo paramResponsePlayInfo)
  {
    if ((paramPlayInfo != null) && (paramResponsePlayInfo != null))
    {
      LinkedHashMap localLinkedHashMap1;
      HashMap localHashMap1;
      LinkedHashMap localLinkedHashMap2;
      if (paramResponsePlayInfo.getFee() == 1)
      {
        paramPlayInfo.setFee(true);
        paramPlayInfo.setVideoTitle(paramResponsePlayInfo.getTv_name());
        paramPlayInfo.setTvID(Long.toString(paramResponsePlayInfo.getTv_id()));
        paramPlayInfo.setIsSohu(paramResponsePlayInfo.getIs_sohu());
        paramPlayInfo.setArea(paramResponsePlayInfo.getArea_id());
        paramPlayInfo.setSubCategoryID(paramResponsePlayInfo.getSub_code());
        paramPlayInfo.setSiteName(paramResponsePlayInfo.getSite_name());
        paramPlayInfo.setVc(paramResponsePlayInfo.getSub_code());
        paramPlayInfo.setDuration(paramResponsePlayInfo.getTime_length());
        localLinkedHashMap1 = paramPlayInfo.getUrlMap();
        localHashMap1 = paramPlayInfo.getUrlMapType();
        if (localLinkedHashMap1 != null)
          break label236;
        localLinkedHashMap2 = new LinkedHashMap();
        label112: if (localHashMap1 != null)
          break label246;
      }
      for (HashMap localHashMap2 = new HashMap(); ; localHashMap2 = localHashMap1)
      {
        List localList = paramResponsePlayInfo.getPlayinfos();
        if (localList == null)
          break label258;
        for (int i = -1 + localList.size(); i >= 0; i--)
        {
          localLinkedHashMap2.put(((PlayUrl)localList.get(i)).getName(), localList.get(i));
          localHashMap2.put(((PlayUrl)localList.get(i)).getName(), Integer.toString(((PlayUrl)localList.get(i)).getId()));
        }
        paramPlayInfo.setFee(false);
        break;
        label236: localLinkedHashMap1.clear();
        localLinkedHashMap2 = localLinkedHashMap1;
        break label112;
        label246: localHashMap1.clear();
      }
      label258: paramPlayInfo.setUrlMap(localLinkedHashMap2);
      paramPlayInfo.setUrlMapType(localHashMap2);
    }
  }

  private void a(final boolean paramBoolean)
  {
    OutputLog.i("Sohuplayer", "tryGetUrlAndPlay()");
    if (com.sohutv.tv.player.util.a.a.f == null)
    {
      Log.e("Sohuplayer", "tryGetUrlAndPlay() mPlayInfo = null");
      return;
    }
    String str1 = com.sohutv.tv.player.util.a.c.a(com.sohutv.tv.player.util.a.a.f, "ott");
    OutputLog.i("Sohuplayer", " = " + str1);
    try
    {
      String[] arrayOfString = str1.split("\\?");
      if (arrayOfString.length == 2)
      {
        String str2 = arrayOfString[1].replace("api_key", "replacement").replace("7ad23396564b27116418d3c03a77db45", "replacement").replace("video_id", "firstid").replace("album_id", "secondid").replace("cate_code", "thirdid");
        Log.i("Sohuplayer", "HTTPURL = " + str2);
      }
      HttpAPI.get(str1, ResponsePlayInfo.class, new HttpAPI.SuccessListener()
      {
        public void a(ResponsePlayInfo paramAnonymousResponsePlayInfo)
        {
          if (paramAnonymousResponsePlayInfo != null)
          {
            Log.i("Sohuplayer", "playinfo response got " + paramAnonymousResponsePlayInfo.getIsPlay() + " " + paramAnonymousResponsePlayInfo.getTv_name());
            if (paramAnonymousResponsePlayInfo.getTv_name() == null)
            {
              Log.e("Sohuplayer", "播放信息为空");
              h.a().a(new Throwable("播放信息为空"));
              return;
            }
            if (paramAnonymousResponsePlayInfo.getIsPlay() == 0)
            {
              Log.e("Sohuplayer", "isPlay=0 不能播放 描述：" + paramAnonymousResponsePlayInfo.getTip());
              h.a().a(new Throwable(paramAnonymousResponsePlayInfo.getTip()));
              return;
            }
            com.sohutv.tv.player.util.a.a.e = paramAnonymousResponsePlayInfo;
            i.a(i.this, com.sohutv.tv.player.util.a.a.f, com.sohutv.tv.player.util.a.a.e);
            i.a(i.this, i.a(i.this, com.sohutv.tv.player.util.a.a.e));
            OutputLog.i("Sohuplayer", "网络返回ResponseUrl = " + i.a(i.this));
            if (TextUtils.isEmpty(i.a(i.this)))
            {
              Log.e("Sohuplayer", "播放地址为空，请检查片单信息");
              h.a().a(new Throwable("播放地址为空，请检查片单信息"));
              return;
            }
            i.a(i.this, i.b(i.this, i.a(i.this)));
            if (paramBoolean)
              try
              {
                SohuLoggerAgent.getInstance().onLogStop();
                if (com.sohutv.tv.player.util.a.a.f.isFee())
                {
                  i.b(i.this);
                  return;
                }
              }
              catch (Exception localException2)
              {
                while (true)
                  localException2.printStackTrace();
                i.c(i.this);
                return;
              }
            try
            {
              SohuLoggerAgent.getInstance().onLogStop();
              if (i.d(i.this) != null)
              {
                i.d(i.this).a(i.a(i.this));
                Log.i("Sohuplayer", "API for play ad");
                return;
              }
            }
            catch (Exception localException1)
            {
              while (true)
                localException1.printStackTrace();
              Log.e("Sohuplayer", "API mSohuTVAdvertise is null");
              return;
            }
          }
          Log.e("Sohuplayer", "tryGetUrlAndPlay responseInfo is null");
        }
      }
      , new HttpAPI.ErrorListener()
      {
        public void onErrorResponse(int paramAnonymousInt, Throwable paramAnonymousThrowable)
        {
          Log.e("Sohuplayer", "播放地址请求错误 ");
          if (paramAnonymousThrowable != null)
          {
            paramAnonymousThrowable.printStackTrace();
            if (paramAnonymousThrowable.getMessage() != null)
              h.a().a(paramAnonymousThrowable);
          }
          else
          {
            return;
          }
          h.a().a(new Throwable("播放地址请求错误 "));
        }
      });
      return;
    }
    catch (Exception localException)
    {
      while (true)
      {
        Log.e("Sohuplayer", "http request url is error");
        localException.printStackTrace();
      }
    }
  }

  private boolean b(String paramString)
  {
    try
    {
      Log.i("Sohuplayer", "parseJosnString jsonString = " + paramString);
      JSONObject localJSONObject = new JSONObject(paramString);
      int i;
      String str1;
      label70: String str2;
      label85: String str3;
      label100: String str4;
      label115: String str5;
      label130: String str6;
      label155: PlayInfo localPlayInfo;
      String str7;
      if (localJSONObject.isNull("position"))
      {
        i = 0;
        com.sohutv.tv.player.util.a.a.c = i;
        if (!localJSONObject.isNull("playurl"))
          break label327;
        str1 = "";
        if (!localJSONObject.isNull("vid"))
          break label340;
        str2 = "";
        if (!localJSONObject.isNull("sid"))
          break label353;
        str3 = "";
        if (!localJSONObject.isNull("cid"))
          break label366;
        str4 = "";
        if (!localJSONObject.isNull("catecode"))
          break label379;
        str5 = "";
        if (AppConstants.getInstance().getmProjectType() != 0)
          break label405;
        if (!localJSONObject.isNull("video_source"))
          break label392;
        str6 = "25";
        localPlayInfo = new PlayInfo();
        localPlayInfo.setAlbumID(str3);
        localPlayInfo.setVideoID(str2);
        localPlayInfo.setCategoryID(str4);
        localPlayInfo.setCateCode(str5);
        localPlayInfo.setEnterID(str6);
        localPlayInfo.setAdSource(str6);
        if (!localJSONObject.isNull("passport"))
          break label437;
        str7 = "";
        label221: if (!localJSONObject.isNull("definition"))
          break label450;
      }
      label392: label405: label437: label450: for (int j = 1; ; j = localJSONObject.getInt("definition"))
      {
        com.sohutv.tv.player.util.a.a.b = j;
        localPlayInfo.setPassport(str7);
        localPlayInfo.setCurrentDefinitionType(com.sohutv.tv.player.util.a.a.b);
        com.sohutv.tv.player.util.a.a.f = localPlayInfo;
        if (TextUtils.isEmpty(str1))
          break label526;
        OutputLog.i("Sohuplayer", "Direct Play");
        if (this.b == null)
          break label507;
        SohuTVAdvertise.b = SohuTVAdvertise.PlaybackState.INVIDEO;
        this.e = str1;
        if (this.f == null)
          break label463;
        this.f.b();
        break label528;
        i = localJSONObject.getInt("position");
        break;
        label327: str1 = localJSONObject.getString("playurl");
        break label70;
        label340: str2 = localJSONObject.getString("vid");
        break label85;
        label353: str3 = localJSONObject.getString("sid");
        break label100;
        label366: str4 = localJSONObject.getString("cid");
        break label115;
        label379: str5 = localJSONObject.getString("catecode");
        break label130;
        str6 = localJSONObject.getString("video_source");
        break label155;
        if (localJSONObject.isNull("video_source"))
        {
          str6 = "0";
          break label155;
        }
        str6 = localJSONObject.getString("video_source");
        break label155;
        str7 = localJSONObject.getString("passport");
        break label221;
      }
      label463: Log.e("Sohuplayer", "mAdListener = null");
    }
    catch (JSONException localJSONException)
    {
      Log.e("Sohuplayer", "parseJsonSohuPlayInfo() 片单json解析异常");
      h.a().a(new Throwable("片单信息解析异常"));
      localJSONException.printStackTrace();
      return false;
      Log.e("Sohuplayer", "parseJosnString player is null");
    }
    catch (Exception localException)
    {
      label507: localException.printStackTrace();
      return false;
    }
    label526: return true;
    label528: return false;
  }

  private String c(String paramString)
  {
    String str1 = paramString.replace("http://hot.vrs.sohu.com", com.sohutv.tv.player.util.a.c.e);
    String str2 = str1 + "&uid=" + DeviceConstants.getInstance().getUID();
    OutputLog.i("Sohuplayer", "处理后 responseurl=" + str2);
    return str2;
  }

  private void c()
  {
    if (com.sohutv.tv.player.util.a.a.h.booleanValue())
    {
      if (this.f != null)
      {
        this.f.a();
        return;
      }
      Log.e("Sohuplayer", "mAdListener = null");
      return;
    }
    if (this.f != null)
    {
      this.f.b();
      return;
    }
    Log.e("Sohuplayer", "mAdListener = null");
  }

  private void d()
  {
    String str1 = com.sohutv.tv.player.util.a.a.f.getPassport();
    Log.i("Sohuplayer", "getFeeVideoUrl() passport=" + str1);
    if (TextUtils.isEmpty(str1))
    {
      h.a().a(new Throwable("您正在观看的是视频预览，观看全片，请到搜狐视频网站购买。"));
      Log.e("Sohuplayer", "您正在观看的是视频预览，观看全片，请到搜狐视频网站购买。");
      c();
      return;
    }
    String str2 = String.valueOf(com.sohutv.tv.player.util.a.a.f.getAlbumID());
    String str3 = String.valueOf(com.sohutv.tv.player.util.a.a.f.getVideoID());
    String str4 = d.a(str1 + str2 + str3 + com.sohutv.tv.player.util.a.c.f + com.sohutv.tv.player.util.a.c.g);
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(new BasicNameValuePair("passport", str1));
    localArrayList.add(new BasicNameValuePair("pid", str2));
    localArrayList.add(new BasicNameValuePair("vid", str3));
    localArrayList.add(new BasicNameValuePair("channel", com.sohutv.tv.player.util.a.c.f));
    localArrayList.add(new BasicNameValuePair("sign", str4));
    HttpAPI.asypost("http://store.tv.sohu.com/web/checkpermission.do", localArrayList, FeeKeyData.class, new HttpAPI.SuccessListener()
    {
      public void a(FeeKeyData paramAnonymousFeeKeyData)
      {
        if (paramAnonymousFeeKeyData == null)
          Log.e("Sohuplayer", "tryToPlayFeeUrl but feekeydata is null");
        while (true)
        {
          i.c(i.this);
          return;
          com.sohutv.tv.player.util.a.a.g = paramAnonymousFeeKeyData;
          i.a(i.this, i.a(i.this) + "&fkey=" + com.sohutv.tv.player.util.a.a.g.getAntileechkey());
        }
      }
    }
    , new HttpAPI.ErrorListener()
    {
      public void onErrorResponse(int paramAnonymousInt, Throwable paramAnonymousThrowable)
      {
        Log.e("Sohuplayer", "tryToPlayFeeUrl err");
        if (paramAnonymousThrowable != null)
          paramAnonymousThrowable.printStackTrace();
        i.c(i.this);
      }
    });
  }

  @SuppressLint({"NewApi"})
  private boolean e()
  {
    if (Build.VERSION.SDK_INT >= 16)
    {
      int i = MediaCodecList.getCodecCount();
      for (int j = 0; j < i; j++)
      {
        MediaCodecInfo localMediaCodecInfo = MediaCodecList.getCodecInfoAt(j);
        if (!localMediaCodecInfo.isEncoder());
        for (String str : localMediaCodecInfo.getSupportedTypes())
          if ((str != null) && (str.toLowerCase(Locale.getDefault()).contains("hevc")))
          {
            Log.i("Sohuplayer", "检查设备，支持H265");
            return true;
          }
      }
    }
    Log.i("Sohuplayer", "检查设备，不支持H265");
    return false;
  }

  public void a(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    if (paramInt1 < 0)
      paramInt1 = 2;
    if (paramInt2 < 0)
      paramInt2 = 0;
    com.sohutv.tv.player.util.a.a.b = paramInt1;
    com.sohutv.tv.player.util.a.a.c = paramInt2;
    if (com.sohutv.tv.player.util.a.a.e == null)
    {
      Log.e("Sohuplayer", "播放信息为空：设置清晰度之前要先请求播放API");
      return;
    }
    Log.i("Sohuplayer", "设置清晰度代码 : " + paramInt1);
    this.e = a(com.sohutv.tv.player.util.a.a.e);
    this.e = c(this.e);
    if (paramBoolean)
    {
      if ((com.sohutv.tv.player.util.a.a.f.isFee()) && (com.sohutv.tv.player.util.a.a.g != null))
        this.e = (this.e + "&" + com.sohutv.tv.player.util.a.a.g.getAntileechkey());
      OutputLog.i("Sohuplayer", "setDefinitionAndPlay responseUrl=" + this.e);
      b();
      return;
    }
    if (this.g != null)
    {
      this.g.a(this.e);
      return;
    }
    Log.e("Sohuplayer", "ISohuOttAPI is null");
  }

  public void a(Context paramContext, String paramString, boolean paramBoolean)
  {
    if (this.b == null)
      Log.e("Sohuplayer", "parseJsonSohuPlayInfo() mPlayer = null");
    do
    {
      return;
      this.a = paramContext;
    }
    while (!b(paramString));
    a(paramBoolean);
  }

  public void a(com.sohutv.tv.player.ad.c paramc)
  {
    this.f = paramc;
  }

  public void a(ISohuOttPlayer paramISohuOttPlayer)
  {
    this.b = paramISohuOttPlayer;
  }

  public void a(g.a parama)
  {
    this.g = parama;
    h.a().a(parama);
  }

  public void a(String paramString)
  {
    try
    {
      if ((this.b != null) && (!com.sohutv.tv.player.util.c.a.a(paramString)))
      {
        this.b.setSVideoPath(paramString);
        Log.i("Sohuplayer", "preparePlayAD");
      }
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public void b()
  {
    try
    {
      if ((this.b != null) && (!com.sohutv.tv.player.util.c.a.a(this.e)))
      {
        if (com.sohutv.tv.player.util.a.a.f != null)
          com.sohutv.tv.player.util.a.a.f.setCurrentUrl(this.e);
        OutputLog.i("Sohuplayer", "preparePlayContent() responseUrl=" + this.e);
        this.b.setSVideoPath(this.e);
        SohuTVAdvertise.b = SohuTVAdvertise.PlaybackState.INVIDEO;
        Log.i("Sohuplayer", "preparePlayContent");
      }
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohutv.tv.player.util.i
 * JD-Core Version:    0.6.2
 */