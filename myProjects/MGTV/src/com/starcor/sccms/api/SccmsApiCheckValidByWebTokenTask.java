package com.starcor.sccms.api;

import com.starcor.config.DeviceInfo;
import com.starcor.core.domain.UserInfo;
import com.starcor.core.epgapi.params.CheckTokenParams;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.parser.sax.CheckUserInfoSAXParser;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;
import java.io.ByteArrayInputStream;

public class SccmsApiCheckValidByWebTokenTask extends ServerApiTask
{
  private ISccmsApiCheckValidByWebTokenTaskListener lsr;

  public String getTaskName()
  {
    return "N200_A_2";
  }

  public String getUrl()
  {
    CheckTokenParams localCheckTokenParams = new CheckTokenParams(DeviceInfo.getMac());
    localCheckTokenParams.setResultFormat(0);
    return webUrlFormatter.i().formatUrl(localCheckTokenParams.toString(), localCheckTokenParams.getApiName());
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
    CheckUserInfoSAXParser localCheckUserInfoSAXParser = new CheckUserInfoSAXParser(GlobalLogic.getInstance().getDeviceId(), DeviceInfo.getMac());
    try
    {
      UserInfo localUserInfo = (UserInfo)localCheckUserInfoSAXParser.parser(new ByteArrayInputStream(paramSCResponse.getContents()));
      Logger.i("SccmsApiCheckValidByWebTokenTask", " result:" + localUserInfo.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localUserInfo);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiCheckValidByWebTokenTaskListener paramISccmsApiCheckValidByWebTokenTaskListener)
  {
    this.lsr = paramISccmsApiCheckValidByWebTokenTaskListener;
  }

  public static abstract interface ISccmsApiCheckValidByWebTokenTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, UserInfo paramUserInfo);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiCheckValidByWebTokenTask
 * JD-Core Version:    0.6.2
 */