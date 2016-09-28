package com.starcor.sccms.api;

import com.starcor.core.domain.AAAVipInfo;
import com.starcor.core.epgapi.params.AAAGetVipInfoParams;
import com.starcor.core.parser.json.AAAGetVipInfoSAXParserJson;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsApiAAAGetVipInfoTask extends ServerApiTask
{
  private String license = "";
  private ISccmsApiAAAGetVipInfoTaskListener lsr;
  private int vipId = -1;

  public SccmsApiAAAGetVipInfoTask(String paramString, int paramInt)
  {
    this.license = paramString;
    this.vipId = paramInt;
  }

  public String getTaskName()
  {
    return "N212_A_3";
  }

  public String getUrl()
  {
    AAAGetVipInfoParams localAAAGetVipInfoParams = new AAAGetVipInfoParams(this.license, this.vipId);
    localAAAGetVipInfoParams.setResultFormat(1);
    String str = webUrlFormatter.i().formatUrl(localAAAGetVipInfoParams.toString(), localAAAGetVipInfoParams.getApiName());
    Logger.i("SccmsApiAAAGetVipInfoTask", "N212_A_3 url:" + str);
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
      AAAVipInfo localAAAVipInfo = (AAAVipInfo)new AAAGetVipInfoSAXParserJson().parser(paramSCResponse.getContents());
      Logger.i("SccmsApiAAAGetVipInfoTask", "N212_A_3 result:" + localAAAVipInfo.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localAAAVipInfo);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiAAAGetVipInfoTaskListener paramISccmsApiAAAGetVipInfoTaskListener)
  {
    this.lsr = paramISccmsApiAAAGetVipInfoTaskListener;
  }

  public static abstract interface ISccmsApiAAAGetVipInfoTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, AAAVipInfo paramAAAVipInfo);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiAAAGetVipInfoTask
 * JD-Core Version:    0.6.2
 */