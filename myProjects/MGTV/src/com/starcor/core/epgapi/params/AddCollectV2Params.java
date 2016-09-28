package com.starcor.core.epgapi.params;

import android.text.TextUtils;
import com.starcor.config.DeviceInfo;
import com.starcor.core.domain.AppInfo;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.StringParams;
import com.starcor.core.logic.GlobalLogic;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class AddCollectV2Params extends Api
{
  public static final int TYPE_COLLECT = 1;
  public static final int TYPE_PLAYLIST = 2;
  public static final int TYPE_TRACEOPLAY = 3;
  private int mark;
  private StringParams nns_action_params;
  private StringParams nns_category_id;
  private StringParams nns_mac_id;
  private StringParams nns_media_asset_id;
  private StringParams nns_played_time_len;
  private StringParams nns_quality;
  private StringParams nns_time_len;
  private StringParams nns_tstv_begin_time;
  private StringParams nns_tstv_time_len;
  private StringParams nns_user_id;
  public StringParams nns_version;
  private StringParams nns_video_id;
  private StringParams nns_video_index;
  private StringParams nns_video_name;
  private StringParams nns_video_online_time;
  private StringParams nns_video_type;
  private StringParams nns_webtoken;
  private String task_name = "";

  public AddCollectV2Params(int paramInt1, String paramString1, String paramString2, int paramInt2, int paramInt3, int paramInt4, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, int paramInt5)
  {
    this.taksCategory = 11;
    this.prefix = AppInfo.URL_n40_a;
    this.nns_func = new StringParams("nns_func");
    if (paramInt1 == 1)
    {
      this.nns_func.setValue("add_collect_v2");
      this.task_name = "N40_A_2";
    }
    while (true)
    {
      this.nns_user_id = new StringParams("nns_user_id");
      this.nns_user_id.setValue(GlobalLogic.getInstance().getUserId());
      this.nns_video_id = new StringParams("nns_video_id");
      this.nns_video_id.setValue(paramString1);
      this.nns_video_name = new StringParams("nns_video_name");
      this.nns_video_name.setValue(paramString2);
      this.nns_media_asset_id = new StringParams("nns_media_asset_id");
      this.nns_media_asset_id.setValue(paramString6);
      this.nns_category_id = new StringParams("nns_category_id");
      this.nns_category_id.setValue(paramString7);
      this.nns_video_type = new StringParams("nns_video_type");
      this.nns_video_type.setValue(paramInt2 + "");
      this.nns_video_index = new StringParams("nns_video_index");
      this.nns_video_index.setValue(paramInt3 + "");
      this.nns_tstv_begin_time = new StringParams("nns_tstv_begin_time");
      label296: label326: Object localObject;
      if (!TextUtils.isEmpty(paramString4))
      {
        this.nns_tstv_begin_time.setValue(paramString3 + paramString4);
        this.nns_tstv_time_len = new StringParams("nns_tstv_time_len");
        if (TextUtils.isEmpty(paramString5))
          break label747;
        this.nns_tstv_time_len.setValue(paramString5);
        this.nns_time_len = new StringParams("nns_time_len");
        if (TextUtils.isEmpty(paramString5))
          break label759;
        this.nns_time_len.setValue(paramString5);
        this.nns_webtoken = new StringParams("nns_webtoken");
        this.nns_webtoken.setValue(GlobalLogic.getInstance().getWebToken());
        this.nns_version = new StringParams("nns_version");
        this.nns_version.setValue(DeviceInfo.getMGTVVersion());
        if (paramInt1 == 2)
        {
          this.nns_quality = new StringParams("nns_quality");
          this.nns_quality.setValue("HD");
          this.nns_played_time_len = new StringParams("nns_played_time_len");
          this.nns_played_time_len.setValue(paramInt4 + "");
          this.nns_video_online_time = new StringParams("nns_video_online_time");
          this.nns_video_online_time.setValue("0");
        }
        this.cacheValidTime = 0L;
        localObject = "";
        if (paramInt1 == 3)
        {
          this.mark = 3;
          this.nns_mac_id = new StringParams("nns_mac_id");
          this.nns_mac_id.setValue(DeviceInfo.getMac().replace("-", "").trim());
          this.nns_action_params = new StringParams("nns_action_params");
        }
      }
      try
      {
        String str = "action=m_open_detail_page&video_id=" + URLEncoder.encode(paramString1, "utf-8") + "&" + "video_type=" + URLEncoder.encode(String.valueOf(paramInt2), "utf-8") + "&" + "media_asset_id=" + URLEncoder.encode(paramString6, "utf-8") + "&" + "category_id=" + URLEncoder.encode(paramString1, "utf-8") + "&ui_style=" + URLEncoder.encode(String.valueOf(paramInt5), "utf-8") + "&" + "auto_play=0";
        localObject = str;
        this.nns_action_params.setValue((String)localObject);
        return;
        if (paramInt1 == 2)
        {
          this.nns_func.setValue("add_playlist_v2");
          this.task_name = "N40_A_5";
          continue;
        }
        if (paramInt1 != 3)
          continue;
        this.nns_func.setValue("add_catch_v2");
        this.task_name = "N40_A_8";
        continue;
        this.nns_tstv_begin_time.setValue("");
        break label296;
        label747: this.nns_tstv_time_len.setValue("");
        break label326;
        label759: this.nns_time_len.setValue("");
      }
      catch (UnsupportedEncodingException localUnsupportedEncodingException)
      {
        while (true)
          localUnsupportedEncodingException.printStackTrace();
      }
    }
  }

  public AddCollectV2Params(String paramString1, String paramString2, int paramInt1, int paramInt2, int paramInt3, String paramString3, String paramString4, int paramInt4)
  {
    this(3, paramString1, paramString2, paramInt1, paramInt2, 0, null, null, null, paramString3, paramString4, paramInt4);
  }

  public AddCollectV2Params(String paramString1, String paramString2, int paramInt1, int paramInt2, String paramString3, String paramString4, int paramInt3)
  {
    this(1, paramString1, paramString2, paramInt1, paramInt2, 0, null, null, null, paramString3, paramString4, paramInt3);
  }

  public String getApiName()
  {
    return this.task_name;
  }

  public StringParams getCategory_id()
  {
    return this.nns_category_id;
  }

  public StringParams getName()
  {
    return this.nns_video_name;
  }

  public StringParams getPackage_id()
  {
    return this.nns_media_asset_id;
  }

  public StringParams getPlayed_time()
  {
    return this.nns_tstv_time_len;
  }

  public StringParams getQuality()
  {
    return this.nns_quality;
  }

  public StringParams getVideoId()
  {
    return this.nns_video_id;
  }

  public StringParams getVideo_name()
  {
    return this.nns_video_name;
  }

  public StringParams getVideo_online_time()
  {
    return this.nns_video_online_time;
  }

  public String toString()
  {
    String str1 = this.prefix;
    if (!str1.contains("?"))
      str1 = str1 + "?";
    String str2 = str1 + this.nns_func + this.nns_user_id + this.nns_video_id + this.nns_video_name + this.nns_video_type + this.nns_video_index + this.nns_tstv_begin_time + this.nns_tstv_time_len + this.nns_media_asset_id + this.nns_category_id + this.nns_version + this.nns_webtoken + this.nns_quality + this.nns_played_time_len + this.nns_time_len + this.nns_video_online_time;
    if (this.mark == 3)
      str2 = str2 + this.nns_action_params;
    return str2 + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.AddCollectV2Params
 * JD-Core Version:    0.6.2
 */