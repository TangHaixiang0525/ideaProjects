package com.starcor.sccms.api;

import com.starcor.core.domain.UserCenterBindMobile;
import com.starcor.core.epgapi.params.UserCenterBindMobileParams;
import com.starcor.core.parser.json.BindMobileSAXParserJson;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;
import java.io.ByteArrayInputStream;

public class SccmsApiUserCenterBindMobileTask extends ServerApiTask
{
  private String license = "";
  private ISccmsApiBindMobileTaskListener lsr;
  private String mobile = "";
  private String mobileCode = "";
  private String password = "";
  private String ticket = "";
  private String userName = "";

  public SccmsApiUserCenterBindMobileTask(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6)
  {
    this.userName = paramString1;
    this.password = paramString2;
    this.ticket = paramString3;
    this.mobile = paramString4;
    this.mobileCode = paramString5;
    this.license = paramString6;
  }

  public String getTaskName()
  {
    return "N212_B_9";
  }

  public String getUrl()
  {
    UserCenterBindMobileParams localUserCenterBindMobileParams = new UserCenterBindMobileParams(this.userName, this.password, this.ticket, this.mobile, this.mobileCode, this.license);
    localUserCenterBindMobileParams.setResultFormat(1);
    String str = webUrlFormatter.i().formatUrl(localUserCenterBindMobileParams.toString(), localUserCenterBindMobileParams.getApiName());
    Logger.i("SccmsApiUserCenterBindMobileTask", "N212_B_9 url:" + str);
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
      UserCenterBindMobile localUserCenterBindMobile = (UserCenterBindMobile)new BindMobileSAXParserJson().parser(new ByteArrayInputStream(paramSCResponse.getContents()));
      Logger.i("SccmsApiUserCenterBindMobileTask", "N212_B_9 result:" + localUserCenterBindMobile.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localUserCenterBindMobile);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiBindMobileTaskListener paramISccmsApiBindMobileTaskListener)
  {
    this.lsr = paramISccmsApiBindMobileTaskListener;
  }

  public static abstract interface ISccmsApiBindMobileTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, UserCenterBindMobile paramUserCenterBindMobile);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiUserCenterBindMobileTask
 * JD-Core Version:    0.6.2
 */