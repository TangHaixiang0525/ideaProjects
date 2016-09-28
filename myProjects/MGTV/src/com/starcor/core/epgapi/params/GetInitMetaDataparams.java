package com.starcor.core.epgapi.params;

import com.starcor.config.DeviceInfo;
import com.starcor.core.domain.AppInfo;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.StringParams;

public class GetInitMetaDataparams extends Api
{
  private StringParams nns_time_zone;
  private StringParams nns_version;

  public GetInitMetaDataparams()
  {
    this.prefix = AppInfo.URL_n36_a;
    this.nns_func.setValue("get_init_meta_data");
    this.nns_version = new StringParams("nns_version");
    this.nns_time_zone = new StringParams("nns_time_zone");
    this.nns_time_zone.setValue("");
    this.nns_version.setValue(DeviceInfo.getMGTVVersion());
    this.cacheValidTime = 0L;
  }

  public String getApiName()
  {
    return "N36_A_1";
  }

  public String toString()
  {
    String str = this.prefix;
    if (!str.contains("?"))
      str = str + "?";
    return str + this.nns_func + this.nns_time_zone + this.nns_version + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.GetInitMetaDataparams
 * JD-Core Version:    0.6.2
 */