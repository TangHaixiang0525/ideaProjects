package com.starcor.core.epgapi.params;

import com.starcor.core.domain.AppInfo;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.IntegerParams;
import com.starcor.core.epgapi.StringParams;
import com.starcor.core.logic.GlobalLogic;

public class GetFilmIdParams extends Api
{
  private StringParams nns_asset_id;
  private IntegerParams nns_index;
  private StringParams nns_token;
  private StringParams nns_webtoken;

  public GetFilmIdParams(String paramString, int paramInt)
  {
    this.prefix = AppInfo.URL_n200;
    this.nns_func.setValue("get_video_id_by_asset_id");
    this.nns_asset_id = new StringParams("nns_asset_id");
    this.nns_asset_id.setValue(paramString);
    this.nns_index = new IntegerParams("nns_index");
    this.nns_index.setValue(paramInt);
    this.nns_token = new StringParams("nns_token");
    this.nns_token.setValue(GlobalLogic.getInstance().getNnToken());
    this.nns_webtoken = new StringParams("nns_webtoken");
    this.nns_webtoken.setValue(GlobalLogic.getInstance().getWebToken());
    this.cacheValidTime = -1L;
  }

  public String getApiName()
  {
    return "N200_A_3";
  }

  public String toString()
  {
    String str = this.prefix;
    if (!str.contains("?"))
      str = str + "?";
    return str + this.nns_func + this.nns_asset_id + this.nns_index + this.nns_token + this.nns_webtoken + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.GetFilmIdParams
 * JD-Core Version:    0.6.2
 */