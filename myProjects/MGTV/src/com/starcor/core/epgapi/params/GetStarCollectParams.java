package com.starcor.core.epgapi.params;

import com.starcor.core.domain.AppInfo;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.StringParams;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.utils.Logger;
import com.starcor.sccms.api.SccmsApiGetStarCollectDataTask.GetStarCollectParamType;
import java.util.List;

public class GetStarCollectParams extends Api
{
  private static final String TAG = GetStarCollectParams.class.getSimpleName();
  private SccmsApiGetStarCollectDataTask.GetStarCollectParamType mType = SccmsApiGetStarCollectDataTask.GetStarCollectParamType.GET_PARAM_BY_VIDEO_ID;
  private StringParams nns_label_id;
  private StringParams nns_token;
  private StringParams nns_video_id;
  private StringParams nns_video_type;
  private StringParams nns_webtoken;

  public GetStarCollectParams(String paramString1, String paramString2)
  {
    this.prefix = AppInfo.URL_n39_a;
    this.nns_func.setValue("get_actor_star_list");
    this.nns_video_id = new StringParams("nns_video_id");
    this.nns_video_id.setValue(paramString1);
    this.nns_video_type = new StringParams("nns_video_type");
    this.nns_video_type.setValue(paramString2);
    this.nns_token = new StringParams("nns_token");
    this.nns_token.setValue(GlobalLogic.getInstance().getNnToken());
    this.nns_webtoken = new StringParams("nns_webtoken");
    this.nns_webtoken.setValue(GlobalLogic.getInstance().getWebToken());
    this.cacheValidTime = -1L;
  }

  public GetStarCollectParams(List<String> paramList)
  {
    this.prefix = AppInfo.URL_n39_a;
    this.nns_func.setValue("get_actor_star_list");
    String str = "";
    int i;
    int j;
    if ((paramList != null) && (paramList.size() > 0))
    {
      i = paramList.size();
      Logger.i(TAG, "GetStarCollectParams size=" + i);
      j = 0;
    }
    while (j < i)
    {
      str = str + (String)paramList.get(j);
      if (j < i - 1)
        str = str + ",";
      j++;
      continue;
      Logger.i(TAG, "GetStarCollectParams null == nns_label_id or nns_label_id.size()==0");
    }
    Logger.i(TAG, "GetStarCollectParams labelIds=" + str);
    this.nns_label_id = new StringParams("nns_label_id");
    this.nns_label_id.setValue(str);
    this.nns_token = new StringParams("nns_token");
    this.nns_token.setValue(GlobalLogic.getInstance().getNnToken());
    this.nns_webtoken = new StringParams("nns_webtoken");
    this.nns_webtoken.setValue(GlobalLogic.getInstance().getWebToken());
    this.cacheValidTime = -1L;
  }

  public String getApiName()
  {
    return "N39_A_4";
  }

  public String toString()
  {
    String str = this.prefix;
    if (!str.contains("?"))
      str = str + "?";
    if (SccmsApiGetStarCollectDataTask.GetStarCollectParamType.GET_PARAM_BY_VIDEO_ID.equals(this.mType))
      str = str + this.nns_func + this.nns_video_id + this.nns_video_type + this.nns_token + this.nns_webtoken + this.suffix;
    while (true)
    {
      Logger.i(TAG, "toString() 返回url=" + str);
      return str;
      if (SccmsApiGetStarCollectDataTask.GetStarCollectParamType.GET_PARAM_BY_LABEL_ID.equals(this.mType))
        str = str + this.nns_func + this.nns_label_id + this.nns_token + this.nns_webtoken + this.suffix;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.GetStarCollectParams
 * JD-Core Version:    0.6.2
 */