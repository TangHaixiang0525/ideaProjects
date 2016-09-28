package com.starcor.sccms.api;

import com.starcor.core.domain.AAAMovieCouponInfo;
import com.starcor.core.epgapi.params.AAAGetMovieCouponHistoryParams;
import com.starcor.core.parser.json.AAAGetMovieCouponInfoSAXParseJson;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCachedTask;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsApiAAAGetMovieCouponHistoryTask extends ServerApiCachedTask
{
  private int businessId = -1;
  private String license = "";
  ISccmsApiAAAGetMovieCouponHistoryTaskListener lsr;
  private int num = 0;
  private int page = 0;
  private String ticket = "";

  public SccmsApiAAAGetMovieCouponHistoryTask(String paramString1, int paramInt1, String paramString2, int paramInt2, int paramInt3)
  {
    this.ticket = paramString1;
    this.businessId = paramInt1;
    this.license = paramString2;
    this.page = paramInt2;
    this.num = paramInt3;
  }

  public String getTaskName()
  {
    return "N212_A_20";
  }

  public String getUrl()
  {
    AAAGetMovieCouponHistoryParams localAAAGetMovieCouponHistoryParams = new AAAGetMovieCouponHistoryParams(this.ticket, this.businessId, this.license, this.page, this.num);
    localAAAGetMovieCouponHistoryParams.setResultFormat(1);
    String str = webUrlFormatter.i().formatUrl(localAAAGetMovieCouponHistoryParams.toString(), localAAAGetMovieCouponHistoryParams.getApiName());
    Logger.i("SccmsApiAAAGetMovieCouponHistoryTask", "N212_A_20 url:" + str);
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
          Logger.i("SccmsApiAAAGetMovieCouponHistoryTask", "N212_A_20 result:" + localAAAMovieCouponInfo.toString());
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
      Logger.i("SccmsApiAAAGetMovieCouponHistoryTask", "N212_A_20 result is null");
    }
  }

  public void setListener(ISccmsApiAAAGetMovieCouponHistoryTaskListener paramISccmsApiAAAGetMovieCouponHistoryTaskListener)
  {
    this.lsr = paramISccmsApiAAAGetMovieCouponHistoryTaskListener;
  }

  public static abstract interface ISccmsApiAAAGetMovieCouponHistoryTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, AAAMovieCouponInfo paramAAAMovieCouponInfo);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiAAAGetMovieCouponHistoryTask
 * JD-Core Version:    0.6.2
 */