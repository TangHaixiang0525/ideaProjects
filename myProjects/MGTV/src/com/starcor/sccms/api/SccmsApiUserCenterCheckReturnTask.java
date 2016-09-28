package com.starcor.sccms.api;

import com.starcor.core.domain.UserCenterCheckReturn;
import com.starcor.core.epgapi.params.UserCenterCheckReturnParams;
import com.starcor.core.parser.json.UserCenterCheckReturnSAXParserJson;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsApiUserCenterCheckReturnTask extends ServerApiTask
{
  private String license = "";
  private ISccmsApiCheckUserCenterReturnTaskListener lsr;
  private String rcode = "";

  public SccmsApiUserCenterCheckReturnTask(String paramString1, String paramString2)
  {
    this.rcode = paramString1;
    this.license = paramString2;
  }

  public String getTaskName()
  {
    return "N212_B_6";
  }

  public String getUrl()
  {
    UserCenterCheckReturnParams localUserCenterCheckReturnParams = new UserCenterCheckReturnParams(this.rcode, this.license);
    localUserCenterCheckReturnParams.setResultFormat(1);
    String str = webUrlFormatter.i().formatUrl(localUserCenterCheckReturnParams.toString(), localUserCenterCheckReturnParams.getApiName());
    Logger.i("SccmsApiUserCenterCheckReturnTask", "N212_B_6 url:" + str);
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
      UserCenterCheckReturn localUserCenterCheckReturn = (UserCenterCheckReturn)new UserCenterCheckReturnSAXParserJson().parser(paramSCResponse.getContents());
      Logger.i("SccmsApiUserCenterCheckReturnTask", "N212_B_6 result:" + localUserCenterCheckReturn.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localUserCenterCheckReturn);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiCheckUserCenterReturnTaskListener paramISccmsApiCheckUserCenterReturnTaskListener)
  {
    this.lsr = paramISccmsApiCheckUserCenterReturnTaskListener;
  }

  public static abstract interface ISccmsApiCheckUserCenterReturnTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, UserCenterCheckReturn paramUserCenterCheckReturn);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiUserCenterCheckReturnTask
 * JD-Core Version:    0.6.2
 */