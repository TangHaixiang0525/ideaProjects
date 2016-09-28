package com.starcor.sccms.api;

import com.starcor.core.domain.AAAMovieCouponCount;
import com.starcor.core.epgapi.params.AAAGetMovieCouponCountParams;
import com.starcor.core.parser.json.AAAGetMovieCouponCountSAXParseJson;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCachedTask;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsApiAAAGetMovieCouponCountTask extends ServerApiCachedTask
{
  private int businessId = -1;
  private String license = "";
  ISccmsApiAAAGetMovieCouponCountTaskListener lsr;
  private String ticket = "";

  public SccmsApiAAAGetMovieCouponCountTask(String paramString1, int paramInt, String paramString2)
  {
    this.ticket = paramString1;
    this.businessId = paramInt;
    this.license = paramString2;
  }

  public String getTaskName()
  {
    return "N212_A_17";
  }

  public String getUrl()
  {
    AAAGetMovieCouponCountParams localAAAGetMovieCouponCountParams = new AAAGetMovieCouponCountParams(this.ticket, this.businessId, this.license);
    localAAAGetMovieCouponCountParams.setResultFormat(1);
    String str = webUrlFormatter.i().formatUrl(localAAAGetMovieCouponCountParams.toString(), localAAAGetMovieCouponCountParams.getApiName());
    Logger.i("SccmsApiAAAGetMovieCouponInfoTask", "N212_A_17 url:" + str);
    return str;
  }

  public void perform(SCResponse paramSCResponse)
  {
    AAAGetMovieCouponCountSAXParseJson localAAAGetMovieCouponCountSAXParseJson = new AAAGetMovieCouponCountSAXParseJson();
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    while (true)
    {
      try
      {
        AAAMovieCouponCount localAAAMovieCouponCount = (AAAMovieCouponCount)localAAAGetMovieCouponCountSAXParseJson.parser(paramSCResponse.getContents());
        if (localAAAMovieCouponCount != null)
        {
          Logger.i("SccmsApiAAAGetMovieCouponInfoTask", "N212_A_17 result:" + localAAAMovieCouponCount.toString());
          this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localAAAMovieCouponCount);
          return;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
        return;
      }
      Logger.i("SccmsApiAAAGetMovieCouponInfoTask", "N212_A_17 result is null");
    }
  }

  public void setListener(ISccmsApiAAAGetMovieCouponCountTaskListener paramISccmsApiAAAGetMovieCouponCountTaskListener)
  {
    this.lsr = paramISccmsApiAAAGetMovieCouponCountTaskListener;
  }

  public static abstract interface ISccmsApiAAAGetMovieCouponCountTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, AAAMovieCouponCount paramAAAMovieCouponCount);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiAAAGetMovieCouponCountTask
 * JD-Core Version:    0.6.2
 */