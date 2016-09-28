package com.starcor.core.epgapi.params;

import com.starcor.core.domain.AppInfo;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.StringParams;
import com.starcor.core.logic.GlobalLogic;

public class GetMediaAssetsBindLabelParams extends Api
{
  private StringParams nns_packet_id;
  private StringParams nns_token;
  private StringParams nns_webtoken;

  public GetMediaAssetsBindLabelParams(String paramString)
  {
    this.taksCategory = 113;
    this.prefix = AppInfo.URL_n39_a;
    this.nns_func.setValue("get_media_assets_bind_labels_v2");
    this.nns_packet_id = new StringParams("nns_media_assets_id");
    this.nns_packet_id.setValue(paramString);
    this.nns_token = new StringParams("nns_token");
    this.nns_token.setValue(GlobalLogic.getInstance().getNnToken());
    this.nns_webtoken = new StringParams("nns_webtoken");
    this.nns_webtoken.setValue(GlobalLogic.getInstance().getWebToken());
    this.cacheValidTime = -1L;
  }

  public String getApiName()
  {
    return "N39_A_23";
  }

  public String toString()
  {
    String str = this.prefix;
    if (!str.contains("?"))
      str = str + "?";
    return str + this.nns_func + this.nns_packet_id + this.nns_token + this.nns_webtoken + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.GetMediaAssetsBindLabelParams
 * JD-Core Version:    0.6.2
 */