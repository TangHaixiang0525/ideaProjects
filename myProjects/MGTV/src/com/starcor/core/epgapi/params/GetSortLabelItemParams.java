package com.starcor.core.epgapi.params;

import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.IntegerParams;
import com.starcor.core.epgapi.StringParams;
import com.starcor.core.logic.GlobalLogic;

public class GetSortLabelItemParams extends Api
{
  private static int baseNumber = 100;
  private StringParams nns_asset_id;
  private StringParams nns_label_type_id;
  private IntegerParams nns_page_index;
  private IntegerParams nns_page_size;
  private StringParams nns_token;
  private StringParams nns_webtoken;

  public GetSortLabelItemParams(String paramString1, String paramString2, int paramInt1, int paramInt2)
  {
    baseNumber = 1 + baseNumber;
    this.taksCategory = baseNumber;
    this.prefix = com.starcor.core.domain.AppInfo.URL_n3_a_i[5];
    this.nns_func.setValue("get_video_label_by_type");
    this.nns_asset_id = new StringParams("nns_media_assets_id");
    this.nns_asset_id.setValue(paramString1);
    this.nns_label_type_id = new StringParams("nns_label_type_id");
    this.nns_label_type_id.setValue(paramString2);
    this.nns_page_index = new IntegerParams("nns_page_index");
    this.nns_page_index.setValue(0);
    this.nns_page_size = new IntegerParams("nns_page_size");
    this.nns_page_size.setValue(paramInt2);
    this.nns_token = new StringParams("nns_token");
    this.nns_token.setValue(GlobalLogic.getInstance().getNnToken());
    this.nns_webtoken = new StringParams("nns_webtoken");
    this.nns_webtoken.setValue(GlobalLogic.getInstance().getWebToken());
    this.cacheValidTime = -1L;
  }

  public String getApiName()
  {
    return "N3_A_G_2";
  }

  public String toString()
  {
    String str = this.prefix;
    if (!str.contains("?"))
      str = str + "?";
    return str + this.nns_func + this.nns_asset_id + this.nns_label_type_id + this.nns_page_index + this.nns_page_size + this.nns_token + this.nns_webtoken + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.GetSortLabelItemParams
 * JD-Core Version:    0.6.2
 */