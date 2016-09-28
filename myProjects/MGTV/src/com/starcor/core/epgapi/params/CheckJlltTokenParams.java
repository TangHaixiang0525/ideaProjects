package com.starcor.core.epgapi.params;

import com.starcor.core.domain.AppInfo;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.StringParams;
import com.starcor.core.utils.Logger;

public class CheckJlltTokenParams extends Api
{
  private StringParams nns_device_id;
  private StringParams nns_epg_server;
  private StringParams nns_mac_id;
  private StringParams nns_webtoken;

  public CheckJlltTokenParams(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    this.taksCategory = 2;
    this.prefix = AppInfo.URL_n200;
    this.nns_func.setValue("check_valid_by_webtoken");
    this.nns_webtoken = new StringParams("nns_webtoken");
    this.nns_webtoken.setValue(paramString1);
    this.nns_mac_id = new StringParams("nns_mac_id");
    this.nns_mac_id.setValue(paramString2);
    this.nns_device_id = new StringParams("nns_device_id");
    this.nns_device_id.setValue(paramString3);
    this.nns_epg_server = new StringParams("nns_epg_server");
    this.nns_epg_server.setValue(paramString4);
    this.cacheValidTime = 0L;
  }

  public String getApiName()
  {
    return "N200_A_2";
  }

  public String toString()
  {
    String str1 = this.prefix;
    if (!str1.contains("?"))
      str1 = str1 + "?";
    String str2 = str1 + this.nns_func + this.nns_mac_id + this.nns_webtoken + this.nns_device_id + this.nns_epg_server + this.suffix;
    Logger.i("CheckTokenParams", "N200_A_2 url=" + str2);
    return str2;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.CheckJlltTokenParams
 * JD-Core Version:    0.6.2
 */