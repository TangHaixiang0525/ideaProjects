package com.starcor.core.epgapi.params;

import com.starcor.core.domain.AppInfo;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.StringParams;
import com.starcor.core.logic.GlobalLogic;

public class CheckTokenParams extends Api
{
  private StringParams nns_device_id;
  private StringParams nns_mac_id;
  private StringParams nns_smart_card_id;
  private StringParams nns_user_id;
  private StringParams nns_webtoken;

  public CheckTokenParams(String paramString)
  {
    this.taksCategory = 2;
    this.prefix = AppInfo.URL_n200;
    this.nns_func.setValue("check_valid_by_webtoken");
    this.nns_mac_id = new StringParams("nns_mac_id");
    this.nns_mac_id.setValue(paramString);
    this.nns_device_id = new StringParams("nns_device_id");
    this.nns_device_id.setValue(GlobalLogic.getInstance().getDeviceId());
    this.nns_user_id = new StringParams("nns_user_id");
    this.nns_user_id.setValue(GlobalLogic.getInstance().getUserId());
    this.nns_smart_card_id = new StringParams("nns_smart_card_id");
    this.nns_smart_card_id.setValue(GlobalLogic.getInstance().getSmartCardId());
    this.nns_webtoken = new StringParams("nns_webtoken");
    this.nns_webtoken.setValue(GlobalLogic.getInstance().getCheckWebToken());
    this.cacheValidTime = 0L;
  }

  public String getApiName()
  {
    return "N200_A_2";
  }

  public String toString()
  {
    String str = this.prefix;
    if (!str.contains("?"))
      str = str + "?";
    return str + this.nns_func + this.nns_mac_id + this.nns_webtoken + this.nns_device_id + this.nns_smart_card_id + this.nns_user_id + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.CheckTokenParams
 * JD-Core Version:    0.6.2
 */