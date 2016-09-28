package com.starcor.core.epgapi.params;

import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.IntegerParams;
import com.starcor.core.epgapi.StringParams;
import com.starcor.core.logic.GlobalLogic;

public class AddCollectParams extends Api
{
  public static final int TYPE_COLLECT = 1;
  public static final int TYPE_PLAYLIST = 2;
  public static final int TYPE_TRACEOPLAY = 3;
  private StringParams begin_time;
  private StringParams category_id;
  private StringParams collect_type;
  private StringParams day;
  private StringParams nns_token;
  private StringParams nns_webtoken;
  private StringParams package_id;
  public IntegerParams played_time;
  private StringParams time_len;
  private StringParams video_id;
  private IntegerParams video_index;
  private StringParams video_name;
  private IntegerParams video_type;

  public AddCollectParams(int paramInt1, String paramString1, String paramString2, int paramInt2, int paramInt3, int paramInt4, String paramString3, String paramString4, String paramString5)
  {
    this.taksCategory = 11;
    this.prefix = com.starcor.core.domain.AppInfo.URL_n3_a_i[0];
    this.nns_func.setValue("add_collect");
    this.collect_type = new StringParams("nns_collect_type");
    if (paramInt1 == 1)
      this.collect_type.setValue("collect");
    while (true)
    {
      this.video_id = new StringParams("nns_video_id");
      this.video_id.setValue(paramString1);
      this.video_name = new StringParams("nns_video_name");
      this.video_name.setValue(paramString2);
      this.package_id = new StringParams("nns_media_assets_id");
      this.package_id.setValue(null);
      this.category_id = new StringParams("nns_category_id");
      this.category_id.setValue(null);
      this.video_type = new IntegerParams("nns_video_type");
      this.video_type.setValue(paramInt2);
      this.video_index = new IntegerParams("nns_video_index");
      this.video_index.setValue(paramInt3);
      this.played_time = new IntegerParams("nns_played_time");
      this.played_time.setValue(paramInt4);
      this.day = new StringParams("nns_day");
      this.day.setValue(paramString3);
      this.begin_time = new StringParams("nns_begin");
      this.begin_time.setValue(paramString4);
      this.time_len = new StringParams("nns_time_len");
      this.time_len.setValue(paramString5);
      this.nns_token = new StringParams("nns_token");
      this.nns_token.setValue(GlobalLogic.getInstance().getNnToken());
      this.nns_webtoken = new StringParams("nns_webtoken");
      this.nns_webtoken.setValue(GlobalLogic.getInstance().getWebToken());
      this.cacheValidTime = 0L;
      return;
      if (paramInt1 == 2)
        this.collect_type.setValue("playlist");
      else if (paramInt1 == 3)
        this.collect_type.setValue("catch");
    }
  }

  public AddCollectParams(String paramString1, String paramString2, int paramInt1, int paramInt2)
  {
    this(1, paramString1, paramString2, paramInt1, paramInt2, 0, null, null, null);
  }

  public AddCollectParams(String paramString1, String paramString2, int paramInt1, int paramInt2, int paramInt3)
  {
    this(3, paramString1, paramString2, paramInt1, paramInt2, 0, null, null, null);
  }

  public String getApiName()
  {
    return "N3_A_A_5";
  }

  public StringParams getCategory_id()
  {
    return this.category_id;
  }

  public StringParams getPackage_id()
  {
    return this.package_id;
  }

  public IntegerParams getPlayed_time()
  {
    return this.played_time;
  }

  public StringParams getVideo_name()
  {
    return this.video_name;
  }

  public String toString()
  {
    String str = this.prefix;
    if (!str.contains("?"))
      str = str + "?";
    return str + this.nns_func + this.collect_type + this.video_id + this.video_name + this.package_id + this.category_id + this.video_type + this.video_index + this.played_time + this.day + this.begin_time + this.time_len + this.nns_token + this.nns_webtoken + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.AddCollectParams
 * JD-Core Version:    0.6.2
 */