package com.starcor.sccms.api;

import com.starcor.core.domain.AAAGetLicense;
import com.starcor.core.epgapi.params.AAAGetLicenseParams;
import com.starcor.core.parser.json.AAAGetLicenseSAXParserJson;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsApiAAAGetLicenseTask extends ServerApiTask
{
  private String deviceId = "";
  private ISccmsApiGetLicenseTaskListener lsr;

  public SccmsApiAAAGetLicenseTask(String paramString)
  {
    this.deviceId = paramString;
  }

  public String getTaskName()
  {
    return "N212_A_1";
  }

  public String getUrl()
  {
    AAAGetLicenseParams localAAAGetLicenseParams = new AAAGetLicenseParams(this.deviceId);
    localAAAGetLicenseParams.setResultFormat(1);
    String str = webUrlFormatter.i().formatUrl(localAAAGetLicenseParams.toString(), localAAAGetLicenseParams.getApiName());
    Logger.i("SccmsApiAAAGetLicenseTask", "N212_A_1 url:" + str);
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
      AAAGetLicenseSAXParserJson localAAAGetLicenseSAXParserJson = new AAAGetLicenseSAXParserJson();
      Logger.i("SccmsApiAAAGetLicenseTask", "N212_A_1 result:" + new String(paramSCResponse.getContents()));
      AAAGetLicense localAAAGetLicense = (AAAGetLicense)localAAAGetLicenseSAXParserJson.parser(paramSCResponse.getContents());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localAAAGetLicense);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiGetLicenseTaskListener paramISccmsApiGetLicenseTaskListener)
  {
    this.lsr = paramISccmsApiGetLicenseTaskListener;
  }

  public static abstract interface ISccmsApiGetLicenseTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, AAAGetLicense paramAAAGetLicense);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiAAAGetLicenseTask
 * JD-Core Version:    0.6.2
 */