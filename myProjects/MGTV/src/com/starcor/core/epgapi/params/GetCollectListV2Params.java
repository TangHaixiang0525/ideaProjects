package com.starcor.core.epgapi.params;

import com.starcor.config.DeviceInfo;
import com.starcor.core.domain.AppInfo;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.StringParams;
import com.starcor.core.logic.GlobalLogic;

public class GetCollectListV2Params extends Api
{
  public static final String API_NAME_CATCH = "N40_A_7";
  public static final String API_NAME_COLLECT = "N40_A_1";
  public static final String API_NAME_PLAYLIST = "N40_A_4";
  public static final int URL_TYPE_CATCH = 18;
  public static final int URL_TYPE_COLLECT = 16;
  public static final int URL_TYPE_PLAYLIST = 17;
  private String mApiName = "N40_A_1";
  private StringParams nns_user_id = new StringParams("nns_user_id");
  private StringParams nns_version = new StringParams("nns_version");
  private StringParams nns_webtoken = new StringParams("nns_webtoken");

  public GetCollectListV2Params(String paramString)
  {
    this.taksCategory = 12;
    this.prefix = AppInfo.URL_n40_a;
    if ("N40_A_1".equals(paramString))
      this.nns_func.setValue("get_collect_list_v2");
    while (true)
    {
      this.mApiName = paramString;
      this.nns_user_id.setValue(GlobalLogic.getInstance().getUserId());
      this.nns_version.setValue(DeviceInfo.getMGTVVersion());
      this.nns_webtoken.setValue(GlobalLogic.getInstance().getWebToken());
      this.cacheValidTime = 0L;
      return;
      if ("N40_A_4".equals(paramString))
      {
        this.nns_func.setValue("get_playlist_v2");
      }
      else
      {
        paramString = "N40_A_7";
        this.nns_func.setValue("get_catch_list_v2");
      }
    }
  }

  public String getApiName()
  {
    return this.mApiName;
  }

  public String toString()
  {
    String str = AppInfo.URL_n40_a;
    if (!str.contains("?"))
      str = str + "?";
    return str + this.nns_func + this.nns_user_id + this.nns_version + this.nns_webtoken + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.GetCollectListV2Params
 * JD-Core Version:    0.6.2
 */