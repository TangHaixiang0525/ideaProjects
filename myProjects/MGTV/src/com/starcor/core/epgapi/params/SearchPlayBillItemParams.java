package com.starcor.core.epgapi.params;

import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.IntegerParams;
import com.starcor.core.epgapi.StringParams;
import com.starcor.core.logic.GlobalLogic;

public class SearchPlayBillItemParams extends Api
{
  private StringParams nns_day_begin;
  private StringParams nns_day_end;
  private IntegerParams nns_page_index;
  private IntegerParams nns_page_size;
  private StringParams nns_search_key;
  private StringParams nns_search_type;
  private IntegerParams nns_time_zone;
  private StringParams nns_token;
  private StringParams nns_video_id;
  private IntegerParams nns_video_type;
  private StringParams nns_webtoken;

  public SearchPlayBillItemParams(String paramString, int paramInt1, int paramInt2)
  {
    this.taksCategory = 17;
    this.prefix = com.starcor.core.domain.AppInfo.URL_n3_a_i[0];
    this.nns_func.setValue("search_playbill");
    this.nns_video_id = new StringParams("nns_video_id");
    this.nns_video_id.setValue("");
    this.nns_video_type = new IntegerParams("nns_video_type");
    this.nns_video_type.setValue(1);
    this.nns_time_zone = new IntegerParams("nns_time_zone");
    this.nns_time_zone.setValue(TIMEZONE_PLUS_EIGHT);
    this.nns_day_begin = new StringParams("nns_day_begin");
    this.nns_day_begin.setValue("");
    this.nns_day_end = new StringParams("nns_day_end");
    this.nns_day_end.setValue("");
    this.nns_search_key = new StringParams("nns_search_key");
    this.nns_search_key.setValue(paramString);
    this.nns_search_type = new StringParams("nns_search_type");
    this.nns_search_type.setValue("pinyin_firstchar");
    this.nns_page_index = new IntegerParams("nns_page_index");
    this.nns_page_index.setValue(paramInt1);
    this.nns_page_size = new IntegerParams("nns_page_size");
    this.nns_page_size.setValue(paramInt2);
    this.nns_token = new StringParams("nns_token");
    this.nns_token.setValue(GlobalLogic.getInstance().getNnToken());
    this.nns_webtoken = new StringParams("nns_webtoken");
    this.nns_webtoken.setValue(GlobalLogic.getInstance().getWebToken());
    this.cacheValidTime = -1L;
  }

  public String getApiName()
  {
    return "N3_A_A_9";
  }

  public StringParams getNns_day_begin()
  {
    return this.nns_day_begin;
  }

  public StringParams getNns_day_end()
  {
    return this.nns_day_end;
  }

  public StringParams getNns_video_id()
  {
    return this.nns_video_id;
  }

  public StringParams getSearch_type()
  {
    return this.nns_search_type;
  }

  public String toString()
  {
    String str = this.prefix;
    if (!str.contains("?"))
      str = str + "?";
    return str + this.nns_func + this.nns_video_id + this.nns_video_type + this.nns_time_zone + this.nns_day_begin + this.nns_day_end + this.nns_search_key + this.nns_search_type + this.nns_page_index + this.nns_page_size + this.nns_token + this.nns_webtoken + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.SearchPlayBillItemParams
 * JD-Core Version:    0.6.2
 */