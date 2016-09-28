package com.starcor.mgtv.api;

import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.WebUrlBuilder;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class MgtvApiCheckNetStateTask extends ServerApiTask
{
  IMgtvApiCheckNetStateTaskkListener lsr;

  public String getTaskName()
  {
    return "MgtvApiCheckNetStateTask";
  }

  public String getUrl()
  {
    return WebUrlBuilder.getCheckNetStateUrl();
  }

  public void perform(SCResponse paramSCResponse)
  {
    String str = "";
    if (paramSCResponse.getContents() != null)
      str = new String(paramSCResponse.getContents());
    Logger.i("MgtvApiCheckNetStateTask", "perform() rep.getHttpCode:" + paramSCResponse.getHttpCode() + ", rep.getRunState:" + paramSCResponse.getRunState() + ", rep.getContents:" + str);
    if (paramSCResponse.getRunState() != 1)
      try
      {
        this.lsr.onError(new ServerApiTaskInfo(getTaskId(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
        return;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        this.lsr.onError(new ServerApiTaskInfo(getTaskId(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.getMessage()));
        return;
      }
    if ((200 == paramSCResponse.getHttpCode()) && ("ok".equals(str)))
    {
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), paramSCResponse), "");
      return;
    }
    this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), paramSCResponse), WebUrlBuilder.getCheckNetStateUrl());
  }

  public void setListener(IMgtvApiCheckNetStateTaskkListener paramIMgtvApiCheckNetStateTaskkListener)
  {
    this.lsr = paramIMgtvApiCheckNetStateTaskkListener;
  }

  public static abstract interface IMgtvApiCheckNetStateTaskkListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, String paramString);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.mgtv.api.MgtvApiCheckNetStateTask
 * JD-Core Version:    0.6.2
 */