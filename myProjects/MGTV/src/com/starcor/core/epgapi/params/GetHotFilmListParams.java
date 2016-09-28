package com.starcor.core.epgapi.params;

import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.IntegerParams;
import com.starcor.core.epgapi.StringParams;
import com.starcor.core.utils.Logger;

public class GetHotFilmListParams extends Api
{
  private IntegerParams nns_page_index;
  private IntegerParams nns_page_size;
  private StringParams nns_video_id;

  public GetHotFilmListParams(String paramString, int paramInt)
  {
    this.prefix = com.starcor.core.domain.AppInfo.URL_n3_a_i[3];
    this.nns_func = new StringParams("nns_func");
    this.nns_func.setValue("get_video_recom_list_by_area");
    this.nns_page_size = new IntegerParams("nns_page_size");
    this.nns_page_size.setValue(paramInt);
    this.nns_page_index = new IntegerParams("nns_page_index");
    this.nns_page_index.setValue(0);
    this.nns_video_id = new StringParams("nns_video_id");
    this.nns_video_id.setValue(paramString);
  }

  public String getApiName()
  {
    return "N3_A_D_4";
  }

  public String toString()
  {
    String str1 = this.prefix;
    if (!str1.contains("?"))
      str1 = str1 + "?";
    String str2 = str1 + this.nns_func + this.nns_video_id + this.nns_page_size + this.nns_page_index + this.suffix;
    Logger.i("GetHotFilmListParams", "N3_A_D_8 url" + str2);
    return str2;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.GetHotFilmListParams
 * JD-Core Version:    0.6.2
 */