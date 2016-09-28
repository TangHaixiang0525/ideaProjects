package com.starcor.sccms.api;

import com.starcor.core.domain.UserRecommendItem;
import com.starcor.core.epgapi.params.AddUserRecommendParams;
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

public class SccmsApiAddMediaRecommendTask extends ServerApiCachedTask
{
  ISccmsApiAddMediaRecommendTaskListener lsr;
  AddUserRecommendParams para = null;

  public SccmsApiAddMediaRecommendTask(AddUserRecommendParams paramAddUserRecommendParams)
  {
    this.para = paramAddUserRecommendParams;
    this.para.setResultFormat(0);
  }

  public String getTaskName()
  {
    return " N40_D_2";
  }

  public String getUrl()
  {
    return webUrlFormatter.i().formatUrl(this.para.toString(), this.para.getApiName());
  }

  public void perform(SCResponse paramSCResponse)
  {
    GetUserRecommendSAXParse localGetUserRecommendSAXParse = new GetUserRecommendSAXParse();
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    try
    {
      ArrayList localArrayList = (ArrayList)localGetUserRecommendSAXParse.parser(new ByteArrayInputStream(paramSCResponse.getContents()));
      Logger.i("SccmsApiAddCollectRecordTask", "增加用户推荐影片  result:" + localArrayList);
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localArrayList);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiAddMediaRecommendTaskListener paramISccmsApiAddMediaRecommendTaskListener)
  {
    this.lsr = paramISccmsApiAddMediaRecommendTaskListener;
  }

  public static abstract interface ISccmsApiAddMediaRecommendTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, ArrayList<UserRecommendItem> paramArrayList);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiAddMediaRecommendTask
 * JD-Core Version:    0.6.2
 */