package com.starcor.core.epgapi.params;

import com.starcor.config.MgtvUrl;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.StringParams;

public class GetSecretKeysParams extends Api
{
  public GetSecretKeysParams()
  {
    this.prefix = MgtvUrl.getN41SecretKeysUrl();
    this.nns_func.setValue("get_secret_keys");
  }

  public String getApiName()
  {
    return "N41_A_1";
  }

  public String toString()
  {
    String str = this.prefix;
    if (str == null)
      return null;
    if (!str.contains("?"))
      str = str + "?";
    return str + this.nns_func + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.GetSecretKeysParams
 * JD-Core Version:    0.6.2
 */