package com.starcor.sccms.api;

import com.starcor.core.domain.UserCenterChangePwd;
import com.starcor.core.epgapi.params.UserCenterChangePwdParams;
import com.starcor.core.parser.json.UserCenterChangePwdSAXParserJson;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCachedTask;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsApiUserCenterChangePwdTask extends ServerApiCachedTask
{
  String license;
  ISccmsApiUserCenterChangePwdTaskListener lsr;
  String name;
  String newpwd;
  String oldpwd;
  String pwd;
  String ticket;

  public SccmsApiUserCenterChangePwdTask(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6)
  {
    this.name = paramString1;
    this.pwd = paramString2;
    this.oldpwd = paramString3;
    this.newpwd = paramString4;
    this.ticket = paramString5;
    this.license = paramString6;
  }

  public String getTaskName()
  {
    return "N212_B_4";
  }

  public String getUrl()
  {
    UserCenterChangePwdParams localUserCenterChangePwdParams = new UserCenterChangePwdParams(this.name, this.oldpwd, this.newpwd, this.ticket, this.license);
    localUserCenterChangePwdParams.setResultFormat(1);
    String str = webUrlFormatter.i().formatUrl(localUserCenterChangePwdParams.toString(), localUserCenterChangePwdParams.getApiName());
    Logger.i("SccmsApiUserCenterChangePwdTask", "N212_B_4 url:" + str);
    return str;
  }

  public void perform(SCResponse paramSCResponse)
  {
    UserCenterChangePwdSAXParserJson localUserCenterChangePwdSAXParserJson = new UserCenterChangePwdSAXParserJson();
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    try
    {
      UserCenterChangePwd localUserCenterChangePwd = (UserCenterChangePwd)localUserCenterChangePwdSAXParserJson.parser(paramSCResponse.getContents());
      Logger.i("SccmsApiUserCenterChangePwdTask", "N212_B_4 result:" + localUserCenterChangePwd.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localUserCenterChangePwd);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiUserCenterChangePwdTaskListener paramISccmsApiUserCenterChangePwdTaskListener)
  {
    this.lsr = paramISccmsApiUserCenterChangePwdTaskListener;
  }

  public static abstract interface ISccmsApiUserCenterChangePwdTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, UserCenterChangePwd paramUserCenterChangePwd);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiUserCenterChangePwdTask
 * JD-Core Version:    0.6.2
 */