package com.starcor.core.epgapi.params;

import com.starcor.config.DeviceInfo;
import com.starcor.core.domain.AppInfo;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.StringParams;
import com.starcor.core.logic.GlobalEnv;
import com.starcor.core.logic.GlobalLogic;

public class GetUserAuthV2Params extends Api
{
  private StringParams begin;
  private StringParams begin_time;
  private StringParams caps;
  private StringParams category_id;
  private StringParams clt_data;
  private StringParams day;
  private StringParams devices_id;
  private StringParams mac_id;
  private StringParams media_assets_id;
  private StringParams media_id;
  private StringParams quality;
  private StringParams smart_card_id;
  private StringParams tag;
  private StringParams time_len;
  private StringParams user_agent;
  private StringParams user_id;
  private StringParams version;
  private StringParams video_id;
  private StringParams video_index;
  private StringParams video_type;
  private StringParams webtoken;

  public GetUserAuthV2Params(String paramString1, String paramString2)
  {
    this(paramString1, paramString2, "");
  }

  public GetUserAuthV2Params(String paramString1, String paramString2, String paramString3)
  {
    this.taksCategory = 66;
    this.prefix = AppInfo.URL_n50_a;
    this.nns_func.setValue("auth_play");
    this.user_id = new StringParams("nns_user_id");
    this.user_id.setValue(GlobalLogic.getInstance().getUserId());
    this.devices_id = new StringParams("nns_device_id");
    this.devices_id.setValue(GlobalEnv.getInstance().getAAALicense());
    this.smart_card_id = new StringParams("nns_smart_card_id");
    this.smart_card_id.setValue(GlobalLogic.getInstance().getSmartCardId());
    this.version = new StringParams("nns_version");
    this.version.setValue(DeviceInfo.getMGTVVersion());
    this.mac_id = new StringParams("nns_mac_id");
    this.mac_id.setValue(DeviceInfo.getMac());
    this.video_id = new StringParams("nns_video_id");
    this.video_id.setValue(paramString1);
    this.video_type = new StringParams("nns_video_type");
    this.video_type.setValue(paramString2);
    this.video_index = new StringParams("nns_video_index");
    this.video_index.setValue("0");
    this.webtoken = new StringParams("nns_webtoken");
    this.webtoken.setValue(GlobalLogic.getInstance().getWebToken());
    this.media_id = new StringParams("nns_media_id");
    this.media_id.setValue("");
    this.media_assets_id = new StringParams("nns_media_assets_id");
    this.media_assets_id.setValue("");
    this.category_id = new StringParams("nns_category_id");
    this.category_id.setValue("");
    this.caps = new StringParams("nns_caps");
    this.caps.setValue("");
    this.day = new StringParams("nns_day");
    this.day.setValue("");
    this.begin = new StringParams("nns_begin");
    this.begin.setValue("");
    this.time_len = new StringParams("nns_time_len");
    this.time_len.setValue("");
    this.begin_time = new StringParams("nns_begin_time");
    this.begin_time.setValue("");
    this.quality = new StringParams("nns_quality");
    this.quality.setValue(paramString3);
    this.user_agent = new StringParams("nns_user_agent");
    this.user_agent.setValue("");
    this.clt_data = new StringParams("nns_clt_data");
    this.clt_data.setValue("");
    this.tag = new StringParams("nns_tag");
    this.tag.setValue("");
    this.cacheValidTime = 0L;
  }

  public String getApiName()
  {
    return "N50_A_1";
  }

  public StringParams getMediaIdParams()
  {
    return this.media_id;
  }

  public StringParams getQualityParams()
  {
    return this.quality;
  }

  public StringParams getVideoIndexParams()
  {
    return this.video_index;
  }

  public void setQuality(String paramString)
  {
    if (this.quality == null)
      this.quality = new StringParams("nns_quality");
    this.quality.setValue(paramString);
  }

  public String toString()
  {
    String str = this.prefix;
    if (!str.contains("?"))
      str = str + "?";
    return str + this.nns_func + this.user_id + this.devices_id + this.smart_card_id + this.version + this.mac_id + this.webtoken + this.video_id + this.video_type + this.video_index + this.media_id + this.media_assets_id + this.category_id + this.caps + this.day + this.begin_time + this.begin + this.time_len + this.quality + this.clt_data + this.tag + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.GetUserAuthV2Params
 * JD-Core Version:    0.6.2
 */