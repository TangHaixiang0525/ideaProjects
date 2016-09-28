package com.starcor.core.epgapi.params;

import com.starcor.config.DeviceInfo;
import com.starcor.core.domain.AppInfo;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.StringParams;
import com.starcor.core.logic.GlobalLogic;

public class GetPlaybillSelectedParams extends Api
{
  public StringParams nns_category_id;
  public StringParams nns_media_asset_id;
  public StringParams nns_page_index;
  public StringParams nns_page_size;
  public StringParams nns_tag;
  public StringParams nns_time_zone;
  public StringParams nns_user_area;
  public StringParams nns_user_attr;
  public StringParams nns_version;
  public StringParams nns_video_type;
  public StringParams nns_webtoken;

  public GetPlaybillSelectedParams(String paramString1, String paramString2, int paramInt1, int paramInt2)
  {
    this.prefix = AppInfo.URL_n39_a;
    this.nns_func.setValue("get_media_asset_list_v2");
    this.nns_version = new StringParams("nns_version");
    this.nns_version.setValue(DeviceInfo.getMGTVVersion());
    this.nns_media_asset_id = new StringParams("nns_media_asset_id");
    this.nns_media_asset_id.setValue(paramString1);
    this.nns_category_id = new StringParams("nns_category_id");
    this.nns_category_id.setValue(paramString2);
    this.nns_page_size = new StringParams("nns_page_size");
    this.nns_page_size.setValue(String.valueOf(paramInt1));
    this.nns_page_index = new StringParams("nns_page_index");
    this.nns_page_index.setValue(String.valueOf(paramInt2));
    this.nns_time_zone = new StringParams("nns_time_zone");
    this.nns_time_zone.setValue(String.valueOf(8));
    this.nns_tag = new StringParams("nns_tag");
    this.nns_tag.setValue(AppInfo.nns_tag);
    this.nns_webtoken = new StringParams("nns_webtoken");
    this.nns_webtoken.setValue(GlobalLogic.getInstance().getWebToken());
    this.cacheValidTime = 0L;
  }

  public GetPlaybillSelectedParams(String paramString1, String paramString2, int paramInt1, int paramInt2, String paramString3, String paramString4, String paramString5)
  {
    this(paramString1, paramString2, paramInt1, paramInt2);
    this.nns_user_area = new StringParams("nns_user_area");
    this.nns_user_area.setValue(paramString3);
    this.nns_user_attr = new StringParams("nns_user_attr");
    this.nns_user_attr.setValue(paramString4);
    this.nns_video_type = new StringParams("nns_video_type");
    this.nns_video_type.setValue(paramString5);
    this.nns_tag = new StringParams("nns_tag");
    this.nns_tag.setValue(AppInfo.nns_tag);
  }

  public String getApiName()
  {
    return "N39_A_15";
  }

  public String toString()
  {
    String str1 = this.prefix;
    if (!str1.contains("?"))
      str1 = str1 + "?";
    String str2 = str1 + this.nns_func + this.nns_media_asset_id + this.nns_category_id + this.nns_page_size + this.nns_page_index + this.nns_time_zone + this.nns_version + this.nns_webtoken + this.suffix + this.nns_tag;
    if ((this.nns_user_attr != null) && (this.nns_user_area != null) && (this.nns_video_type != null))
      str2 = str2 + this.nns_user_area + this.nns_user_attr + this.nns_video_type;
    return str2;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.GetPlaybillSelectedParams
 * JD-Core Version:    0.6.2
 */