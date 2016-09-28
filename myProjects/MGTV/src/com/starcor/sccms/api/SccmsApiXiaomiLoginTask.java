package com.starcor.sccms.api;

import com.starcor.core.domain.UserCenterLogin;
import com.starcor.core.epgapi.params.XiaomiLoginParams;
import com.starcor.core.parser.json.GetUserCenterLoginSAXParserJson;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCachedTask;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsApiXiaomiLoginTask extends ServerApiCachedTask
{
  String code;
  String license;
  ISccmsApiXiaomiLoginTaskListener lsr;

  public SccmsApiXiaomiLoginTask(String paramString1, String paramString2)
  {
    this.code = paramString1;
    this.license = paramString2;
  }

  public String getTaskName()
  {
    return "n212_B_16";
  }

  public String getUrl()
  {
    XiaomiLoginParams localXiaomiLoginParams = new XiaomiLoginParams(this.code, this.license);
    localXiaomiLoginParams.setResultFormat(1);
    String str = webUrlFormatter.i().formatUrl(localXiaomiLoginParams.toString(), localXiaomiLoginParams.getApiName());
    Logger.i("SccmsApiXiaomiLoginTask", "url:" + str);
    return str;
  }

  public void perform(SCResponse paramSCResponse)
  {
    GetUserCenterLoginSAXParserJson localGetUserCenterLoginSAXParserJson = new GetUserCenterLoginSAXParserJson();
    if (ServerApiTools.isSCResponseError(paramSCResponse))
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
    while (paramSCResponse == null)
      return;
    try
    {
      UserCenterLogin localUserCenterLogin = (UserCenterLogin)localGetUserCenterLoginSAXParserJson.parser(paramSCResponse.getContents());
      Logger.i("SccmsApiXiaomiLoginTask", "n212_B_16 result:" + localUserCenterLogin.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), paramSCResponse), localUserCenterLogin);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiXiaomiLoginTaskListener paramISccmsApiXiaomiLoginTaskListener)
  {
    this.lsr = paramISccmsApiXiaomiLoginTaskListener;
  }

  public static abstract interface ISccmsApiXiaomiLoginTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, UserCenterLogin paramUserCenterLogin);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiXiaomiLoginTask
 * JD-Core Version:    0.6.2
 */