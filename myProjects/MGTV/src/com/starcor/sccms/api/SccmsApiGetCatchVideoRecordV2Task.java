package com.starcor.sccms.api;

import com.starcor.core.domain.CollectListItem;
import com.starcor.core.epgapi.params.GetCollectListV2Params;
import com.starcor.core.parser.sax.GetCollectListV2SAXParse;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCachedTask;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class SccmsApiGetCatchVideoRecordV2Task extends ServerApiCachedTask
{
  private String TAG;
  ISccmsApiGetCatchVideoRecordV2TaskListener lsr;

  public String getTaskName()
  {
    return "N40_A_7";
  }

  public String getUrl()
  {
    GetCollectListV2Params localGetCollectListV2Params = new GetCollectListV2Params("N40_A_7");
    localGetCollectListV2Params.setResultFormat(0);
    return webUrlFormatter.i().formatUrl(localGetCollectListV2Params.toString(), localGetCollectListV2Params.getApiName());
  }

  public void perform(SCResponse paramSCResponse)
  {
    super.perform(paramSCResponse);
    if (this.lsr == null)
    {
      Logger.i(this.TAG, "perform() lsr is null");
      return;
    }
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    GetCollectListV2SAXParse localGetCollectListV2SAXParse = new GetCollectListV2SAXParse();
    try
    {
      ArrayList localArrayList = (ArrayList)localGetCollectListV2SAXParse.parser(new ByteArrayInputStream(paramSCResponse.getContents()));
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localArrayList);
      return;
    }
    catch (Exception localException)
    {
      Logger.i(this.TAG, "获取追剧 result error!");
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiGetCatchVideoRecordV2TaskListener paramISccmsApiGetCatchVideoRecordV2TaskListener)
  {
    this.lsr = paramISccmsApiGetCatchVideoRecordV2TaskListener;
  }

  public static abstract interface ISccmsApiGetCatchVideoRecordV2TaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, ArrayList<CollectListItem> paramArrayList);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiGetCatchVideoRecordV2Task
 * JD-Core Version:    0.6.2
 */