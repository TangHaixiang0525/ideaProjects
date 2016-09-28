package com.starcor.core.epgapi.params;

import android.text.TextUtils;
import com.starcor.core.domain.AppInfo;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.StringParams;
import com.starcor.core.logic.GlobalLogic;

public class GetCityInfoByIdParams extends Api
{
  private StringParams nns_city_id;
  private StringParams nns_token;
  private StringParams nns_webtoken;

  public GetCityInfoByIdParams(String paramString)
  {
    this.taksCategory = 100;
    this.prefix = AppInfo.URL_n100;
    this.nns_func.setValue("get_district_info_by_id");
    this.nns_city_id = new StringParams("city_id");
    if (TextUtils.isEmpty(paramString))
      this.nns_city_id.setValue("");
    while (true)
    {
      this.nns_token = new StringParams("nns_token");
      this.nns_token.setValue(GlobalLogic.getInstance().getNnToken());
      this.nns_webtoken = new StringParams("nns_webtoken");
      this.nns_webtoken.setValue(GlobalLogic.getInstance().getWebToken());
      this.cacheValidTime = -1L;
      return;
      this.nns_city_id.setValue(paramString);
    }
  }

  public String getApiName()
  {
    return "N100_A_2";
  }

  public String toString()
  {
    String str = this.prefix;
    if (!str.contains("?"))
      str = str + "?";
    return str + this.nns_func + this.nns_city_id + this.nns_token + this.nns_webtoken + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.GetCityInfoByIdParams
 * JD-Core Version:    0.6.2
 */