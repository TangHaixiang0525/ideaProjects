package com.starcor.sccms.api;

import com.starcor.core.domain.PayAuthorizeStatus;
import com.starcor.core.epgapi.params.PayAuthorStatusParams;
import com.starcor.core.parser.json.GetPayAuthorStatusSAXParserJson;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCachedTask;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsApiAAAGetPayAuthorizeStatusTask extends ServerApiCachedTask
{
  private static String TAG = SccmsApiAAAGetPayAuthorizeStatusTask.class.getSimpleName();
  private int businessId = -1;
  private String license = "";
  ISccmsApiAAAGetPayAuthorizeStatusListener lsr;
  private String ticket = "";

  public SccmsApiAAAGetPayAuthorizeStatusTask(String paramString1, int paramInt, String paramString2)
  {
    this.ticket = paramString1;
    this.businessId = paramInt;
    this.license = paramString2;
  }

  public String getTaskName()
  {
    return "N212_A_22";
  }

  public String getUrl()
  {
    PayAuthorStatusParams localPayAuthorStatusParams = new PayAuthorStatusParams(0, this.ticket, this.businessId, this.license);
    localPayAuthorStatusParams.setResultFormat(1);
    String str = webUrlFormatter.i().formatUrl(localPayAuthorStatusParams.toString(), localPayAuthorStatusParams.getApiName());
    Logger.i(TAG, "N212_A_22 url:" + str);
    return str;
  }

  public void perform(SCResponse paramSCResponse)
  {
    GetPayAuthorStatusSAXParserJson localGetPayAuthorStatusSAXParserJson = new GetPayAuthorStatusSAXParserJson();
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    try
    {
      PayAuthorizeStatus localPayAuthorizeStatus = (PayAuthorizeStatus)localGetPayAuthorStatusSAXParserJson.parser(paramSCResponse.getContents());
      Logger.i(TAG, "N212_A_22 result:" + localPayAuthorizeStatus.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localPayAuthorizeStatus);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiAAAGetPayAuthorizeStatusListener paramISccmsApiAAAGetPayAuthorizeStatusListener)
  {
    this.lsr = paramISccmsApiAAAGetPayAuthorizeStatusListener;
  }

  public static abstract interface ISccmsApiAAAGetPayAuthorizeStatusListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, PayAuthorizeStatus paramPayAuthorizeStatus);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiAAAGetPayAuthorizeStatusTask
 * JD-Core Version:    0.6.2
 */