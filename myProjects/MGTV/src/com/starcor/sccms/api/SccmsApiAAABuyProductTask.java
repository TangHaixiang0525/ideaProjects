package com.starcor.sccms.api;

import com.starcor.core.domain.AAAOrderInfo;
import com.starcor.core.epgapi.params.AAABuyProductParams;
import com.starcor.core.parser.json.AAABuyProductSAXParserJson;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsApiAAABuyProductTask extends ServerApiTask
{
  private int businessId = -1;
  private String channel = "";
  private int is_sqzf = 0;
  private String license = "";
  private ISccmsApiAAABuyProductTaskListener lsr;
  private String mobile = "";
  private int productId = -1;
  private String ticket = "";

  public SccmsApiAAABuyProductTask(int paramInt1, String paramString1, String paramString2, int paramInt2, int paramInt3, String paramString3)
  {
    init(paramInt1, paramString1, paramString2, paramInt2, paramInt3, paramString3);
  }

  public SccmsApiAAABuyProductTask(String paramString1, String paramString2, int paramInt1, int paramInt2, String paramString3, String paramString4)
  {
    this.license = paramString1;
    this.ticket = paramString2;
    this.channel = paramString4;
    this.businessId = paramInt1;
    this.productId = paramInt2;
    this.mobile = paramString3;
  }

  private void init(int paramInt1, String paramString1, String paramString2, int paramInt2, int paramInt3, String paramString3)
  {
    this.license = paramString1;
    this.is_sqzf = paramInt1;
    this.ticket = paramString2;
    this.channel = paramString3;
    this.businessId = paramInt2;
    this.productId = paramInt3;
  }

  public String getTaskName()
  {
    return "N212_A_9";
  }

  public String getUrl()
  {
    AAABuyProductParams localAAABuyProductParams = new AAABuyProductParams(this.is_sqzf, this.license, this.ticket, this.businessId, this.productId, this.mobile, this.channel);
    localAAABuyProductParams.setResultFormat(1);
    String str = webUrlFormatter.i().formatUrl(localAAABuyProductParams.toString(), localAAABuyProductParams.getApiName());
    Logger.i("SccmsApiAAABuyProductTask", "N212_A_9 url:" + str);
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
      Logger.i("SccmsApiAAABuyProductTask", "N212_A_9 result:" + localAAAOrderInfo.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localAAAOrderInfo);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiAAABuyProductTaskListener paramISccmsApiAAABuyProductTaskListener)
  {
    this.lsr = paramISccmsApiAAABuyProductTaskListener;
  }

  public static abstract interface ISccmsApiAAABuyProductTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, AAAOrderInfo paramAAAOrderInfo);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiAAABuyProductTask
 * JD-Core Version:    0.6.2
 */