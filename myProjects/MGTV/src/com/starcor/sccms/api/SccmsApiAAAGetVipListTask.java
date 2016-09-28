package com.starcor.sccms.api;

import com.starcor.core.domain.AAAVipList;
import com.starcor.core.epgapi.params.AAAGetVipListParams;
import com.starcor.core.parser.json.AAAGetVipListSAXParserJson;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsApiAAAGetVipListTask extends ServerApiTask
{
  private String license = "";
  private ISccmsApiAAAGetVipListTaskListener lsr;

  public SccmsApiAAAGetVipListTask(String paramString)
  {
    this.license = paramString;
  }

  public String getTaskName()
  {
    return "N212_A_3";
  }

  public String getUrl()
  {
    AAAGetVipListParams localAAAGetVipListParams = new AAAGetVipListParams(this.license);
    localAAAGetVipListParams.setResultFormat(1);
    String str = webUrlFormatter.i().formatUrl(localAAAGetVipListParams.toString(), localAAAGetVipListParams.getApiName());
    Logger.i("SccmsApiAAAGetVipListTask", "N212_A_3 url:" + str);
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
      AAAVipList localAAAVipList = (AAAVipList)new AAAGetVipListSAXParserJson().parser(paramSCResponse.getContents());
      Logger.i("SccmsApiAAAGetVipListTask", "N212_A_3 result:" + localAAAVipList.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localAAAVipList);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiAAAGetVipListTaskListener paramISccmsApiAAAGetVipListTaskListener)
  {
    this.lsr = paramISccmsApiAAAGetVipListTaskListener;
  }

  public static abstract interface ISccmsApiAAAGetVipListTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, AAAVipList paramAAAVipList);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiAAAGetVipListTask
 * JD-Core Version:    0.6.2
 */