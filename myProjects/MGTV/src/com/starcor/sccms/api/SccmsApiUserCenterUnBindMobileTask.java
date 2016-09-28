package com.starcor.sccms.api;

import com.starcor.core.domain.UserCenterBindMobile;
import com.starcor.core.epgapi.params.UserCenterUnBindMobileParams;
import com.starcor.core.parser.json.BindMobileSAXParserJson;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;
import java.io.ByteArrayInputStream;

public class SccmsApiUserCenterUnBindMobileTask extends ServerApiTask
{
  private String license = "";
  private ISccmsApiUnBindMobileTaskListener lsr;
  private String mobile = "";
  private String password = "";
  private String ticket = "";
  private String userName = "";

  public SccmsApiUserCenterUnBindMobileTask(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    this.userName = paramString1;
    this.password = paramString2;
    this.ticket = paramString3;
    this.mobile = paramString4;
    this.license = paramString5;
  }

  public String getTaskName()
  {
    return "N212_B_10";
  }

  public String getUrl()
  {
    UserCenterUnBindMobileParams localUserCenterUnBindMobileParams = new UserCenterUnBindMobileParams(this.userName, this.password, this.ticket, this.mobile, this.license);
    localUserCenterUnBindMobileParams.setResultFormat(1);
    String str = webUrlFormatter.i().formatUrl(localUserCenterUnBindMobileParams.toString(), localUserCenterUnBindMobileParams.getApiName());
    Logger.i("SccmsApiUserCenterUnBindMobileTask", "N212_B_10 url:" + str);
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
      Logger.i("SccmsApiUserCenterUnBindMobileTask", "N212_B_10 result:" + localUserCenterBindMobile.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localUserCenterBindMobile);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiUnBindMobileTaskListener paramISccmsApiUnBindMobileTaskListener)
  {
    this.lsr = paramISccmsApiUnBindMobileTaskListener;
  }

  public static abstract interface ISccmsApiUnBindMobileTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, UserCenterBindMobile paramUserCenterBindMobile);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiUserCenterUnBindMobileTask
 * JD-Core Version:    0.6.2
 */