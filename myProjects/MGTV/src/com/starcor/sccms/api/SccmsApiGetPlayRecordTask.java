package com.starcor.sccms.api;

import com.starcor.core.domain.CollectListItem;
import com.starcor.core.epgapi.params.GetCollectListParams;
import com.starcor.core.parser.json.GetCollectListSAXParserJson;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCachedTask;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;
import java.util.ArrayList;

public class SccmsApiGetPlayRecordTask extends ServerApiCachedTask
{
  ISccmsApiGetPlayRecordTaskListener lsr;

  public String getTaskName()
  {
    return "N3_A_A_6";
  }

  public String getUrl()
  {
    GetCollectListParams localGetCollectListParams = new GetCollectListParams(2);
    localGetCollectListParams.setResultFormat(1);
    return webUrlFormatter.i().formatUrl(localGetCollectListParams.toString(), localGetCollectListParams.getApiName());
  }

  public void perform(SCResponse paramSCResponse)
  {
    GetCollectListSAXParserJson localGetCollectListSAXParserJson = new GetCollectListSAXParserJson();
    if (this.lsr == null)
    {
      Logger.i("SccmsApiGetPlayRecordTask", "perform() lsr is null");
      return;
    }
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    try
    {
      ArrayList localArrayList = (ArrayList)localGetCollectListSAXParserJson.parser(paramSCResponse.getContents());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localArrayList);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiGetPlayRecordTaskListener paramISccmsApiGetPlayRecordTaskListener)
  {
    this.lsr = paramISccmsApiGetPlayRecordTaskListener;
  }

  public static abstract interface ISccmsApiGetPlayRecordTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, ArrayList<CollectListItem> paramArrayList);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiGetPlayRecordTask
 * JD-Core Version:    0.6.2
 */