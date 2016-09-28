package com.starcor.core.epgapi.params;

import com.starcor.core.domain.AppInfo;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.StringParams;
import com.starcor.core.logic.GlobalLogic;

public class HotWordListTotalParams extends Api
{
  private StringParams nns_hot_word;
  private StringParams nns_token;
  private StringParams nns_webtoken;

  public HotWordListTotalParams(String paramString)
  {
    this.prefix = AppInfo.URL_n21_a;
    this.nns_func.setValue("inc_hot_word_count");
    this.nns_token = new StringParams("nns_token");
    this.nns_token.setValue(GlobalLogic.getInstance().getNnToken());
    this.nns_webtoken = new StringParams("nns_webtoken");
    this.nns_webtoken.setValue(GlobalLogic.getInstance().getWebToken());
    this.nns_hot_word = new StringParams("nns_hot_word");
    this.nns_hot_word.setValue(paramString);
    this.cacheValidTime = -1L;
  }

  public String getApiName()
  {
    return "N21_A_3";
  }

  public String toString()
  {
    String str = this.prefix;
    if (!str.contains("?"))
      str = str + "?";
    return str + this.nns_func + this.nns_hot_word + this.nns_token + this.nns_webtoken + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.HotWordListTotalParams
 * JD-Core Version:    0.6.2
 */