package com.starcor.core.epgapi.params;

import android.text.TextUtils;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.IntegerParams;
import com.starcor.core.epgapi.StringParams;
import com.starcor.core.logic.GlobalLogic;

public class GetPlayInfoParams extends Api
{
  private StringParams category_id;
  private StringParams media_id;
  private StringParams nns_begin;
  private StringParams nns_caps;
  private StringParams nns_day;
  private StringParams nns_huawei_cid;
  private StringParams nns_quality;
  private StringParams nns_time_len;
  private StringParams nns_token;
  private StringParams nns_webtoken;
  private StringParams package_id;
  private StringParams video_id;
  private IntegerParams video_index;
  private IntegerParams video_type;

  public GetPlayInfoParams(String paramString1, int paramInt, String paramString2, String paramString3)
  {
    this(paramString1, paramInt, paramString2, paramString3, "", "", "", "");
  }

  public GetPlayInfoParams(String paramString1, int paramInt, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7)
  {
    this(paramString1, paramInt, paramString2, paramString3, paramString4, paramString5, paramString6, paramString7, "");
  }

  public GetPlayInfoParams(String paramString1, int paramInt, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8)
  {
    this.taksCategory = 7;
    this.prefix = com.starcor.core.domain.AppInfo.URL_n3_a_i[0];
    this.nns_func.setValue("apply_play_video");
    this.video_id = new StringParams("nns_video_id");
    this.video_id.setValue(paramString1);
    this.video_type = new IntegerParams("nns_video_type");
    this.video_type.setValue(paramInt);
    this.media_id = new StringParams("nns_media_id");
    this.media_id.setValue(paramString2);
    this.package_id = new StringParams("nns_media_assets_id");
    this.category_id = new StringParams("nns_category_id");
    this.category_id.setValue("1000");
    this.video_index = new IntegerParams("nns_video_index");
    this.video_index.setValue(0);
    this.nns_quality = new StringParams("nns_quality");
    this.nns_quality.setValue(paramString3);
    this.nns_day = new StringParams("nns_day");
    this.nns_day.setValue(paramString4);
    this.nns_begin = new StringParams("nns_begin");
    this.nns_begin.setValue(paramString5);
    this.nns_time_len = new StringParams("nns_time_len");
    this.nns_time_len.setValue(paramString6);
    this.nns_huawei_cid = new StringParams("nns_huawei_cid");
    this.nns_huawei_cid.setValue(paramString7);
    this.nns_token = new StringParams("nns_token");
    this.nns_token.setValue(GlobalLogic.getInstance().getNnToken());
    this.nns_webtoken = new StringParams("nns_webtoken");
    this.nns_webtoken.setValue(GlobalLogic.getInstance().getWebToken());
    this.nns_caps = new StringParams("nns_caps");
    this.nns_caps.setValue(paramString8);
    this.cacheValidTime = 0L;
  }

  public String getApiName()
  {
    return "N3_A_A_4";
  }

  public StringParams getCategory_id()
  {
    return this.category_id;
  }

  public StringParams getPackage_id()
  {
    return this.package_id;
  }

  public IntegerParams getVideo_index()
  {
    return this.video_index;
  }

  public String toString()
  {
    String str1 = this.prefix;
    if (str1 == null)
      return "";
    if (!str1.contains("?"))
      str1 = str1 + "?";
    String str2 = str1 + this.nns_func + this.video_id + this.video_type + this.media_id + this.nns_quality + this.package_id + this.category_id + this.video_index + this.nns_token + this.nns_webtoken;
    if (!TextUtils.isEmpty(this.nns_huawei_cid.getValue()))
      str2 = str2 + this.nns_huawei_cid;
    if (!TextUtils.isEmpty(this.nns_day.getValue()))
      str2 = str2 + this.nns_day;
    if (!TextUtils.isEmpty(this.nns_begin.getValue()))
      str2 = str2 + this.nns_begin;
    if (!TextUtils.isEmpty(this.nns_time_len.getValue()))
      str2 = str2 + this.nns_time_len;
    if (!TextUtils.isEmpty(this.nns_caps.getValue()))
      str2 = str2 + this.nns_caps;
    return str2 + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.GetPlayInfoParams
 * JD-Core Version:    0.6.2
 */