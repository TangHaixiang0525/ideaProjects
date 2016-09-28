package com.starcor.core.epgapi.params;

import com.starcor.core.domain.AppInfo;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.IntegerParams;
import com.starcor.core.epgapi.StringParams;
import com.starcor.core.logic.GlobalLogic;

public class SearchActorStarListParams extends Api
{
  private StringParams nns_label_id;
  private IntegerParams nns_page_index;
  private IntegerParams nns_page_size;
  private StringParams nns_search_key;
  private StringParams nns_search_type;
  private StringParams nns_token;
  private StringParams nns_webtoken;

  public SearchActorStarListParams(int paramInt1, int paramInt2, String paramString1, String paramString2)
  {
    this.prefix = AppInfo.URL_n39_a;
    this.nns_func.setValue("search_actor_star_list");
    this.nns_search_type = new StringParams("nns_search_type");
    this.nns_search_type.setValue("pinyin_likechar");
    this.nns_page_index = new IntegerParams("nns_page_index");
    this.nns_page_index.setValue(paramInt1);
    this.nns_page_size = new IntegerParams("nns_page_size");
    this.nns_page_size.setValue(paramInt2);
    this.nns_search_key = new StringParams("nns_search_key");
    this.nns_search_key.setValue(paramString1);
    this.nns_label_id = new StringParams("nns_label_id");
    this.nns_label_id.setValue(paramString2);
    this.nns_token = new StringParams("nns_token");
    this.nns_token.setValue(GlobalLogic.getInstance().getNnToken());
    this.nns_webtoken = new StringParams("nns_webtoken");
    this.nns_webtoken.setValue(GlobalLogic.getInstance().getWebToken());
    this.cacheValidTime = -1L;
  }

  public String getApiName()
  {
    return "N39_A_14";
  }

  public String toString()
  {
    String str = this.prefix;
    if (!str.contains("?"))
      str = str + "?";
    return str + this.nns_func + this.nns_search_type + this.nns_page_index + this.nns_page_size + this.nns_search_key + this.nns_label_id + this.nns_token + this.nns_webtoken + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.SearchActorStarListParams
 * JD-Core Version:    0.6.2
 */