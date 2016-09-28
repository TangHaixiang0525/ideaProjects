package com.starcor.core.epgapi.params;

import com.starcor.config.DeviceInfo;
import com.starcor.core.domain.AppInfo;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.StringParams;

public class GetVideoInfoListParams extends Api
{
  public StringParams nns_info_type;
  private StringParams nns_tag;
  public StringParams nns_version;
  public StringParams nns_video_ids;

  public GetVideoInfoListParams(String paramString1, String paramString2)
  {
    this.prefix = AppInfo.URL_n39_a;
    this.nns_func.setValue("get_video_info_list");
    this.nns_version = new StringParams("nns_version");
    this.nns_version.setValue(DeviceInfo.getMGTVVersion());
    this.nns_video_ids = new StringParams("nns_video_ids");
    this.nns_video_ids.setValue(paramString1);
    this.nns_info_type = new StringParams("nns_info_type");
    this.nns_info_type.setValue(paramString2);
    this.nns_tag = new StringParams("nns_tag");
    this.cacheValidTime = 0L;
  }

  public String getApiName()
  {
    return "N39_a_20";
  }

  public String toString()
  {
    String str = this.prefix;
    if (!str.contains("?"))
      str = str + "?";
    return str + this.nns_func + this.nns_video_ids + this.nns_info_type + this.nns_tag + this.nns_version + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.GetVideoInfoListParams
 * JD-Core Version:    0.6.2
 */