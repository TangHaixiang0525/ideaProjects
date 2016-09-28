package com.starcor.core.epgapi.params;

import com.starcor.core.domain.AppInfo;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.StringParams;

public class GetAdvertisementPosInfoParams extends Api
{
  private StringParams categoryId;
  private StringParams mediaAssetsId;
  private StringParams serviceId;
  private StringParams videoId;
  private StringParams videoType;

  public GetAdvertisementPosInfoParams(String paramString1, int paramInt, String paramString2, String paramString3, String paramString4)
  {
    this.prefix = AppInfo.URL_n7_a;
    this.nns_func.setValue("get_ad_info_by_video_id");
    this.videoId = new StringParams("nns_video_id");
    this.videoId.setValue(paramString1);
    this.videoType = new StringParams("nns_video_type");
    this.videoType.setValue(String.valueOf(paramInt));
    this.mediaAssetsId = new StringParams("nns_media_assets_id");
    this.mediaAssetsId.setValue(paramString2);
    this.serviceId = new StringParams("nns_service_id");
    this.serviceId.setValue(paramString3);
    this.categoryId = new StringParams("nns_category_id");
    this.categoryId.setValue(paramString4);
  }

  public String getApiName()
  {
    return "N7_A_2";
  }

  public String toString()
  {
    String str = this.prefix;
    if ((str != null) && (!str.contains("?")))
      str = str + "?";
    return str + this.nns_func + this.videoId + this.videoType + this.mediaAssetsId + this.serviceId + this.categoryId + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.GetAdvertisementPosInfoParams
 * JD-Core Version:    0.6.2
 */