package com.starcor.core.epgapi.params;

import com.starcor.config.DeviceInfo;
import com.starcor.core.domain.AppInfo;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.StringParams;

public class GetAppInfoParams extends Api
{
  private StringParams nns_app_id;
  private StringParams nns_version;
  private StringParams nns_version_id;

  public GetAppInfoParams(String paramString1, String paramString2)
  {
    this.prefix = AppInfo.URL_n650_a;
    this.nns_func.setValue("get_app_info");
    this.nns_version_id = new StringParams("nns_version_id");
    this.nns_version_id.setValue(paramString1);
    this.nns_app_id = new StringParams("nns_app_id");
    this.nns_app_id.setValue(paramString2);
    this.nns_version = new StringParams("nns_version");
    this.nns_version.setValue(DeviceInfo.getMGTVVersion());
  }

  public String getApiName()
  {
    return "N650_A_2";
  }

  public String toString()
  {
    String str = this.prefix;
    if (!str.contains("?"))
      str = str + "?";
    return str + this.nns_func + this.nns_version_id + this.nns_app_id + this.nns_version + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.GetAppInfoParams
 * JD-Core Version:    0.6.2
 */