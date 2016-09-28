package com.starcor.core.epgapi.params;

import com.starcor.core.domain.AppInfo;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.StringParams;

public class GetAdInfoByAdPosParams extends Api
{
  private StringParams adPosId;

  public GetAdInfoByAdPosParams(String paramString)
  {
    this.prefix = AppInfo.URL_n7_a;
    this.nns_func.setValue("get_ad_info_by_ad_pos");
    this.adPosId = new StringParams("nns_ad_pos_id");
    this.adPosId.setValue(paramString);
  }

  public String getApiName()
  {
    return "N7_A_3";
  }

  public String toString()
  {
    String str = this.prefix;
    if ((str != null) && (!str.contains("?")))
      str = str + "?";
    return str + this.nns_func + this.adPosId + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.GetAdInfoByAdPosParams
 * JD-Core Version:    0.6.2
 */