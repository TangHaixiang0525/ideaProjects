package com.starcor.core.epgapi.params;

import com.starcor.core.domain.AppInfo;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.StringParams;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.utils.Logger;

public class GetActorStarInfoParams extends Api
{
  private static final String TAG = GetStarCollectParams.class.getSimpleName();
  private StringParams nns_actor_id;
  private StringParams nns_label_id;
  private StringParams nns_token;
  private StringParams nns_webtoken;

  public GetActorStarInfoParams(String paramString1, String paramString2)
  {
    this.prefix = AppInfo.URL_n39_a;
    this.nns_func.setValue("get_actor_star_info");
    this.nns_actor_id = new StringParams("nns_actor_id");
    this.nns_actor_id.setValue(paramString1);
    this.nns_label_id = new StringParams("nns_label_id");
    this.nns_label_id.setValue(paramString2);
    this.nns_token = new StringParams("nns_token");
    this.nns_token.setValue(GlobalLogic.getInstance().getNnToken());
    this.nns_webtoken = new StringParams("nns_webtoken");
    this.nns_webtoken.setValue(GlobalLogic.getInstance().getWebToken());
    this.cacheValidTime = -1L;
  }

  public String getApiName()
  {
    return "N39_A_5";
  }

  public String toString()
  {
    String str1 = this.prefix;
    if (!str1.contains("?"))
      str1 = str1 + "?";
    String str2 = str1 + this.nns_func + this.nns_actor_id + this.nns_label_id + this.nns_token + this.nns_webtoken + this.suffix;
    Logger.i(TAG, "toString() 返回url=" + str2);
    return str2;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.GetActorStarInfoParams
 * JD-Core Version:    0.6.2
 */