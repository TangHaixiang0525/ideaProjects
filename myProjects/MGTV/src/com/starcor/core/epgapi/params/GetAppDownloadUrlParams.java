package com.starcor.core.epgapi.params;

import com.starcor.core.domain.AppInfo;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.StringParams;
import com.starcor.core.logic.GlobalLogic;

public class GetAppDownloadUrlParams extends Api
{
  private StringParams nns_token;
  private StringParams nns_version_id;

  public GetAppDownloadUrlParams(String paramString)
  {
    this.prefix = AppInfo.URL_n650_a;
    this.nns_func.setValue("get_app_download_url");
    this.nns_version_id = new StringParams("nns_version_id");
    this.nns_version_id.setValue(paramString);
    this.nns_token = new StringParams("nns_token");
    this.nns_token.setValue(GlobalLogic.getInstance().getNnToken());
  }

  public String getApiName()
  {
    return "N650_A_3";
  }

  public String toString()
  {
    String str = this.prefix;
    if (!str.contains("?"))
      str = str + "?";
    return str + this.nns_func + this.nns_version_id + this.nns_token + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.GetAppDownloadUrlParams
 * JD-Core Version:    0.6.2
 */