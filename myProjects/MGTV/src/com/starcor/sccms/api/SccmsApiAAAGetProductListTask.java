package com.starcor.sccms.api;

import com.starcor.core.domain.AAAProductList;
import com.starcor.core.epgapi.params.AAAGetProductListParams;
import com.starcor.core.parser.json.AAAGetProductListSAXParserJson;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsApiAAAGetProductListTask extends ServerApiTask
{
  private int businessId = -1;
  private String license = "";
  private ISccmsApiAAAGetProductListTaskListener lsr;
  private String ticket = "";
  private int vipId = -1;

  public SccmsApiAAAGetProductListTask(String paramString1, String paramString2, int paramInt1, int paramInt2)
  {
    this.license = paramString1;
    this.ticket = paramString2;
    this.businessId = paramInt1;
    this.vipId = paramInt2;
  }

  public String getTaskName()
  {
    return "N212_A_4";
  }

  public String getUrl()
  {
    AAAGetProductListParams localAAAGetProductListParams = new AAAGetProductListParams(this.license, this.ticket, this.businessId, this.vipId);
    localAAAGetProductListParams.setResultFormat(1);
    String str = webUrlFormatter.i().formatUrl(localAAAGetProductListParams.toString(), localAAAGetProductListParams.getApiName());
    Logger.i("SccmsApiAAAGetProductListTask", "N212_A_4 url:" + str);
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
      AAAProductList localAAAProductList = (AAAProductList)new AAAGetProductListSAXParserJson().parser(paramSCResponse.getContents());
      Logger.i("SccmsApiAAAGetProductListTask", "N212_A_4 result:" + localAAAProductList.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localAAAProductList);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiAAAGetProductListTaskListener paramISccmsApiAAAGetProductListTaskListener)
  {
    this.lsr = paramISccmsApiAAAGetProductListTaskListener;
  }

  public static abstract interface ISccmsApiAAAGetProductListTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, AAAProductList paramAAAProductList);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiAAAGetProductListTask
 * JD-Core Version:    0.6.2
 */