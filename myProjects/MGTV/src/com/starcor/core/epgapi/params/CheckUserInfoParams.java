package com.starcor.core.epgapi.params;

import com.starcor.core.domain.AppInfo;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.StringParams;

public class CheckUserInfoParams extends Api
{
  private StringParams nns_device_id;
  private StringParams nns_mac_id;
  private StringParams nns_net_id;
  private StringParams nns_webtoken;

  public CheckUserInfoParams(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    this.taksCategory = 2;
    this.prefix = AppInfo.URL_n2_a;
    this.nns_func.setValue("check_valid_by_token");
    this.nns_net_id = new StringParams("nns_net_id");
    this.nns_net_id.setValue(paramString2);
    this.nns_device_id = new StringParams("nns_device_id");
    this.nns_device_id.setValue(paramString3);
    this.nns_webtoken = new StringParams("nns_webtoken");
    this.nns_webtoken.setValue(paramString1);
    this.nns_mac_id = new StringParams("nns_mac_id");
    this.nns_mac_id.setValue(paramString4);
    this.cacheValidTime = 0L;
  }

  public String getApiName()
  {
    return "N2_A_4";
  }

  public StringParams getNns_device_id()
  {
    return this.nns_device_id;
  }

  public StringParams getNns_net_id()
  {
    return this.nns_net_id;
  }

  public String toString()
  {
    String str = this.prefix;
    if (!str.contains("?"))
      str = str + "?";
    return str + this.nns_func + this.nns_net_id + this.nns_device_id + this.nns_mac_id + this.nns_webtoken + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.CheckUserInfoParams
 * JD-Core Version:    0.6.2
 */