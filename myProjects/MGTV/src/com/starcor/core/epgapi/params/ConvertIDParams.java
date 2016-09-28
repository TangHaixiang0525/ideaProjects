package com.starcor.core.epgapi.params;

import com.starcor.core.domain.AppInfo;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.IntegerParams;
import com.starcor.core.epgapi.StringParams;

public class ConvertIDParams extends Api
{
  private StringParams nns_ids;
  private StringParams nns_index;
  private IntegerParams nns_mode;
  private StringParams nns_type;
  private IntegerParams nns_video_type;

  public ConvertIDParams(String paramString1, String paramString2, int paramInt1, int paramInt2, int paramInt3)
  {
    this.prefix = AppInfo.URL_n39_a;
    this.nns_func.setValue("transformat_keys");
    this.nns_ids = new StringParams("nns_ids");
    this.nns_ids.setValue(paramString1);
    this.nns_index = new StringParams("nns_index");
    this.nns_index.setValue(paramString2);
    this.nns_mode = new IntegerParams("nns_mode");
    this.nns_mode.setValue(paramInt1);
    this.nns_type = new StringParams("nns_type");
    switch (paramInt2)
    {
    default:
    case 0:
    case 1:
    case 2:
    }
    while (true)
    {
      this.nns_video_type = new IntegerParams("nns_video_type");
      this.nns_video_type.setValue(paramInt3);
      return;
      this.nns_type.setValue("video");
      continue;
      this.nns_type.setValue("index");
      continue;
      this.nns_type.setValue("media");
    }
  }

  public String getApiName()
  {
    return "N39_A_8";
  }

  public String toString()
  {
    String str = this.prefix;
    if (!str.contains("?"))
      str = str + "?";
    return str + this.nns_func + this.nns_ids + this.nns_index + this.nns_mode + this.nns_type + this.nns_video_type + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.ConvertIDParams
 * JD-Core Version:    0.6.2
 */