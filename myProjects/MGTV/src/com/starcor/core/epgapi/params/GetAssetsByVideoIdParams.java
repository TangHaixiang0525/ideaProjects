package com.starcor.core.epgapi.params;

import com.starcor.core.domain.AppInfo;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.StringParams;

public class GetAssetsByVideoIdParams extends Api
{
  private StringParams video_id;

  public GetAssetsByVideoIdParams(String paramString)
  {
    this.taksCategory = 110;
    this.prefix = AppInfo.URL_n39_a;
    this.nns_func.setValue("get_media_asset_by_video_id");
    this.video_id = new StringParams("nns_video_id");
    this.video_id.setValue(paramString);
    this.cacheValidTime = -1L;
  }

  public String getApiName()
  {
    return "N39_A_2";
  }

  public String toString()
  {
    String str = this.prefix;
    if (str == null)
      str = "";
    if (!str.contains("?"))
      str = str + "?";
    return str + this.nns_func + this.video_id + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.GetAssetsByVideoIdParams
 * JD-Core Version:    0.6.2
 */