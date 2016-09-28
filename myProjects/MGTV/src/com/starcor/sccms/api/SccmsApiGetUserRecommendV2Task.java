package com.starcor.sccms.api;

import com.starcor.core.domain.UserRecommendV2Item;
import com.starcor.core.epgapi.params.GetUserRecommendV2Params;
import com.starcor.core.parser.sax.GetUserRecommendV2SAXParse;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCachedTask;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class SccmsApiGetUserRecommendV2Task extends ServerApiCachedTask
{
  String TAG = SccmsApiGetUserRecommendV2Task.class.getSimpleName();
  ISccmsApiGetUserRecommendV2TaskListener lsr;
  private String nns_packet_id;
  int size = 0;

  public SccmsApiGetUserRecommendV2Task(String paramString, int paramInt)
  {
    this.nns_packet_id = paramString;
    this.size = paramInt;
  }

  public String getTaskName()
  {
    return "N40_D_3";
  }

  public String getUrl()
  {
    GetUserRecommendV2Params localGetUserRecommendV2Params = new GetUserRecommendV2Params(this.nns_packet_id, this.size);
    localGetUserRecommendV2Params.setResultFormat(0);
    return webUrlFormatter.i().formatUrl(localGetUserRecommendV2Params.toString(), localGetUserRecommendV2Params.getApiName());
  }

  public void perform(SCResponse paramSCResponse)
  {
    if (this.lsr == null)
    {
      Logger.i(this.TAG, "perform() lsr is null");
      return;
    }
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    GetUserRecommendV2SAXParse localGetUserRecommendV2SAXParse = new GetUserRecommendV2SAXParse();
    try
    {
      ArrayList localArrayList = (ArrayList)localGetUserRecommendV2SAXParse.parser(new ByteArrayInputStream(paramSCResponse.getContents()));
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localArrayList);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiGetUserRecommendV2TaskListener paramISccmsApiGetUserRecommendV2TaskListener)
  {
    this.lsr = paramISccmsApiGetUserRecommendV2TaskListener;
  }

  public static abstract interface ISccmsApiGetUserRecommendV2TaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, ArrayList<UserRecommendV2Item> paramArrayList);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiGetUserRecommendV2Task
 * JD-Core Version:    0.6.2
 */