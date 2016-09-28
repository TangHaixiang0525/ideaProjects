package com.starcor.core.epgapi.params;

import com.starcor.core.domain.AppInfo;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.StringParams;
import com.starcor.core.logic.GlobalLogic;

public class CheckUiPackageUpdateParams extends Api
{
  private StringParams nns_token;
  private StringParams nns_ui_version;
  private StringParams nns_version;
  private StringParams nns_webtoken;

  public CheckUiPackageUpdateParams(String paramString1, String paramString2)
  {
    this.prefix = AppInfo.URL_n38_a;
    this.nns_func.setValue("check_ui_package_update");
    this.nns_version = new StringParams("nns_version");
    this.nns_version.setValue(paramString1);
    this.nns_ui_version = new StringParams("nns_ui_version");
    this.nns_ui_version.setValue(paramString2);
    this.nns_token = new StringParams("nns_token");
    this.nns_token.setValue(GlobalLogic.getInstance().getNnToken());
    this.nns_webtoken = new StringParams("nns_webtoken");
    this.nns_webtoken.setValue(GlobalLogic.getInstance().getWebToken());
    this.cacheValidTime = 0L;
  }

  public String getApiName()
  {
    return "N38_A_2";
  }

  public String toString()
  {
    String str = this.prefix;
    if (!str.contains("?"))
      str = str + "?";
    return str + this.nns_func + this.nns_version + this.nns_ui_version + this.nns_token + this.nns_webtoken + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.CheckUiPackageUpdateParams
 * JD-Core Version:    0.6.2
 */