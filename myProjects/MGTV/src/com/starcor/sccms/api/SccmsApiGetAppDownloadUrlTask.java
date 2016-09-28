package com.starcor.sccms.api;

import com.starcor.core.domain.AppDownloadUrl;
import com.starcor.core.epgapi.params.GetAppDownloadUrlParams;
import com.starcor.core.parser.sax.GetAppDownloadUrlSAXParser;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;
import java.io.ByteArrayInputStream;

public class SccmsApiGetAppDownloadUrlTask extends ServerApiTask
{
  private ISccmsApiGetAppDownloadUrlTaskListener lsr;
  private String versionId;

  public SccmsApiGetAppDownloadUrlTask(String paramString)
  {
    this.versionId = paramString;
  }

  public String getTaskName()
  {
    return "N650_A_3";
  }

  public String getUrl()
  {
    GetAppDownloadUrlParams localGetAppDownloadUrlParams = new GetAppDownloadUrlParams(this.versionId);
    localGetAppDownloadUrlParams.setResultFormat(0);
    return webUrlFormatter.i().formatUrl(localGetAppDownloadUrlParams.toString(), localGetAppDownloadUrlParams.getApiName());
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
    GetAppDownloadUrlSAXParser localGetAppDownloadUrlSAXParser = new GetAppDownloadUrlSAXParser();
    try
    {
      AppDownloadUrl localAppDownloadUrl = (AppDownloadUrl)localGetAppDownloadUrlSAXParser.parser(new ByteArrayInputStream(paramSCResponse.getContents()));
      Logger.i("SccmsApiGetAppDownloadUrlTask", " result:" + localAppDownloadUrl.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localAppDownloadUrl);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiGetAppDownloadUrlTaskListener paramISccmsApiGetAppDownloadUrlTaskListener)
  {
    this.lsr = paramISccmsApiGetAppDownloadUrlTaskListener;
  }

  public static abstract interface ISccmsApiGetAppDownloadUrlTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, AppDownloadUrl paramAppDownloadUrl);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiGetAppDownloadUrlTask
 * JD-Core Version:    0.6.2
 */