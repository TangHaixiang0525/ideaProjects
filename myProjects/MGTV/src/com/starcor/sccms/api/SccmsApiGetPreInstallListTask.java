package com.starcor.sccms.api;

import com.starcor.core.domain.PreInstallList;
import com.starcor.core.epgapi.params.GetAppPreInstallListParams;
import com.starcor.core.parser.sax.GetPreInstallListSAXParser;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;
import java.io.ByteArrayInputStream;

public class SccmsApiGetPreInstallListTask extends ServerApiTask
{
  private ISccmsApiGetPreInstallListTaskListener lsr;

  public String getTaskName()
  {
    return "N650_A_6";
  }

  public String getUrl()
  {
    GetAppPreInstallListParams localGetAppPreInstallListParams = new GetAppPreInstallListParams();
    localGetAppPreInstallListParams.setResultFormat(0);
    return webUrlFormatter.i().formatUrl(localGetAppPreInstallListParams.toString(), localGetAppPreInstallListParams.getApiName());
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
    GetPreInstallListSAXParser localGetPreInstallListSAXParser = new GetPreInstallListSAXParser();
    try
    {
      PreInstallList localPreInstallList = (PreInstallList)localGetPreInstallListSAXParser.parser(new ByteArrayInputStream(paramSCResponse.getContents()));
      Logger.i("SccmsApiGetPreInstallListTask", " result:" + localPreInstallList.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localPreInstallList);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiGetPreInstallListTaskListener paramISccmsApiGetPreInstallListTaskListener)
  {
    this.lsr = paramISccmsApiGetPreInstallListTaskListener;
  }

  public static abstract interface ISccmsApiGetPreInstallListTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, PreInstallList paramPreInstallList);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiGetPreInstallListTask
 * JD-Core Version:    0.6.2
 */