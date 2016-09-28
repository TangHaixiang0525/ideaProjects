package com.starcor.core.epgapi.params;

import com.starcor.core.domain.AppInfo;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.StringParams;

public class CheckVersionUpdataParams extends Api
{
  private StringParams deviceId;
  private StringParams macId;
  private StringParams smartCardId;
  private StringParams userId;
  private StringParams version;

  public CheckVersionUpdataParams(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    this.taksCategory = 20130621;
    this.prefix = AppInfo.URL_n22_a;
    this.nns_func.setValue("check_update");
    this.version = new StringParams("nns_version");
    this.version.setValue(paramString1);
    this.macId = new StringParams("nns_mac_id");
    this.macId.setValue(paramString2);
    this.deviceId = new StringParams("nns_device_id");
    this.deviceId.setValue(paramString3);
    this.smartCardId = new StringParams("nns_smart_card_id");
    this.smartCardId.setValue(paramString4);
    this.userId = new StringParams("nns_user_id");
    this.userId.setValue(paramString5);
    this.cacheValidTime = 0L;
  }

  public String getApiName()
  {
    return "N22_A_1";
  }

  public String toString()
  {
    String str = this.prefix;
    if (!str.contains("?"))
      str = str + "?";
    return str + this.nns_func + this.version + this.macId + this.deviceId + this.smartCardId + this.userId + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.CheckVersionUpdataParams
 * JD-Core Version:    0.6.2
 */