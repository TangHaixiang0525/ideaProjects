package com.starcor.sccms.api;

import com.starcor.core.domain.UserCenterInfo;
import com.starcor.core.epgapi.params.GetUserCenterInfoParams;
import com.starcor.core.parser.json.GetUserCenterInfoSAXParserJson;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCachedTask;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsApiAAAGetUserInfoTask extends ServerApiCachedTask
{
  private static String TAG = "SccmsApiAAAGetUserInfoTask";
  private int businessId = -1;
  private String license = "";
  ISccmsApiGetUserCenterInfoTaskListener lsr;
  private String ticket = "";

  public SccmsApiAAAGetUserInfoTask(String paramString1, int paramInt, String paramString2)
  {
    this.ticket = paramString1;
    this.businessId = paramInt;
    this.license = paramString2;
  }

  public String getTaskName()
  {
    return "N212_A_12";
  }

  public String getUrl()
  {
    GetUserCenterInfoParams localGetUserCenterInfoParams = new GetUserCenterInfoParams(this.ticket, this.businessId, this.license);
    localGetUserCenterInfoParams.setResultFormat(1);
    String str = webUrlFormatter.i().formatUrl(localGetUserCenterInfoParams.toString(), localGetUserCenterInfoParams.getApiName());
    Logger.i("SccmsApiAAAGetUserInfoTask", "N212_A_12 url:" + str);
    return str;
  }

  public void perform(SCResponse paramSCResponse)
  {
    GetUserCenterInfoSAXParserJson localGetUserCenterInfoSAXParserJson = new GetUserCenterInfoSAXParserJson();
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    try
    {
      UserCenterInfo localUserCenterInfo = (UserCenterInfo)localGetUserCenterInfoSAXParserJson.parser(paramSCResponse.getContents());
      Logger.i("SccmsApiAAAGetUserInfoTask", "N212_A_12 result:" + localUserCenterInfo.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localUserCenterInfo);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiGetUserCenterInfoTaskListener paramISccmsApiGetUserCenterInfoTaskListener)
  {
    this.lsr = paramISccmsApiGetUserCenterInfoTaskListener;
  }

  public static abstract interface ISccmsApiGetUserCenterInfoTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, UserCenterInfo paramUserCenterInfo);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiAAAGetUserInfoTask
 * JD-Core Version:    0.6.2
 */