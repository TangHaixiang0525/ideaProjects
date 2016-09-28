package com.starcor.core.epgapi.params;

import com.starcor.config.DeviceInfo;
import com.starcor.core.domain.AppInfo;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.IntegerParams;
import com.starcor.core.epgapi.StringParams;
import com.starcor.core.logic.GlobalLogic;

public class ReportUserBehaviorParams extends Api
{
  private StringParams nns_begin_time;
  private StringParams nns_category_id;
  private StringParams nns_media_asset_id;
  private StringParams nns_time_len;
  private StringParams nns_user_area;
  private StringParams nns_user_id;
  private StringParams nns_version;
  private StringParams nns_video_id;
  private IntegerParams nns_video_type;
  private IntegerParams nns_view_type;
  private StringParams nns_webtoken;

  public ReportUserBehaviorParams(int paramInt1, int paramInt2, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    this.prefix = AppInfo.URL_n40_f;
    this.nns_func.setValue("add_user_focus");
    this.nns_user_id = new StringParams("nns_user_id");
    this.nns_user_id.setValue(GlobalLogic.getInstance().getUserId());
    this.nns_video_type = new IntegerParams("nns_video_type");
    this.nns_video_type.setValue(paramInt1);
    this.nns_view_type = new IntegerParams("nns_view_type");
    this.nns_view_type.setValue(paramInt2);
    this.nns_video_id = new StringParams("nns_video_id");
    this.nns_video_id.setValue(paramString1);
    this.nns_begin_time = new StringParams("nns_begin_time");
    this.nns_begin_time.setValue(paramString2);
    this.nns_time_len = new StringParams("nns_time_len");
    this.nns_time_len.setValue(paramString3);
    this.nns_media_asset_id = new StringParams("nns_media_asset_id");
    this.nns_media_asset_id.setValue(paramString4);
    this.nns_category_id = new StringParams("nns_category_id");
    this.nns_category_id.setValue(paramString5);
    this.nns_version = new StringParams("nns_version");
    this.nns_version.setValue(DeviceInfo.getMGTVVersion());
    this.nns_webtoken = new StringParams("nns_webtoken");
    this.nns_webtoken.setValue(GlobalLogic.getInstance().getWebToken());
    this.nns_user_area = new StringParams("nns_user_area");
    this.nns_user_area.setValue("000000");
    this.cacheValidTime = -1L;
  }

  public String getApiName()
  {
    return "N40_F_2";
  }

  public String toString()
  {
    String str = this.prefix;
    if (!str.contains("?"))
      str = str + "?";
    return str + this.nns_func + this.nns_user_id + this.nns_video_type + this.nns_view_type + this.nns_video_id + this.nns_begin_time + this.nns_time_len + this.nns_media_asset_id + this.nns_category_id + this.nns_version + this.nns_user_area + this.nns_webtoken + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.ReportUserBehaviorParams
 * JD-Core Version:    0.6.2
 */