package com.starcor.core.epgapi.params;

import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.StringParams;
import com.starcor.core.logic.GlobalLogic;

public class GetCollectListParams extends Api
{
  private StringParams collect_type;
  private StringParams nns_token;
  private StringParams nns_webtoken;

  public GetCollectListParams(int paramInt)
  {
    this.taksCategory = 12;
    this.prefix = com.starcor.core.domain.AppInfo.URL_n3_a_i[0];
    this.nns_func.setValue("get_collect_list");
    this.collect_type = new StringParams("nns_collect_type");
    if (paramInt == 1)
      this.collect_type.setValue("collect");
    while (true)
    {
      this.nns_token = new StringParams("nns_token");
      this.nns_token.setValue(GlobalLogic.getInstance().getNnToken());
      this.nns_webtoken = new StringParams("nns_webtoken");
      this.nns_webtoken.setValue(GlobalLogic.getInstance().getWebToken());
      this.cacheValidTime = 0L;
      return;
      if (paramInt == 2)
        this.collect_type.setValue("playlist");
      else if (paramInt == 3)
        this.collect_type.setValue("catch");
    }
  }

  public String getApiName()
  {
    return "N3_A_A_6";
  }

  public String toString()
  {
    String str = this.prefix;
    if (!str.contains("?"))
      str = str + "?";
    return str + this.nns_func + this.collect_type + this.nns_token + this.nns_webtoken + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.GetCollectListParams
 * JD-Core Version:    0.6.2
 */