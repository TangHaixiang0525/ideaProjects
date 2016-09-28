package com.starcor.sccms.api;

import com.starcor.core.domain.UserRecommendItem;
import com.starcor.core.epgapi.params.GetUserRecommendParams;
import com.starcor.core.parser.sax.GetUserRecommendSAXParse;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCachedTask;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class SccmsApiGetUserRecommendTask extends ServerApiCachedTask
{
  String TAG = SccmsApiGetUserRecommendTask.class.getSimpleName();
  ISccmsApiGetUserRecommendTaskListener lsr;

  public String getTaskName()
  {
    return "N40_D_1";
  }

  public String getUrl()
  {
    GetUserRecommendParams localGetUserRecommendParams = new GetUserRecommendParams();
    localGetUserRecommendParams.setResultFormat(0);
    return webUrlFormatter.i().formatUrl(localGetUserRecommendParams.toString(), localGetUserRecommendParams.getApiName());
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
    GetUserRecommendSAXParse localGetUserRecommendSAXParse = new GetUserRecommendSAXParse();
    try
    {
      ArrayList localArrayList = (ArrayList)localGetUserRecommendSAXParse.parser(new ByteArrayInputStream(paramSCResponse.getContents()));
      if (localArrayList != null)
        Logger.i(this.TAG, "获取用户推荐影片result:" + localArrayList.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localArrayList);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiGetUserRecommendTaskListener paramISccmsApiGetUserRecommendTaskListener)
  {
    this.lsr = paramISccmsApiGetUserRecommendTaskListener;
  }

  public static abstract interface ISccmsApiGetUserRecommendTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, ArrayList<UserRecommendItem> paramArrayList);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiGetUserRecommendTask
 * JD-Core Version:    0.6.2
 */