package com.starcor.sccms.api;

import com.starcor.core.domain.CollectListItem;
import com.starcor.core.epgapi.params.AddCollectV2Params;
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

public class SccmsApiAddCatchVideoRecordV2Task extends ServerApiCachedTask
{
  ISccmsApiAddCatchVideoRecordV2TaskListener lsr;
  AddCollectV2Params para = null;

  public SccmsApiAddCatchVideoRecordV2Task(AddCollectV2Params paramAddCollectV2Params)
  {
    this.para = paramAddCollectV2Params;
    this.para.setResultFormat(0);
  }

  public String getTaskName()
  {
    return "N40_A_8";
  }

  public String getUrl()
  {
    return webUrlFormatter.i().formatUrl(this.para.toString(), this.para.getApiName());
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
      Logger.i("SccmsApiAddCollectRecordTask", "增加追剧记录  result:" + localArrayList);
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localArrayList);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiAddCatchVideoRecordV2TaskListener paramISccmsApiAddCatchVideoRecordV2TaskListener)
  {
    this.lsr = paramISccmsApiAddCatchVideoRecordV2TaskListener;
  }

  public static abstract interface ISccmsApiAddCatchVideoRecordV2TaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, ArrayList<CollectListItem> paramArrayList);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiAddCatchVideoRecordV2Task
 * JD-Core Version:    0.6.2
 */