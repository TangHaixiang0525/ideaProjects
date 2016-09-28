package com.starcor.sccms.api;

import com.starcor.core.epgapi.params.RemoveCollectParams;
import com.starcor.core.parser.json.OpRecordSAXParserJson;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCachedTask;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsApiDelPlayRecordTask extends ServerApiCachedTask
{
  String collectIds = null;
  ISccmsApiDelPlayRecordTaskListener lsr;

  public SccmsApiDelPlayRecordTask(String paramString)
  {
    this.collectIds = paramString;
  }

  public String getTaskName()
  {
    return "N3_A_A_7";
  }

  public String getUrl()
  {
    RemoveCollectParams localRemoveCollectParams = new RemoveCollectParams(this.collectIds);
    localRemoveCollectParams.setResultFormat(1);
    return webUrlFormatter.i().formatUrl(localRemoveCollectParams.toString(), localRemoveCollectParams.getApiName());
  }

  public void perform(SCResponse paramSCResponse)
  {
    OpRecordSAXParserJson localOpRecordSAXParserJson = new OpRecordSAXParserJson();
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    try
    {
      String str = (String)localOpRecordSAXParserJson.parser(paramSCResponse.getContents());
      Logger.i("SccmsApiDelPlayRecordTask", " result:" + str);
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), str);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiDelPlayRecordTaskListener paramISccmsApiDelPlayRecordTaskListener)
  {
    this.lsr = paramISccmsApiDelPlayRecordTaskListener;
  }

  public static abstract interface ISccmsApiDelPlayRecordTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, String paramString);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiDelPlayRecordTask
 * JD-Core Version:    0.6.2
 */