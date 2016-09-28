package com.starcor.sccms.api;

import com.starcor.core.domain.SearchActorStarList;
import com.starcor.core.epgapi.params.GetHotActorStarListParams;
import com.starcor.core.parser.sax.SearchActorStarListSAXParser;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;
import java.io.ByteArrayInputStream;

public class SccmsApiGetHotActorStarListTask extends ServerApiTask
{
  ISccmsApiGetHotActorStarListTaskListener lsr;

  public SccmsApiGetHotActorStarListTask()
  {
    setCacheLife(1800000L);
  }

  public String getTaskName()
  {
    return "N39_A_17";
  }

  public String getUrl()
  {
    GetHotActorStarListParams localGetHotActorStarListParams = new GetHotActorStarListParams();
    localGetHotActorStarListParams.setResultFormat(0);
    return webUrlFormatter.i().formatUrl(localGetHotActorStarListParams.toString(), localGetHotActorStarListParams.getApiName());
  }

  public void perform(SCResponse paramSCResponse)
  {
    SearchActorStarListSAXParser localSearchActorStarListSAXParser = new SearchActorStarListSAXParser();
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    try
    {
      SearchActorStarList localSearchActorStarList = (SearchActorStarList)localSearchActorStarListSAXParser.parser(new ByteArrayInputStream(paramSCResponse.getContents()));
      Logger.i("SccmsApiGetHotActorStarListTask", " result:" + localSearchActorStarList.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localSearchActorStarList);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiGetHotActorStarListTaskListener paramISccmsApiGetHotActorStarListTaskListener)
  {
    this.lsr = paramISccmsApiGetHotActorStarListTaskListener;
  }

  public static abstract interface ISccmsApiGetHotActorStarListTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, SearchActorStarList paramSearchActorStarList);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiGetHotActorStarListTask
 * JD-Core Version:    0.6.2
 */