package com.starcor.core.epgapi.params;

import com.starcor.core.domain.AppInfo;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.StringParams;

public class GetAdPosByPageIdParams extends Api
{
  private StringParams pageId;

  public GetAdPosByPageIdParams(String paramString)
  {
    this.prefix = AppInfo.URL_n7_a;
    this.nns_func.setValue("get_ad_pos_by_page_id");
    this.pageId = new StringParams("nns_page_id");
    this.pageId.setValue(paramString);
  }

  public String getApiName()
  {
    return "N7_A_1";
  }

  public String toString()
  {
    String str = this.prefix;
    if ((str != null) && (!str.contains("?")))
      str = str + "?";
    return str + this.nns_func + this.pageId + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.GetAdPosByPageIdParams
 * JD-Core Version:    0.6.2
 */