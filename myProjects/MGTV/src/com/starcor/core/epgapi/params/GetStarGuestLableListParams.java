package com.starcor.core.epgapi.params;

import android.text.TextUtils;
import com.starcor.core.domain.AppInfo;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.IntegerParams;
import com.starcor.core.epgapi.StringParams;
import com.starcor.core.logic.GlobalLogic;

public class GetStarGuestLableListParams extends Api
{
  public static final String SHORT_TYPE_CLICK = "click";
  public static final String SHORT_TYPE_DEFAULT = "default";
  public static final String SHORT_TYPE_TIME = "time";
  private StringParams nns_category_id;
  private StringParams nns_include_category;
  private StringParams nns_label_id;
  private StringParams nns_media_assets_category_id;
  private StringParams nns_media_assets_id;
  private IntegerParams nns_page_index;
  private IntegerParams nns_page_size;
  private StringParams nns_tag;
  private StringParams nns_token;
  private StringParams nns_webtoken;
  private StringParams sort_type;

  public GetStarGuestLableListParams(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2, String paramString4)
  {
    this.prefix = AppInfo.URL_n39_a;
    this.nns_func.setValue("search_video_index_list");
    this.nns_media_assets_id = new StringParams("nns_media_assets_id");
    this.nns_media_assets_id.setValue(paramString1);
    this.nns_category_id = new StringParams("nns_category_id");
    if (TextUtils.isEmpty(paramString2))
      paramString2 = "1000";
    this.nns_category_id.setValue(paramString2);
    this.nns_media_assets_category_id = new StringParams("nns_media_assets_category_id");
    this.nns_media_assets_category_id.setValue(paramString3);
    this.nns_page_index = new IntegerParams("nns_page_index");
    this.nns_page_index.setValue(paramInt1);
    this.nns_page_size = new IntegerParams("nns_page_size");
    this.nns_page_size.setValue(paramInt2);
    this.sort_type = new StringParams("nns_sort_type");
    this.sort_type.setValue("default");
    this.nns_label_id = new StringParams("nns_label_id");
    this.nns_label_id.setValue(paramString4);
    this.nns_include_category = new StringParams("nns_include_category");
    this.nns_include_category.setValue("1");
    this.nns_tag = new StringParams("nns_tag");
    this.nns_tag.setValue(AppInfo.nns_tag);
    this.nns_token = new StringParams("nns_token");
    this.nns_token.setValue(GlobalLogic.getInstance().getNnToken());
    this.nns_webtoken = new StringParams("nns_webtoken");
    this.nns_webtoken.setValue(GlobalLogic.getInstance().getWebToken());
    this.cacheValidTime = -1L;
  }

  public String getApiName()
  {
    return "N39_A_11";
  }

  public StringParams getSort_type()
  {
    return this.sort_type;
  }

  public String toString()
  {
    String str = this.prefix;
    if (!str.contains("?"))
      str = str + "?";
    return str + this.nns_func + this.nns_media_assets_id + this.nns_category_id + this.nns_media_assets_category_id + this.nns_page_index + this.nns_page_size + this.nns_label_id + this.sort_type + this.nns_include_category + this.nns_tag + this.nns_token + this.nns_webtoken + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.GetStarGuestLableListParams
 * JD-Core Version:    0.6.2
 */