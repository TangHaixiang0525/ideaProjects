package com.starcor.sccms.api;

import com.starcor.core.domain.PlayBillRecommendStrut;
import com.starcor.core.epgapi.params.GetPlayBillRecommendParams;
import com.starcor.core.parser.sax.GetPlayBillRecommendSAXParser;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCachedTask;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;
import java.util.List;

public class SccmsApiGetReplayRecommendDataTask extends ServerApiCachedTask
{
  private int afterDays;
  private int beforeDays;
  private ISccmsApiGetReplayRecommendDataTaskListener lsr;

  public SccmsApiGetReplayRecommendDataTask(int paramInt1, int paramInt2)
  {
    this.beforeDays = paramInt1;
    this.afterDays = paramInt2;
  }

  public String getTaskName()
  {
    return "N3_A_A_17";
  }

  public String getUrl()
  {
    GetPlayBillRecommendParams localGetPlayBillRecommendParams = new GetPlayBillRecommendParams(this.beforeDays, this.afterDays);
    localGetPlayBillRecommendParams.setResultFormat(0);
    return webUrlFormatter.i().formatUrl(localGetPlayBillRecommendParams.toString(), localGetPlayBillRecommendParams.getApiName());
  }

  protected byte[] localPerform(SCResponse paramSCResponse)
  {
    if (this.lsr == null)
      return null;
    GetPlayBillRecommendSAXParser localGetPlayBillRecommendSAXParser = new GetPlayBillRecommendSAXParser();
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return null;
    }
    try
    {
      List localList = (List)localGetPlayBillRecommendSAXParser.parser(paramSCResponse.getContents());
      Logger.i("SccmsApiGetReplayRecommendDataTask", " result:" + localList.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localList);
      byte[] arrayOfByte = paramSCResponse.getContents();
      return arrayOfByte;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
    return null;
  }

  public void perform(SCResponse paramSCResponse)
  {
    byte[] arrayOfByte = localPerform(paramSCResponse);
    if (arrayOfByte != null)
      serializeApiData(arrayOfByte);
  }

  public void setListener(ISccmsApiGetReplayRecommendDataTaskListener paramISccmsApiGetReplayRecommendDataTaskListener)
  {
    this.lsr = paramISccmsApiGetReplayRecommendDataTaskListener;
  }

  public static abstract interface ISccmsApiGetReplayRecommendDataTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, List<PlayBillRecommendStrut> paramList);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiGetReplayRecommendDataTask
 * JD-Core Version:    0.6.2
 */