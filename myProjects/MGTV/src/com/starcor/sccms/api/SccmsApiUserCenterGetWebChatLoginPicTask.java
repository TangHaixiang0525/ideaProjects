package com.starcor.sccms.api;

import com.starcor.core.domain.WebChatLogin;
import com.starcor.core.epgapi.params.WebChatLoginParams;
import com.starcor.core.parser.json.WebChatLoginSAXParserJson;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCachedTask;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsApiUserCenterGetWebChatLoginPicTask extends ServerApiCachedTask
{
  String license;
  ISccmsApiGetWebChatLoginPicTaskListenter lsr;
  int type;

  public SccmsApiUserCenterGetWebChatLoginPicTask(String paramString, int paramInt)
  {
    this.license = paramString;
    this.type = paramInt;
  }

  public String getTaskName()
  {
    return "N212_B_5";
  }

  public String getUrl()
  {
    WebChatLoginParams localWebChatLoginParams = new WebChatLoginParams(this.license, this.type);
    localWebChatLoginParams.setResultFormat(1);
    String str = webUrlFormatter.i().formatUrl(localWebChatLoginParams.toString(), localWebChatLoginParams.getApiName());
    Logger.i("SccmsApiUserCenterGetWebChatLoginPicTask", "N212_B_5 url:" + str);
    return str;
  }

  public void perform(SCResponse paramSCResponse)
  {
    WebChatLoginSAXParserJson localWebChatLoginSAXParserJson = new WebChatLoginSAXParserJson();
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    try
    {
      WebChatLogin localWebChatLogin = (WebChatLogin)localWebChatLoginSAXParserJson.parser(paramSCResponse.getContents());
      Logger.i("SccmsApiUserCenterGetWebChatLoginPicTask", " N212_B_5 result:" + localWebChatLogin.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localWebChatLogin);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiGetWebChatLoginPicTaskListenter paramISccmsApiGetWebChatLoginPicTaskListenter)
  {
    this.lsr = paramISccmsApiGetWebChatLoginPicTaskListenter;
  }

  public static abstract interface ISccmsApiGetWebChatLoginPicTaskListenter
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, WebChatLogin paramWebChatLogin);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiUserCenterGetWebChatLoginPicTask
 * JD-Core Version:    0.6.2
 */