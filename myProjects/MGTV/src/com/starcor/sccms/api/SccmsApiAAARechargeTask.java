package com.starcor.sccms.api;

import com.starcor.core.domain.AAAOrderInfo;
import com.starcor.core.epgapi.params.AAARechargeParams;
import com.starcor.core.parser.json.AAABuyProductSAXParserJson;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsApiAAARechargeTask extends ServerApiTask
{
  private int businessId = -1;
  private String channel = "";
  private String count = "0";
  private int is_sqzf = 0;
  private String license = "";
  private ISccmsApiAAARechargeTaskListener lsr;
  private String orderId = "";
  private int productId = -1;
  private String ticket = "";

  public SccmsApiAAARechargeTask(int paramInt1, String paramString1, String paramString2, int paramInt2, int paramInt3, String paramString3, String paramString4, String paramString5)
  {
    this.is_sqzf = paramInt1;
    this.license = paramString1;
    this.ticket = paramString2;
    this.channel = paramString3;
    this.businessId = paramInt2;
    this.productId = paramInt3;
    this.orderId = paramString4;
    this.count = paramString5;
  }

  public SccmsApiAAARechargeTask(String paramString1, String paramString2, int paramInt1, int paramInt2, String paramString3, String paramString4, String paramString5)
  {
    this.license = paramString1;
    this.ticket = paramString2;
    this.channel = paramString3;
    this.businessId = paramInt1;
    this.productId = paramInt2;
    this.orderId = paramString4;
    this.count = paramString5;
  }

  public String getTaskName()
  {
    return "N212_A_10";
  }

  public String getUrl()
  {
    AAARechargeParams localAAARechargeParams = new AAARechargeParams(this.is_sqzf, this.license, this.ticket, this.businessId, this.productId, this.channel, this.orderId, this.count);
    localAAARechargeParams.setResultFormat(1);
    String str = webUrlFormatter.i().formatUrl(localAAARechargeParams.toString(), localAAARechargeParams.getApiName());
    Logger.i("SccmsApiAAARechargeTask", "N212_A_10 url:" + str);
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
      AAAOrderInfo localAAAOrderInfo = (AAAOrderInfo)new AAABuyProductSAXParserJson().parser(paramSCResponse.getContents());
      Logger.i("SccmsApiAAARechargeTask", "N212_A_10 result:" + localAAAOrderInfo.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localAAAOrderInfo);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiAAARechargeTaskListener paramISccmsApiAAARechargeTaskListener)
  {
    this.lsr = paramISccmsApiAAARechargeTaskListener;
  }

  public static abstract interface ISccmsApiAAARechargeTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, AAAOrderInfo paramAAAOrderInfo);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiAAARechargeTask
 * JD-Core Version:    0.6.2
 */