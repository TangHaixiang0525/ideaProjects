package com.starcor.core.epgapi.params;

import com.starcor.config.MgtvVersion;
import com.starcor.core.domain.AppInfo;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.StringParams;

public class GetPublicImageParams extends Api
{
  private StringParams nns_type;
  private StringParams version;

  public GetPublicImageParams(String paramString)
  {
    this.prefix = AppInfo.URL_n36_a;
    this.nns_func.setValue("get_public_image");
    this.version = new StringParams("nns_version");
    this.version.setValue(MgtvVersion.getVersion());
    this.nns_type = new StringParams("nns_type");
    this.nns_type.setValue(paramString);
  }

  public String getApiName()
  {
    return "N36_A_3";
  }

  public String toString()
  {
    String str = this.prefix;
    if (!str.contains("?"))
      str = str + "?";
    return str + this.nns_func + this.version + this.nns_type + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.GetPublicImageParams
 * JD-Core Version:    0.6.2
 */