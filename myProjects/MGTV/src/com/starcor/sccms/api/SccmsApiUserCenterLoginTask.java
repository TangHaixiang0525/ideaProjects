package com.starcor.sccms.api;

import com.starcor.core.domain.UserCenterLogin;
import com.starcor.core.epgapi.params.UserCenterLoginParams;
import com.starcor.core.parser.json.GetUserCenterLoginSAXParserJson;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCachedTask;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsApiUserCenterLoginTask extends ServerApiCachedTask
{
  String license;
  ISccmsApiUserCenterLoginTaskListener lsr;
  String name;
  String pwd;

  public SccmsApiUserCenterLoginTask(String paramString1, String paramString2, String paramString3)
  {
    this.name = paramString1;
    this.pwd = paramString2;
    this.license = paramString3;
  }

  public String getTaskName()
  {
    return "N212_B_1";
  }

  public String getUrl()
  {
    UserCenterLoginParams localUserCenterLoginParams = new UserCenterLoginParams(this.name, this.pwd, this.license);
    localUserCenterLoginParams.setResultFormat(1);
    String str = webUrlFormatter.i().formatUrl(localUserCenterLoginParams.toString(), localUserCenterLoginParams.getApiName());
    Logger.i("SccmsApiUserCenterLoginTask", "N212_B_1 url:" + str);
    return str;
  }

  public void perform(SCResponse paramSCResponse)
  {
    GetUserCenterLoginSAXParserJson localGetUserCenterLoginSAXParserJson = new GetUserCenterLoginSAXParserJson();
    if (ServerApiTools.isSCResponseError(paramSCResponse))
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
    while (paramSCResponse == null)
      return;
    try
    {
      UserCenterLogin localUserCenterLogin = (UserCenterLogin)localGetUserCenterLoginSAXParserJson.parser(paramSCResponse.getContents());
      Logger.i("SccmsApiUserCenterLoginTask", "N212_B_1 result:" + localUserCenterLogin.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localUserCenterLogin);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiUserCenterLoginTaskListener paramISccmsApiUserCenterLoginTaskListener)
  {
    this.lsr = paramISccmsApiUserCenterLoginTaskListener;
  }

  public static abstract interface ISccmsApiUserCenterLoginTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, UserCenterLogin paramUserCenterLogin);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiUserCenterLoginTask
 * JD-Core Version:    0.6.2
 */