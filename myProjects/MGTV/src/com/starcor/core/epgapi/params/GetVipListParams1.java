package com.starcor.core.epgapi.params;

import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.StringParams;
import com.starcor.core.logic.GlobalLogic;

public class GetVipListParams1 extends Api
{
  private StringParams nns_page_index;
  private StringParams nns_page_size;
  private StringParams nns_product_id;
  private StringParams nns_token;
  private StringParams nns_webtoken;

  public GetVipListParams1(String paramString1, String paramString2, String paramString3)
  {
    this.prefix = com.starcor.core.domain.AppInfo.URL_n3_a_i[6];
    this.nns_func.setValue("get_media_assets_item_by_product_id");
    this.nns_product_id = new StringParams("nns_product_id");
    this.nns_product_id.setValue(paramString1);
    this.nns_page_index = new StringParams("nns_page_index");
    this.nns_page_index.setValue(paramString2);
    this.nns_page_size = new StringParams("nns_page_size");
    this.nns_page_size.setValue(paramString3);
    this.nns_token = new StringParams("nns_token");
    this.nns_token.setValue(GlobalLogic.getInstance().getNnToken());
    this.nns_webtoken = new StringParams("nns_webtoken");
    this.nns_webtoken.setValue(GlobalLogic.getInstance().getWebToken());
    this.cacheValidTime = 0L;
  }

  public String getApiName()
  {
    return "N3-A-H-1";
  }

  public String toString()
  {
    String str = this.prefix;
    if (!str.contains("?"))
      str = str + "?";
    return str + this.nns_func + this.nns_product_id + this.nns_page_size + this.nns_page_index + this.nns_token + this.nns_webtoken + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.GetVipListParams1
 * JD-Core Version:    0.6.2
 */