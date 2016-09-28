package com.starcor.core.epgapi.params;

import com.starcor.core.domain.AppInfo;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.IntegerParams;
import com.starcor.core.epgapi.StringParams;
import com.starcor.core.logic.GlobalLogic;

public class ExistsUserWishParams extends Api
{
  private IntegerParams nns_type;
  private StringParams nns_user_id;
  private StringParams nns_video_id;

  public ExistsUserWishParams(String paramString)
  {
    this.prefix = AppInfo.URL_n40_g;
    this.nns_func.setValue("exists_user_wish");
    this.nns_user_id = new StringParams("nns_user_id");
    this.nns_user_id.setValue(GlobalLogic.getInstance().getUserId());
    this.nns_video_id = new StringParams("nns_video_id");
    this.nns_video_id.setValue(paramString);
    this.nns_type = new IntegerParams("nns_page_size");
    this.nns_type.setValue(0);
  }

  public String getApiName()
  {
    return "N40_G_2";
  }

  public String toString()
  {
    String str = this.prefix;
    if (!str.contains("?"))
      str = str + "?";
    return str + this.nns_func + this.nns_user_id + this.nns_video_id + this.nns_type + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.ExistsUserWishParams
 * JD-Core Version:    0.6.2
 */