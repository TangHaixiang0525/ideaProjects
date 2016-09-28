package com.starcor.core.epgapi.params;

import com.starcor.core.domain.AppInfo;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.IntegerParams;
import com.starcor.core.epgapi.StringParams;
import com.starcor.core.logic.GlobalLogic;

public class HotWordListParams extends Api
{
  private IntegerParams nns_page_index;
  private IntegerParams nns_page_size;
  private StringParams nns_token;
  private StringParams nns_webtoken;

  public HotWordListParams(int paramInt1, int paramInt2)
  {
    this.prefix = AppInfo.URL_n21_a;
    this.nns_func.setValue("hot_word_list");
    this.nns_token = new StringParams("nns_token");
    this.nns_token.setValue(GlobalLogic.getInstance().getNnToken());
    this.nns_webtoken = new StringParams("nns_webtoken");
    this.nns_webtoken.setValue(GlobalLogic.getInstance().getWebToken());
    this.nns_page_index = new IntegerParams("nns_page_index");
    this.nns_page_index.setValue(paramInt1);
    this.nns_page_size = new IntegerParams("nns_page_size");
    this.nns_page_size.setValue(paramInt2);
    this.cacheValidTime = -1L;
  }

  public String getApiName()
  {
    return "N21_A_1";
  }

  public String toString()
  {
    String str = this.prefix;
    if (!str.contains("?"))
      str = str + "?";
    return str + this.nns_func + this.nns_page_index + this.nns_page_size + this.nns_token + this.nns_webtoken + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.HotWordListParams
 * JD-Core Version:    0.6.2
 */