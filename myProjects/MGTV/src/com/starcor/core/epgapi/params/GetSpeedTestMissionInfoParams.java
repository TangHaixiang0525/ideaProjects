package com.starcor.core.epgapi.params;

import com.starcor.core.domain.AppInfo;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.StringParams;

public class GetSpeedTestMissionInfoParams extends Api
{
  public GetSpeedTestMissionInfoParams()
  {
    this.taksCategory = 6;
    this.prefix = AppInfo.URL_n26_a;
    this.nns_func.setValue("get_test_speed");
    this.cacheValidTime = -1L;
  }

  public String getApiName()
  {
    return "N3_A26_1";
  }

  public String toString()
  {
    String str = this.prefix;
    if (str == null)
      return null;
    if (!str.contains("?"))
      str = str + "?";
    return str + this.nns_func + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.GetSpeedTestMissionInfoParams
 * JD-Core Version:    0.6.2
 */