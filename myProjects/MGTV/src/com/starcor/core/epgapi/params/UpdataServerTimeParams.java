package com.starcor.core.epgapi.params;

import com.starcor.core.domain.AppInfo;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.StringParams;
import com.starcor.core.logic.GlobalLogic;

public class UpdataServerTimeParams extends Api
{
  private StringParams nns_token;
  private StringParams nns_webtoken;

  public UpdataServerTimeParams()
  {
    this.taksCategory = 16;
    this.prefix = (AppInfo.URL_n2_a + "?");
    this.nns_func.setValue("sync_time");
    this.nns_token = new StringParams("nns_token");
    this.nns_token.setValue(GlobalLogic.getInstance().getNnToken());
    this.nns_webtoken = new StringParams("nns_webtoken");
    this.nns_webtoken.setValue(GlobalLogic.getInstance().getWebToken());
    this.cacheValidTime = 0L;
  }

  public String getApiName()
  {
    return "N2_A_3";
  }

  public String toString()
  {
    String str = this.prefix;
    if (!str.contains("?"))
      str = str + "?";
    return str + this.nns_func + this.nns_token + this.nns_webtoken + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.UpdataServerTimeParams
 * JD-Core Version:    0.6.2
 */