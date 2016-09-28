package com.starcor.core.epgapi.params;

import com.starcor.core.domain.AppInfo;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.StringParams;

public class ReportSccmsSpeedTestResultParams extends Api
{
  public ReportSccmsSpeedTestResultParams()
  {
    this.taksCategory = 6;
    this.prefix = AppInfo.URL_n26_a;
    this.nns_func.setValue("update_test_speed_result");
    this.cacheValidTime = -1L;
  }

  public String getApiName()
  {
    return "N26_A_2";
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
 * Qualified Name:     com.starcor.core.epgapi.params.ReportSccmsSpeedTestResultParams
 * JD-Core Version:    0.6.2
 */