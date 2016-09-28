package com.starcor.core.epgapi.params;

import com.starcor.config.MgtvUrl;
import com.starcor.core.epgapi.Api;

public class InitMainURLParams extends Api
{
  public InitMainURLParams()
  {
    this.taksCategory = 1;
    this.cacheValidTime = 0L;
    this.retryNum = 3;
  }

  public String getApiName()
  {
    return "N1_A_1";
  }

  public String toString()
  {
    return MgtvUrl.N1_A();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.InitMainURLParams
 * JD-Core Version:    0.6.2
 */