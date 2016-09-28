package com.starcor.core.epgapi.params;

import com.starcor.core.domain.AppInfo;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.StringParams;

public class GetAdInfoPaeams extends Api
{
  private StringParams ad_pos_id;
  private StringParams user_agent;

  public GetAdInfoPaeams(String paramString1, String paramString2)
  {
    this.taksCategory = 20131202;
    this.prefix = AppInfo.URL_n7_a;
    this.nns_func.setValue("get_ad_info_by_ad_pos_list");
    this.ad_pos_id = new StringParams("nns_ad_pos_id");
    this.ad_pos_id.setValue(paramString1);
    this.user_agent = new StringParams("nns_user_agent");
    this.user_agent.setValue(paramString2);
  }

  public String getApiName()
  {
    return "N7_A";
  }

  public String toString()
  {
    String str = this.prefix;
    if (!str.contains("?"))
      str = str + "?";
    return str + this.nns_func + this.ad_pos_id + this.user_agent + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.GetAdInfoPaeams
 * JD-Core Version:    0.6.2
 */