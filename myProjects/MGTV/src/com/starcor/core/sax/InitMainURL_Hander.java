package com.starcor.core.sax;

import android.text.TextUtils;
import com.starcor.config.DeviceInfo;
import com.starcor.config.MgtvUrl;
import com.starcor.core.domain.AppInfo;
import com.starcor.core.epgapi.params.UserFeedbackParams;
import com.starcor.core.logic.GlobalEnv;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.utils.Logger;
import com.starcor.mgtv.boss.WebUrlBuilder;
import java.util.HashMap;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class InitMainURL_Hander extends DefaultHandler
{
  private String group;
  String key;
  String str;
  private String tempValue;
  private String tempkey;

  public void characters(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    throws SAXException
  {
    if (this.str != null)
    {
      this.str += new String(paramArrayOfChar, paramInt1, paramInt2);
      return;
    }
    this.str = new String(paramArrayOfChar, paramInt1, paramInt2);
  }

  public void endDocument()
    throws SAXException
  {
    super.endDocument();
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
          if (this.tempkey.equalsIgnoreCase("API_HOST"))
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
            AppInfo.cdnUrl.put(this.tempValue, this.str.trim());
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
          if (this.tempkey.contains("API_HOST"))
          {
            this.group = null;
            AppInfo.domainUrl.put(this.tempValue, this.str.trim());
            this.tempValue = null;
            this.tempkey = "";
          }
        }
        if (this.key.equalsIgnoreCase("login"))
          WebUrlBuilder.setLoginUrl(this.str);
        else if (this.key.equalsIgnoreCase("test_speed"))
          WebUrlBuilder.setSpeedUrl(this.str);
        else if (this.key.equalsIgnoreCase("test_speed_callback"))
          WebUrlBuilder.setSpeedCallbackUrl(this.str);
        else if (this.key.equalsIgnoreCase("check_net_state_url"))
          WebUrlBuilder.setCheckNetStateUrl(this.str);
        else if (this.key.equalsIgnoreCase("msgboard_data_url"))
          WebUrlBuilder.setMessageBoardDataUrl(this.str);
        else if (this.key.equalsIgnoreCase("msgboard_send_respond_url"))
          WebUrlBuilder.setMsgBoardSendRespondUrl(this.str);
        else if (this.key.equalsIgnoreCase("sysmsg_url"))
          WebUrlBuilder.setSystemMessageUrl(this.str);
        else if (this.key.equalsIgnoreCase("sysmsg_url_beta"))
          WebUrlBuilder.setSystemMessageUrl_beta(this.str);
        else if (this.key.equalsIgnoreCase("speed_server_info_url"))
          WebUrlBuilder.setSpeedServerInfoUrl(this.str);
        else if (this.key.equalsIgnoreCase("speed_callback_v2_url"))
          WebUrlBuilder.setSpeedCallbackUrlV2(this.str);
        else if (this.key.equalsIgnoreCase("speed_test_v2_url"))
          WebUrlBuilder.setSpeedUrlV2(this.str);
        else if (!this.key.equalsIgnoreCase("report_terminal_data_url"))
          if (this.key.equalsIgnoreCase("main_page_refresh_time"))
          {
            WebUrlBuilder.setMainPageRefreshTime(Long.parseLong(this.str));
          }
          else if (this.key.equalsIgnoreCase("report_terminal_status_v2_url"))
          {
            MgtvUrl.setReportTerminalStatus(this.str);
          }
          else if (this.key.equalsIgnoreCase("terminal_user_feedback_url"))
          {
            UserFeedbackParams.setUserFeedbackReportUrl(this.str);
          }
          else if (this.key.equalsIgnoreCase("report_terminal_log_secret_prefix"))
          {
            GlobalLogic.getInstance().setLogSecretPrefix(this.str);
          }
          else if (this.key.equalsIgnoreCase("terminal_external_control_enabled"))
          {
            Logger.i("initMainURLHandler", "terminal_external_control_enabled value=" + this.str);
            if ("0".equals(this.str))
              com.starcor.config.AppFuncCfg.FUNCTION_ENABLE_EXTERNAL_CONTROL = false;
            else if ("1".equals(this.str))
              com.starcor.config.AppFuncCfg.FUNCTION_ENABLE_EXTERNAL_CONTROL = true;
          }
          else if (this.key.equalsIgnoreCase("terminal_barrage_control_enabled"))
          {
            Logger.i("initMainURLHandler", "terminal_barrage_control_enabled value=" + this.str);
            if ("0".equals(this.str))
              com.starcor.config.AppFuncCfg.FUNCTION_ENABLE_BARRAGE_CONTROL = false;
            else if ("1".equals(this.str))
              com.starcor.config.AppFuncCfg.FUNCTION_ENABLE_BARRAGE_CONTROL = true;
          }
          else if (this.key.equalsIgnoreCase("reserve_notice_delay"))
          {
            try
            {
              int i5 = Integer.valueOf(this.str).intValue();
              i4 = i5;
              GlobalEnv.getInstance().setReservationDelayNotifyTime(i4);
            }
            catch (NumberFormatException localNumberFormatException8)
            {
              while (true)
                int i4 = 300;
            }
          }
          else if (this.key.equalsIgnoreCase("terminal_get_catch_time_delay"))
          {
            int i2 = 1800;
            try
            {
              int i3 = Integer.valueOf(this.str).intValue();
              if (i3 > i2)
                i2 = i3;
              GlobalEnv.getInstance().setTerminalGetCatchTimeDelayTime(i2);
            }
            catch (NumberFormatException localNumberFormatException7)
            {
              while (true)
              {
                i2 = 1800;
                localNumberFormatException7.printStackTrace();
              }
            }
          }
          else if (this.key.equalsIgnoreCase("free_play_time_percent"))
          {
            try
            {
              int i1 = Integer.valueOf(this.str).intValue();
              n = i1;
              GlobalEnv.getInstance().setFreePlaTimePercent(n);
            }
            catch (NumberFormatException localNumberFormatException6)
            {
              while (true)
                int n = 0;
            }
          }
          else if (this.key.equalsIgnoreCase("server_telephone"))
          {
            if (!TextUtils.isEmpty(this.str))
              GlobalEnv.getInstance().setPhoneNumer(this.str.trim());
          }
          else if (this.key.equalsIgnoreCase("weixin_control_disable"))
          {
            if ("1".equals(this.str))
              com.starcor.config.AppFuncCfg.FUNCTION_ENABLE_AIR_CONTROL = false;
            else if ("0".equals(this.str))
              com.starcor.config.AppFuncCfg.FUNCTION_ENABLE_AIR_CONTROL = true;
          }
          else if (this.key.equalsIgnoreCase("terminal_is_show_playbill_recom"))
          {
            if ("1".equals(this.str))
              com.starcor.config.AppFuncCfg.FUNCTION_SHOW_REPLAY_RECOMMEND = false;
            else if ("0".equals(this.str))
              com.starcor.config.AppFuncCfg.FUNCTION_SHOW_REPLAY_RECOMMEND = true;
          }
          else if (this.key.equalsIgnoreCase("m3u8_live_enable"))
          {
            if ("1".equals(this.str))
              com.starcor.config.AppFuncCfg.FUNCTION_LIVE_ENABLE_M3U8 = true;
            else if ("0".equals(this.str))
              com.starcor.config.AppFuncCfg.FUNCTION_LIVE_ENABLE_M3U8 = false;
          }
          else if (this.key.equalsIgnoreCase("m3u8_tstv_enable"))
          {
            if ("1".equals(this.str))
              com.starcor.config.AppFuncCfg.FUNCTION_TIMESHIFT_ENABLE_M3U8 = true;
            else if ("0".equals(this.str))
              com.starcor.config.AppFuncCfg.FUNCTION_TIMESHIFT_ENABLE_M3U8 = false;
          }
          else if (this.key.equalsIgnoreCase("m3u8_playbill_enable"))
          {
            if ("1".equals(this.str))
              com.starcor.config.AppFuncCfg.FUNCTION_PLAYBACK_ENABLE_M3U8 = true;
            else if ("0".equals(this.str))
              com.starcor.config.AppFuncCfg.FUNCTION_PLAYBACK_ENABLE_M3U8 = false;
          }
          else if (this.key.equalsIgnoreCase("m3u8_vod_enable"))
          {
            if ("1".equals(this.str))
              com.starcor.config.AppFuncCfg.FUNCTION_VOD_ENABLE_M3U8 = true;
            else if ("0".equals(this.str))
              com.starcor.config.AppFuncCfg.FUNCTION_VOD_ENABLE_M3U8 = false;
          }
          else if ("boot_ad_pos_id".equalsIgnoreCase(this.key))
          {
            if (!TextUtils.isEmpty(this.str))
              GlobalEnv.getInstance().setBootAdPosId(this.str.trim());
            Logger.d("开机广告id boot_ad_pos_id=" + this.str);
          }
          else if ("player_menu_ad_pos_id".equalsIgnoreCase(this.key))
          {
            if (!TextUtils.isEmpty(this.str))
              GlobalEnv.getInstance().setPlayerMenuAdPosId(this.str.trim());
          }
          else if ("asset_huawei_tid".equalsIgnoreCase(this.key))
          {
            if (!TextUtils.isEmpty(this.str))
              GlobalEnv.getInstance().setAssetHuaWeiTid(this.str.trim());
          }
          else if ("terminal_media_mode_value".equalsIgnoreCase(this.key))
          {
            if (!TextUtils.isEmpty(this.str))
              GlobalLogic.getInstance().setVideoQualityString(this.str);
          }
          else if ("terminal_support_try_play_disabled".equalsIgnoreCase(this.key))
          {
            if (!TextUtils.isEmpty(this.str))
            {
              if ("1".equals(this.str))
                com.starcor.config.AppFuncCfg.FUNCTION_ENABLE_TRY_LOOK = false;
              else
                com.starcor.config.AppFuncCfg.FUNCTION_ENABLE_TRY_LOOK = true;
            }
            else
              com.starcor.config.AppFuncCfg.FUNCTION_ENABLE_TRY_LOOK = true;
          }
          else if ("terminal_player_certification".equalsIgnoreCase(this.key))
          {
            if (!TextUtils.isEmpty(this.str))
              if ("1".equals(this.str))
                com.starcor.config.AppFuncCfg.FUNCTION_GUEST_CAN_PLAY_VIDEO = false;
              else if ("0".equals(this.str))
                com.starcor.config.AppFuncCfg.FUNCTION_GUEST_CAN_PLAY_VIDEO = true;
          }
          else if ("terminal_weixin_pay_url".equalsIgnoreCase(this.key))
          {
            if (!TextUtils.isEmpty(this.str))
              MgtvUrl.setWechatUrl(this.str);
          }
          else if (this.key.equalsIgnoreCase("terminal_search_index_assists"))
          {
            if (!TextUtils.isEmpty(this.str))
              GlobalLogic.getInstance().setSearchStarPackageId(this.str);
          }
          else
          {
            if (this.key.equalsIgnoreCase("terminal_ad_start_switch"))
            {
              if (!TextUtils.isEmpty(this.str))
                GlobalEnv.getInstance().setBootAdEnabled(this.str);
              while (true)
              {
                Logger.d("开机广告开关 terminal_ad_start_switch=" + this.str);
                break;
                GlobalEnv.getInstance().setBootAdEnabled("0");
              }
            }
            if (this.key.equalsIgnoreCase("terminal_ad_start_api_url"))
            {
              GlobalEnv.getInstance().setBootAdUrl(this.str);
              Logger.d("开机广告地址 terminal_ad_start_api_url=" + this.str);
            }
            else if (this.key.equalsIgnoreCase("terminal_ad_api_url"))
            {
              if (!TextUtils.isEmpty(this.str))
                WebUrlBuilder.setTerminalAdUrl(this.str);
            }
            else if (this.key.equalsIgnoreCase("terminal_ad_api_params_pos_id"))
            {
              if (!TextUtils.isEmpty(this.str))
                GlobalLogic.getInstance().setPlayerAdId(this.str);
            }
            else if (this.key.equalsIgnoreCase("terminal_ad_api_big_data_url"))
            {
              if (!TextUtils.isEmpty(this.str))
                WebUrlBuilder.setAdInfoReportErrorUrl(this.str);
            }
            else if (this.key.equalsIgnoreCase("terminal_channel_select_icon_show"))
            {
              if ("0".equals(this.str))
                com.starcor.config.AppFuncCfg.FUNCTION_TV_SELECT_SHOW_ICON = false;
              else if ("1".equals(this.str))
                com.starcor.config.AppFuncCfg.FUNCTION_TV_SELECT_SHOW_ICON = true;
              else
                com.starcor.config.AppFuncCfg.FUNCTION_TV_SELECT_SHOW_ICON = false;
            }
            else if (this.key.equalsIgnoreCase("sysmsg_http_auth_url"))
            {
              WebUrlBuilder.setMessageSystemV3HttpUrl(this.str);
            }
            else if (this.key.equalsIgnoreCase("sysmsg_tcp_receive_url"))
            {
              WebUrlBuilder.setMessageSystemV3TCPUrl(this.str);
            }
            else if (this.key.equalsIgnoreCase("report_user_data_to_umeng_enabled"))
            {
              if ("0".equals(this.str))
                com.starcor.config.AppFuncCfg.FUNCTION_USE_UMENG_REPORT = false;
              else if ("1".equals(this.str))
                com.starcor.config.AppFuncCfg.FUNCTION_USE_UMENG_REPORT = true;
              else
                com.starcor.config.AppFuncCfg.FUNCTION_USE_UMENG_REPORT = false;
            }
            else
            {
              if (this.key.equalsIgnoreCase("report_the_data_sampling_rate"))
                while (true)
                {
                  double d;
                  try
                  {
                    d = Double.valueOf(this.str).doubleValue();
                    if (d >= 0.0D)
                      break label2057;
                    d = 0.0D;
                    GlobalEnv.getInstance().setGsSamplingRate(d);
                  }
                  catch (NumberFormatException localNumberFormatException5)
                  {
                  }
                  break;
                  label2057: if (d > 1.0D)
                    d = 1.0D;
                }
              if (this.key.equalsIgnoreCase("terminal_player_proxy_enable"))
              {
                if ("0".equals(this.str))
                  com.starcor.config.AppFuncCfg.FUNCTION_OTTTV_PROXY = false;
                else if ("1".equals(this.str))
                  com.starcor.config.AppFuncCfg.FUNCTION_OTTTV_PROXY = true;
              }
              else
              {
                if ("terminal_detail_page_open_type_by_outer".equalsIgnoreCase(this.key))
                {
                  if ("1".equals(this.str))
                    com.starcor.config.AppFuncCfg.FUNCTION_ENABLE_FLOATING_DETAIL_PAGE = false;
                  while (true)
                  {
                    if (DeviceInfo.isXiaoMi())
                    {
                      com.starcor.config.AppFuncCfg.FUNCTION_ENABLE_FLOATING_DETAIL_PAGE = false;
                      Logger.d("xiaomi FUNCTION_ENABLE_FLOATING_DETAIL_PAGE = false");
                    }
                    Logger.d("后台下发详情配置清单 = " + this.str);
                    break;
                    if ("2".equals(this.str))
                      com.starcor.config.AppFuncCfg.FUNCTION_ENABLE_FLOATING_DETAIL_PAGE = true;
                  }
                }
                if ("stb_alarm_clock_ad".equalsIgnoreCase(this.key))
                {
                  if (!TextUtils.isEmpty(this.str))
                    GlobalEnv.getInstance().setClockAdId(this.str);
                  Logger.d("时钟广告id = " + this.str);
                }
                else if (this.key.equalsIgnoreCase("N40_E_1_RF_TIME"))
                {
                  try
                  {
                    int m = Integer.valueOf(this.str).intValue();
                    k = m;
                    GlobalEnv.getInstance().setMessageTimeInterval(k);
                  }
                  catch (NumberFormatException localNumberFormatException4)
                  {
                    while (true)
                      int k = 0;
                  }
                }
                else
                {
                  if ("report_video_hot_data_enabled".equalsIgnoreCase(this.key))
                  {
                    if ("0".equals(this.str))
                      com.starcor.config.AppFuncCfg.FUNCTION_ENABEL_REPORT_USER_BEHAVIOR = false;
                    while (true)
                    {
                      Logger.d("上报用户影片播放热度 = " + this.str);
                      break;
                      if ("1".equals(this.str))
                        com.starcor.config.AppFuncCfg.FUNCTION_ENABEL_REPORT_USER_BEHAVIOR = true;
                    }
                  }
                  if ("terminal_std_p2p_enabled".equalsIgnoreCase(this.key))
                  {
                    Logger.i("p2p", "terminal_std_p2p_enabled=" + this.str);
                    if ("0".equals(this.str))
                      com.starcor.config.AppFuncCfg.FUNCTION_OTTTV_P2P = false;
                    while (true)
                    {
                      Logger.d("是否启用P2P加速视频 = " + this.str);
                      break;
                      if ("1".equals(this.str))
                        com.starcor.config.AppFuncCfg.FUNCTION_OTTTV_P2P = true;
                    }
                  }
                  if ("terminal_std_p2p_support_min_memary_size".equalsIgnoreCase(this.key))
                  {
                    int j = Integer.valueOf(this.str).intValue();
                    if (j > 0)
                      GlobalEnv.getInstance().setP2PMinValue(j);
                    Logger.d("P2P配置p2p min内存 = " + this.str + ",temp=" + j);
                  }
                  else if ("terminal_std_p2p_local_cache_max_size".equalsIgnoreCase(this.key))
                  {
                    int i = Integer.valueOf(this.str).intValue();
                    if (i > 0)
                      GlobalEnv.getInstance().setP2PMaxValue(i);
                    Logger.d("P2P配置p2p max内存 = " + this.str + ",temp=" + i);
                  }
                  else if ("report_std_p2p_data_report_url".equalsIgnoreCase(this.key))
                  {
                    WebUrlBuilder.setP2PCmsUrl(this.str);
                    Logger.d("P2P配置p2p url内存 = " + this.str);
                  }
                  else if ("report_new_data_url".equalsIgnoreCase(this.key))
                  {
                    WebUrlBuilder.setReportUrl(this.str);
                    Logger.d("NewReportUrl = " + this.str);
                  }
                  else if ("report_live_data_report_url".equalsIgnoreCase(this.key))
                  {
                    WebUrlBuilder.setLiveReportUrl(this.str);
                    Logger.d("LiveReportUrl = " + this.str);
                  }
                  else
                  {
                    if ("sysmsg_enable".equalsIgnoreCase(this.key))
                    {
                      if ("0".equals(this.str))
                        com.starcor.config.AppFuncCfg.FUNCTION_MESSAGE_SYSTEM_BUTTON = false;
                      while (true)
                      {
                        Logger.d("消息系统开关  " + this.str);
                        break;
                        if ("1".equals(this.str))
                          com.starcor.config.AppFuncCfg.FUNCTION_MESSAGE_SYSTEM_BUTTON = true;
                      }
                    }
                    if ("terminal_external_into_player_enabled".equalsIgnoreCase(this.key))
                    {
                      if (!TextUtils.isEmpty(this.str))
                        GlobalLogic.getInstance().saveIsEnableIntoPlayerFlag(this.str);
                      Logger.d("允许外部进入播放= " + this.str);
                    }
                    else if ("terminal_continue_play_enabled".equalsIgnoreCase(this.key))
                    {
                      if (!TextUtils.isEmpty(this.str))
                      {
                        GlobalLogic.getInstance().saveEnableMediasConPlay(this.str);
                        Logger.d("媒资间连播开关 " + this.str);
                      }
                    }
                    else if ("terminal_request_barrage_url".equalsIgnoreCase(this.key))
                    {
                      if (!TextUtils.isEmpty(this.str))
                        GlobalLogic.getInstance().setReqBarrageDataUrl(this.str);
                      Logger.d("拉取弹幕URL " + this.str);
                    }
                    else if ("report_play_cdn_error_interface1_url".equalsIgnoreCase(this.key))
                    {
                      if (!TextUtils.isEmpty(this.str))
                        GlobalLogic.getInstance().setCDNReportIF1Url(this.str);
                      Logger.d("CDNReportIF1 URL: " + this.str);
                    }
                    else if ("report_play_cdn_error_interface2_url".equalsIgnoreCase(this.key))
                    {
                      if (!TextUtils.isEmpty(this.str))
                        GlobalLogic.getInstance().setCDNReportIF2Url(this.str);
                      Logger.d("CDNReportIF2 URL: " + this.str);
                    }
                    else if ("terminal_vod_apply_play_retry_max_num".equals(this.key))
                    {
                      if (!TextUtils.isEmpty(this.str))
                        try
                        {
                          GlobalEnv.getInstance().setVodRetryCount(Integer.valueOf(this.str).intValue());
                          Logger.e("点播取串retry的最大次数控制=" + this.str);
                        }
                        catch (NumberFormatException localNumberFormatException3)
                        {
                          while (true)
                            Logger.e("点播取串retry的最大次数控制失败" + this.str);
                        }
                    }
                    else if ("terminal_live_apply_play_retry_max_num".equals(this.key))
                    {
                      if (!TextUtils.isEmpty(this.str))
                        try
                        {
                          GlobalEnv.getInstance().setTVRetryCount(Integer.valueOf(this.str).intValue());
                          Logger.e("直播取串retry的最大次数控制=" + this.str);
                        }
                        catch (NumberFormatException localNumberFormatException2)
                        {
                          while (true)
                            Logger.e("直播取串retry的最大次数控制失败" + this.str);
                        }
                    }
                    else if ("terminal_play_retry_max_num".equals(this.key))
                    {
                      if (!TextUtils.isEmpty(this.str))
                        try
                        {
                          GlobalEnv.getInstance().setPlayRetryCount(Integer.valueOf(this.str).intValue());
                          Logger.e("设置控制播流失败要重试次数=" + this.str);
                        }
                        catch (NumberFormatException localNumberFormatException1)
                        {
                          while (true)
                            Logger.e("设置控制播流失败要重试次数失败" + this.str);
                        }
                    }
                    else if ("report_pay_data_url".equals(this.key))
                    {
                      WebUrlBuilder.setPayReportCmsUrl(this.str);
                      Logger.i("支付数据上报地址：" + this.str);
                    }
                  }
                }
              }
            }
          }
      }
    }
  }

  public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
    throws SAXException
  {
    if (paramString2.equalsIgnoreCase("n2_a"))
    {
      AppInfo.URL_n2_a = paramAttributes.getValue("url");
      AppInfo.url.put(paramString2.toLowerCase(), paramAttributes.getValue("url"));
    }
    do
    {
      do
      {
        do
        {
          return;
          if (paramString2.equalsIgnoreCase("n3_a_2"))
          {
            AppInfo.URL_n3_a = paramAttributes.getValue("url");
            AppInfo.url.put(paramString2.toLowerCase(), paramAttributes.getValue("url"));
            return;
          }
          if (paramString2.equalsIgnoreCase("n7_a"))
          {
            AppInfo.URL_n7_a = paramAttributes.getValue("url");
            AppInfo.url.put(paramString2.toLowerCase(), paramAttributes.getValue("url"));
            return;
          }
          if (paramString2.equalsIgnoreCase("n100_a"))
          {
            AppInfo.URL_n100 = paramAttributes.getValue("url");
            AppInfo.url.put(paramString2.toLowerCase(), paramAttributes.getValue("url"));
            return;
          }
          if (paramString2.equalsIgnoreCase("n200_a"))
          {
            AppInfo.URL_n200 = paramAttributes.getValue("url");
            AppInfo.url.put(paramString2.toLowerCase(), paramAttributes.getValue("url"));
            return;
          }
          if (paramString2.equalsIgnoreCase("n208_a"))
          {
            AppInfo.URL_n208 = paramAttributes.getValue("url");
            AppInfo.url.put(paramString2.toLowerCase(), paramAttributes.getValue("url"));
            return;
          }
          if (paramString2.equalsIgnoreCase("n21_a"))
          {
            AppInfo.URL_n21_a = paramAttributes.getValue("url");
            return;
          }
          if (paramString2.equalsIgnoreCase("n22_a"))
          {
            AppInfo.URL_n22_a = paramAttributes.getValue("url");
            return;
          }
          if (paramString2.equalsIgnoreCase("n23_a"))
          {
            AppInfo.URL_n23_a = paramAttributes.getValue("url");
            return;
          }
          if (paramString2.equalsIgnoreCase("n24_a"))
          {
            AppInfo.URL_n24_a = paramAttributes.getValue("url");
            return;
          }
          if (paramString2.equalsIgnoreCase("n26_a"))
          {
            AppInfo.URL_n26_a = paramAttributes.getValue("url");
            return;
          }
          if (paramString2.equalsIgnoreCase("n31_a"))
          {
            AppInfo.URL_n31_a = paramAttributes.getValue("url");
            return;
          }
          if (paramString2.equalsIgnoreCase("n36_a"))
          {
            AppInfo.URL_n36_a = paramAttributes.getValue("url");
            return;
          }
          if (paramString2.equalsIgnoreCase("n38_a"))
          {
            AppInfo.URL_n38_a = paramAttributes.getValue("url");
            return;
          }
          if (paramString2.equalsIgnoreCase("n39_a"))
          {
            AppInfo.URL_n39_a = paramAttributes.getValue("url");
            return;
          }
          if (paramString2.equalsIgnoreCase("n40_a"))
          {
            AppInfo.URL_n40_a = paramAttributes.getValue("url");
            return;
          }
          if (paramString2.equalsIgnoreCase("n41_a"))
          {
            AppInfo.URL_n41_a = paramAttributes.getValue("url");
            return;
          }
          if (paramString2.equalsIgnoreCase("n40_d"))
          {
            AppInfo.URL_n40_d = paramAttributes.getValue("url");
            Logger.i("观众推荐接口", AppInfo.URL_n40_d);
            return;
          }
          if (paramString2.equalsIgnoreCase("n40_e"))
          {
            AppInfo.URL_n40_e = paramAttributes.getValue("url");
            Logger.i("消息系统接口", AppInfo.URL_n40_e);
            return;
          }
          if (paramString2.equalsIgnoreCase("n650_a"))
          {
            AppInfo.URL_n650_a = paramAttributes.getValue("url");
            return;
          }
          if (paramString2.equalsIgnoreCase("n50_a"))
          {
            AppInfo.URL_n50_a = paramAttributes.getValue("url");
            return;
          }
          if (paramString2.equalsIgnoreCase("n212_a"))
          {
            AppInfo.URL_n212_a = paramAttributes.getValue("url");
            return;
          }
          if (paramString2.equalsIgnoreCase("n212_b"))
          {
            AppInfo.URL_n212_b = paramAttributes.getValue("url");
            return;
          }
          if (paramString2.equalsIgnoreCase("n40_a"))
          {
            AppInfo.URL_n40_a = paramAttributes.getValue("url");
            return;
          }
          if (!paramString2.equalsIgnoreCase("key"))
            break;
        }
        while (paramAttributes.getValue("group") == null);
        this.group = paramAttributes.getValue("group").trim();
        if ((!TextUtils.isEmpty(this.group)) && ("CDN".equals(this.group)))
          this.tempkey = "a";
        if ((!TextUtils.isEmpty(this.group)) && ("encrypt".equals(this.group)))
          this.tempkey = "encrypt";
      }
      while ((TextUtils.isEmpty(this.group)) || (!"API_HOST".equals(this.group)));
      this.tempkey = "API_HOST";
      return;
      if (paramString2.equalsIgnoreCase("n40_f"))
      {
        AppInfo.URL_n40_f = paramAttributes.getValue("url");
        return;
      }
      if (paramString2.equalsIgnoreCase("n40_g"))
      {
        AppInfo.URL_n40_g = paramAttributes.getValue("url");
        return;
      }
      if (paramString2.equalsIgnoreCase("n40_h"))
      {
        AppInfo.URL_n40_h = paramAttributes.getValue("url");
        return;
      }
      if (paramString2.equalsIgnoreCase("n40_i"))
      {
        AppInfo.URL_n40_i = paramAttributes.getValue("url");
        return;
      }
    }
    while (!paramString2.equalsIgnoreCase("n40_k"));
    AppInfo.URL_n40_k = paramAttributes.getValue("url");
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.sax.InitMainURL_Hander
 * JD-Core Version:    0.6.2
 */