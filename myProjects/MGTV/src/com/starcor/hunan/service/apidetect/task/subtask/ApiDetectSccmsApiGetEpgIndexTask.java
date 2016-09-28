package com.starcor.hunan.service.apidetect.task.subtask;

import com.starcor.core.epgapi.params.InitMainURLParams;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.hunan.service.apidetect.parser.ApiDetectInitMainURLSAXParser;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;
import java.io.ByteArrayInputStream;

public class ApiDetectSccmsApiGetEpgIndexTask extends ServerApiTask
{
  private final String TAG = ApiDetectSccmsApiGetEpgIndexTask.class.getSimpleName();
  private IApiDetectSccmsApiGetEpgDataTaskListener lsr;

  public String getTaskName()
  {
    return "N1_A_1";
  }

  public String getUrl()
  {
    InitMainURLParams localInitMainURLParams = new InitMainURLParams();
    localInitMainURLParams.setResultFormat(0);
    return webUrlFormatter.i().formatUrl(localInitMainURLParams.toString(), localInitMainURLParams.getApiName());
  }

  public void perform(SCResponse paramSCResponse)
  {
    if (this.lsr == null)
      return;
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    ApiDetectInitMainURLSAXParser localApiDetectInitMainURLSAXParser = new ApiDetectInitMainURLSAXParser();
    try
    {
      Integer localInteger = (Integer)localApiDetectInitMainURLSAXParser.parser(new ByteArrayInputStream(paramSCResponse.getContents()));
      Logger.i(this.TAG, " result:" + localInteger.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), paramSCResponse), localInteger);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.getMessage()));
    }
  }

  public void setListener(IApiDetectSccmsApiGetEpgDataTaskListener paramIApiDetectSccmsApiGetEpgDataTaskListener)
  {
    this.lsr = paramIApiDetectSccmsApiGetEpgDataTaskListener;
  }

  public static abstract interface IApiDetectSccmsApiGetEpgDataTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, Integer paramInteger);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.service.apidetect.task.subtask.ApiDetectSccmsApiGetEpgIndexTask
 * JD-Core Version:    0.6.2
 */