package com.starcor.sccms.api;

import com.starcor.core.domain.UserCenterChangePwd;
import com.starcor.core.epgapi.params.UserCenterResetPwdParams;
import com.starcor.core.parser.json.UserCenterChangePwdSAXParserJson;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCachedTask;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsApiUserCenterResetPwdTask extends ServerApiCachedTask
{
  String license;
  ISccmsApiUserCenterResetPwdTaskListener lsr;
  String mobilecode;
  String name;
  String newpwd;

  public SccmsApiUserCenterResetPwdTask(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    this.name = paramString1;
    this.newpwd = paramString2;
    this.mobilecode = paramString3;
    this.license = paramString4;
  }

  public String getTaskName()
  {
    return "N212_B_8";
  }

  public String getUrl()
  {
    UserCenterResetPwdParams localUserCenterResetPwdParams = new UserCenterResetPwdParams(this.name, this.newpwd, this.mobilecode, this.license);
    localUserCenterResetPwdParams.setResultFormat(1);
    String str = webUrlFormatter.i().formatUrl(localUserCenterResetPwdParams.toString(), localUserCenterResetPwdParams.getApiName());
    Logger.i("SccmsApiUserCenterResetPwdTask", "N212_B_8 url:" + str);
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
      Logger.i("SccmsApiUserCenterResetPwdTask", "N212_B_8 result:" + localUserCenterChangePwd.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localUserCenterChangePwd);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiUserCenterResetPwdTaskListener paramISccmsApiUserCenterResetPwdTaskListener)
  {
    this.lsr = paramISccmsApiUserCenterResetPwdTaskListener;
  }

  public static abstract interface ISccmsApiUserCenterResetPwdTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, UserCenterChangePwd paramUserCenterChangePwd);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiUserCenterResetPwdTask
 * JD-Core Version:    0.6.2
 */