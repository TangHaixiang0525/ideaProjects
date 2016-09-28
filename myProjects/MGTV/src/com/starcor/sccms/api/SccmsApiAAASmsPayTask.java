package com.starcor.sccms.api;

import com.starcor.core.domain.AAASmsPay;
import com.starcor.core.epgapi.params.AAASmsPayParams;
import com.starcor.core.parser.json.AAASmsPaySaxParserJson;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsApiAAASmsPayTask extends ServerApiTask
{
  private String TAG = "SccmsApiAAASmsPaysTask";
  private String license = "";
  private ISccmsApiAAASmsPayTaskListener lsr;

  public SccmsApiAAASmsPayTask(String paramString)
  {
    this.license = paramString;
  }

  public String getTaskName()
  {
    return "N212_A_16";
  }

  public String getUrl()
  {
    AAASmsPayParams localAAASmsPayParams = new AAASmsPayParams(this.license);
    localAAASmsPayParams.setResultFormat(1);
    String str = webUrlFormatter.i().formatUrl(localAAASmsPayParams.toString(), localAAASmsPayParams.getApiName());
    Logger.i(this.TAG, "N212_A_16 url:" + str);
    return str;
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
      AAASmsPay localAAASmsPay = (AAASmsPay)new AAASmsPaySaxParserJson().parser(paramSCResponse.getContents());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localAAASmsPay);
      Logger.i(this.TAG, "N212_A_16 result:" + localAAASmsPay.toString());
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiAAASmsPayTaskListener paramISccmsApiAAASmsPayTaskListener)
  {
    this.lsr = paramISccmsApiAAASmsPayTaskListener;
  }

  public static abstract interface ISccmsApiAAASmsPayTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, AAASmsPay paramAAASmsPay);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiAAASmsPayTask
 * JD-Core Version:    0.6.2
 */