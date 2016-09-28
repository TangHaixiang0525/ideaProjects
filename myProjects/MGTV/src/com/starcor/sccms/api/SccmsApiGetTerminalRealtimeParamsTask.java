package com.starcor.sccms.api;

import com.starcor.core.domain.AppInfo;
import com.starcor.core.domain.TerminalRealtimeParamList;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.StringParams;
import com.starcor.core.parser.json.GetTermialRealtimeParamsSAXParserJson;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsApiGetTerminalRealtimeParamsTask extends ServerApiTask
{
  IGetTerminalRealtimeParamsTaskListener listener = null;

  public String getTaskName()
  {
    return "N36_A_5";
  }

  public String getUrl()
  {
    GetTerminalRealtimeParamsTaskParams localGetTerminalRealtimeParamsTaskParams = new GetTerminalRealtimeParamsTaskParams();
    localGetTerminalRealtimeParamsTaskParams.setResultFormat(1);
    String str = webUrlFormatter.i().formatUrl(localGetTerminalRealtimeParamsTaskParams.toString(), localGetTerminalRealtimeParamsTaskParams.getApiName());
    Logger.i(localGetTerminalRealtimeParamsTaskParams.getApiName(), "url: " + str);
    return str;
  }

  public void perform(SCResponse paramSCResponse)
  {
    if (this.listener == null)
      return;
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.listener.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    try
    {
      TerminalRealtimeParamList localTerminalRealtimeParamList = (TerminalRealtimeParamList)new GetTermialRealtimeParamsSAXParserJson().parser(paramSCResponse.getContents());
      if (localTerminalRealtimeParamList != null)
      {
        this.listener.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localTerminalRealtimeParamList);
        return;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.listener.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    this.listener.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
  }

  public void setListener(IGetTerminalRealtimeParamsTaskListener paramIGetTerminalRealtimeParamsTaskListener)
  {
    this.listener = paramIGetTerminalRealtimeParamsTaskListener;
  }

  class GetTerminalRealtimeParamsTaskParams extends Api
  {
    GetTerminalRealtimeParamsTaskParams()
    {
      this.prefix = AppInfo.URL_n36_a;
      this.nns_func.setValue("get_terminal_realtime_params");
    }

    public String getApiName()
    {
      return "N36_A_5";
    }

    public String toString()
    {
      String str = this.prefix;
      if (!str.contains("?"))
        str = str + "?";
      return str + this.nns_func + this.suffix;
    }
  }

  public static abstract interface IGetTerminalRealtimeParamsTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, TerminalRealtimeParamList paramTerminalRealtimeParamList);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiGetTerminalRealtimeParamsTask
 * JD-Core Version:    0.6.2
 */