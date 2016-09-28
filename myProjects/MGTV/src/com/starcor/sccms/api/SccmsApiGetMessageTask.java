package com.starcor.sccms.api;

import com.starcor.core.domain.ServerMessageList;
import com.starcor.core.epgapi.params.GetMessageParams;
import com.starcor.core.parser.sax.GetMessageSaxParser;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;
import java.io.ByteArrayInputStream;

public class SccmsApiGetMessageTask extends ServerApiTask
{
  private String TAG = "SccmsApiGetMessageTask";
  private ISccmsApiGetMessageTaskListener lsr;
  private String time = "";

  public SccmsApiGetMessageTask(String paramString)
  {
    this.time = paramString;
  }

  public String getTaskName()
  {
    return "N40_E_1";
  }

  public String getUrl()
  {
    GetMessageParams localGetMessageParams = new GetMessageParams(this.time);
    localGetMessageParams.setResultFormat(0);
    return webUrlFormatter.i().formatUrl(localGetMessageParams.toString(), localGetMessageParams.getApiName());
  }

  public void perform(SCResponse paramSCResponse)
  {
    if (this.lsr == null)
      return;
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    try
    {
      ServerMessageList localServerMessageList = (ServerMessageList)new GetMessageSaxParser().parser(new ByteArrayInputStream(paramSCResponse.getContents()));
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localServerMessageList);
      Logger.i(this.TAG, "N40_E_1 result:" + localServerMessageList.toString());
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiGetMessageTaskListener paramISccmsApiGetMessageTaskListener)
  {
    this.lsr = paramISccmsApiGetMessageTaskListener;
  }

  public static abstract interface ISccmsApiGetMessageTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, ServerMessageList paramServerMessageList);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiGetMessageTask
 * JD-Core Version:    0.6.2
 */