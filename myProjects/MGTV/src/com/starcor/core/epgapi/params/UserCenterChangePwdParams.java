package com.starcor.core.epgapi.params;

import com.starcor.config.DeviceInfo;
import com.starcor.core.domain.AppInfo;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.StringParams;
import org.json.JSONException;
import org.json.JSONObject;

public class UserCenterChangePwdParams extends Api
{
  public StringParams nns_params;
  public StringParams nns_version;

  public UserCenterChangePwdParams(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    this.prefix = AppInfo.URL_n212_b;
    this.nns_func = new StringParams("nns_func");
    this.nns_func.setValue("user_center_change_pwd");
    this.nns_params = new StringParams("nns_params");
    try
    {
      JSONObject localJSONObject1 = new JSONObject();
      localJSONObject1.put("username", paramString1);
      localJSONObject1.put("oldpwd", paramString2);
      localJSONObject1.put("newpwd", paramString3);
      localJSONObject1.put("ticket", paramString4);
      localJSONObject1.put("license", paramString5);
      localJSONObject1.put("version", DeviceInfo.getMGTVVersion());
      localJSONObject1.put("mac", DeviceInfo.getMac());
      JSONObject localJSONObject2 = new JSONObject();
      localJSONObject2.put("sign", "");
      localJSONObject2.put("invoker", "itvsdk");
      localJSONObject2.put("data", localJSONObject1);
      this.nns_params.setValue(localJSONObject2.toString());
      this.nns_version = new StringParams("nns_version");
      this.nns_version.setValue(DeviceInfo.getMGTVVersion());
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
    return "N212_B_4";
  }

  public String toString()
  {
    String str = this.prefix;
    if (!str.contains("?"))
      str = str + "?";
    return str + this.nns_func + this.nns_version + this.nns_params + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.UserCenterChangePwdParams
 * JD-Core Version:    0.6.2
 */