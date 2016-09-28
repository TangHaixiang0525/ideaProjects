package com.starcor.sccms.api;

import com.starcor.core.epgapi.params.AddCollectParams;
import com.starcor.core.parser.json.OpRecordSAXParserJson;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCachedTask;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsApiAddPlayRecordTask extends ServerApiCachedTask
{
  ISccmsApiAddPlayRecordTaskListener lsr;
  AddCollectParams para = null;

  public SccmsApiAddPlayRecordTask(AddCollectParams paramAddCollectParams)
  {
    this.para = paramAddCollectParams;
    this.para.setResultFormat(1);
  }

  public String getTaskName()
  {
    return "N3_A_A_5";
  }

  public String getUrl()
  {
    return webUrlFormatter.i().formatUrl(this.para.toString(), this.para.getApiName());
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
      Logger.i("SccmsApiAddPlayRecordTask", " result:" + str);
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), str);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiAddPlayRecordTaskListener paramISccmsApiAddPlayRecordTaskListener)
  {
    this.lsr = paramISccmsApiAddPlayRecordTaskListener;
  }

  public static abstract interface ISccmsApiAddPlayRecordTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, String paramString);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiAddPlayRecordTask
 * JD-Core Version:    0.6.2
 */