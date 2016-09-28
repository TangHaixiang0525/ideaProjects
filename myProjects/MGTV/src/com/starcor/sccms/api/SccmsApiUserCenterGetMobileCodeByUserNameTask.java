package com.starcor.sccms.api;

import com.starcor.core.domain.MobileCode;
import com.starcor.core.epgapi.params.UserCenterGetMobileCodeByUserNameParams;
import com.starcor.core.parser.json.GetMobileCodeByUserNameSAXParserJson;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCachedTask;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsApiUserCenterGetMobileCodeByUserNameTask extends ServerApiCachedTask
{
  String license;
  ISccmsApiGetMobileCodeByUserNameTaskListener lsr;
  String userName;

  public SccmsApiUserCenterGetMobileCodeByUserNameTask(String paramString1, String paramString2)
  {
    this.userName = paramString1;
    this.license = paramString2;
  }

  public String getTaskName()
  {
    return "N212_B_7";
  }

  public String getUrl()
  {
    UserCenterGetMobileCodeByUserNameParams localUserCenterGetMobileCodeByUserNameParams = new UserCenterGetMobileCodeByUserNameParams(this.userName, this.license);
    localUserCenterGetMobileCodeByUserNameParams.setResultFormat(1);
    String str = webUrlFormatter.i().formatUrl(localUserCenterGetMobileCodeByUserNameParams.toString(), localUserCenterGetMobileCodeByUserNameParams.getApiName());
    Logger.i("SccmsApiUserCenterGetMobileCodeByUserNameTask", "N212_B_7 url:" + str);
    return str;
  }

  public void perform(SCResponse paramSCResponse)
  {
    GetMobileCodeByUserNameSAXParserJson localGetMobileCodeByUserNameSAXParserJson = new GetMobileCodeByUserNameSAXParserJson();
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    try
    {
      MobileCode localMobileCode = (MobileCode)localGetMobileCodeByUserNameSAXParserJson.parser(paramSCResponse.getContents());
      Logger.i("SccmsApiUserCenterGetMobileCodeByUserNameTask", "N212_B_7 result:" + localMobileCode.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localMobileCode);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiGetMobileCodeByUserNameTaskListener paramISccmsApiGetMobileCodeByUserNameTaskListener)
  {
    this.lsr = paramISccmsApiGetMobileCodeByUserNameTaskListener;
  }

  public static abstract interface ISccmsApiGetMobileCodeByUserNameTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, MobileCode paramMobileCode);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiUserCenterGetMobileCodeByUserNameTask
 * JD-Core Version:    0.6.2
 */