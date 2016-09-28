package com.starcor.core.epgapi.params;

import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.IntegerParams;
import com.starcor.core.epgapi.StringParams;

public class GetRelevantFilmsParams extends Api
{
  private StringParams nns_category_id;
  private StringParams nns_media_assets_id;
  private StringParams nns_page_index;
  private StringParams nns_page_size;
  private StringParams nns_video_id;
  private IntegerParams nns_video_type;

  public GetRelevantFilmsParams(String paramString1, int paramInt1, String paramString2, String paramString3, int paramInt2, int paramInt3)
  {
    this.taksCategory = 6;
    this.prefix = com.starcor.core.domain.AppInfo.URL_n3_a_i[3];
    this.nns_func.setValue("get_video_recom_list_by_area");
    this.nns_video_id = new StringParams("nns_video_id");
    this.nns_video_id.setValue(paramString1);
    this.nns_video_type = new IntegerParams("nns_video_type");
    this.nns_video_type.setValue(paramInt1);
    this.nns_media_assets_id = new StringParams("nns_media_assets_id");
    this.nns_media_assets_id.setValue(paramString2);
    this.nns_category_id = new StringParams("nns_category_id");
    this.nns_category_id.setValue(paramString3);
    this.nns_page_size = new StringParams("nns_page_size");
    this.nns_page_index = new StringParams("nns_page_index");
    this.nns_page_size.setValue(String.valueOf(paramInt3));
    this.nns_page_index.setValue(String.valueOf(paramInt2));
    this.cacheValidTime = -1L;
  }

  public String getApiName()
  {
    return "N3_A_D_9";
  }

  public String toString()
  {
    String str = this.prefix;
    if (str == null)
      return null;
    if (!str.contains("?"))
      str = str + "?";
    return str + this.nns_func + this.nns_video_id + this.nns_video_type + this.nns_media_assets_id + this.nns_category_id + this.nns_page_size + this.nns_page_index + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.GetRelevantFilmsParams
 * JD-Core Version:    0.6.2
 */