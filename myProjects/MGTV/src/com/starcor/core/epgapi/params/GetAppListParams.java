package com.starcor.core.epgapi.params;

import com.starcor.config.DeviceInfo;
import com.starcor.core.domain.AppInfo;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.IntegerParams;
import com.starcor.core.epgapi.StringParams;

public class GetAppListParams extends Api
{
  private StringParams nns_category_id;
  private IntegerParams nns_page_index;
  private IntegerParams nns_page_size;
  private StringParams nns_version;

  public GetAppListParams(String paramString, int paramInt1, int paramInt2)
  {
    this.prefix = AppInfo.URL_n650_a;
    this.nns_func.setValue("get_app_list");
    this.nns_category_id = new StringParams("nns_category_id");
    this.nns_category_id.setValue(paramString);
    this.nns_page_index = new IntegerParams("nns_page_index");
    this.nns_page_index.setValue(paramInt1);
    this.nns_page_size = new IntegerParams("nns_page_size");
    this.nns_page_size.setValue(paramInt2);
    this.nns_version = new StringParams("nns_version");
    this.nns_version.setValue(DeviceInfo.getMGTVVersion());
  }

  public String getApiName()
  {
    return "N650_A_1";
  }

  public String toString()
  {
    String str = this.prefix;
    if (!str.contains("?"))
      str = str + "?";
    return str + this.nns_func + this.nns_category_id + this.nns_page_index + this.nns_page_size + this.nns_version + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.GetAppListParams
 * JD-Core Version:    0.6.2
 */