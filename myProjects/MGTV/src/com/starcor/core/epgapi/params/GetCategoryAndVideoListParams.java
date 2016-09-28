package com.starcor.core.epgapi.params;

import com.starcor.core.domain.AppInfo;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.IntegerParams;
import com.starcor.core.epgapi.StringParams;
import com.starcor.core.logic.GlobalLogic;

public class GetCategoryAndVideoListParams extends Api
{
  public static final String SHORT_TYPE_CLICK = "click";
  public static final String SHORT_TYPE_DEFAULT = "default";
  public static final String SHORT_TYPE_TIME = "time";
  private IntegerParams category_size;
  private IntegerParams include_category;
  private StringParams nns_category_type;
  private IntegerParams nns_special_size;
  private StringParams nns_tag;
  private StringParams nns_token;
  private StringParams nns_user_id;
  private IntegerParams nns_video_size;
  private StringParams nns_webtoken;
  private StringParams package_id;
  private StringParams sort_type;

  public GetCategoryAndVideoListParams(String paramString, int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfInt)
  {
    this.taksCategory = 5;
    this.prefix = AppInfo.URL_n39_a;
    this.nns_func.setValue("get_item_list_by_assist_id");
    this.package_id = new StringParams("nns_media_asset_id");
    this.package_id.setValue(paramString);
    this.nns_video_size = new IntegerParams("nns_video_size");
    this.nns_video_size.setValue(paramInt1);
    this.nns_special_size = new IntegerParams("nns_special_size");
    this.nns_special_size.setValue(paramInt2);
    this.category_size = new IntegerParams("nns_max_category");
    this.category_size.setValue(paramInt3);
    this.sort_type = new StringParams("nns_sort_type");
    this.sort_type.setValue("default");
    this.include_category = new IntegerParams("nns_include_category");
    this.include_category.setValue(0);
    this.nns_tag = new StringParams("nns_tag");
    this.nns_tag.setValue(AppInfo.nns_tag);
    this.nns_category_type = new StringParams("nns_category_type");
    String str = "";
    int i = paramArrayOfInt.length;
    for (int j = 0; j < i; j++)
    {
      int k = paramArrayOfInt[j];
      str = str + k + ",";
    }
    this.nns_category_type.setValue(str);
    this.nns_token = new StringParams("nns_token");
    this.nns_token.setValue(GlobalLogic.getInstance().getNnToken());
    this.nns_webtoken = new StringParams("nns_webtoken");
    this.nns_webtoken.setValue(GlobalLogic.getInstance().getWebToken());
    this.nns_user_id = new StringParams("nns_user_id");
    this.nns_user_id.setValue(GlobalLogic.getInstance().getUserId());
  }

  public String getApiName()
  {
    return "N39_A_22";
  }

  public String toString()
  {
    String str = this.prefix;
    if (!str.contains("?"))
      str = str + "?";
    return str + this.nns_func + this.package_id + this.nns_video_size + this.nns_special_size + this.nns_category_type + this.sort_type + this.include_category + this.nns_token + this.nns_webtoken + this.nns_user_id + this.category_size + this.nns_tag + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.GetCategoryAndVideoListParams
 * JD-Core Version:    0.6.2
 */