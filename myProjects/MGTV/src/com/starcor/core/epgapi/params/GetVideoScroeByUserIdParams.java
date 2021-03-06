package com.starcor.core.epgapi.params;

import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.StringParams;
import com.starcor.core.logic.GlobalLogic;

public class GetVideoScroeByUserIdParams extends Api
{
  private StringParams nns_token;
  private StringParams nns_webtoken;
  private StringParams user_id;
  private StringParams video_id;

  public GetVideoScroeByUserIdParams(String paramString1, String paramString2)
  {
    this.taksCategory = 15;
    this.prefix = com.starcor.core.domain.AppInfo.URL_n3_a_i[0];
    this.nns_func.setValue("get_user_score_by_video_id");
    this.video_id = new StringParams("nns_video_id");
    this.video_id.setValue(paramString1);
    this.user_id = new StringParams("nns_user_id");
    this.user_id.setValue(paramString2);
    this.nns_token = new StringParams("nns_token");
    this.nns_token.setValue(GlobalLogic.getInstance().getNnToken());
    this.nns_webtoken = new StringParams("nns_webtoken");
    this.nns_webtoken.setValue(GlobalLogic.getInstance().getWebToken());
    this.cacheValidTime = -1L;
  }

  public String getApiName()
  {
    return "N3_A_A_15";
  }

  public String toString()
  {
    String str = this.prefix;
    if (!str.contains("?"))
      str = str + "?";
    return str + this.nns_func + this.video_id + this.user_id + this.nns_token + this.nns_webtoken + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.GetVideoScroeByUserIdParams
 * JD-Core Version:    0.6.2
 */