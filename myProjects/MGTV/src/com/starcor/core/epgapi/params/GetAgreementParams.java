package com.starcor.core.epgapi.params;

import com.starcor.core.domain.AppInfo;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.StringParams;

public class GetAgreementParams extends Api
{
  public GetAgreementParams()
  {
    this.prefix = AppInfo.URL_n39_a;
    this.nns_func.setValue("get_agreement_list");
  }

  public String getApiName()
  {
    return "N39_A_13";
  }

  public String toString()
  {
    String str = this.prefix;
    if (!str.contains("?"))
      str = str + "?";
    return str + this.nns_func + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.GetAgreementParams
 * JD-Core Version:    0.6.2
 */