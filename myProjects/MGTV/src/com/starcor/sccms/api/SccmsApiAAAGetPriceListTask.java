package com.starcor.sccms.api;

import com.starcor.core.domain.AAAPriceList;
import com.starcor.core.epgapi.params.AAAGetPriceListParams;
import com.starcor.core.parser.json.AAAGetPriceListSAXParserJson;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsApiAAAGetPriceListTask extends ServerApiTask
{
  private String license = "";
  private ISccmsApiAAAGetPriceListTaskListener lsr;

  public SccmsApiAAAGetPriceListTask(String paramString)
  {
    this.license = paramString;
  }

  public String getTaskName()
  {
    return "N212_A_5";
  }

  public String getUrl()
  {
    AAAGetPriceListParams localAAAGetPriceListParams = new AAAGetPriceListParams(this.license);
    localAAAGetPriceListParams.setResultFormat(1);
    String str = webUrlFormatter.i().formatUrl(localAAAGetPriceListParams.toString(), localAAAGetPriceListParams.getApiName());
    Logger.i("SccmsApiAAAGetPriceListTask", "N212_A_5 url:" + str);
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
      AAAPriceList localAAAPriceList = (AAAPriceList)new AAAGetPriceListSAXParserJson().parser(paramSCResponse.getContents());
      Logger.i("SccmsApiAAAGetPriceListTask", "N212_A_5 result:" + localAAAPriceList.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localAAAPriceList);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiAAAGetPriceListTaskListener paramISccmsApiAAAGetPriceListTaskListener)
  {
    this.lsr = paramISccmsApiAAAGetPriceListTaskListener;
  }

  public static abstract interface ISccmsApiAAAGetPriceListTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, AAAPriceList paramAAAPriceList);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiAAAGetPriceListTask
 * JD-Core Version:    0.6.2
 */