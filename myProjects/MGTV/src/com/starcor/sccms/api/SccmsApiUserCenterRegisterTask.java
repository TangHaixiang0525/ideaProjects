package com.starcor.sccms.api;

import com.starcor.core.domain.UserCenterRegister;
import com.starcor.core.epgapi.params.UserCenterRegisterParams;
import com.starcor.core.parser.json.GetUserCenterRegisterSAXParserJson;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCachedTask;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsApiUserCenterRegisterTask extends ServerApiCachedTask
{
  String license;
  ISccmsApiUserCenterRegisterTaskListener lsr;
  String mobilecode;
  String pwd;
  String userName;

  public SccmsApiUserCenterRegisterTask(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    this.userName = paramString1;
    this.pwd = paramString2;
    this.mobilecode = paramString3;
    this.license = paramString4;
  }

  public String getTaskName()
  {
    return "N212_B_3";
  }

  public String getUrl()
  {
    UserCenterRegisterParams localUserCenterRegisterParams = new UserCenterRegisterParams(this.userName, this.pwd, this.mobilecode, this.license);
    localUserCenterRegisterParams.setResultFormat(1);
    String str = webUrlFormatter.i().formatUrl(localUserCenterRegisterParams.toString(), localUserCenterRegisterParams.getApiName());
    Logger.i("SccmsApiUserCenterRegisterTask", "N212_B_3 url:" + str);
    return str;
  }

  public void perform(SCResponse paramSCResponse)
  {
    GetUserCenterRegisterSAXParserJson localGetUserCenterRegisterSAXParserJson = new GetUserCenterRegisterSAXParserJson();
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    try
    {
      UserCenterRegister localUserCenterRegister = (UserCenterRegister)localGetUserCenterRegisterSAXParserJson.parser(paramSCResponse.getContents());
      Logger.i("SccmsApiUserCenterRegisterTask", "N212_B_3 result:" + localUserCenterRegister.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localUserCenterRegister);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiUserCenterRegisterTaskListener paramISccmsApiUserCenterRegisterTaskListener)
  {
    this.lsr = paramISccmsApiUserCenterRegisterTaskListener;
  }

  public static abstract interface ISccmsApiUserCenterRegisterTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, UserCenterRegister paramUserCenterRegister);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiUserCenterRegisterTask
 * JD-Core Version:    0.6.2
 */