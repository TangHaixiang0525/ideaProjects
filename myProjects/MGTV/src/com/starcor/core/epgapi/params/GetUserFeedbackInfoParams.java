package com.starcor.core.epgapi.params;

import com.starcor.config.DeviceInfo;
import com.starcor.core.domain.AppInfo;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.StringParams;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.utils.Logger;

public class GetUserFeedbackInfoParams extends Api
{
  private static final String TAG = GetUserFeedbackInfoParams.class.getSimpleName();
  private StringParams nns_page_index;
  private StringParams nns_page_size;
  private StringParams nns_token;
  private StringParams nns_version;
  private StringParams nns_webtoken;

  public GetUserFeedbackInfoParams(String paramString1, String paramString2)
  {
    this.prefix = AppInfo.URL_n23_a;
    this.nns_func.setValue("get_user_feedback");
    this.nns_page_size = new StringParams("nns_page_size");
    this.nns_page_size.setValue(paramString1);
    this.nns_page_index = new StringParams("nns_page_index");
    this.nns_page_index.setValue(paramString2);
    this.nns_version = new StringParams("nns_version");
    this.nns_version.setValue(DeviceInfo.getMGTVVersion());
    this.nns_token = new StringParams("nns_token");
    this.nns_token.setValue(GlobalLogic.getInstance().getNnToken());
    this.nns_webtoken = new StringParams("nns_webtoken");
    this.nns_webtoken.setValue(GlobalLogic.getInstance().getWebToken());
    this.cacheValidTime = -1L;
  }

  public String getApiName()
  {
    return "N23_A_2";
  }

  public String toString()
  {
    String str1 = this.prefix;
    if (!str1.contains("?"))
      str1 = str1 + "?";
    String str2 = str1 + this.nns_func + this.nns_page_size + this.nns_page_index + this.nns_version + this.nns_token + this.nns_webtoken + this.suffix;
    Logger.i(TAG, "toString() 返回url=" + str2);
    return str2;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.GetUserFeedbackInfoParams
 * JD-Core Version:    0.6.2
 */