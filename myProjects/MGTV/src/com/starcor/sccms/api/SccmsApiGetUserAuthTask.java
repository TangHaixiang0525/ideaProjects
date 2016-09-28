package com.starcor.sccms.api;

import com.starcor.core.domain.UserAuth;
import com.starcor.core.epgapi.params.GetUserAuthParams;
import com.starcor.core.parser.json.GetUserAuthSAXParserJson;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCachedTask;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsApiGetUserAuthTask extends ServerApiCachedTask
{
  ISccmsApiGetUserAuthTaskListener lsr;
  String nns_user_id = null;
  String nns_video_id = null;
  String nns_video_type = null;

  public SccmsApiGetUserAuthTask(String paramString1, String paramString2, String paramString3)
  {
    this.nns_user_id = paramString1;
    this.nns_video_id = paramString2;
    this.nns_video_type = paramString3;
  }

  public String getTaskName()
  {
    return "N200_A_1";
  }

  public String getUrl()
  {
    GetUserAuthParams localGetUserAuthParams = new GetUserAuthParams(this.nns_user_id, this.nns_video_id, this.nns_video_type);
    localGetUserAuthParams.setResultFormat(1);
    return webUrlFormatter.i().formatUrl(localGetUserAuthParams.toString(), localGetUserAuthParams.getApiName());
  }

  public void perform(SCResponse paramSCResponse)
  {
    GetUserAuthSAXParserJson localGetUserAuthSAXParserJson = new GetUserAuthSAXParserJson();
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    try
    {
      UserAuth localUserAuth = (UserAuth)localGetUserAuthSAXParserJson.parser(paramSCResponse.getContents());
      Logger.i("SccmsApiGetUserAuthTask", " result:" + localUserAuth.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localUserAuth);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiGetUserAuthTaskListener paramISccmsApiGetUserAuthTaskListener)
  {
    this.lsr = paramISccmsApiGetUserAuthTaskListener;
  }

  public static abstract interface ISccmsApiGetUserAuthTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, UserAuth paramUserAuth);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiGetUserAuthTask
 * JD-Core Version:    0.6.2
 */