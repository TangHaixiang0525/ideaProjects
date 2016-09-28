package com.starcor.core.epgapi.params;

import com.starcor.config.DeviceInfo;
import com.starcor.core.domain.AppInfo;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.StringParams;
import com.starcor.core.logic.GlobalEnv;
import com.starcor.core.utils.Logger;
import org.json.JSONException;
import org.json.JSONObject;

public class XiaomiLoginParams extends Api
{
  public StringParams nns_params;
  public StringParams nns_version;

  public XiaomiLoginParams(String paramString1, String paramString2)
  {
    this.prefix = AppInfo.URL_n212_b;
    this.nns_func = new StringParams("nns_func");
    this.nns_func.setValue("xiaomi_user_account_login");
    this.nns_params = new StringParams("nns_params");
    try
    {
      JSONObject localJSONObject = new JSONObject();
      localJSONObject.put("invoker", "itvsdk");
      localJSONObject.put("code", paramString1);
      localJSONObject.put("license", paramString2);
      localJSONObject.put("version", DeviceInfo.getMGTVVersion());
      localJSONObject.put("mac", DeviceInfo.getMac());
      localJSONObject.put("uip", GlobalEnv.getInstance().getAAANetIp());
      this.nns_params.setValue(localJSONObject.toString());
      this.nns_version = new StringParams("nns_version");
      this.nns_version.setValue(DeviceInfo.getMGTVVersion());
      this.cacheValidTime = 0L;
      return;
    }
    catch (JSONException localJSONException)
    {
      while (true)
        localJSONException.printStackTrace();
    }
  }

  public String getApiName()
  {
    return "n212_B_16";
  }

  public String toString()
  {
    String str1 = this.prefix;
    if (!str1.contains("?"))
      str1 = str1 + "?";
    String str2 = str1 + this.nns_func + this.nns_version + this.nns_params + this.suffix;
    Logger.i("xxxxxx url=" + str2);
    return str2;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.XiaomiLoginParams
 * JD-Core Version:    0.6.2
 */