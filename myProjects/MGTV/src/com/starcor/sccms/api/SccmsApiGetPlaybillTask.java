package com.starcor.sccms.api;

import com.starcor.core.domain.PlayBillStruct;
import com.starcor.core.epgapi.params.GetPlayBillParams;
import com.starcor.core.parser.json.GetPlayBillSAXParserJson;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCachedTask;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;
import java.util.ArrayList;

public class SccmsApiGetPlaybillTask extends ServerApiCachedTask
{
  ISccmsApiGetPlaybillTaskListener lsr;
  int nns_day_begin = -1;
  int nns_day_end = -1;
  String nns_video_id = null;

  public SccmsApiGetPlaybillTask(String paramString, int paramInt1, int paramInt2)
  {
    this.nns_video_id = paramString;
    this.nns_day_begin = paramInt1;
    this.nns_day_end = paramInt2;
  }

  public String getTaskName()
  {
    return "N3_A_A_8";
  }

  public String getUrl()
  {
    GetPlayBillParams localGetPlayBillParams = new GetPlayBillParams(this.nns_video_id, this.nns_day_begin, this.nns_day_end);
    localGetPlayBillParams.setResultFormat(1);
    return webUrlFormatter.i().formatUrl(localGetPlayBillParams.toString(), localGetPlayBillParams.getApiName());
  }

  public void perform(SCResponse paramSCResponse)
  {
    GetPlayBillSAXParserJson localGetPlayBillSAXParserJson = new GetPlayBillSAXParserJson();
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    try
    {
      ArrayList localArrayList = (ArrayList)localGetPlayBillSAXParserJson.parser(paramSCResponse.getContents());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localArrayList);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiGetPlaybillTaskListener paramISccmsApiGetPlaybillTaskListener)
  {
    this.lsr = paramISccmsApiGetPlaybillTaskListener;
  }

  public static abstract interface ISccmsApiGetPlaybillTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, ArrayList<PlayBillStruct> paramArrayList);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiGetPlaybillTask
 * JD-Core Version:    0.6.2
 */