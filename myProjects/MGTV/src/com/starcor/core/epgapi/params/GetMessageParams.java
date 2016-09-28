package com.starcor.core.epgapi.params;

import com.starcor.config.DeviceInfo;
import com.starcor.core.domain.AppInfo;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.StringParams;

public class GetMessageParams extends Api
{
  public StringParams nns_time;
  public StringParams nns_version;

  public GetMessageParams(String paramString)
  {
    this.prefix = AppInfo.URL_n40_e;
    this.nns_func.setValue("get_message_info");
    this.nns_version = new StringParams("nns_version");
    this.nns_version.setValue(DeviceInfo.getMGTVVersion());
    this.nns_time = new StringParams("nns_time");
    this.nns_time.setValue(paramString);
  }

  public String getApiName()
  {
    return "N40_E_1";
  }

  public String toString()
  {
    String str = this.prefix;
    if (!str.contains("?"))
      str = str + "?";
    return str + this.nns_func + this.nns_time + this.nns_version + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.GetMessageParams
 * JD-Core Version:    0.6.2
 */