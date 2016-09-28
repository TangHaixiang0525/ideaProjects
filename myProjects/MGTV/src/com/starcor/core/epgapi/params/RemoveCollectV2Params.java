package com.starcor.core.epgapi.params;

import com.starcor.config.DeviceInfo;
import com.starcor.core.domain.AppInfo;
import com.starcor.core.domain.VideoInfo;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.StringParams;
import com.starcor.core.logic.GlobalLogic;

public class RemoveCollectV2Params extends Api
{
  private int mark;
  private String name = "";
  private StringParams nns_action_params;
  private StringParams nns_collect_id;
  private StringParams nns_user_id;
  private StringParams nns_version;
  private StringParams nns_webtoken;
  private VideoInfo videoinfo = null;

  public RemoveCollectV2Params(String paramString, VideoInfo paramVideoInfo, int paramInt)
  {
    this.taksCategory = 16;
    this.prefix = AppInfo.URL_n40_a;
    this.videoinfo = paramVideoInfo;
    if (paramInt == 1)
    {
      this.nns_func.setValue("delete_collect_v2");
      this.nns_collect_id = new StringParams("nns_collect_id");
      this.name = "N40_A_3";
    }
    while (true)
    {
      this.nns_collect_id.setValue(paramString);
      this.nns_version = new StringParams("nns_version");
      this.nns_version.setValue(DeviceInfo.getMGTVVersion());
      this.nns_user_id = new StringParams("nns_user_id");
      this.nns_user_id.setValue(GlobalLogic.getInstance().getUserId());
      this.nns_webtoken = new StringParams("nns_webtoken");
      this.nns_webtoken.setValue(GlobalLogic.getInstance().getWebToken());
      this.cacheValidTime = 0L;
      if (paramVideoInfo != null)
      {
        String str = "action=m_open_detail_page&video_id=" + paramVideoInfo.videoId + "&" + "video_type=" + paramVideoInfo.videoType + "&" + "media_asset_id=" + paramVideoInfo.packageId + "&" + "category_id=" + paramVideoInfo.categoryId + "ui_style=" + paramVideoInfo.view_type + "&" + "auto_play=0";
        this.nns_action_params.setValue(str);
      }
      return;
      if (paramInt == 2)
      {
        this.nns_func.setValue("delete_playlist_v2");
        this.nns_collect_id = new StringParams("nns_playlist_id");
        this.name = "N40_A_6";
      }
      else if (paramInt == 3)
      {
        this.mark = 3;
        this.nns_func.setValue("delete_catch_v2");
        this.nns_collect_id = new StringParams("nns_catch_id");
        this.name = "N40_A_9";
        this.nns_action_params = new StringParams("nns_action_params");
      }
    }
  }

  public String getApiName()
  {
    return this.name;
  }

  public String toString()
  {
    String str1 = this.prefix;
    if (!str1.contains("?"))
      str1 = str1 + "?";
    String str2 = str1 + this.nns_func + this.nns_user_id + this.nns_collect_id + this.nns_version + this.nns_webtoken;
    if ((this.mark == 3) && (this.videoinfo != null))
      str2 = str2 + this.nns_action_params;
    return str2 + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.RemoveCollectV2Params
 * JD-Core Version:    0.6.2
 */