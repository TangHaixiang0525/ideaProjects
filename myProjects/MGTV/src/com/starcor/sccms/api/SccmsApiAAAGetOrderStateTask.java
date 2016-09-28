package com.starcor.sccms.api;

import com.starcor.core.domain.AAAOrderState;
import com.starcor.core.epgapi.params.AAAGetOrderStateParams;
import com.starcor.core.parser.json.AAAGetOrderStateSAXParserJson;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsApiAAAGetOrderStateTask extends ServerApiTask
{
  private int businessId = -1;
  private String license = "";
  private ISccmsApiAAAGetOrderStateTaskListener lsr;
  private String orderId = "";
  private String shortCode = "";
  private String ticket = "";

  public SccmsApiAAAGetOrderStateTask(String paramString1, String paramString2, int paramInt, String paramString3, String paramString4)
  {
    this.license = paramString1;
    this.ticket = paramString2;
    this.businessId = paramInt;
    this.orderId = paramString3;
    this.shortCode = paramString4;
  }

  public String getTaskName()
  {
    return "N212_A_11";
  }

  public String getUrl()
  {
    AAAGetOrderStateParams localAAAGetOrderStateParams = new AAAGetOrderStateParams(this.license, this.ticket, this.businessId, this.orderId, this.shortCode);
    localAAAGetOrderStateParams.setResultFormat(1);
    String str = webUrlFormatter.i().formatUrl(localAAAGetOrderStateParams.toString(), localAAAGetOrderStateParams.getApiName());
    Logger.i("SccmsApiAAAGetOrderStateTask", "N212_A_11 url:" + str);
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
      AAAOrderState localAAAOrderState = (AAAOrderState)new AAAGetOrderStateSAXParserJson().parser(paramSCResponse.getContents());
      Logger.i("SccmsApiAAAGetOrderStateTask", "N212_A_11 result:" + localAAAOrderState.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localAAAOrderState);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiAAAGetOrderStateTaskListener paramISccmsApiAAAGetOrderStateTaskListener)
  {
    this.lsr = paramISccmsApiAAAGetOrderStateTaskListener;
  }

  public static abstract interface ISccmsApiAAAGetOrderStateTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, AAAOrderState paramAAAOrderState);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiAAAGetOrderStateTask
 * JD-Core Version:    0.6.2
 */