package com.starcor.sccms.api;

import com.starcor.core.domain.AAAPayChannelList;
import com.starcor.core.epgapi.params.AAAGetPayChannelListParams;
import com.starcor.core.parser.json.AAAGetPayChannelListSAXParserJson;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsApiAAAGetPayChannelListTask extends ServerApiTask
{
  private String license = "";
  private ISccmsApiAAAGetPayChannelListTaskListener lsr;
  private String type = "";

  public SccmsApiAAAGetPayChannelListTask(String paramString1, String paramString2)
  {
    this.license = paramString1;
    this.type = paramString2;
  }

  public String getTaskName()
  {
    return "N212_A_6";
  }

  public String getUrl()
  {
    AAAGetPayChannelListParams localAAAGetPayChannelListParams = new AAAGetPayChannelListParams(this.license, this.type);
    localAAAGetPayChannelListParams.setResultFormat(1);
    String str = webUrlFormatter.i().formatUrl(localAAAGetPayChannelListParams.toString(), localAAAGetPayChannelListParams.getApiName());
    Logger.i("SccmsApiAAAGetPayChannelListTask", "N212_A_6 url:" + str);
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
      AAAPayChannelList localAAAPayChannelList = (AAAPayChannelList)new AAAGetPayChannelListSAXParserJson().parser(paramSCResponse.getContents());
      Logger.i("SccmsApiAAAGetPayChannelListTask", "N212_A_6 result:" + localAAAPayChannelList.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localAAAPayChannelList);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiAAAGetPayChannelListTaskListener paramISccmsApiAAAGetPayChannelListTaskListener)
  {
    this.lsr = paramISccmsApiAAAGetPayChannelListTaskListener;
  }

  public static abstract interface ISccmsApiAAAGetPayChannelListTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, AAAPayChannelList paramAAAPayChannelList);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiAAAGetPayChannelListTask
 * JD-Core Version:    0.6.2
 */