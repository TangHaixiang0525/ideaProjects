package com.starcor.core.epgapi.params;

import com.starcor.core.domain.AppInfo;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.StringParams;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.utils.Logger;

public class GetVideoIdInfoParams extends Api
{
  private StringParams nns_asset_id;
  private StringParams nns_clip_id;
  private StringParams nns_file_id;
  private StringParams nns_token;
  private StringParams nns_webtoken;

  public GetVideoIdInfoParams(String paramString1, String paramString2, String paramString3)
  {
    Logger.i("GetVideoIdInfoParams", "nns_asset_id:" + paramString1 + ", nns_clip_id:" + paramString2 + ", nns_file_id:" + paramString3);
    this.taksCategory = 2;
    this.prefix = AppInfo.URL_n200;
    this.nns_func.setValue("get_ids_by_mgtv");
    this.nns_asset_id = new StringParams("nns_asset_id");
    this.nns_asset_id.setValue(paramString1);
    this.nns_clip_id = new StringParams("nns_clip_id");
    this.nns_clip_id.setValue(paramString2);
    this.nns_file_id = new StringParams("nns_file_id");
    this.nns_file_id.setValue(paramString3);
    this.nns_token = new StringParams("nns_token");
    this.nns_token.setValue(GlobalLogic.getInstance().getNnToken());
    this.nns_webtoken = new StringParams("nns_webtoken");
    this.nns_webtoken.setValue(GlobalLogic.getInstance().getWebToken());
    this.cacheValidTime = -1L;
  }

  public String getApiName()
  {
    return "N200-A-22";
  }

  public String toString()
  {
    String str = this.prefix;
    if (!str.contains("?"))
      str = str + "?";
    return str + this.nns_func + this.nns_asset_id + this.nns_clip_id + this.nns_file_id + this.nns_token + this.nns_webtoken + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.GetVideoIdInfoParams
 * JD-Core Version:    0.6.2
 */