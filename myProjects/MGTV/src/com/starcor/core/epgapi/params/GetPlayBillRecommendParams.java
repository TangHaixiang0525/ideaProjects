package com.starcor.core.epgapi.params;

import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.IntegerParams;
import com.starcor.core.epgapi.StringParams;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.service.SystemTimeManager;

public class GetPlayBillRecommendParams extends Api
{
  private IntegerParams after_day;
  private IntegerParams before_day;
  private StringParams begin_day;
  private StringParams end_day;
  private StringParams mode;
  private StringParams nns_Current_day;
  private StringParams nns_token;
  private StringParams nns_webtoken;
  private IntegerParams play_bill_type;
  private IntegerParams time_zone;

  public GetPlayBillRecommendParams(int paramInt1, int paramInt2)
  {
    this.taksCategory = 11;
    this.prefix = com.starcor.core.domain.AppInfo.URL_n3_a_i[0];
    this.nns_func.setValue("get_playbill_recom");
    this.mode = new StringParams("nns_mode");
    this.mode.setValue("relative");
    this.begin_day = new StringParams("nns_day_begin");
    this.begin_day.setValue("");
    this.end_day = new StringParams("nns_day_end");
    this.end_day.setValue("");
    this.before_day = new IntegerParams("nns_before_day");
    this.before_day.setValue(paramInt1);
    this.after_day = new IntegerParams("nns_after_day");
    this.after_day.setValue(paramInt2);
    this.time_zone = new IntegerParams("nns_time_zone");
    this.time_zone.setValue(8);
    this.play_bill_type = new IntegerParams("nns_playbill_type");
    this.play_bill_type.setValue(0);
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

  public String getApiName()
  {
    return "N3_A_A_17";
  }

  public String toString()
  {
    String str = this.prefix;
    if (!str.contains("?"))
      str = str + "?";
    return str + this.nns_func + this.play_bill_type + this.time_zone + this.begin_day + this.end_day + this.mode + this.before_day + this.after_day + this.nns_Current_day + this.nns_token + this.nns_webtoken + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.GetPlayBillRecommendParams
 * JD-Core Version:    0.6.2
 */