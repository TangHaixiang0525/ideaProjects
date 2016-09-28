package com.starcor.core.epgapi.params;

import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.StringParams;
import com.starcor.mgtv.boss.WebUrlBuilder;

public class SpeedParams extends Api
{
  StringParams fileIDpParams = null;

  public SpeedParams(String paramString)
  {
    this.taksCategory = 44;
    this.prefix = WebUrlBuilder.getSpeedUrl();
    this.fileIDpParams.setValue(paramString);
    this.cacheValidTime = 0L;
  }

  public String getApiName()
  {
    return "testSpeed";
  }

  public String toString()
  {
    String str = this.prefix;
    if (!str.contains("?"))
      str = str + "?";
    return str + this.fileIDpParams;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.SpeedParams
 * JD-Core Version:    0.6.2
 */