package com.starcor.sccms.api;

import com.starcor.core.domain.UserCenterLogout;
import com.starcor.core.epgapi.params.UserCenterLogoutParams;
import com.starcor.core.parser.json.UserCenterLogoutSAXParserJson;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCachedTask;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsApiUserCenterLogoutTask extends ServerApiCachedTask
{
  String license;
  ISccmsApiUserCenterLogoutTaskListener lsr;
  String ticket;

  public SccmsApiUserCenterLogoutTask(String paramString1, String paramString2)
  {
    this.ticket = paramString1;
    this.license = paramString2;
  }

  public String getTaskName()
  {
    return "N212_B_13";
  }

  public String getUrl()
  {
    UserCenterLogoutParams localUserCenterLogoutParams = new UserCenterLogoutParams(this.ticket, this.license);
    localUserCenterLogoutParams.setResultFormat(1);
    String str = webUrlFormatter.i().formatUrl(localUserCenterLogoutParams.toString(), localUserCenterLogoutParams.getApiName());
    Logger.i("SccmsApiUserCenterLogoutTask", "N212_B_13 url:" + str);
    return str;
  }

  public void perform(SCResponse paramSCResponse)
  {
    UserCenterLogoutSAXParserJson localUserCenterLogoutSAXParserJson = new UserCenterLogoutSAXParserJson();
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    try
    {
      UserCenterLogout localUserCenterLogout = (UserCenterLogout)localUserCenterLogoutSAXParserJson.parser(paramSCResponse.getContents());
      Logger.i("SccmsApiUserCenterLogoutTask", "N212_B_13 result:" + localUserCenterLogout.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localUserCenterLogout);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiUserCenterLogoutTaskListener paramISccmsApiUserCenterLogoutTaskListener)
  {
    this.lsr = paramISccmsApiUserCenterLogoutTaskListener;
  }

  public static abstract interface ISccmsApiUserCenterLogoutTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, UserCenterLogout paramUserCenterLogout);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiUserCenterLogoutTask
 * JD-Core Version:    0.6.2
 */