package com.starcor.core.epgapi.params;

import com.starcor.config.DeviceInfo;
import com.starcor.core.domain.AppInfo;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.StringParams;
import com.starcor.core.logic.GlobalEnv;
import org.json.JSONException;
import org.json.JSONObject;

public class AAAGetMovieCouponHistoryParams extends Api
{
  private StringParams nns_params;
  private StringParams nns_version;

  public AAAGetMovieCouponHistoryParams(String paramString1, int paramInt1, String paramString2, int paramInt2, int paramInt3)
  {
    this.prefix = AppInfo.URL_n212_a;
    this.nns_func.setValue("aaa_view_coupon_usedlist");
    this.nns_params = new StringParams("nns_params");
    try
    {
      JSONObject localJSONObject1 = new JSONObject();
      localJSONObject1.put("license", paramString2);
      localJSONObject1.put("ip", GlobalEnv.getInstance().getAAANetIp());
      localJSONObject1.put("mac", DeviceInfo.getMac());
      localJSONObject1.put("ticket", paramString1);
      localJSONObject1.put("business_id", paramInt1);
      localJSONObject1.put("page", paramInt2);
      localJSONObject1.put("page_num", paramInt3);
      JSONObject localJSONObject2 = new JSONObject();
      localJSONObject2.put("sign", "");
      localJSONObject2.put("version", DeviceInfo.getMGTVVersion());
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
    return "N212_A_20";
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
 * Qualified Name:     com.starcor.core.epgapi.params.AAAGetMovieCouponHistoryParams
 * JD-Core Version:    0.6.2
 */