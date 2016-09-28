package com.starcor.sccms.api;

import com.starcor.core.domain.UiUpdatePackage;
import com.starcor.core.epgapi.params.CheckUiPackageUpdateParams;
import com.starcor.core.parser.sax.ScCheckUiPackageUpdateParser;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsApiCheckUiPackageUpdateTask extends ServerApiTask
{
  private ISccmsApiCheckUiPackageUpdateTaskListener lsr;
  private String nnsUiVersion;
  private String nnsVersion;

  public SccmsApiCheckUiPackageUpdateTask(String paramString1, String paramString2)
  {
    this.nnsVersion = paramString1;
    this.nnsUiVersion = paramString2;
  }

  public String getTaskName()
  {
    return "N38_A_2";
  }

  public String getUrl()
  {
    CheckUiPackageUpdateParams localCheckUiPackageUpdateParams = new CheckUiPackageUpdateParams(this.nnsVersion, this.nnsUiVersion);
    localCheckUiPackageUpdateParams.setResultFormat(0);
    return webUrlFormatter.i().formatUrl(localCheckUiPackageUpdateParams.toString(), localCheckUiPackageUpdateParams.getApiName());
  }

  public void perform(SCResponse paramSCResponse)
  {
    if (this.lsr == null)
      return;
    ScCheckUiPackageUpdateParser localScCheckUiPackageUpdateParser = new ScCheckUiPackageUpdateParser();
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    try
    {
      UiUpdatePackage localUiUpdatePackage = (UiUpdatePackage)localScCheckUiPackageUpdateParser.parser(paramSCResponse.getContents());
      Logger.i("SccmsApiCheckUiPackageUpdateTask", " result:" + localUiUpdatePackage.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localUiUpdatePackage);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiCheckUiPackageUpdateTaskListener paramISccmsApiCheckUiPackageUpdateTaskListener)
  {
    this.lsr = paramISccmsApiCheckUiPackageUpdateTaskListener;
  }

  public static abstract interface ISccmsApiCheckUiPackageUpdateTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, UiUpdatePackage paramUiUpdatePackage);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiCheckUiPackageUpdateTask
 * JD-Core Version:    0.6.2
 */