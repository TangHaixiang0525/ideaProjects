package com.starcor.core.epgapi.params;

import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.StringParams;
import com.starcor.core.utils.Logger;

public class AddUserScoreParams extends Api
{
  private StringParams nns_score;
  private StringParams nns_user_id;
  private StringParams nns_video_id;

  public AddUserScoreParams(String paramString1, int paramInt, String paramString2)
  {
    this.prefix = com.starcor.core.domain.AppInfo.URL_n3_a_i[3];
    this.nns_func = new StringParams("nns_func");
    this.nns_func.setValue("set_media_assets_item_score");
    this.nns_video_id = new StringParams("nns_video_id");
    this.nns_video_id.setValue(paramString1);
    this.nns_score = new StringParams("nns_score");
    this.nns_score.setValue(paramInt + "");
    this.nns_user_id = new StringParams("nns_user_id");
    this.nns_user_id.setValue(paramString2);
  }

  public String getApiName()
  {
    return "N3_A_D_4";
  }

  public String toString()
  {
    String str1 = this.prefix;
    if (!str1.contains("?"))
      str1 = str1 + "?";
    String str2 = str1 + this.nns_func + this.nns_video_id + this.nns_score + this.nns_user_id + this.suffix;
    Logger.i("AddUserScoreParams", "N3_A_D_4 url" + str2);
    return str2;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.AddUserScoreParams
 * JD-Core Version:    0.6.2
 */