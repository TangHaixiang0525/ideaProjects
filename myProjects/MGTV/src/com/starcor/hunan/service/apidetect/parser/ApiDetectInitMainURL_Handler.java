package com.starcor.hunan.service.apidetect.parser;

import android.text.TextUtils;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.service.apidetect.data.ApiDetectAppInfo;
import com.starcor.hunan.service.apidetect.data.ApiDetectGlobalEnv;
import com.starcor.hunan.service.apidetect.data.ApiDetectGlobalLogic;
import com.starcor.hunan.service.apidetect.data.ApiDetectMgtvUrl;
import com.starcor.hunan.service.apidetect.data.ApiDetectWebUrlBuilder;
import java.util.HashMap;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ApiDetectInitMainURL_Handler extends DefaultHandler
{
  private String group;
  String key;
  String str;
  private String tempValue;
  private String tempkey;

  public void characters(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    throws SAXException
  {
    if (this.str != null);
    for (this.str += new String(paramArrayOfChar, paramInt1, paramInt2); ; this.str = new String(paramArrayOfChar, paramInt1, paramInt2))
    {
      Logger.d(" ---> " + this.str);
      return;
    }
  }

  public void endElement(String paramString1, String paramString2, String paramString3)
    throws SAXException
  {
    if (paramString2.equalsIgnoreCase("key"))
      if (!TextUtils.isEmpty(this.str))
      {
        this.key = this.str.trim().toLowerCase();
        if (this.tempkey != null)
        {
          if (this.tempkey.equalsIgnoreCase("a"))
            this.tempValue = this.key;
          if (this.tempkey.equalsIgnoreCase("encrypt"))
            this.tempValue = this.key;
        }
      }
    while (true)
    {
      this.str = null;
      return;
      if (paramString2.equalsIgnoreCase("value"))
      {
        if (this.tempkey != null)
        {
          if (this.tempkey.equalsIgnoreCase("a"))
          {
            ApiDetectAppInfo.cdnUrl.put(this.tempValue, this.str.trim());
            this.group = null;
            this.tempkey = "b";
            this.tempValue = null;
          }
          if (this.tempkey.equalsIgnoreCase("encrypt"))
          {
            this.group = null;
            this.tempkey = "c";
            this.tempValue = null;
          }
        }
        if (this.key.equalsIgnoreCase("login"))
          ApiDetectWebUrlBuilder.setLoginUrl(this.str);
        else if (this.key.equalsIgnoreCase("test_speed"))
          ApiDetectWebUrlBuilder.setSpeedUrl(this.str);
        else if (this.key.equalsIgnoreCase("test_speed_callback"))
          ApiDetectWebUrlBuilder.setSpeedCallbackUrl(this.str);
        else if (this.key.equalsIgnoreCase("check_net_state_url"))
          ApiDetectWebUrlBuilder.setCheckNetStateUrl(this.str);
        else if (this.key.equalsIgnoreCase("msgboard_data_url"))
          ApiDetectWebUrlBuilder.setMessageBoardDataUrl(this.str);
        else if (this.key.equalsIgnoreCase("msgboard_send_respond_url"))
          ApiDetectWebUrlBuilder.setMsgBoardSendRespondUrl(this.str);
        else if (this.key.equalsIgnoreCase("sysmsg_url"))
          ApiDetectWebUrlBuilder.setSystemMessageUrl(this.str);
        else if (this.key.equalsIgnoreCase("sysmsg_url_beta"))
          ApiDetectWebUrlBuilder.setSystemMessageUrl_beta(this.str);
        else if (this.key.equalsIgnoreCase("speed_server_info_url"))
          ApiDetectWebUrlBuilder.setSpeedServerInfoUrl(this.str);
        else if (this.key.equalsIgnoreCase("speed_callback_v2_url"))
          ApiDetectWebUrlBuilder.setSpeedCallbackUrlV2(this.str);
        else if (this.key.equalsIgnoreCase("speed_test_v2_url"))
          ApiDetectWebUrlBuilder.setSpeedUrlV2(this.str);
        else if (this.key.equalsIgnoreCase("new_report_url"))
          ApiDetectWebUrlBuilder.setReportUrl(this.str);
        else if (this.key.equalsIgnoreCase("main_page_refresh_time"))
          ApiDetectWebUrlBuilder.setMainPageRefreshTime(Long.parseLong(this.str));
        else if (this.key.equalsIgnoreCase("report_terminal_status_v2_url"))
          ApiDetectMgtvUrl.setReportTerminalStatus(this.str);
        else if (this.key.equalsIgnoreCase("report_terminal_log_secret_prefix"))
          ApiDetectGlobalLogic.getInstance().setLogSecretPrefix(this.str);
        else if (this.key.equalsIgnoreCase("reserve_notice_delay"))
          try
          {
            k = Integer.valueOf(this.str).intValue();
            Logger.i("SAX", "reserve_notice_delay:" + k);
            ApiDetectGlobalEnv.getInstance().setReservationDelayNotifyTime(k);
          }
          catch (NumberFormatException localNumberFormatException3)
          {
            while (true)
            {
              int k = 300;
              Logger.e("SAX", "reserve_notice_delay:" + k);
            }
          }
        else if (this.key.equalsIgnoreCase("terminal_get_catch_time_delay"))
          try
          {
            j = Integer.valueOf(this.str).intValue();
            Logger.i("SAX", "terminal_get_catch_time_delayTime:" + j);
            ApiDetectGlobalEnv.getInstance().setTerminalGetCatchTimeDelayTime(j);
          }
          catch (NumberFormatException localNumberFormatException2)
          {
            while (true)
            {
              int j = 3600000;
              localNumberFormatException2.printStackTrace();
            }
          }
        else if (this.key.equalsIgnoreCase("free_play_time_percent"))
          try
          {
            i = Integer.valueOf(this.str).intValue();
            Logger.i("SAX", "free_play_time_percent:" + i);
            ApiDetectGlobalEnv.getInstance().setFreePlaTimePercent(i);
          }
          catch (NumberFormatException localNumberFormatException1)
          {
            while (true)
            {
              Logger.e("SAX", "free_play_time_percent:" + 0);
              int i = 0;
            }
          }
        else if (this.key.equalsIgnoreCase("server_telephone"))
        {
          if (!TextUtils.isEmpty(this.str))
            ApiDetectGlobalEnv.getInstance().setPhoneNumer(this.str.trim());
        }
        else if (this.key.equalsIgnoreCase("weixin_control_disable"))
        {
          if ("1".equals(this.str))
          {
            Logger.i("SAX", "AirControl disabled!!!");
            com.starcor.hunan.service.apidetect.data.ApiDetectAppFuncCfg.FUNCTION_ENABLE_AIR_CONTROL = false;
          }
          else if ("0".equals(this.str))
          {
            Logger.i("SAX", "AirControl enabled!!!");
            com.starcor.hunan.service.apidetect.data.ApiDetectAppFuncCfg.FUNCTION_ENABLE_AIR_CONTROL = true;
          }
        }
        else if (this.key.equalsIgnoreCase("m3u8_live_enable"))
        {
          if ("1".equals(this.str))
          {
            Logger.i("SAX", "m3u8_live_enable enable!");
            com.starcor.hunan.service.apidetect.data.ApiDetectAppFuncCfg.FUNCTION_LIVE_ENABLE_M3U8 = true;
          }
          else if ("0".equals(this.str))
          {
            Logger.i("SAX", "m3u8_live_enable disable!");
            com.starcor.hunan.service.apidetect.data.ApiDetectAppFuncCfg.FUNCTION_LIVE_ENABLE_M3U8 = false;
          }
        }
        else if (this.key.equalsIgnoreCase("m3u8_tstv_enable"))
        {
          if ("1".equals(this.str))
          {
            Logger.i("SAX", "m3u8_tstv_enable enable!");
            com.starcor.hunan.service.apidetect.data.ApiDetectAppFuncCfg.FUNCTION_TIMESHIFT_ENABLE_M3U8 = true;
          }
          else if ("0".equals(this.str))
          {
            Logger.i("SAX", "m3u8_tstv_enable disable!");
            com.starcor.hunan.service.apidetect.data.ApiDetectAppFuncCfg.FUNCTION_TIMESHIFT_ENABLE_M3U8 = false;
          }
        }
        else if (this.key.equalsIgnoreCase("m3u8_playbill_enable"))
        {
          if ("1".equals(this.str))
          {
            Logger.i("SAX", "m3u8_playbill_enable enable!");
            com.starcor.hunan.service.apidetect.data.ApiDetectAppFuncCfg.FUNCTION_PLAYBACK_ENABLE_M3U8 = true;
          }
          else if ("0".equals(this.str))
          {
            Logger.i("SAX", "m3u8_playbill_enable disable!");
            com.starcor.hunan.service.apidetect.data.ApiDetectAppFuncCfg.FUNCTION_PLAYBACK_ENABLE_M3U8 = false;
          }
        }
        else if (this.key.equalsIgnoreCase("m3u8_vod_enable"))
        {
          if ("1".equals(this.str))
          {
            Logger.i("SAX", "m3u8_vod_enable enable!");
            com.starcor.hunan.service.apidetect.data.ApiDetectAppFuncCfg.FUNCTION_VOD_ENABLE_M3U8 = true;
          }
          else if ("0".equals(this.str))
          {
            Logger.i("SAX", "m3u8_vod_enable disable!");
            com.starcor.hunan.service.apidetect.data.ApiDetectAppFuncCfg.FUNCTION_VOD_ENABLE_M3U8 = false;
          }
        }
        else if ("boot_ad_pos_id".equalsIgnoreCase(this.key))
        {
          if (!TextUtils.isEmpty(this.str))
            ApiDetectGlobalEnv.getInstance().setBootAdPosId(this.str.trim());
        }
        else if ("player_menu_ad_pos_id".equalsIgnoreCase(this.key))
        {
          if (!TextUtils.isEmpty(this.str))
            ApiDetectGlobalEnv.getInstance().setPlayerMenuAdPosId(this.str.trim());
        }
        else if ("asset_huawei_tid".equalsIgnoreCase(this.key))
        {
          if (!TextUtils.isEmpty(this.str))
            ApiDetectGlobalEnv.getInstance().setAssetHuaWeiTid(this.str.trim());
        }
        else if ("terminal_media_mode_value".equalsIgnoreCase(this.key))
        {
          if (!TextUtils.isEmpty(this.str))
            ApiDetectGlobalLogic.getInstance().setVideoQualityString(this.str);
        }
        else if ("terminal_player_certification".equalsIgnoreCase(this.key))
        {
          if (!TextUtils.isEmpty(this.str))
            if ("1".equals(this.str))
            {
              Logger.i("SAX", "terminal_player_certification enable!");
              com.starcor.hunan.service.apidetect.data.ApiDetectAppFuncCfg.FUNCTION_GUEST_CAN_PLAY_VIDEO = false;
            }
            else if ("0".equals(this.str))
            {
              Logger.i("SAX", "terminal_player_certification disable!");
              com.starcor.hunan.service.apidetect.data.ApiDetectAppFuncCfg.FUNCTION_GUEST_CAN_PLAY_VIDEO = true;
            }
        }
        else if ("terminal_weixin_pay_url".equalsIgnoreCase(this.key))
        {
          if (!TextUtils.isEmpty(this.str))
            ApiDetectMgtvUrl.setWechatUrl(this.str);
        }
        else if (this.key.equalsIgnoreCase("terminal_search_index_assists"))
        {
          if (!TextUtils.isEmpty(this.str))
            ApiDetectGlobalLogic.getInstance().setSearchStarPackageId(this.str);
        }
        else if (this.key.equalsIgnoreCase("terminal_ad_api_url"))
        {
          if (!TextUtils.isEmpty(this.str))
            ApiDetectWebUrlBuilder.setTerminalAdUrl(this.str);
        }
        else if (this.key.equalsIgnoreCase("terminal_ad_api_params_pos_id"))
        {
          if (!TextUtils.isEmpty(this.str))
            ApiDetectGlobalLogic.getInstance().setPlayerAdId(this.str);
        }
        else if (this.key.equalsIgnoreCase("terminal_ad_api_big_data_url"))
        {
          if (!TextUtils.isEmpty(this.str))
            ApiDetectWebUrlBuilder.setAdInfoReportErrorUrl(this.str);
        }
        else if (this.key.equalsIgnoreCase("terminal_channel_select_icon_show"))
        {
          if ("0".equals(this.str))
            com.starcor.hunan.service.apidetect.data.ApiDetectAppFuncCfg.FUNCTION_TV_SELECT_SHOW_ICON = false;
          else if ("1".equals(this.str))
            com.starcor.hunan.service.apidetect.data.ApiDetectAppFuncCfg.FUNCTION_TV_SELECT_SHOW_ICON = true;
          else
            com.starcor.hunan.service.apidetect.data.ApiDetectAppFuncCfg.FUNCTION_TV_SELECT_SHOW_ICON = false;
        }
        else if (this.key.equalsIgnoreCase("sysmsg_http_auth_url"))
          ApiDetectWebUrlBuilder.setMessageSystemV2HttpUrl(this.str);
        else if (this.key.equalsIgnoreCase("sysmsg_tcp_receive_url"))
          ApiDetectWebUrlBuilder.setMessageSystemV2TCPUrl(this.str);
      }
    }
  }

  public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
    throws SAXException
  {
    if (paramString2.equalsIgnoreCase("n2_a"))
    {
      ApiDetectAppInfo.URL_n2_a = paramAttributes.getValue("url");
      ApiDetectAppInfo.url.put(paramString2.toLowerCase(), paramAttributes.getValue("url"));
    }
    do
    {
      do
      {
        return;
        if (paramString2.equalsIgnoreCase("n3_a_2"))
        {
          ApiDetectAppInfo.URL_n3_a = paramAttributes.getValue("url");
          Logger.i("", "http://cs.interface.hifuntv.com/mgtv/EPGindex=" + ApiDetectAppInfo.URL_n3_a);
          ApiDetectAppInfo.url.put(paramString2.toLowerCase(), paramAttributes.getValue("url"));
          return;
        }
        if (paramString2.equalsIgnoreCase("n7_a"))
        {
          ApiDetectAppInfo.URL_n7_a = paramAttributes.getValue("url");
          ApiDetectAppInfo.url.put(paramString2.toLowerCase(), paramAttributes.getValue("url"));
          return;
        }
        if (paramString2.equalsIgnoreCase("n100_a"))
        {
          ApiDetectAppInfo.URL_n100 = paramAttributes.getValue("url");
          ApiDetectAppInfo.url.put(paramString2.toLowerCase(), paramAttributes.getValue("url"));
          return;
        }
        if (paramString2.equalsIgnoreCase("n200_a"))
        {
          ApiDetectAppInfo.URL_n200 = paramAttributes.getValue("url");
          ApiDetectAppInfo.url.put(paramString2.toLowerCase(), paramAttributes.getValue("url"));
          return;
        }
        if (paramString2.equalsIgnoreCase("n208_a"))
        {
          ApiDetectAppInfo.URL_n208 = paramAttributes.getValue("url");
          ApiDetectAppInfo.url.put(paramString2.toLowerCase(), paramAttributes.getValue("url"));
          return;
        }
        if (paramString2.equalsIgnoreCase("n21_a"))
        {
          ApiDetectAppInfo.URL_n21_a = paramAttributes.getValue("url");
          String str5 = paramAttributes.getValue("url");
          ApiDetectAppInfo.URL_n21_a = str5;
          Logger.i("热词搜索接口", str5);
          return;
        }
        if (paramString2.equalsIgnoreCase("n22_a"))
        {
          ApiDetectAppInfo.URL_n22_a = paramAttributes.getValue("url");
          String str4 = paramAttributes.getValue("url");
          ApiDetectAppInfo.URL_n22_a = str4;
          Logger.i("升级主接口", str4);
          return;
        }
        if (paramString2.equalsIgnoreCase("n23_a"))
        {
          ApiDetectAppInfo.URL_n23_a = paramAttributes.getValue("url");
          String str3 = paramAttributes.getValue("url");
          ApiDetectAppInfo.URL_n23_a = str3;
          Logger.i("常见问题主接口", str3);
          return;
        }
        if (paramString2.equalsIgnoreCase("n24_a"))
        {
          ApiDetectAppInfo.URL_n24_a = paramAttributes.getValue("url");
          String str2 = paramAttributes.getValue("url");
          ApiDetectAppInfo.URL_n24_a = str2;
          Logger.i("专题功能主接口", str2);
          return;
        }
        if (paramString2.equalsIgnoreCase("n26_a"))
        {
          ApiDetectAppInfo.URL_n26_a = paramAttributes.getValue("url");
          String str1 = paramAttributes.getValue("url");
          ApiDetectAppInfo.URL_n26_a = str1;
          Logger.i("测速功能主接口", str1);
          return;
        }
        if (paramString2.equalsIgnoreCase("n31_a"))
        {
          ApiDetectAppInfo.URL_n31_a = paramAttributes.getValue("url");
          Logger.i("微信上报接口", ApiDetectAppInfo.URL_n31_a);
          return;
        }
        if (paramString2.equalsIgnoreCase("n36_a"))
        {
          ApiDetectAppInfo.URL_n36_a = paramAttributes.getValue("url");
          Logger.i("皮肤接口", ApiDetectAppInfo.URL_n36_a);
          return;
        }
        if (paramString2.equalsIgnoreCase("n38_a"))
        {
          ApiDetectAppInfo.URL_n38_a = paramAttributes.getValue("url");
          Logger.i("UI布局接口", ApiDetectAppInfo.URL_n38_a);
          return;
        }
        if (paramString2.equalsIgnoreCase("n39_a"))
        {
          ApiDetectAppInfo.URL_n39_a = paramAttributes.getValue("url");
          Logger.i("EPG 接口 V2", ApiDetectAppInfo.URL_n39_a);
          return;
        }
        if (paramString2.equalsIgnoreCase("n40_a"))
        {
          ApiDetectAppInfo.URL_n40_a = paramAttributes.getValue("url");
          Logger.i("URL加解密接口", ApiDetectAppInfo.URL_n40_a);
          return;
        }
        if (paramString2.equalsIgnoreCase("n41_a"))
        {
          ApiDetectAppInfo.URL_n41_a = paramAttributes.getValue("url");
          Logger.i("URL加解密接口", ApiDetectAppInfo.URL_n41_a);
          return;
        }
        if (paramString2.equalsIgnoreCase("n650_a"))
        {
          ApiDetectAppInfo.URL_n650_a = paramAttributes.getValue("url");
          Logger.i("app接口", ApiDetectAppInfo.URL_n650_a);
          return;
        }
        if (paramString2.equalsIgnoreCase("n50_a"))
        {
          ApiDetectAppInfo.URL_n50_a = paramAttributes.getValue("url");
          Logger.i("app接口", ApiDetectAppInfo.URL_n50_a);
          return;
        }
        if (paramString2.equalsIgnoreCase("n212_a"))
        {
          ApiDetectAppInfo.URL_n212_a = paramAttributes.getValue("url");
          Logger.i("新3A计费接口", ApiDetectAppInfo.URL_n212_a);
          return;
        }
        if (paramString2.equalsIgnoreCase("n212_b"))
        {
          ApiDetectAppInfo.URL_n212_b = paramAttributes.getValue("url");
          Logger.i("新3A用户中心接口", ApiDetectAppInfo.URL_n212_b);
          return;
        }
        if (paramString2.equalsIgnoreCase("n40_a"))
        {
          ApiDetectAppInfo.URL_n40_a = paramAttributes.getValue("url");
          Logger.i("EPG 接口 V2 URL_n40_a", ApiDetectAppInfo.URL_n40_a);
          return;
        }
      }
      while ((!paramString2.equalsIgnoreCase("key")) || (paramAttributes.getValue("group") == null));
      this.group = paramAttributes.getValue("group").trim();
      if ((!TextUtils.isEmpty(this.group)) && ("CDN".equals(this.group)))
        this.tempkey = "a";
    }
    while ((TextUtils.isEmpty(this.group)) || (!"encrypt".equals(this.group)));
    this.tempkey = "encrypt";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.service.apidetect.parser.ApiDetectInitMainURL_Handler
 * JD-Core Version:    0.6.2
 */