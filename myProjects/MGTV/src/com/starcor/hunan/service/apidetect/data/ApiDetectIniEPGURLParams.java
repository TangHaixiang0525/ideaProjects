package com.starcor.hunan.service.apidetect.data;

import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.StringParams;

public class ApiDetectIniEPGURLParams extends Api
{
  private StringParams nns_device_id = new StringParams("nns_device_id");
  private String nns_language;
  private StringParams nns_smart_card_id;
  private StringParams nns_token;
  private StringParams nns_user_id;
  private StringParams nns_webtoken;

  public ApiDetectIniEPGURLParams(String paramString1, String paramString2)
  {
    this.taksCategory = 3;
    this.prefix = ApiDetectAppInfo.URL_n3_a;
    this.nns_smart_card_id = new StringParams("nns_smart_card_id");
    this.nns_user_id = new StringParams("nns_user_id");
    this.nns_token = new StringParams("nns_token");
    this.nns_token.setValue(paramString1);
    this.nns_webtoken = new StringParams("nns_webtoken");
    this.nns_webtoken.setValue(paramString2);
    this.nns_language = "";
    this.cacheValidTime = 0L;
    this.retryNum = 3;
  }

  public ApiDetectIniEPGURLParams(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    this.taksCategory = 3;
    this.prefix = ApiDetectAppInfo.URL_n3_a;
    this.nns_device_id.setValue(paramString3);
    this.nns_smart_card_id = new StringParams("nns_smart_card_id");
    this.nns_user_id = new StringParams("nns_user_id");
    this.nns_token = new StringParams("nns_token");
    this.nns_token.setValue(paramString1);
    this.nns_webtoken = new StringParams("nns_webtoken");
    this.nns_webtoken.setValue(paramString2);
    this.nns_language = "";
    this.cacheValidTime = 0L;
    this.retryNum = 3;
  }

  public String getApiName()
  {
    return "N3_A_2";
  }

  public StringParams getNns_device_id()
  {
    return this.nns_device_id;
  }

  public String getNns_language()
  {
    return this.nns_language;
  }

  public StringParams getNns_smart_card_id()
  {
    return this.nns_smart_card_id;
  }

  public StringParams getNns_user_id()
  {
    return this.nns_user_id;
  }

  public String toString()
  {
    String str = this.prefix;
    if (!str.contains("?"))
      str = str + "?";
    return str + this.nns_device_id + this.nns_smart_card_id + this.nns_user_id + this.nns_token + this.nns_webtoken + this.nns_language + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.service.apidetect.data.ApiDetectIniEPGURLParams
 * JD-Core Version:    0.6.2
 */