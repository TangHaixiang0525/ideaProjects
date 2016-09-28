package com.starcor.core.epgapi.params;

import com.starcor.core.domain.AppInfo;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.StringParams;

public class GetSpecialTopicColumnTreePaeams extends Api
{
  private StringParams output;
  private StringParams special_id;

  public GetSpecialTopicColumnTreePaeams(String paramString)
  {
    this.taksCategory = 2013120415;
    this.prefix = AppInfo.URL_n24_a;
    this.nns_func.setValue("get_special_info");
    this.special_id = new StringParams("nns_special_id");
    this.special_id.setValue(paramString);
    this.output = new StringParams("OutputType");
    this.output.setValue("json");
  }

  public String getApiName()
  {
    return "N24_A_1";
  }

  public String toString()
  {
    String str = this.prefix;
    if (!str.contains("?"))
      str = str + "?";
    return str + this.nns_func + this.special_id + this.output;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.GetSpecialTopicColumnTreePaeams
 * JD-Core Version:    0.6.2
 */