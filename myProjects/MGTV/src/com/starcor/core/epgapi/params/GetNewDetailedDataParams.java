package com.starcor.core.epgapi.params;

import com.starcor.core.domain.AppInfo;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.StringParams;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.utils.Logger;

public class GetNewDetailedDataParams extends Api
{
  private static final String TAG = GetNewDetailedDataParams.class.getSimpleName();
  private StringParams nns_token;
  private StringParams nns_video_id;
  private StringParams nns_webtoken;

  public GetNewDetailedDataParams(String paramString)
  {
    this.prefix = AppInfo.URL_n39_a;
    this.nns_func.setValue("get_dy_data_by_video_id");
    this.nns_video_id = new StringParams("nns_video_id");
    this.nns_video_id.setValue(paramString);
    this.nns_token = new StringParams("nns_token");
    this.nns_token.setValue(GlobalLogic.getInstance().getNnToken());
    this.nns_webtoken = new StringParams("nns_webtoken");
    this.nns_webtoken.setValue(GlobalLogic.getInstance().getWebToken());
    this.cacheValidTime = -1L;
  }

  public String getApiName()
  {
    return "N39_A_6";
  }

  public String getPrefix()
  {
    return super.getPrefix();
  }

  public String toString()
  {
    String str1 = this.prefix;
    if (!str1.contains("?"))
      str1 = str1 + "?";
    String str2 = str1 + this.nns_func + this.nns_video_id + this.nns_token + this.nns_webtoken + this.suffix;
    Logger.i(TAG, "toString() 返回url=" + str2);
    return str2;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.GetNewDetailedDataParams
 * JD-Core Version:    0.6.2
 */