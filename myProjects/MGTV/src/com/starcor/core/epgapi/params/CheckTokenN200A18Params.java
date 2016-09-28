package com.starcor.core.epgapi.params;

import com.starcor.core.domain.AppInfo;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.StringParams;

public class CheckTokenN200A18Params extends Api
{
  private StringParams nns_mac_id;
  private StringParams nns_webtoken;

  public CheckTokenN200A18Params(String paramString1, String paramString2)
  {
    this.prefix = AppInfo.URL_n200;
    this.nns_func.setValue("check_webtoken");
    this.nns_webtoken = new StringParams("nns_webtoken");
    this.nns_webtoken.setValue(paramString1);
    this.nns_mac_id = new StringParams("nns_mac_id");
    this.nns_mac_id.setValue(paramString2);
    this.cacheValidTime = 0L;
  }

  public String getApiName()
  {
    return "N200_A_18";
  }

  public String toString()
  {
    String str = this.prefix;
    if (!str.contains("?"))
      str = str + "?";
    return str + this.nns_func + this.nns_mac_id + this.nns_webtoken + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.CheckTokenN200A18Params
 * JD-Core Version:    0.6.2
 */