package com.starcor.core.epgapi.params;

import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.StringParams;

public class SearchActorsAndDirectorsByPinyinParams extends Api
{
  private StringParams nns_pinyin;

  public SearchActorsAndDirectorsByPinyinParams(String paramString)
  {
    this.taksCategory = 10;
    this.prefix = com.starcor.core.domain.AppInfo.URL_n3_a_i[5];
    this.nns_func.setValue("get_actor_director_label_by_pinyin");
    this.nns_pinyin = new StringParams("nns_pinyin");
    this.nns_pinyin.setValue(paramString);
    this.cacheValidTime = -1L;
  }

  public String getApiName()
  {
    return "N3_A_G_5";
  }

  public String toString()
  {
    String str = this.prefix;
    if (!str.contains("?"))
      str = str + "?";
    return str + this.nns_func + this.nns_pinyin + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.SearchActorsAndDirectorsByPinyinParams
 * JD-Core Version:    0.6.2
 */