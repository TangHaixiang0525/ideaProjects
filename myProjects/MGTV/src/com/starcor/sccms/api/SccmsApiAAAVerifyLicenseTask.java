package com.starcor.sccms.api;

import com.starcor.core.domain.AAAVerifyLicense;
import com.starcor.core.epgapi.params.AAAVerifyLicenseParams;
import com.starcor.core.parser.json.AAAVerifyLicenseSAXParserJson;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsApiAAAVerifyLicenseTask extends ServerApiTask
{
  private String license = "";
  private ISccmsApiVerifyLicenseTaskListener lsr;

  public SccmsApiAAAVerifyLicenseTask(String paramString)
  {
    this.license = paramString;
  }

  public String getTaskName()
  {
    return "N212_A_2";
  }

  public String getUrl()
  {
    AAAVerifyLicenseParams localAAAVerifyLicenseParams = new AAAVerifyLicenseParams(this.license);
    localAAAVerifyLicenseParams.setResultFormat(1);
    String str = webUrlFormatter.i().formatUrl(localAAAVerifyLicenseParams.toString(), localAAAVerifyLicenseParams.getApiName());
    Logger.i("SccmsApiAAAVerifyLicenseTask", "N212_A_2 url:" + str);
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
      AAAVerifyLicense localAAAVerifyLicense = (AAAVerifyLicense)new AAAVerifyLicenseSAXParserJson().parser(paramSCResponse.getContents());
      Logger.i("SccmsApiAAAVerifyLicenseTask", "N212_A_2 result:" + localAAAVerifyLicense.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localAAAVerifyLicense);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiVerifyLicenseTaskListener paramISccmsApiVerifyLicenseTaskListener)
  {
    this.lsr = paramISccmsApiVerifyLicenseTaskListener;
  }

  public static abstract interface ISccmsApiVerifyLicenseTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, AAAVerifyLicense paramAAAVerifyLicense);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiAAAVerifyLicenseTask
 * JD-Core Version:    0.6.2
 */