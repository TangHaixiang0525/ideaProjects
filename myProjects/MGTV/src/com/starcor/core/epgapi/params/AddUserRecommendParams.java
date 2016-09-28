package com.starcor.core.epgapi.params;

import com.starcor.config.DeviceInfo;
import com.starcor.core.domain.AppInfo;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.StringParams;
import com.starcor.core.logic.GlobalLogic;

public class AddUserRecommendParams extends Api
{
  private StringParams nns_assist_id = new StringParams("nns_assist_id");
  private StringParams nns_category_id = new StringParams("nns_category_id");
  private StringParams nns_func = new StringParams("nns_func");
  private StringParams nns_img_v;
  private StringParams nns_tstv_begin_time = new StringParams("nns_tstv_begin_time");
  private StringParams nns_tstv_time_len = new StringParams("nns_tstv_time_len");
  private StringParams nns_user_id = new StringParams("nns_user_id");
  private StringParams nns_version = new StringParams("nns_version");
  private StringParams nns_video_id = new StringParams("nns_video_id");
  private StringParams nns_video_name = new StringParams("nns_video_name");
  private StringParams nns_video_type = new StringParams("nns_video_type");
  private StringParams nns_webtoken = new StringParams("nns_webtoken");

  public AddUserRecommendParams(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7)
  {
    this.nns_func.setValue("set_user_recommend");
    this.nns_video_id.setValue(paramString1);
    this.nns_video_type.setValue(paramString2);
    this.nns_video_name.setValue(paramString3);
    this.nns_tstv_begin_time.setValue(paramString6);
    this.nns_tstv_time_len.setValue(paramString7);
    this.nns_assist_id.setValue(paramString4);
    this.nns_category_id.setValue(paramString5);
    this.nns_user_id.setValue(GlobalLogic.getInstance().getUserId());
    this.nns_version.setValue(DeviceInfo.getMGTVVersion());
    this.nns_webtoken.setValue(GlobalLogic.getInstance().getWebToken());
  }

  public String getApiName()
  {
    return "N40_D_2";
  }

  public String toString()
  {
    String str = AppInfo.URL_n40_d;
    if (!str.contains("?"))
      str = str + "?";
    return str + this.nns_func + this.nns_user_id + this.nns_video_id + this.nns_video_type + this.nns_tstv_begin_time + this.nns_tstv_time_len + this.nns_video_name + this.nns_assist_id + this.nns_category_id + this.nns_version + this.nns_webtoken + this.nns_img_v + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.AddUserRecommendParams
 * JD-Core Version:    0.6.2
 */