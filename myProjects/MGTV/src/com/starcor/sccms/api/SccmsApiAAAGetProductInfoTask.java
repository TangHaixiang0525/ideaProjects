package com.starcor.sccms.api;

import com.starcor.core.domain.AAAProductInfo;
import com.starcor.core.epgapi.params.AAAGetProductInfoParams;
import com.starcor.core.parser.json.AAAGetProductInfoSAXParserJson;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsApiAAAGetProductInfoTask extends ServerApiTask
{
  private int businessId = -1;
  private String license = "";
  private ISccmsApiAAAGetProductInfoTaskListener lsr;
  private String ticket = "";
  private int vipId = -1;

  public SccmsApiAAAGetProductInfoTask(String paramString1, String paramString2, int paramInt1, int paramInt2)
  {
    this.license = paramString1;
    this.ticket = paramString2;
    this.businessId = paramInt1;
    this.vipId = paramInt2;
  }

  public String getTaskName()
  {
    return "N212_A_7";
  }

  public String getUrl()
  {
    AAAGetProductInfoParams localAAAGetProductInfoParams = new AAAGetProductInfoParams(this.license, this.ticket, this.businessId, this.vipId);
    localAAAGetProductInfoParams.setResultFormat(1);
    String str = webUrlFormatter.i().formatUrl(localAAAGetProductInfoParams.toString(), localAAAGetProductInfoParams.getApiName());
    Logger.i("SccmsApiAAAGetProductInfoTask", "N212_A_7 url:" + str);
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
      AAAProductInfo localAAAProductInfo = (AAAProductInfo)new AAAGetProductInfoSAXParserJson().parser(paramSCResponse.getContents());
      Logger.i("SccmsApiAAAGetProductInfoTask", "N212_A_7 result:" + localAAAProductInfo.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localAAAProductInfo);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiAAAGetProductInfoTaskListener paramISccmsApiAAAGetProductInfoTaskListener)
  {
    this.lsr = paramISccmsApiAAAGetProductInfoTaskListener;
  }

  public static abstract interface ISccmsApiAAAGetProductInfoTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, AAAProductInfo paramAAAProductInfo);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiAAAGetProductInfoTask
 * JD-Core Version:    0.6.2
 */