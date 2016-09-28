package com.starcor.core.epgapi.params;

import android.text.TextUtils;
import com.starcor.config.DeviceInfo;
import com.starcor.core.domain.AppInfo;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.IntegerParams;
import com.starcor.core.epgapi.StringParams;
import com.starcor.core.logic.GlobalEnv;
import com.starcor.core.logic.GlobalLogic;

public class GetPlayInfoV2Params extends Api
{
  private StringParams category_id;
  private StringParams device_id;
  private StringParams mac_id;
  private StringParams media_id;
  private StringParams nns_begin;
  private StringParams nns_begin_time;
  private StringParams nns_caps;
  private StringParams nns_carry;
  private StringParams nns_clt_data;
  private StringParams nns_day;
  private StringParams nns_huawei_cid;
  private StringParams nns_preview;
  private StringParams nns_quality;
  private StringParams nns_retry_time;
  private StringParams nns_tag;
  private StringParams nns_time_len;
  private StringParams nns_user_agent;
  private StringParams nns_user_id;
  private StringParams nns_video_index_id;
  private StringParams nns_video_preview_type;
  private StringParams package_id;
  private StringParams smart_card_id;
  private StringParams version;
  private StringParams video_id;
  private IntegerParams video_index;
  private IntegerParams video_type;
  private StringParams webtoken;

  public GetPlayInfoV2Params(String paramString1, int paramInt, String paramString2, String paramString3)
  {
    this(paramString1, paramInt, paramString2, paramString3, "", "", "", "");
  }

  public GetPlayInfoV2Params(String paramString1, int paramInt, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7)
  {
    this(paramString1, paramInt, "", paramString2, paramString3, paramString4, paramString5, paramString6, paramString7, "");
  }

  public GetPlayInfoV2Params(String paramString1, int paramInt, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9)
  {
    this.taksCategory = 7;
    this.prefix = AppInfo.URL_n50_a;
    this.nns_func.setValue("apply_play");
    this.device_id = new StringParams("nns_device_id");
    this.device_id.setValue(GlobalEnv.getInstance().getAAALicense());
    this.smart_card_id = new StringParams("nns_smart_card_id");
    this.smart_card_id.setValue(GlobalLogic.getInstance().getSmartCardId());
    this.version = new StringParams("nns_version");
    this.version.setValue(DeviceInfo.getMGTVVersion());
    this.mac_id = new StringParams("nns_mac_id");
    this.mac_id.setValue(DeviceInfo.getMac());
    this.webtoken = new StringParams("nns_webtoken");
    this.webtoken.setValue(GlobalLogic.getInstance().getWebToken());
    this.video_id = new StringParams("nns_video_id");
    this.video_id.setValue(paramString1);
    this.video_type = new IntegerParams("nns_video_type");
    this.video_type.setValue(paramInt);
    this.video_index = new IntegerParams("nns_video_index");
    this.video_index.setValue(0);
    this.media_id = new StringParams("nns_media_id");
    this.media_id.setValue(paramString3);
    this.package_id = new StringParams("nns_media_assets_id");
    this.category_id = new StringParams("nns_category_id");
    this.nns_carry = new StringParams("nns_carry");
    this.nns_caps = new StringParams("nns_caps");
    this.nns_caps.setValue(paramString9);
    this.nns_quality = new StringParams("nns_quality");
    this.nns_quality.setValue(paramString4);
    this.nns_day = new StringParams("nns_day");
    this.nns_day.setValue(paramString5);
    this.nns_begin = new StringParams("nns_begin");
    this.nns_begin.setValue(paramString6);
    this.nns_begin_time = new StringParams("nns_begin_time");
    this.nns_begin_time.setValue(paramString5 + paramString6);
    this.nns_time_len = new StringParams("nns_time_len");
    this.nns_time_len.setValue(paramString7);
    this.nns_clt_data = new StringParams("nns_clt_data");
    this.nns_tag = new StringParams("nns_tag");
    this.nns_retry_time = new StringParams("nns_retry_time");
    this.nns_user_id = new StringParams("nns_user_id");
    this.nns_user_id.setValue(GlobalLogic.getInstance().getUserId());
    this.nns_huawei_cid = new StringParams("nns_huawei_cid");
    this.nns_huawei_cid.setValue(paramString8);
    this.nns_preview = new StringParams("nns_preview");
    this.nns_preview.setValue(paramString2);
    this.nns_video_index_id = new StringParams("nns_video_index_id");
    this.nns_video_preview_type = new StringParams("nns_video_preview_type");
    this.cacheValidTime = 0L;
  }

  public String getApiName()
  {
    return "N50_A_2";
  }

  public StringParams getCategory_id()
  {
    return this.category_id;
  }

  public StringParams getClt_data()
  {
    return this.nns_clt_data;
  }

  public StringParams getNns_video_index_id()
  {
    return this.nns_video_index_id;
  }

  public StringParams getNns_video_preview_type()
  {
    return this.nns_video_preview_type;
  }

  public StringParams getPackage_id()
  {
    return this.package_id;
  }

  public StringParams getRetry_time()
  {
    return this.nns_retry_time;
  }

  public IntegerParams getVideo_index()
  {
    return this.video_index;
  }

  public String toString()
  {
    String str1 = this.prefix;
    if (str1 == null)
      return "";
    if (!str1.contains("?"))
      str1 = str1 + "?";
    if (TextUtils.isEmpty(this.nns_retry_time.getValue()))
      this.nns_retry_time.setValue("1");
    String str2 = str1 + this.nns_func + this.device_id + this.smart_card_id + this.version + this.mac_id + this.webtoken + this.video_id + this.video_type + this.video_index + this.media_id + this.package_id + this.category_id + this.nns_carry + this.nns_caps + this.nns_day + this.nns_begin + this.nns_begin_time + this.nns_time_len + this.nns_quality + this.nns_clt_data + this.nns_tag + this.nns_user_id + this.nns_preview + this.nns_retry_time + this.nns_video_index_id + this.nns_video_preview_type;
    return str2 + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.GetPlayInfoV2Params
 * JD-Core Version:    0.6.2
 */