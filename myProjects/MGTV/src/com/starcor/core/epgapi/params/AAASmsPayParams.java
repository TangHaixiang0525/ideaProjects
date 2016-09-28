package com.starcor.core.epgapi.params;

import com.starcor.config.DeviceInfo;
import com.starcor.core.domain.AppInfo;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.StringParams;
import com.starcor.core.logic.GlobalEnv;
import com.starcor.core.logic.GlobalLogic;
import org.json.JSONException;
import org.json.JSONObject;

public class AAASmsPayParams extends Api
{
  private StringParams nns_params;
  private StringParams nns_version;

  public AAASmsPayParams(String paramString)
  {
    this.prefix = AppInfo.URL_n212_a;
    this.nns_func.setValue("aaa_note_paid");
    this.nns_version = new StringParams("nns_version");
    this.nns_version.setValue(DeviceInfo.getMGTVVersion());
    this.nns_params = new StringParams("nns_params");
    try
    {
      JSONObject localJSONObject1 = new JSONObject();
      localJSONObject1.put("license", paramString);
      localJSONObject1.put("ip", GlobalEnv.getInstance().getAAANetIp());
      localJSONObject1.put("mac", DeviceInfo.getMac());
      localJSONObject1.put("ticket", GlobalLogic.getInstance().getWebToken());
      localJSONObject1.put("business_id", 1);
      JSONObject localJSONObject2 = new JSONObject();
      localJSONObject2.put("data", localJSONObject1);
      localJSONObject2.put("version", DeviceInfo.getMGTVVersion());
      localJSONObject2.put("sign", paramString);
      this.nns_params.setValue(localJSONObject2.toString());
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
    return "N212_A_16";
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
 * Qualified Name:     com.starcor.core.epgapi.params.AAASmsPayParams
 * JD-Core Version:    0.6.2
 */