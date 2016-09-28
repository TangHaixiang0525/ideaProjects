package com.starcor.sccms.api;

import com.starcor.core.domain.ActorStarInfoList;
import com.starcor.core.epgapi.params.GetActorStarInfoParams;
import com.starcor.core.parser.sax.GetActorStarInfoSAXParser;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;
import java.io.ByteArrayInputStream;

public class SccmsApiGetActorStarInfoTask extends ServerApiTask
{
  private static final String TAG = SccmsApiGetActorStarInfoTask.class.getSimpleName();
  private ISccmsApiGetActorStarInfoTaskListener mListener = null;
  private String nns_actor_id;
  private String nns_label_id;

  public SccmsApiGetActorStarInfoTask(String paramString1, String paramString2)
  {
    if ("null".equals(paramString1))
    {
      this.nns_actor_id = "";
      if (!"null".equals(paramString2))
        break label56;
    }
    label56: for (this.nns_label_id = ""; ; this.nns_label_id = paramString2)
    {
      setCacheLife(1800000L);
      return;
      this.nns_actor_id = paramString1;
      break;
    }
  }

  public String getTaskName()
  {
    return "N39_A_5";
  }

  public String getUrl()
  {
    GetActorStarInfoParams localGetActorStarInfoParams = new GetActorStarInfoParams(this.nns_actor_id, this.nns_label_id);
    localGetActorStarInfoParams.setResultFormat(0);
    return webUrlFormatter.i().formatUrl(localGetActorStarInfoParams.toString(), localGetActorStarInfoParams.getApiName());
  }

  public void perform(SCResponse paramSCResponse)
  {
    Logger.i(TAG, "ServerApiTools.isSCResponseError(rep)=" + ServerApiTools.isSCResponseError(paramSCResponse));
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.mListener.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    GetActorStarInfoSAXParser localGetActorStarInfoSAXParser = new GetActorStarInfoSAXParser();
    try
    {
      ActorStarInfoList localActorStarInfoList = (ActorStarInfoList)localGetActorStarInfoSAXParser.parser(new ByteArrayInputStream(paramSCResponse.getContents()));
      this.mListener.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localActorStarInfoList);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.mListener.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiGetActorStarInfoTaskListener paramISccmsApiGetActorStarInfoTaskListener)
  {
    this.mListener = paramISccmsApiGetActorStarInfoTaskListener;
  }

  public static abstract interface ISccmsApiGetActorStarInfoTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, ActorStarInfoList paramActorStarInfoList);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiGetActorStarInfoTask
 * JD-Core Version:    0.6.2
 */