package com.starcor.sccms.api;

import com.starcor.core.epgapi.params.UpdataServerTimeParams;
import com.starcor.core.parser.json.UpdataServerTimeSAXParserJson;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;
import java.io.ByteArrayInputStream;

public class SccmsApiSyncTimeTask extends ServerApiTask
{
  private ISccmsApiSyncTimeTaskListener lsr;

  public String getTaskName()
  {
    return "N2_A_3";
  }

  public String getUrl()
  {
    UpdataServerTimeParams localUpdataServerTimeParams = new UpdataServerTimeParams();
    localUpdataServerTimeParams.setResultFormat(1);
    return webUrlFormatter.i().formatUrl(localUpdataServerTimeParams.toString(), localUpdataServerTimeParams.getApiName());
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
    try
    {
      String str = (String)new UpdataServerTimeSAXParserJson().parser(new ByteArrayInputStream(paramSCResponse.getContents()));
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), str);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiSyncTimeTaskListener paramISccmsApiSyncTimeTaskListener)
  {
    this.lsr = paramISccmsApiSyncTimeTaskListener;
  }

  public static abstract interface ISccmsApiSyncTimeTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, String paramString);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiSyncTimeTask
 * JD-Core Version:    0.6.2
 */