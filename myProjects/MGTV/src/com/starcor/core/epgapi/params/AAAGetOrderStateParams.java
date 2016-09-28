package com.starcor.core.epgapi.params;

import android.text.TextUtils;
import com.starcor.config.DeviceInfo;
import com.starcor.core.domain.AppInfo;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.StringParams;
import org.json.JSONException;
import org.json.JSONObject;

public class AAAGetOrderStateParams extends Api
{
  private StringParams nns_params;
  private StringParams nns_version;

  public AAAGetOrderStateParams(String paramString1, String paramString2, int paramInt, String paramString3, String paramString4)
  {
    this.prefix = AppInfo.URL_n212_a;
    this.nns_func.setValue("aaa_get_order_status");
    this.nns_params = new StringParams("nns_params");
    try
    {
      localJSONObject1 = new JSONObject();
      localJSONObject1.put("license", paramString1);
      localJSONObject1.put("ticket", paramString2);
      localJSONObject1.put("business_id", paramInt);
      if ((TextUtils.isEmpty(paramString3)) && (TextUtils.isEmpty(paramString4)))
        localJSONObject1.put("order_id", paramString3);
      while (true)
      {
        JSONObject localJSONObject2 = new JSONObject();
        localJSONObject2.put("sign", "");
        localJSONObject2.put("version", DeviceInfo.getMGTVVersion());
        localJSONObject2.put("data", localJSONObject1);
        this.nns_params.setValue(localJSONObject2.toString());
        this.nns_version = new StringParams("nns_version");
        this.nns_version.setValue(DeviceInfo.getMGTVVersion());
        this.cacheValidTime = 0L;
        return;
        if (TextUtils.isEmpty(paramString3))
          break;
        localJSONObject1.put("order_id", paramString3);
      }
    }
    catch (JSONException localJSONException)
    {
      while (true)
      {
        JSONObject localJSONObject1;
        localJSONException.printStackTrace();
        continue;
        localJSONObject1.put("short_code", paramString4);
      }
    }
  }

  public String getApiName()
  {
    return "N212_A_11";
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
 * Qualified Name:     com.starcor.core.epgapi.params.AAAGetOrderStateParams
 * JD-Core Version:    0.6.2
 */