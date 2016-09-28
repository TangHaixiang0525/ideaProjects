package com.starcor.core.epgapi.params;

import com.starcor.config.DeviceInfo;
import com.starcor.core.domain.AppInfo;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.StringParams;
import com.starcor.core.logic.GlobalLogic;

public class GetUserRecommendParams extends Api
{
  private StringParams nns_func = new StringParams("nns_func");
  private StringParams nns_user_id = new StringParams("nns_user_id");
  private StringParams nns_version = new StringParams("nns_version");
  private StringParams nns_webtoken = new StringParams("nns_webtoken");

  public GetUserRecommendParams()
  {
    this.nns_func.setValue("get_user_recommend");
    this.nns_user_id.setValue(GlobalLogic.getInstance().getUserId());
    this.nns_version.setValue(DeviceInfo.getMGTVVersion());
    this.nns_webtoken.setValue(GlobalLogic.getInstance().getWebToken());
  }

  public String getApiName()
  {
    return "N40_D_1";
  }

  public String toString()
  {
    String str = AppInfo.URL_n40_d;
    if (!str.contains("?"))
      str = str + "?";
    return str + this.nns_func + this.nns_user_id + this.nns_version + this.nns_webtoken + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.GetUserRecommendParams
 * JD-Core Version:    0.6.2
 */