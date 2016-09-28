package com.starcor.sccms.api;

import com.starcor.core.domain.UserCenterUnbindWebChat;
import com.starcor.core.epgapi.params.UserCenterUnbindWebChatParams;
import com.starcor.core.parser.json.UserCenterUnbindWebChatSAXParserJson;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsApiUserCenterUnbindWebchatTask extends ServerApiTask
{
  private String license;
  private ISccmsApiUserCenterUnbindWebchatTaskListener lsr;
  private String ticket;

  public SccmsApiUserCenterUnbindWebchatTask(String paramString1, String paramString2)
  {
    this.license = paramString1;
    this.ticket = paramString2;
  }

  public String getTaskName()
  {
    return "N212_B_14";
  }

  public String getUrl()
  {
    UserCenterUnbindWebChatParams localUserCenterUnbindWebChatParams = new UserCenterUnbindWebChatParams(this.license, this.ticket);
    localUserCenterUnbindWebChatParams.setResultFormat(1);
    return webUrlFormatter.i().formatUrl(localUserCenterUnbindWebChatParams.toString(), localUserCenterUnbindWebChatParams.getApiName());
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
      UserCenterUnbindWebChat localUserCenterUnbindWebChat = (UserCenterUnbindWebChat)new UserCenterUnbindWebChatSAXParserJson().parser(paramSCResponse.getContents());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localUserCenterUnbindWebChat);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiUserCenterUnbindWebchatTaskListener paramISccmsApiUserCenterUnbindWebchatTaskListener)
  {
    this.lsr = paramISccmsApiUserCenterUnbindWebchatTaskListener;
  }

  public static abstract interface ISccmsApiUserCenterUnbindWebchatTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, UserCenterUnbindWebChat paramUserCenterUnbindWebChat);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiUserCenterUnbindWebchatTask
 * JD-Core Version:    0.6.2
 */