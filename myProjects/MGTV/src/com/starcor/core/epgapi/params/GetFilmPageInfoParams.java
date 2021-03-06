package com.starcor.core.epgapi.params;

import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.IntegerParams;
import com.starcor.core.epgapi.StringParams;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.hunan.opendownload.drm.DrmAdapter;

public class GetFilmPageInfoParams extends Api
{
  private IntegerParams nns_page_index;
  private IntegerParams nns_page_size;
  private StringParams nns_token;
  private StringParams nns_webtoken;
  private StringParams video_id;
  private IntegerParams video_type;

  public GetFilmPageInfoParams(String paramString, int paramInt1, int paramInt2, int paramInt3)
  {
    this.taksCategory = 6;
    this.prefix = com.starcor.core.domain.AppInfo.URL_n3_a_i[0];
    this.nns_func.setValue("get_video_index_list");
    this.video_id = new StringParams("nns_video_id");
    this.video_id.setValue(paramString);
    this.video_type = new IntegerParams("nns_video_type");
    this.video_type.setValue(paramInt1);
    this.nns_page_index = new IntegerParams("nns_page_index");
    this.nns_page_index.setValue(paramInt2);
    this.nns_page_size = new IntegerParams("nns_page_size");
    this.nns_page_size.setValue(paramInt3);
    this.nns_token = new StringParams("nns_token");
    this.nns_token.setValue(GlobalLogic.getInstance().getNnToken());
    this.nns_webtoken = new StringParams("nns_webtoken");
    this.nns_webtoken.setValue(GlobalLogic.getInstance().getWebToken());
    this.cacheValidTime = -1L;
  }

  public String getApiName()
  {
    return "N3_A_A_12";
  }

  public String toString()
  {
    String str1 = this.prefix;
    if (!str1.contains("?"))
      str1 = str1 + "?";
    String str2 = str1 + this.nns_func + this.video_id + this.video_type + this.nns_page_index + this.nns_page_size + this.nns_token + this.nns_webtoken + this.suffix;
    return str2 + DrmAdapter.getDrmTypeParams();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.GetFilmPageInfoParams
 * JD-Core Version:    0.6.2
 */