package com.starcor.core.epgapi.params;

import com.starcor.core.domain.AppInfo;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.StringParams;
import com.starcor.core.logic.GlobalLogic;

public class GetUserAuthParams extends Api
{
  private StringParams nns_token;
  private StringParams nns_webtoken;
  private StringParams user_id;
  private StringParams video_id;
  private StringParams video_type;

  public GetUserAuthParams(String paramString1, String paramString2, String paramString3)
  {
    this.taksCategory = 66;
    this.prefix = AppInfo.URL_n200;
    this.nns_func.setValue("get_authorize_by_video_id");
    this.user_id = new StringParams("nns_user_id");
    this.user_id.setValue(paramString1);
    this.video_id = new StringParams("nns_video_id");
    this.video_id.setValue(paramString2);
    this.video_type = new StringParams("nns_video_type");
    this.video_type.setValue(paramString3);
    this.nns_token = new StringParams("nns_token");
    this.nns_token.setValue(GlobalLogic.getInstance().getNnToken());
    this.nns_webtoken = new StringParams("nns_webtoken");
    this.nns_webtoken.setValue(GlobalLogic.getInstance().getWebToken());
    this.cacheValidTime = 0L;
  }

  public String getApiName()
  {
    return "N200_A_1";
  }

  public String toString()
  {
    String str = this.prefix;
    if (!str.contains("?"))
      str = str + "?";
    return str + this.nns_func + this.user_id + this.video_id + this.video_type + this.nns_token + this.nns_webtoken + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.GetUserAuthParams
 * JD-Core Version:    0.6.2
 */