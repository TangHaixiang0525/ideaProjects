package com.starcor.sccms.api;

import com.starcor.core.domain.AAAMovieCouponInfo;
import com.starcor.core.epgapi.params.AAAGetMovieCouponInfoParams;
import com.starcor.core.parser.json.AAAGetMovieCouponInfoSAXParseJson;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCachedTask;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsApiAAAGetMovieCouponInfoTask extends ServerApiCachedTask
{
  private int businessId = -1;
  private String license = "";
  ISccmsApiAAAGetMovieCouponInfoTaskListener lsr;
  private String ticket = "";

  public SccmsApiAAAGetMovieCouponInfoTask(String paramString1, int paramInt, String paramString2)
  {
    this.ticket = paramString1;
    this.businessId = paramInt;
    this.license = paramString2;
  }

  public String getTaskName()
  {
    return "N212_A_18";
  }

  public String getUrl()
  {
    AAAGetMovieCouponInfoParams localAAAGetMovieCouponInfoParams = new AAAGetMovieCouponInfoParams(this.ticket, this.businessId, this.license);
    localAAAGetMovieCouponInfoParams.setResultFormat(1);
    String str = webUrlFormatter.i().formatUrl(localAAAGetMovieCouponInfoParams.toString(), localAAAGetMovieCouponInfoParams.getApiName());
    Logger.i("SccmsApiAAAGetMovieCouponInfoTask", "N212_A_18 url:" + str);
    return str;
  }

  public void perform(SCResponse paramSCResponse)
  {
    AAAGetMovieCouponInfoSAXParseJson localAAAGetMovieCouponInfoSAXParseJson = new AAAGetMovieCouponInfoSAXParseJson();
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    while (true)
    {
      try
      {
        AAAMovieCouponInfo localAAAMovieCouponInfo = (AAAMovieCouponInfo)localAAAGetMovieCouponInfoSAXParseJson.parser(paramSCResponse.getContents());
        if (localAAAMovieCouponInfo != null)
        {
          Logger.i("SccmsApiAAAGetMovieCouponInfoTask", "N212_A_18 result:" + localAAAMovieCouponInfo.toString());
          this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localAAAMovieCouponInfo);
          return;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
        return;
      }
      Logger.i("SccmsApiAAAGetMovieCouponInfoTask", "N212_A_18 result is null");
    }
  }

  public void setListener(ISccmsApiAAAGetMovieCouponInfoTaskListener paramISccmsApiAAAGetMovieCouponInfoTaskListener)
  {
    this.lsr = paramISccmsApiAAAGetMovieCouponInfoTaskListener;
  }

  public static abstract interface ISccmsApiAAAGetMovieCouponInfoTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, AAAMovieCouponInfo paramAAAMovieCouponInfo);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiAAAGetMovieCouponInfoTask
 * JD-Core Version:    0.6.2
 */