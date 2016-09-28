package com.starcor.core.epgapi.params;

import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.IntegerParams;
import com.starcor.core.epgapi.StringParams;
import com.starcor.core.logic.GlobalLogic;

public class GetFilmItemParams extends Api
{
  public static final String SHORT_TYPE_CLICK = "click";
  public static final String SHORT_TYPE_DEFAULT = "default";
  public static final String SHORT_TYPE_TIME = "time";
  private StringParams category_id;
  private IntegerParams include_category;
  private StringParams nns_token;
  private StringParams nns_webtoken;
  private StringParams package_id;
  private IntegerParams page_index;
  private IntegerParams page_size;
  private StringParams sort_type;

  public GetFilmItemParams(String paramString1, String paramString2, int paramInt1, int paramInt2)
  {
    this.taksCategory = 5;
    this.prefix = com.starcor.core.domain.AppInfo.URL_n3_a_i[3];
    this.nns_func.setValue("get_media_assets_item_list");
    this.package_id = new StringParams("nns_media_assets_id");
    this.package_id.setValue(paramString1);
    this.category_id = new StringParams("nns_category_id");
    this.category_id.setValue(paramString2);
    this.page_index = new IntegerParams("nns_page_index");
    this.page_index.setValue(paramInt1);
    this.page_size = new IntegerParams("nns_page_size");
    this.page_size.setValue(paramInt2);
    this.sort_type = new StringParams("nns_sort_type");
    this.sort_type.setValue("default");
    this.include_category = new IntegerParams("nns_include_category");
    this.include_category.setValue(0);
    this.nns_token = new StringParams("nns_token");
    this.nns_token.setValue(GlobalLogic.getInstance().getNnToken());
    this.nns_webtoken = new StringParams("nns_webtoken");
    this.nns_webtoken.setValue(GlobalLogic.getInstance().getWebToken());
    this.cacheValidTime = -1L;
  }

  public String getApiName()
  {
    return "N3_A_D_2";
  }

  public IntegerParams getInclude_category()
  {
    return this.include_category;
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
    return str + this.nns_func + this.package_id + this.category_id + this.page_index + this.page_size + this.sort_type + this.include_category + this.nns_token + this.nns_webtoken + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.GetFilmItemParams
 * JD-Core Version:    0.6.2
 */