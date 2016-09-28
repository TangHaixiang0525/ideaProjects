package com.starcor.sccms.api;

import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsApiRequestImpressionTask extends ServerApiTask
{
  private String url;

  public SccmsApiRequestImpressionTask(String paramString)
  {
    this.url = paramString;
    Logger.i("SccmsApiRequestImpressionTask", "SccmsApiRequestImpressionTask url = " + paramString);
  }

  public int getConnectTimeOutMs()
  {
    return 3000;
  }

  public int getRetryTimes()
  {
    return 1;
  }

  public String getTaskName()
  {
    return "SccmsApiRequestImpressionTask";
  }

  public String getUrl()
  {
    return this.url;
  }

  public void perform(SCResponse paramSCResponse)
  {
    if (ServerApiTools.isSCResponseError(paramSCResponse))
      try
      {
        Logger.i("SccmsApiRequestImpressionTask", "error =" + paramSCResponse.getHttpCode());
        return;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return;
      }
    Logger.i("SccmsApiRequestImpressionTask", "ok =" + paramSCResponse.getHttpCode());
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiRequestImpressionTask
 * JD-Core Version:    0.6.2
 */