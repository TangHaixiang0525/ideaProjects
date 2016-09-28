package com.starcor.core.epgapi.params;

import com.starcor.core.domain.AppInfo;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.StringParams;

public class GetAppUninstallListParams extends Api
{
  public GetAppUninstallListParams()
  {
    this.prefix = AppInfo.URL_n650_a;
    this.nns_func.setValue("get_uninstall_list");
  }

  public String getApiName()
  {
    return "N650_A_8";
  }

  public String toString()
  {
    String str = this.prefix;
    if (!str.contains("?"))
      str = str + "?";
    return str + this.nns_func + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.GetAppUninstallListParams
 * JD-Core Version:    0.6.2
 */