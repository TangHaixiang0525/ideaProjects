package com.starcor.sccms.api;

import com.starcor.core.domain.UiPackage;
import com.starcor.core.epgapi.params.GetUiPackageParams;
import com.starcor.core.parser.sax.ScUiPackageParser;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCachedTask;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsApiGetUiPackageTask extends ServerApiCachedTask
{
  private ISccmsApiGetUiPackageTaskListener lsr;
  private String nnsUiVersion;
  private String nnsVersion;

  public SccmsApiGetUiPackageTask(String paramString1, String paramString2)
  {
    this.nnsVersion = paramString1;
    this.nnsUiVersion = paramString2;
  }

  public String getTaskName()
  {
    return "N38_A_1";
  }

  public String getUrl()
  {
    GetUiPackageParams localGetUiPackageParams = new GetUiPackageParams(this.nnsVersion, this.nnsUiVersion);
    localGetUiPackageParams.setResultFormat(0);
    return webUrlFormatter.i().formatUrl(localGetUiPackageParams.toString(), localGetUiPackageParams.getApiName());
  }

  public byte[] localPerform(SCResponse paramSCResponse)
  {
    if (this.lsr == null)
      return null;
    ScUiPackageParser localScUiPackageParser = new ScUiPackageParser();
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return null;
    }
    try
    {
      UiPackage localUiPackage = (UiPackage)localScUiPackageParser.parser(paramSCResponse.getContents());
      Logger.i("SccmsApiGetUiPackageTask", " result:" + localUiPackage.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localUiPackage);
      byte[] arrayOfByte = paramSCResponse.getContents();
      return arrayOfByte;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
    return null;
  }

  public void perform(SCResponse paramSCResponse)
  {
    byte[] arrayOfByte = localPerform(paramSCResponse);
    if (arrayOfByte != null)
      serializeApiData(arrayOfByte);
  }

  public void setListener(ISccmsApiGetUiPackageTaskListener paramISccmsApiGetUiPackageTaskListener)
  {
    this.lsr = paramISccmsApiGetUiPackageTaskListener;
  }

  public static abstract interface ISccmsApiGetUiPackageTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, UiPackage paramUiPackage);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiGetUiPackageTask
 * JD-Core Version:    0.6.2
 */