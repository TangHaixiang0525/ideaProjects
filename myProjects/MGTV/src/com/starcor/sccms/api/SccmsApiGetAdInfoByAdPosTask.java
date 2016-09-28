package com.starcor.sccms.api;

import com.starcor.core.domain.AdInfos;
import com.starcor.core.epgapi.params.GetAdInfoByAdPosParams;
import com.starcor.core.parser.sax.GetAdInfoByAdPosIdParser;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsApiGetAdInfoByAdPosTask extends ServerApiTask
{
  private String adPosId;
  private ISccmsApiGetAdInfoByAdPosTaskListener lsr;

  public SccmsApiGetAdInfoByAdPosTask(String paramString)
  {
    this.adPosId = paramString;
  }

  public String getTaskName()
  {
    return "N7_A_3";
  }

  public String getUrl()
  {
    GetAdInfoByAdPosParams localGetAdInfoByAdPosParams = new GetAdInfoByAdPosParams(this.adPosId);
    localGetAdInfoByAdPosParams.setResultFormat(0);
    return webUrlFormatter.i().formatUrl(localGetAdInfoByAdPosParams.toString(), localGetAdInfoByAdPosParams.getApiName());
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
      AdInfos localAdInfos = (AdInfos)new GetAdInfoByAdPosIdParser().parser(paramSCResponse.getContents());
      Logger.i("SccmsApiGetAdInfoByAdPosTask", " result:" + localAdInfos.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localAdInfos);
      return;
    }
    catch (Exception localException)
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiGetAdInfoByAdPosTaskListener paramISccmsApiGetAdInfoByAdPosTaskListener)
  {
    this.lsr = paramISccmsApiGetAdInfoByAdPosTaskListener;
  }

  public static abstract interface ISccmsApiGetAdInfoByAdPosTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, AdInfos paramAdInfos);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiGetAdInfoByAdPosTask
 * JD-Core Version:    0.6.2
 */