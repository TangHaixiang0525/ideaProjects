package com.starcor.core.epgapi.params;

import com.starcor.config.DeviceInfo;
import com.starcor.core.domain.AppInfo;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.IntegerParams;
import com.starcor.core.epgapi.StringParams;

public class GetQuitVideoIndexsParam extends Api
{
  public StringParams nns_category_id;
  public StringParams nns_media_assets_id;
  public IntegerParams nns_page_size;
  private IntegerParams nns_tag;
  public StringParams nns_version;
  public StringParams nns_video_id;
  public IntegerParams nns_video_type;

  public GetQuitVideoIndexsParam(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2)
  {
    this.prefix = AppInfo.URL_n39_a;
    this.nns_func.setValue("get_player_exit_data");
    this.nns_page_size = new IntegerParams("nns_page_size");
    this.nns_page_size.setValue(paramInt2);
    this.nns_version = new StringParams("nns_version");
    this.nns_version.setValue(DeviceInfo.getMGTVVersion());
    this.nns_media_assets_id = new StringParams("nns_media_assets_id");
    this.nns_media_assets_id.setValue(paramString1);
    this.nns_category_id = new StringParams("nns_category_id");
    this.nns_category_id.setValue(paramString2);
    this.nns_video_id = new StringParams("nns_video_id");
    this.nns_video_id.setValue(paramString3);
    this.nns_video_type = new IntegerParams("nns_video_type");
    this.nns_video_type.setValue(paramInt1);
    this.nns_tag = new IntegerParams("nns_tag");
  }

  public String getApiName()
  {
    return "N39_A_35";
  }

  public String toString()
  {
    String str = this.prefix;
    if (!str.contains("?"))
      str = str + "?";
    return str + this.nns_func + this.nns_page_size + this.nns_media_assets_id + this.nns_category_id + this.nns_video_id + this.nns_video_type + this.nns_tag + this.nns_version + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.GetQuitVideoIndexsParam
 * JD-Core Version:    0.6.2
 */