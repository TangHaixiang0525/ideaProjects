package com.starcor.core.epgapi.params;

import com.starcor.config.DeviceInfo;
import com.starcor.core.domain.AppInfo;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.StringParams;
import com.starcor.core.utils.GeneralUtils;
import com.starcor.core.utils.Logger;
import java.security.MessageDigest;
import org.json.JSONException;
import org.json.JSONObject;

public class UserCenterLoginParams extends Api
{
  public StringParams nns_params;
  public StringParams nns_version;

  public UserCenterLoginParams(String paramString1, String paramString2, String paramString3)
  {
    this.prefix = AppInfo.URL_n212_b;
    this.nns_func = new StringParams("nns_func");
    this.nns_func.setValue("user_center_login");
    this.nns_params = new StringParams("nns_params");
    try
    {
      String str = GeneralUtils.MD5(GeneralUtils.MD5(GeneralUtils.MD5(paramString2)));
      JSONObject localJSONObject1 = new JSONObject();
      localJSONObject1.put("username", paramString1);
      localJSONObject1.put("password", str);
      localJSONObject1.put("license", paramString3);
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

  private String encodeByMD5(String paramString)
  {
    if (paramString != null)
      try
      {
        String str = bytetoString(MessageDigest.getInstance("MD5").digest(paramString.getBytes()));
        return str;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    return null;
  }

  public String bytetoString(byte[] paramArrayOfByte)
  {
    String str1 = "";
    int i = 1;
    if (i < paramArrayOfByte.length)
    {
      String str2 = Integer.toHexString(0xFF & paramArrayOfByte[i]);
      if (str2.length() == 1);
      for (str1 = str1 + "0" + str2; ; str1 = str1 + str2)
      {
        i++;
        break;
      }
    }
    return str1.toLowerCase();
  }

  public String getApiName()
  {
    return "N212_B_1";
  }

  public String toString()
  {
    String str1 = this.prefix;
    if (!str1.contains("?"))
      str1 = str1 + "?";
    String str2 = str1 + this.nns_func + this.nns_version + this.nns_params + this.suffix;
    Logger.i("", "url======" + str2);
    return str2;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.UserCenterLoginParams
 * JD-Core Version:    0.6.2
 */