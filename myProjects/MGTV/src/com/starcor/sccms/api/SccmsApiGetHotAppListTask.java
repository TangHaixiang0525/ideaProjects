package com.starcor.sccms.api;

import com.starcor.core.domain.HotAppList;
import com.starcor.core.epgapi.params.GetHotAppListParams;
import com.starcor.core.parser.sax.GetHotAppListSAXParser;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;
import java.io.ByteArrayInputStream;

public class SccmsApiGetHotAppListTask extends ServerApiTask
{
  private ISccmsApiGetHotAppListTaskListener lsr;
  private int num;

  public SccmsApiGetHotAppListTask(int paramInt)
  {
    this.num = paramInt;
  }

  public String getTaskName()
  {
    return "N650_A_5";
  }

  public String getUrl()
  {
    GetHotAppListParams localGetHotAppListParams = new GetHotAppListParams(this.num);
    localGetHotAppListParams.setResultFormat(0);
    return webUrlFormatter.i().formatUrl(localGetHotAppListParams.toString(), localGetHotAppListParams.getApiName());
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
    GetHotAppListSAXParser localGetHotAppListSAXParser = new GetHotAppListSAXParser();
    try
    {
      HotAppList localHotAppList = (HotAppList)localGetHotAppListSAXParser.parser(new ByteArrayInputStream(paramSCResponse.getContents()));
      Logger.i("SccmsApiGetHotAppListTask", " result:" + localHotAppList.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localHotAppList);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiGetHotAppListTaskListener paramISccmsApiGetHotAppListTaskListener)
  {
    this.lsr = paramISccmsApiGetHotAppListTaskListener;
  }

  public static abstract interface ISccmsApiGetHotAppListTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, HotAppList paramHotAppList);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiGetHotAppListTask
 * JD-Core Version:    0.6.2
 */