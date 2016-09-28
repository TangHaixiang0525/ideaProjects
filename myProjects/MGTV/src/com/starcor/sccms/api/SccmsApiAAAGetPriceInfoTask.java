package com.starcor.sccms.api;

import com.starcor.core.domain.AAAPriceInfo;
import com.starcor.core.epgapi.params.AAAGetPriceInfoParams;
import com.starcor.core.parser.json.AAAGetPriceInfoSAXParserJson;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsApiAAAGetPriceInfoTask extends ServerApiTask
{
  private int businessId = -1;
  private String channel = "";
  private String license = "";
  private ISccmsApiAAAGetPriceInfoTaskListener lsr;
  private int productId = -1;
  private String ticket = "";

  public SccmsApiAAAGetPriceInfoTask(String paramString1, String paramString2, int paramInt1, int paramInt2, String paramString3)
  {
    this.license = paramString1;
    this.ticket = paramString2;
    this.channel = paramString3;
    this.businessId = paramInt1;
    this.productId = paramInt2;
  }

  public String getTaskName()
  {
    return "N212_A_8";
  }

  public String getUrl()
  {
    AAAGetPriceInfoParams localAAAGetPriceInfoParams = new AAAGetPriceInfoParams(this.license, this.ticket, this.businessId, this.productId, this.channel);
    localAAAGetPriceInfoParams.setResultFormat(1);
    String str = webUrlFormatter.i().formatUrl(localAAAGetPriceInfoParams.toString(), localAAAGetPriceInfoParams.getApiName());
    Logger.i("SccmsApiAAAGetPriceInfoTask", "N212_A_8 url:" + str);
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
      AAAPriceInfo localAAAPriceInfo = (AAAPriceInfo)new AAAGetPriceInfoSAXParserJson().parser(paramSCResponse.getContents());
      Logger.i("SccmsApiAAAGetPriceInfoTask", "N212_A_8 result:" + localAAAPriceInfo.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localAAAPriceInfo);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiAAAGetPriceInfoTaskListener paramISccmsApiAAAGetPriceInfoTaskListener)
  {
    this.lsr = paramISccmsApiAAAGetPriceInfoTaskListener;
  }

  public static abstract interface ISccmsApiAAAGetPriceInfoTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, AAAPriceInfo paramAAAPriceInfo);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiAAAGetPriceInfoTask
 * JD-Core Version:    0.6.2
 */