package com.starcor.sccms.api;

import com.starcor.core.domain.MobileCode;
import com.starcor.core.epgapi.params.GetMobileCodeParams;
import com.starcor.core.parser.json.GetMobileCodeSAXParserJson;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCachedTask;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsApiUserCenterGetMobileCodeTask extends ServerApiCachedTask
{
  String license;
  ISccmsApiGetMobileMsgAuthTaskListener lsr;
  String mobile;

  public SccmsApiUserCenterGetMobileCodeTask(String paramString1, String paramString2)
  {
    this.mobile = paramString1;
    this.license = paramString2;
  }

  public String getTaskName()
  {
    return "N212_B_2";
  }

  public String getUrl()
  {
    GetMobileCodeParams localGetMobileCodeParams = new GetMobileCodeParams(this.mobile, this.license);
    localGetMobileCodeParams.setResultFormat(1);
    String str = webUrlFormatter.i().formatUrl(localGetMobileCodeParams.toString(), localGetMobileCodeParams.getApiName());
    Logger.i("SccmsApiUserCenterGetMobileCodeTask", "N212_B_2 url:" + str);
    return str;
  }

  public void perform(SCResponse paramSCResponse)
  {
    GetMobileCodeSAXParserJson localGetMobileCodeSAXParserJson = new GetMobileCodeSAXParserJson();
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    try
    {
      MobileCode localMobileCode = (MobileCode)localGetMobileCodeSAXParserJson.parser(paramSCResponse.getContents());
      Logger.i("SccmsApiUserCenterGetMobileCodeTask", "N212_B_2 result:" + localMobileCode.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localMobileCode);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiGetMobileMsgAuthTaskListener paramISccmsApiGetMobileMsgAuthTaskListener)
  {
    this.lsr = paramISccmsApiGetMobileMsgAuthTaskListener;
  }

  public static abstract interface ISccmsApiGetMobileMsgAuthTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, MobileCode paramMobileCode);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiUserCenterGetMobileCodeTask
 * JD-Core Version:    0.6.2
 */