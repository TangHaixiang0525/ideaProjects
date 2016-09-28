package com.starcor.sccms.api;

import com.starcor.core.domain.AAAUseMovieCouponResult;
import com.starcor.core.epgapi.params.AAAUseMovieCouponParams;
import com.starcor.core.parser.json.AAAUseMovieCouponSAXParseJson;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCachedTask;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsApiAAAUseMovieCouponTask extends ServerApiCachedTask
{
  private int businessId = -1;
  private String license = "";
  ISccmsApiAAAUseMovieCouponTaskListener lsr;
  private String media_id = "";
  private String ticket = "";
  private String type = "0";

  public SccmsApiAAAUseMovieCouponTask(String paramString1, int paramInt, String paramString2, String paramString3, String paramString4)
  {
    this.ticket = paramString1;
    this.businessId = paramInt;
    this.license = paramString2;
    this.media_id = paramString3;
    this.type = paramString4;
  }

  public String getTaskName()
  {
    return "N212_A_19";
  }

  public String getUrl()
  {
    AAAUseMovieCouponParams localAAAUseMovieCouponParams = new AAAUseMovieCouponParams(this.ticket, this.businessId, this.license, this.media_id, this.type);
    localAAAUseMovieCouponParams.setResultFormat(1);
    String str = webUrlFormatter.i().formatUrl(localAAAUseMovieCouponParams.toString(), localAAAUseMovieCouponParams.getApiName());
    Logger.i("SccmsApiAAAUseMovieCouponTask", "N212_A_19 url:" + str);
    return str;
  }

  public void perform(SCResponse paramSCResponse)
  {
    AAAUseMovieCouponSAXParseJson localAAAUseMovieCouponSAXParseJson = new AAAUseMovieCouponSAXParseJson();
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    while (true)
    {
      try
      {
        AAAUseMovieCouponResult localAAAUseMovieCouponResult = (AAAUseMovieCouponResult)localAAAUseMovieCouponSAXParseJson.parser(paramSCResponse.getContents());
        if (localAAAUseMovieCouponResult != null)
        {
          Logger.i("SccmsApiAAAUseMovieCouponTask", "N212_A_19 result:" + localAAAUseMovieCouponResult.toString());
          this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localAAAUseMovieCouponResult);
          return;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
        return;
      }
      Logger.i("SccmsApiAAAUseMovieCouponTask", "N212_A_19 result is null");
    }
  }

  public void setListener(ISccmsApiAAAUseMovieCouponTaskListener paramISccmsApiAAAUseMovieCouponTaskListener)
  {
    this.lsr = paramISccmsApiAAAUseMovieCouponTaskListener;
  }

  public static abstract interface ISccmsApiAAAUseMovieCouponTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, AAAUseMovieCouponResult paramAAAUseMovieCouponResult);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiAAAUseMovieCouponTask
 * JD-Core Version:    0.6.2
 */