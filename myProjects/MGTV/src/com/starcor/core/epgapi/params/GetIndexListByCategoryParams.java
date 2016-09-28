package com.starcor.core.epgapi.params;

import com.starcor.core.domain.AppInfo;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.IntegerParams;
import com.starcor.core.epgapi.StringParams;

public class GetIndexListByCategoryParams extends Api
{
  private StringParams nns_category_id;
  private StringParams nns_media_asset_id;
  private IntegerParams nns_page_index;
  private IntegerParams nns_page_size;
  private StringParams nns_sort_type;

  public GetIndexListByCategoryParams(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2)
  {
    this.prefix = AppInfo.URL_n39_a;
    this.nns_func.setValue("get_index_list_by_category");
    this.nns_media_asset_id = new StringParams("nns_media_asset_id");
    this.nns_media_asset_id.setValue(paramString1);
    this.nns_category_id = new StringParams("nns_category_id");
    this.nns_category_id.setValue(paramString2);
    this.nns_sort_type = new StringParams("nns_type");
    this.nns_sort_type.setValue(paramString3);
    this.nns_page_index = new IntegerParams("nns_page_index");
    this.nns_page_index.setValue(paramInt1);
    this.nns_page_size = new IntegerParams("nns_page_size");
    this.nns_page_size.setValue(paramInt2);
  }

  public String getApiName()
  {
    return "N39_A_9";
  }

  public String toString()
  {
    String str = this.prefix;
    if (!str.contains("?"))
      str = str + "?";
    return str + this.nns_func + this.nns_media_asset_id + this.nns_category_id + this.nns_sort_type + this.nns_page_index + this.nns_page_size + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.GetIndexListByCategoryParams
 * JD-Core Version:    0.6.2
 */