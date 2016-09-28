package com.starcor.core.epgapi.params;

import com.starcor.core.domain.AppInfo;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.StringParams;

public class GetUiPackageParams extends Api
{
  private StringParams nns_ui_version;
  private StringParams nns_version;

  public GetUiPackageParams(String paramString1, String paramString2)
  {
    this.prefix = AppInfo.URL_n38_a;
    this.nns_func.setValue("get_ui_package");
    this.nns_version = new StringParams("nns_version");
    this.nns_version.setValue(paramString1);
    this.nns_ui_version = new StringParams("nns_ui_version");
    this.nns_ui_version.setValue(paramString2);
    this.cacheValidTime = 0L;
  }

  public String getApiName()
  {
    return "N38_A_1";
  }

  public String toString()
  {
    String str = this.prefix;
    if (!str.contains("?"))
      str = str + "?";
    return str + this.nns_func + this.nns_version + this.nns_ui_version + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.GetUiPackageParams
 * JD-Core Version:    0.6.2
 */