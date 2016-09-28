package com.starcor.sccms.api;

import com.starcor.core.domain.UninstallList;
import com.starcor.core.epgapi.params.GetAppUninstallListParams;
import com.starcor.core.parser.sax.GetUninstallListSAXParser;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;
import java.io.ByteArrayInputStream;

public class SccmsApiGetUninstallListTask extends ServerApiTask
{
  private ISccmsApiGetUninstallListTaskListener lsr;

  public String getTaskName()
  {
    return "N650_A_8";
  }

  public String getUrl()
  {
    GetAppUninstallListParams localGetAppUninstallListParams = new GetAppUninstallListParams();
    localGetAppUninstallListParams.setResultFormat(0);
    return webUrlFormatter.i().formatUrl(localGetAppUninstallListParams.toString(), localGetAppUninstallListParams.getApiName());
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
    GetUninstallListSAXParser localGetUninstallListSAXParser = new GetUninstallListSAXParser();
    try
    {
      UninstallList localUninstallList = (UninstallList)localGetUninstallListSAXParser.parser(new ByteArrayInputStream(paramSCResponse.getContents()));
      Logger.i("SccmsApiGetUninstallListTask", " result:" + localUninstallList.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localUninstallList);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiGetUninstallListTaskListener paramISccmsApiGetUninstallListTaskListener)
  {
    this.lsr = paramISccmsApiGetUninstallListTaskListener;
  }

  public static abstract interface ISccmsApiGetUninstallListTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, UninstallList paramUninstallList);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiGetUninstallListTask
 * JD-Core Version:    0.6.2
 */