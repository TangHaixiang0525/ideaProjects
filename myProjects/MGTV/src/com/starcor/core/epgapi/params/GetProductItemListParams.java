package com.starcor.core.epgapi.params;

import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.IntegerParams;
import com.starcor.core.epgapi.StringParams;
import com.starcor.core.logic.GlobalLogic;

public class GetProductItemListParams extends Api
{
  public static final String SHORT_TYPE_CLICK = "click";
  public static final String SHORT_TYPE_DEFAULT = "default";
  public static final String SHORT_TYPE_TIME = "time";
  private IntegerParams ignore_product;
  private StringParams nns_token;
  private StringParams nns_webtoken;
  private IntegerParams page_index;
  private IntegerParams page_size;
  private StringParams product_id;
  private StringParams sort_type;

  public GetProductItemListParams(String paramString, int paramInt1, int paramInt2, int paramInt3)
  {
    this.taksCategory = 105;
    this.prefix = com.starcor.core.domain.AppInfo.URL_n3_a_i[8];
    this.nns_func.setValue("get_media_assets_item_by_product_id");
    this.product_id = new StringParams("nns_product_id");
    this.product_id.setValue(paramString);
    this.page_index = new IntegerParams("nns_page_index");
    this.page_index.setValue(paramInt1);
    this.page_size = new IntegerParams("nns_page_size");
    this.page_size.setValue(paramInt2);
    this.sort_type = new StringParams("nns_sort_type");
    this.sort_type.setValue("time");
    this.ignore_product = new IntegerParams("nns_ignore_product");
    this.ignore_product.setValue(paramInt2);
    this.nns_token = new StringParams("nns_token");
    this.nns_token.setValue(GlobalLogic.getInstance().getNnToken());
    this.nns_webtoken = new StringParams("nns_webtoken");
    this.nns_webtoken.setValue(GlobalLogic.getInstance().getWebToken());
    this.cacheValidTime = 0L;
  }

  public String getApiName()
  {
    return "N3_A_H_1";
  }

  public StringParams getSort_type()
  {
    return this.sort_type;
  }

  public String toString()
  {
    String str = this.prefix;
    if (!str.contains("?"))
      str = str + "?";
    return str + this.nns_func + this.product_id + this.page_index + this.page_size + this.sort_type + this.ignore_product + this.nns_token + this.nns_webtoken + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.GetProductItemListParams
 * JD-Core Version:    0.6.2
 */