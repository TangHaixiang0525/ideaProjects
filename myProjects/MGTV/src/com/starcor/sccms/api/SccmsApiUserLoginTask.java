package com.starcor.sccms.api;

import com.starcor.config.DeviceInfo;
import com.starcor.core.domain.UserInfo;
import com.starcor.core.epgapi.params.UserLoginParams;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.parser.sax.CheckUserInfoSAXParser;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;
import java.io.ByteArrayInputStream;

public class SccmsApiUserLoginTask extends ServerApiTask
{
  private ISccmsApiUserLoginTaskListener lsr;
  private String password;
  private String userName;

  public SccmsApiUserLoginTask(String paramString1, String paramString2)
  {
    this.userName = paramString1;
    this.password = paramString2;
  }

  public String getTaskName()
  {
    return "N200_A_5";
  }

  public String getUrl()
  {
    UserLoginParams localUserLoginParams = new UserLoginParams(this.userName, this.password, DeviceInfo.getMac());
    localUserLoginParams.setResultFormat(0);
    return webUrlFormatter.i().formatUrl(localUserLoginParams.toString(), localUserLoginParams.getApiName());
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
      UserInfo localUserInfo = (UserInfo)new CheckUserInfoSAXParser(GlobalLogic.getInstance().getDeviceId(), DeviceInfo.getMac()).parser(new ByteArrayInputStream(paramSCResponse.getContents()));
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localUserInfo);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiUserLoginTaskListener paramISccmsApiUserLoginTaskListener)
  {
    this.lsr = paramISccmsApiUserLoginTaskListener;
  }

  public static abstract interface ISccmsApiUserLoginTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, UserInfo paramUserInfo);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiUserLoginTask
 * JD-Core Version:    0.6.2
 */