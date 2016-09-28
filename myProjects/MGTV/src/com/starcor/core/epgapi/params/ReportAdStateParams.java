package com.starcor.core.epgapi.params;

import com.starcor.core.domain.AppInfo;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.StringParams;

public class ReportAdStateParams extends Api
{
  private StringParams adEvent;
  private StringParams adId;
  private StringParams adPosId;
  private StringParams userId;

  public ReportAdStateParams(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    this.prefix = AppInfo.URL_n7_a;
    this.nns_func.setValue("stat_ad");
    this.adPosId = new StringParams("nns_ad_pos_id");
    this.adPosId.setValue(paramString1);
    this.adId = new StringParams("nns_ad_id");
    this.adId.setValue(paramString2);
    this.adEvent = new StringParams("nns_ad_event");
    this.adEvent.setValue(paramString3);
    this.userId = new StringParams("nns_user_id");
    this.userId.setValue(paramString4);
  }

  public String getApiName()
  {
    return "N7_A_4";
  }

  public String toString()
  {
    String str = this.prefix;
    if ((str != null) && (!str.contains("?")))
      str = str + "?";
    return str + this.nns_func + this.adPosId + this.adId + this.adEvent + this.userId + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.ReportAdStateParams
 * JD-Core Version:    0.6.2
 */