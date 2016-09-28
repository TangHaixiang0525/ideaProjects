package com.starcor.core.epgapi.params;

import com.starcor.config.DeviceInfo;
import com.starcor.core.domain.AppInfo;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.IntegerParams;
import com.starcor.core.epgapi.StringParams;

public class GetHotAppListParams extends Api
{
  private IntegerParams nns_num;
  private StringParams nns_version;

  public GetHotAppListParams(int paramInt)
  {
    this.prefix = AppInfo.URL_n650_a;
    this.nns_func.setValue("get_hot_app_list");
    this.nns_num = new IntegerParams("nns_num");
    this.nns_num.setValue(paramInt);
    this.nns_version = new StringParams("nns_version");
    this.nns_version.setValue(DeviceInfo.getMGTVVersion());
  }

  public String getApiName()
  {
    return "N650_A_5";
  }

  public String toString()
  {
    String str = this.prefix;
    if (!str.contains("?"))
      str = str + "?";
    return str + this.nns_func + this.nns_num + this.nns_version + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.GetHotAppListParams
 * JD-Core Version:    0.6.2
 */