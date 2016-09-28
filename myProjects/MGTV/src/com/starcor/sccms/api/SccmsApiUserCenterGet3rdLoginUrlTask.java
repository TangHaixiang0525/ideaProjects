package com.starcor.sccms.api;

import com.starcor.core.domain.UserCenter3rdLoginUrl;
import com.starcor.core.epgapi.params.UserCenterGet3rdLoginUrlPrams;
import com.starcor.core.parser.json.UserCenter3rdLoginUrlParserJson;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsApiUserCenterGet3rdLoginUrlTask extends ServerApiTask
{
  private String license = "";
  private ISccmsApiUserCenterGet3rdLoginUrlTaskListener lsr;

  public SccmsApiUserCenterGet3rdLoginUrlTask(String paramString)
  {
    this.license = paramString;
  }

  public String getTaskName()
  {
    return "N212_B_12";
  }

  public String getUrl()
  {
    UserCenterGet3rdLoginUrlPrams localUserCenterGet3rdLoginUrlPrams = new UserCenterGet3rdLoginUrlPrams(this.license);
    localUserCenterGet3rdLoginUrlPrams.setResultFormat(1);
    String str = webUrlFormatter.i().formatUrl(localUserCenterGet3rdLoginUrlPrams.toString(), localUserCenterGet3rdLoginUrlPrams.getApiName());
    Logger.i("UserCenter3rdLoginUrlParserJson", "N212_B_12 url:" + str);
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
      UserCenter3rdLoginUrl localUserCenter3rdLoginUrl = (UserCenter3rdLoginUrl)new UserCenter3rdLoginUrlParserJson().parser(paramSCResponse.getContents());
      Logger.i("UserCenter3rdLoginUrlParserJson", "N212_B_12 result:" + localUserCenter3rdLoginUrl.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localUserCenter3rdLoginUrl);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiUserCenterGet3rdLoginUrlTaskListener paramISccmsApiUserCenterGet3rdLoginUrlTaskListener)
  {
    this.lsr = paramISccmsApiUserCenterGet3rdLoginUrlTaskListener;
  }

  public static abstract interface ISccmsApiUserCenterGet3rdLoginUrlTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, UserCenter3rdLoginUrl paramUserCenter3rdLoginUrl);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiUserCenterGet3rdLoginUrlTask
 * JD-Core Version:    0.6.2
 */