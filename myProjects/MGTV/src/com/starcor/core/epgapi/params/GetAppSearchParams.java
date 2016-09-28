package com.starcor.core.epgapi.params;

import com.starcor.config.DeviceInfo;
import com.starcor.core.domain.AppInfo;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.IntegerParams;
import com.starcor.core.epgapi.StringParams;

public class GetAppSearchParams extends Api
{
  private StringParams nns_app_category_id;
  private IntegerParams nns_page_index;
  private IntegerParams nns_page_size;
  private StringParams nns_search_type;
  private StringParams nns_search_value;
  private StringParams nns_version;

  public GetAppSearchParams(int paramInt1, int paramInt2, int paramInt3, String paramString1, String paramString2)
  {
    this.prefix = AppInfo.URL_n650_a;
    this.nns_func.setValue("search_app");
    this.nns_search_type = new StringParams("nns_search_type");
    switch (paramInt1)
    {
    default:
    case 0:
    case 1:
    case 2:
    }
    while (true)
    {
      this.nns_page_index = new IntegerParams("nns_page_index");
      this.nns_page_index.setValue(paramInt2);
      this.nns_page_size = new IntegerParams("nns_page_size");
      this.nns_page_size.setValue(paramInt3);
      this.nns_search_value = new StringParams("nns_search_value");
      this.nns_search_value.setValue(paramString1);
      this.nns_app_category_id = new StringParams("nns_app_category_id");
      this.nns_app_category_id.setValue(paramString2);
      this.nns_version = new StringParams("nns_version");
      this.nns_version.setValue(DeviceInfo.getMGTVVersion());
      return;
      this.nns_search_type.setValue("name");
      continue;
      this.nns_search_type.setValue("name_en");
      continue;
      this.nns_search_type.setValue("name_pinyin");
    }
  }

  public String getApiName()
  {
    return "N650_A_4";
  }

  public String toString()
  {
    String str = this.prefix;
    if (!str.contains("?"))
      str = str + "?";
    return str + this.nns_func + this.nns_search_type + this.nns_page_index + this.nns_page_size + this.nns_search_value + this.nns_app_category_id + this.nns_version + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.GetAppSearchParams
 * JD-Core Version:    0.6.2
 */