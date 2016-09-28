package com.starcor.core.epgapi.params;

import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.IntegerParams;
import com.starcor.core.epgapi.StringParams;
import com.starcor.core.logic.GlobalLogic;

public class SearchMediaAssetsItemParams extends Api
{
  private IntegerParams current_page;
  private IntegerParams nns_include_category;
  private StringParams nns_label_id;
  private StringParams nns_media_assets_category_id;
  private StringParams nns_sort_type;
  private StringParams nns_token;
  private IntegerParams nns_video_type;
  private StringParams nns_webtoken;
  private StringParams package_id;
  private IntegerParams page_size;
  private StringParams pinyin_firstchar;
  private StringParams search_type;

  public SearchMediaAssetsItemParams(String paramString1, int paramInt1, int paramInt2, String paramString2, String paramString3)
  {
    this(paramString1, "", paramString2, paramString3, "pinyin_likechar", paramInt1, paramInt2);
  }

  public SearchMediaAssetsItemParams(String paramString1, String paramString2, int paramInt1, int paramInt2)
  {
    this("", paramString1, paramString2, "", "pinyin_likechar", paramInt1, paramInt2);
  }

  public SearchMediaAssetsItemParams(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt1, int paramInt2)
  {
    this.taksCategory = 10;
    this.prefix = com.starcor.core.domain.AppInfo.URL_n3_a_i[3];
    this.nns_func.setValue("search_media_assets_item");
    this.nns_media_assets_category_id = new StringParams("nns_media_assets_category_id");
    this.nns_media_assets_category_id.setValue(paramString1);
    this.package_id = new StringParams("nns_media_assets_id");
    this.package_id.setValue(paramString2);
    this.search_type = new StringParams("nns_search_type");
    this.search_type.setValue(paramString5);
    this.pinyin_firstchar = new StringParams("nns_search_key");
    this.pinyin_firstchar.setValue(paramString3);
    this.current_page = new IntegerParams("nns_page_index");
    this.current_page.setValue(paramInt1);
    this.page_size = new IntegerParams("nns_page_size");
    this.page_size.setValue(paramInt2);
    this.nns_label_id = new StringParams("nns_label_id");
    this.nns_label_id.setValue(paramString4);
    this.nns_include_category = new IntegerParams("nns_include_category");
    this.nns_include_category.setValue(1);
    this.nns_video_type = new IntegerParams("nns_video_type");
    this.nns_video_type.setValue(0);
    this.nns_sort_type = new StringParams("nns_sort_type");
    this.nns_sort_type.setValue("click");
    this.nns_token = new StringParams("nns_token");
    this.nns_token.setValue(GlobalLogic.getInstance().getNnToken());
    this.nns_webtoken = new StringParams("nns_webtoken");
    this.nns_webtoken.setValue(GlobalLogic.getInstance().getWebToken());
    this.cacheValidTime = -1L;
  }

  public String getApiName()
  {
    return "N3_A_D_6";
  }

  public StringParams getSearch_type()
  {
    return this.search_type;
  }

  public String toString()
  {
    String str = this.prefix;
    if (!str.contains("?"))
      str = str + "?";
    return str + this.nns_func + this.nns_media_assets_category_id + this.package_id + this.nns_label_id + this.pinyin_firstchar + this.current_page + this.page_size + this.search_type + this.nns_include_category + this.nns_token + this.nns_video_type + this.nns_sort_type + this.nns_webtoken + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.SearchMediaAssetsItemParams
 * JD-Core Version:    0.6.2
 */