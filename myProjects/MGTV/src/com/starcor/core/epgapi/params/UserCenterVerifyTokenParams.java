package com.starcor.core.epgapi.params;

import com.starcor.config.DeviceInfo;
import com.starcor.core.domain.AppInfo;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.StringParams;
import org.json.JSONException;
import org.json.JSONObject;

public class UserCenterVerifyTokenParams extends Api
{
  private StringParams nns_params;
  public StringParams nns_version;

  public UserCenterVerifyTokenParams(String paramString1, String paramString2)
  {
    this.prefix = AppInfo.URL_n212_b;
    this.nns_func.setValue("user_center_token_auth");
    this.nns_params = new StringParams("nns_params");
    try
    {
      JSONObject localJSONObject1 = new JSONObject();
      localJSONObject1.put("ticket", paramString1);
      localJSONObject1.put("license", paramString2);
      localJSONObject1.put("version", DeviceInfo.getMGTVVersion());
      localJSONObject1.put("mac", DeviceInfo.getMac());
      JSONObject localJSONObject2 = new JSONObject();
      localJSONObject2.put("sign", "");
      localJSONObject2.put("invoker", "itvsdk");
      localJSONObject2.put("data", localJSONObject1);
      this.nns_params.setValue(localJSONObject2.toString());
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
    return "N212_B_11";
  }

  public String toString()
  {
    String str = this.prefix;
    if (!str.contains("?"))
      str = str + "?";
    return str + this.nns_func + this.nns_params + this.nns_version + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.UserCenterVerifyTokenParams
 * JD-Core Version:    0.6.2
 */