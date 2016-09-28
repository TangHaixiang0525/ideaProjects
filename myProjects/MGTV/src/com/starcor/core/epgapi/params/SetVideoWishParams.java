package com.starcor.core.epgapi.params;

import com.starcor.config.DeviceInfo;
import com.starcor.core.domain.AppInfo;
import com.starcor.core.domain.VideoInfo;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.IntegerParams;
import com.starcor.core.epgapi.StringParams;
import com.starcor.core.logic.GlobalLogic;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class SetVideoWishParams extends Api
{
  private StringParams nns_action_params;
  private StringParams nns_mac_id;
  private IntegerParams nns_type;
  private StringParams nns_user_id;
  private StringParams nns_video_id;

  public SetVideoWishParams(VideoInfo paramVideoInfo)
  {
    this.prefix = AppInfo.URL_n40_g;
    this.nns_func.setValue("set_video_wish");
    this.nns_user_id = new StringParams("nns_user_id");
    this.nns_user_id.setValue(GlobalLogic.getInstance().getUserId());
    this.nns_video_id = new StringParams("nns_video_id");
    this.nns_video_id.setValue(paramVideoInfo.videoId);
    this.nns_type = new IntegerParams("nns_page_size");
    this.nns_type.setValue(0);
    this.nns_mac_id = new StringParams("nns_mac_id");
    this.nns_mac_id.setValue(DeviceInfo.getMac().replace("-", "").trim());
    this.nns_action_params = new StringParams("nns_action_params");
    if (paramVideoInfo != null);
    try
    {
      String str2 = "action=m_open_detail_page&video_id=" + URLEncoder.encode(paramVideoInfo.videoId, "utf-8") + "&video_type=0" + "&media_asset_id=" + URLEncoder.encode(paramVideoInfo.packageId, "utf-8") + "&category_id=" + URLEncoder.encode(paramVideoInfo.categoryId, "utf-8") + "&" + "ui_style=" + URLEncoder.encode(String.valueOf(paramVideoInfo.uiStyle), "utf-8") + "&" + "auto_play=0";
      str1 = str2;
      this.nns_action_params.setValue(str1);
      return;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      while (true)
      {
        localUnsupportedEncodingException.printStackTrace();
        String str1 = null;
      }
    }
  }

  public String getApiName()
  {
    return "N40_G_1";
  }

  public String toString()
  {
    String str = this.prefix;
    if (!str.contains("?"))
      str = str + "?";
    return str + this.nns_func + this.nns_user_id + this.nns_video_id + this.nns_type + this.nns_action_params + this.nns_mac_id + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.SetVideoWishParams
 * JD-Core Version:    0.6.2
 */