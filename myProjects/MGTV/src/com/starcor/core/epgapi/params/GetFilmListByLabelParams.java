package com.starcor.core.epgapi.params;

import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.IntegerParams;
import com.starcor.core.epgapi.StringParams;
import com.starcor.core.logic.GlobalLogic;

public class GetFilmListByLabelParams extends Api
{
  public static final String SHORT_TYPE_CLICK = "click";
  public static final String SHORT_TYPE_DEFAULT = "default";
  public static final String SHORT_TYPE_TIME = "time";
  private IntegerParams nns_ignore_product;
  private IntegerParams nns_include_category;
  private StringParams nns_label_id;
  private IntegerParams nns_page_index;
  private IntegerParams nns_page_size;
  private StringParams nns_token;
  private IntegerParams nns_video_type;
  private StringParams nns_webtoken;
  private StringParams sort_type;

  public GetFilmListByLabelParams(String paramString1, int paramInt1, int paramInt2, int paramInt3, String paramString2)
  {
    this.taksCategory = 15;
    this.prefix = com.starcor.core.domain.AppInfo.URL_n3_a_i[3];
    this.nns_func.setValue("get_video_list_by_label");
    this.nns_label_id = new StringParams("nns_label_id");
    this.nns_label_id.setValue(paramString1);
    this.nns_video_type = new IntegerParams("nns_video_type");
    this.nns_video_type.setValue(paramInt1);
    this.nns_include_category = new IntegerParams("nns_include_category");
    this.nns_include_category.setValue(1);
    this.nns_ignore_product = new IntegerParams("nns_ignore_product");
    this.nns_ignore_product.setValue(0);
    this.sort_type = new StringParams("nns_sort_type");
    if (paramString2 != null)
      this.sort_type.setValue(paramString2);
    while (true)
    {
      this.nns_page_index = new IntegerParams("nns_page_index");
      this.nns_page_index.setValue(paramInt2);
      this.nns_page_size = new IntegerParams("nns_page_size");
      this.nns_page_size.setValue(paramInt3);
      this.nns_token = new StringParams("nns_token");
      this.nns_token.setValue(GlobalLogic.getInstance().getNnToken());
      this.nns_webtoken = new StringParams("nns_webtoken");
      this.nns_webtoken.setValue(GlobalLogic.getInstance().getWebToken());
      this.cacheValidTime = -1L;
      return;
      this.sort_type.setValue("default");
    }
  }

  public String getApiName()
  {
    return "N3_A_D_10";
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
    return str + this.nns_func + this.nns_label_id + this.nns_video_type + this.nns_include_category + this.nns_ignore_product + this.sort_type + this.nns_page_index + this.nns_page_size + this.nns_token + this.nns_webtoken + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.GetFilmListByLabelParams
 * JD-Core Version:    0.6.2
 */