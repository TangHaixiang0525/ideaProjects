package com.starcor.core.epgapi.params;

import com.starcor.core.domain.AppInfo;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.StringParams;

public class GetFrequentlyAskedQuestionsParams extends Api
{
  private StringParams nns_token;
  private StringParams nns_webtoken;

  public GetFrequentlyAskedQuestionsParams(String paramString1, String paramString2)
  {
    this.taksCategory = 20130609;
    this.prefix = AppInfo.URL_n23_a;
    this.nns_func.setValue("get_faq");
    this.nns_token = new StringParams("nns_token");
    this.nns_token.setValue(paramString1);
    this.nns_webtoken = new StringParams("nns_webtoken");
    this.nns_webtoken.setValue(paramString2);
    this.cacheValidTime = -1L;
  }

  public String getApiName()
  {
    return "N23_A_1";
  }

  public String toString()
  {
    String str = this.prefix;
    if (!str.contains("?"))
      str = str + "?";
    return str + this.nns_func + this.nns_token + this.nns_webtoken + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.GetFrequentlyAskedQuestionsParams
 * JD-Core Version:    0.6.2
 */