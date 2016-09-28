package com.starcor.sccms.api;

import com.starcor.core.domain.AdPosByPageIdInfo;
import com.starcor.core.epgapi.params.GetAdPosByPageIdParams;
import com.starcor.core.parser.sax.GetAdPosByPageIdParser;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;
import java.util.ArrayList;

public class SccmsApiGetAdPosByPageIdTask extends ServerApiTask
{
  private ISccmsApiGetAdPosByPageIdTaskListener lsr;
  private String pageId;

  public SccmsApiGetAdPosByPageIdTask(String paramString)
  {
    this.pageId = paramString;
  }

  public String getTaskName()
  {
    return "N7_A_1";
  }

  public String getUrl()
  {
    GetAdPosByPageIdParams localGetAdPosByPageIdParams = new GetAdPosByPageIdParams(this.pageId);
    localGetAdPosByPageIdParams.setResultFormat(0);
    return webUrlFormatter.i().formatUrl(localGetAdPosByPageIdParams.toString(), localGetAdPosByPageIdParams.getApiName());
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
      ArrayList localArrayList = (ArrayList)new GetAdPosByPageIdParser().parser(paramSCResponse.getContents());
      Logger.i("SccmsApiGetAdPosByPageIdTask", " result:" + localArrayList.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localArrayList);
      return;
    }
    catch (Exception localException)
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiGetAdPosByPageIdTaskListener paramISccmsApiGetAdPosByPageIdTaskListener)
  {
    this.lsr = paramISccmsApiGetAdPosByPageIdTaskListener;
  }

  public static abstract interface ISccmsApiGetAdPosByPageIdTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, ArrayList<AdPosByPageIdInfo> paramArrayList);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiGetAdPosByPageIdTask
 * JD-Core Version:    0.6.2
 */