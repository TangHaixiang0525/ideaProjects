package com.starcor.core.epgapi.params;

import com.starcor.core.domain.AppInfo;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.StringParams;

public class UserLoginParams extends Api
{
  private StringParams nns_mac_id;
  private StringParams password;
  private StringParams userName;

  public UserLoginParams(String paramString1, String paramString2, String paramString3)
  {
    this.taksCategory = 2014122;
    this.prefix = AppInfo.URL_n200;
    this.nns_func.setValue("check_valid_by_login");
    this.userName = new StringParams("nns_user_name");
    this.userName.setValue(paramString1);
    this.password = new StringParams("nns_user_password");
    this.password.setValue(paramString2);
    this.nns_mac_id = new StringParams("nns_mac_id");
    this.nns_mac_id.setValue(paramString3);
    this.cacheValidTime = 0L;
  }

  public String getApiName()
  {
    return "N200_A_5";
  }

  public String toString()
  {
    String str = this.prefix;
    if (!str.contains("?"))
      str = str + "?";
    return str + this.nns_func + this.userName + this.password + this.nns_mac_id + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.UserLoginParams
 * JD-Core Version:    0.6.2
 */