package com.starcor.core.epgapi.params;

import com.starcor.core.domain.AppInfo;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.IntegerParams;
import com.starcor.core.epgapi.StringParams;
import com.starcor.core.logic.GlobalLogic;

public class GetChannelListV2Params extends Api
{
  private StringParams nns_category_id;
  private IntegerParams nns_include_category;
  private StringParams nns_media_assets_id;
  private StringParams nns_token;
  private StringParams nns_webtoken;

  public GetChannelListV2Params(String paramString1, String paramString2)
  {
    this.prefix = AppInfo.URL_n39_a;
    this.nns_func.setValue("get_channel_list");
    this.nns_token = new StringParams("nns_token");
    this.nns_token.setValue(GlobalLogic.getInstance().getNnToken());
    this.nns_webtoken = new StringParams("nns_webtoken");
    this.nns_webtoken.setValue(GlobalLogic.getInstance().getWebToken());
    this.nns_media_assets_id = new StringParams("nns_media_assets_id");
    this.nns_media_assets_id.setValue(paramString1);
    this.nns_category_id = new StringParams("nns_category_id");
    this.nns_category_id.setValue(paramString2);
    this.nns_include_category = new IntegerParams("nns_include_category");
    this.nns_include_category.setValue(1);
    this.cacheValidTime = -1L;
  }

  public String getApiName()
  {
    return "N39_A_1";
  }

  public String toString()
  {
    String str = this.prefix;
    if (!str.contains("?"))
      str = str + "?";
    return str + this.nns_func + this.nns_media_assets_id + this.nns_category_id + this.nns_include_category + this.nns_token + this.nns_webtoken + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.GetChannelListV2Params
 * JD-Core Version:    0.6.2
 */