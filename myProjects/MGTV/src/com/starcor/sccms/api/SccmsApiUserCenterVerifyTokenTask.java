package com.starcor.sccms.api;

import com.starcor.core.domain.UserCenterLogin;
import com.starcor.core.epgapi.params.UserCenterVerifyTokenParams;
import com.starcor.core.parser.json.GetUserCenterLoginSAXParserJson;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsApiUserCenterVerifyTokenTask extends ServerApiTask
{
  private String license = "";
  private ISccmsApiUserCenterVerifyTokenTaskListener lsr;
  private String ticket = "";

  public SccmsApiUserCenterVerifyTokenTask(String paramString1, String paramString2)
  {
    this.ticket = paramString1;
    this.license = paramString2;
  }

  public String getTaskName()
  {
    return "N212_B_11";
  }

  public String getUrl()
  {
    UserCenterVerifyTokenParams localUserCenterVerifyTokenParams = new UserCenterVerifyTokenParams(this.ticket, this.license);
    localUserCenterVerifyTokenParams.setResultFormat(1);
    String str = webUrlFormatter.i().formatUrl(localUserCenterVerifyTokenParams.toString(), localUserCenterVerifyTokenParams.getApiName());
    Logger.i("SccmsApiUserCenterVerifyTokenTask", "N212_B_11 url:" + str);
    return str;
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
      UserCenterLogin localUserCenterLogin = (UserCenterLogin)new GetUserCenterLoginSAXParserJson().parser(paramSCResponse.getContents());
      Logger.e("SccmsApiUserCenterVerifyTokenTask", "N212_B_11 result:" + localUserCenterLogin.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localUserCenterLogin);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiUserCenterVerifyTokenTaskListener paramISccmsApiUserCenterVerifyTokenTaskListener)
  {
    this.lsr = paramISccmsApiUserCenterVerifyTokenTaskListener;
  }

  public static abstract interface ISccmsApiUserCenterVerifyTokenTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, UserCenterLogin paramUserCenterLogin);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiUserCenterVerifyTokenTask
 * JD-Core Version:    0.6.2
 */