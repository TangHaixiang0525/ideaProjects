package com.starcor.core.epgapi.params;

import android.text.TextUtils;
import com.starcor.core.domain.AppInfo;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.StringParams;

public class GetSpecialTopicPutParams extends Api
{
  private StringParams category_id;
  private StringParams packet_id;
  private StringParams packet_type;
  private StringParams special_id;

  public GetSpecialTopicPutParams(String paramString1, String paramString2)
  {
    this.taksCategory = 10;
    this.prefix = AppInfo.URL_n24_a;
    this.nns_func.setValue("get_special_online");
    this.special_id = new StringParams("nns_special_id");
    this.special_id.setValue(paramString1);
    this.packet_id = new StringParams("nns_packet_id");
    this.packet_id.setValue(paramString1);
    this.packet_type = new StringParams("nns_packet_type");
    this.packet_type.setValue(paramString2);
  }

  public GetSpecialTopicPutParams(String paramString1, String paramString2, String paramString3)
  {
    this(paramString2, paramString3);
    this.special_id = new StringParams("nns_special_id");
    this.special_id.setValue(paramString1);
  }

  public GetSpecialTopicPutParams(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    this(paramString1, paramString2, paramString3);
    this.category_id = new StringParams("nns_category_id");
    this.category_id.setValue(paramString4);
  }

  public String getApiName()
  {
    return "N24_A_4";
  }

  public String toString()
  {
    String str1 = this.prefix;
    if (!str1.contains("?"))
      str1 = str1 + "?";
    String str2 = str1 + this.nns_func + this.packet_id + this.packet_type + this.category_id;
    if (!TextUtils.isEmpty(this.special_id.getValue()))
      str2 = str2 + this.special_id;
    return str2 + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.GetSpecialTopicPutParams
 * JD-Core Version:    0.6.2
 */