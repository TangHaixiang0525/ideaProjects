package com.starcor.sccms.api;

import com.starcor.core.domain.CollectListItem;
import com.starcor.core.epgapi.params.RemoveCollectV2Params;
import com.starcor.core.parser.sax.GetCollectListV2SAXParse;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCachedTask;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class SccmsApiDelCollectRecordV2Task extends ServerApiCachedTask
{
  private String TAG = SccmsApiDelCollectRecordV2Task.class.getSimpleName();
  String collectIds = null;
  ISccmsApiDelColllectRecordV2TaskListener lsr;

  public SccmsApiDelCollectRecordV2Task(String paramString)
  {
    this.collectIds = paramString;
  }

  public String getTaskName()
  {
    return "N40_A_3";
  }

  public String getUrl()
  {
    RemoveCollectV2Params localRemoveCollectV2Params = new RemoveCollectV2Params(this.collectIds, null, 1);
    localRemoveCollectV2Params.setResultFormat(0);
    return webUrlFormatter.i().formatUrl(localRemoveCollectV2Params.toString(), localRemoveCollectV2Params.getApiName());
  }

  public void perform(SCResponse paramSCResponse)
  {
    GetCollectListV2SAXParse localGetCollectListV2SAXParse = new GetCollectListV2SAXParse();
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    try
    {
      ArrayList localArrayList = (ArrayList)localGetCollectListV2SAXParse.parser(new ByteArrayInputStream(paramSCResponse.getContents()));
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localArrayList);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiDelColllectRecordV2TaskListener paramISccmsApiDelColllectRecordV2TaskListener)
  {
    this.lsr = paramISccmsApiDelColllectRecordV2TaskListener;
  }

  public static abstract interface ISccmsApiDelColllectRecordV2TaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, ArrayList<CollectListItem> paramArrayList);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiDelCollectRecordV2Task
 * JD-Core Version:    0.6.2
 */