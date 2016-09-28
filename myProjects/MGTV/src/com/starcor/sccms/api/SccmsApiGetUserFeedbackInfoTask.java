package com.starcor.sccms.api;

import com.starcor.core.domain.UserFeedbackInfoList;
import com.starcor.core.epgapi.params.GetUserFeedbackInfoParams;
import com.starcor.core.parser.sax.GetUserFeedbackInfoSAXParser;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;
import java.io.ByteArrayInputStream;

public class SccmsApiGetUserFeedbackInfoTask extends ServerApiTask
{
  private static final String TAG = SccmsApiGetActorStarInfoTask.class.getSimpleName();
  private ISccmsApiGetUserFeedbackInfoTaskListener mListener = null;
  private String nns_page_index;
  private String nns_page_size;

  public SccmsApiGetUserFeedbackInfoTask(String paramString1, String paramString2)
  {
    if ("null".equals(paramString1))
    {
      this.nns_page_size = "";
      if (!"null".equals(paramString2))
        break label56;
    }
    label56: for (this.nns_page_index = ""; ; this.nns_page_index = paramString2)
    {
      setCacheLife(1800000L);
      return;
      this.nns_page_size = paramString1;
      break;
    }
  }

  public String getTaskName()
  {
    return "N23_A_2";
  }

  public String getUrl()
  {
    GetUserFeedbackInfoParams localGetUserFeedbackInfoParams = new GetUserFeedbackInfoParams(this.nns_page_size, this.nns_page_index);
    localGetUserFeedbackInfoParams.setResultFormat(0);
    return webUrlFormatter.i().formatUrl(localGetUserFeedbackInfoParams.toString(), localGetUserFeedbackInfoParams.getApiName());
  }

  public void perform(SCResponse paramSCResponse)
  {
    Logger.i(TAG, "ServerApiTools.isSCResponseError(rep)=" + ServerApiTools.isSCResponseError(paramSCResponse));
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.mListener.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    GetUserFeedbackInfoSAXParser localGetUserFeedbackInfoSAXParser = new GetUserFeedbackInfoSAXParser();
    try
    {
      UserFeedbackInfoList localUserFeedbackInfoList = (UserFeedbackInfoList)localGetUserFeedbackInfoSAXParser.parser(new ByteArrayInputStream(paramSCResponse.getContents()));
      this.mListener.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localUserFeedbackInfoList);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.mListener.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiGetUserFeedbackInfoTaskListener paramISccmsApiGetUserFeedbackInfoTaskListener)
  {
    this.mListener = paramISccmsApiGetUserFeedbackInfoTaskListener;
  }

  public static abstract interface ISccmsApiGetUserFeedbackInfoTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, UserFeedbackInfoList paramUserFeedbackInfoList);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiGetUserFeedbackInfoTask
 * JD-Core Version:    0.6.2
 */