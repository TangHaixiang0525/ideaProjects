package com.starcor.core.epgapi.params;

import com.starcor.core.domain.AppInfo;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.StringParams;
import com.starcor.core.logic.GlobalLogic;

public class GetDynamicCategoryItemListParams extends Api
{
  private StringParams nns_category = new StringParams("nns_category");
  private StringParams nns_language = new StringParams("nns_language");
  private StringParams nns_media_asset_id = new StringParams("nns_media_asset_id");
  private StringParams nns_tag = new StringParams("nns_tag");
  private StringParams nns_time_zone = new StringParams("nns_time_zone");
  private StringParams nns_user_area = new StringParams("nns_user_area");

  public GetDynamicCategoryItemListParams(String paramString, int[] paramArrayOfInt)
  {
    this.prefix = AppInfo.URL_n39_a;
    this.nns_func.setValue("get_dynamic_category_item_list");
    this.nns_media_asset_id.setValue(paramString);
    this.nns_tag.setValue(AppInfo.nns_tag);
    this.nns_language.setValue("en_US zh_CN");
    this.nns_user_area.setValue(GlobalLogic.getInstance().getAreaCode());
    this.nns_time_zone.setValue("");
    String str1 = "";
    int i = paramArrayOfInt.length;
    for (int j = 0; j < i; j++)
    {
      int k = paramArrayOfInt[j];
      str1 = str1 + k + "|";
    }
    String str2 = str1.substring(0, -1 + str1.length());
    this.nns_category.setValue(str2);
  }

  public String getApiName()
  {
    return "N39_A_26";
  }

  public String toString()
  {
    String str = this.prefix;
    if (!str.contains("?"))
      str = str + "?";
    return str + this.nns_func + this.nns_media_asset_id + this.nns_category + this.nns_tag + this.nns_user_area + this.nns_time_zone + this.nns_language + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.GetDynamicCategoryItemListParams
 * JD-Core Version:    0.6.2
 */