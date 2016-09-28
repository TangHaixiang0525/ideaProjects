package com.starcor.core.epgapi.params;

import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.IntegerParams;
import com.starcor.core.epgapi.StringParams;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.service.SystemTimeManager;

public class GetPlayBillParams extends Api
{
  private IntegerParams after_day;
  private IntegerParams before_day;
  private StringParams begin_day;
  private StringParams end_day;
  private StringParams mode;
  private StringParams nns_Current_day;
  private StringParams nns_token;
  private StringParams nns_webtoken;
  private IntegerParams time_zone;
  private StringParams video_id;
  private IntegerParams video_type;

  public GetPlayBillParams(String paramString, int paramInt1, int paramInt2)
  {
    this.taksCategory = 11;
    this.prefix = com.starcor.core.domain.AppInfo.URL_n3_a_i[0];
    this.nns_func.setValue("get_playbill");
    this.mode = new StringParams("nns_mode");
    this.mode.setValue("relative");
    this.video_id = new StringParams("nns_video_id");
    this.video_id.setValue(paramString);
    this.begin_day = new StringParams("nns_day_begin");
    this.begin_day.setValue("");
    this.end_day = new StringParams("nns_day_end");
    this.end_day.setValue("");
    this.before_day = new IntegerParams("nns_before_day");
    this.before_day.setValue(paramInt1);
    this.after_day = new IntegerParams("nns_after_day");
    this.after_day.setValue(paramInt2);
    this.time_zone = new IntegerParams("nns_time_zone");
    this.time_zone.setValue(TIMEZONE_PLUS_EIGHT);
    this.video_type = new IntegerParams("nns_video_type");
    this.video_type.setValue(1);
    this.nns_token = new StringParams("nns_token");
    this.nns_token.setValue(GlobalLogic.getInstance().getNnToken());
    this.nns_webtoken = new StringParams("nns_webtoken");
    this.nns_webtoken.setValue(GlobalLogic.getInstance().getWebToken());
    Logger.i("GetPlayInfoParams", "this.nns_webtoken2=:" + GlobalLogic.getInstance().getWebToken());
    Logger.i("GetPlayInfoParams", "this.nns_webtoken3=:" + this.nns_webtoken);
    this.nns_Current_day = new StringParams("nns_current_day");
    this.nns_Current_day.setValue(SystemTimeManager.getCurrentServerDate());
    this.cacheValidTime = 60000L;
  }

  public GetPlayBillParams(String paramString1, String paramString2, String paramString3)
  {
    this.taksCategory = 11;
    this.prefix = com.starcor.core.domain.AppInfo.URL_n3_a_i[0];
    this.nns_func.setValue("get_playbill");
    this.mode = new StringParams("nns_mode");
    this.mode.setValue("default");
    this.video_id = new StringParams("nns_video_id");
    this.video_id.setValue(paramString1);
    this.begin_day = new StringParams("nns_day_begin");
    this.begin_day.setValue(paramString2);
    this.end_day = new StringParams("nns_day_end");
    this.end_day.setValue(paramString3);
    this.before_day = new IntegerParams("nns_before_day");
    this.before_day.setValue(0);
    this.after_day = new IntegerParams("nns_after_day");
    this.after_day.setValue(0);
    this.time_zone = new IntegerParams("nns_time_zone");
    this.time_zone.setValue(TIMEZONE_PLUS_EIGHT);
    this.video_type = new IntegerParams("nns_video_type");
    this.video_type.setValue(1);
    this.nns_Current_day = new StringParams("nns_current_day");
    this.nns_Current_day.setValue(SystemTimeManager.getCurrentServerDate());
    this.nns_token = new StringParams("nns_token");
    this.nns_token.setValue(GlobalLogic.getInstance().getNnToken());
    this.nns_webtoken = new StringParams("nns_webtoken");
    this.nns_webtoken.setValue(GlobalLogic.getInstance().getWebToken());
    Logger.i("GetPlayInfoParams", "this.nns_webtoken=:" + GlobalLogic.getInstance().getWebToken());
    Logger.i("GetPlayInfoParams", "this.nns_webtoken4=:" + this.nns_webtoken);
    this.cacheValidTime = 60000L;
  }

  public String getApiName()
  {
    return "N3_A_A_8";
  }

  public IntegerParams getTime_zone()
  {
    return this.time_zone;
  }

  public String toString()
  {
    String str = this.prefix;
    if (!str.contains("?"))
      str = str + "?";
    return str + this.nns_func + this.video_id + this.video_type + this.time_zone + this.begin_day + this.end_day + this.mode + this.before_day + this.after_day + this.nns_Current_day + this.nns_token + this.nns_webtoken + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.GetPlayBillParams
 * JD-Core Version:    0.6.2
 */