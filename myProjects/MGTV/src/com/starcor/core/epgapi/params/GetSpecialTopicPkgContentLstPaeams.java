package com.starcor.core.epgapi.params;

import com.starcor.core.domain.AppInfo;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.StringParams;

public class GetSpecialTopicPkgContentLstPaeams extends Api
{
  private StringParams include_category;
  private StringParams page_index;
  private StringParams page_size;
  private StringParams sort_type;
  private StringParams special_id;

  public GetSpecialTopicPkgContentLstPaeams(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    this.taksCategory = 20131204;
    this.prefix = AppInfo.URL_n24_a;
    this.nns_func.setValue("get_special_item_list");
    this.special_id = new StringParams("nns_special_id");
    this.special_id.setValue(paramString1);
    this.page_index = new StringParams("nns_page_index");
    this.page_index.setValue(paramString2);
    this.page_size = new StringParams("nns_page_size");
    this.page_size.setValue(paramString3);
    this.sort_type = new StringParams("nns_sort_type");
    this.sort_type.setValue(paramString4);
    this.include_category = new StringParams("nns_include_category");
    this.include_category.setValue(paramString5);
  }

  public String getApiName()
  {
    return "N24_A_2";
  }

  public String toString()
  {
    String str = this.prefix;
    if (!str.contains("?"))
      str = str + "?";
    return str + this.nns_func + this.special_id + this.page_index + this.page_size + this.sort_type + this.include_category + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.GetSpecialTopicPkgContentLstPaeams
 * JD-Core Version:    0.6.2
 */