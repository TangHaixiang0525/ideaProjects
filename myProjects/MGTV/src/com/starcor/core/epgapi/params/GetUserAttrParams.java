package com.starcor.core.epgapi.params;

import com.starcor.config.DeviceInfo;
import com.starcor.core.domain.AppInfo;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.StringParams;
import com.starcor.core.logic.GlobalLogic;

public class GetUserAttrParams extends Api
{
  private StringParams nns_user_id;
  private StringParams nns_version;

  public GetUserAttrParams()
  {
    this.prefix = AppInfo.URL_n40_f;
    this.nns_func.setValue("get_user_attr");
    this.nns_user_id = new StringParams("nns_user_id");
    this.nns_user_id.setValue(GlobalLogic.getInstance().getUserId());
    this.nns_version = new StringParams("nns_version");
    this.nns_version.setValue(DeviceInfo.getMGTVVersion());
    this.cacheValidTime = -1L;
  }

  public String getApiName()
  {
    return "N40_F_1";
  }

  public String toString()
  {
    String str = this.prefix;
    if (!str.contains("?"))
      str = str + "?";
    return str + this.nns_func + this.nns_user_id + this.nns_version + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.GetUserAttrParams
 * JD-Core Version:    0.6.2
 */