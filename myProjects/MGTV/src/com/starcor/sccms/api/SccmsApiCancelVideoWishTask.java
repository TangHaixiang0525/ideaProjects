package com.starcor.sccms.api;

import com.google.gson.Gson;
import com.starcor.core.domain.CommonState;
import com.starcor.core.domain.VideoInfo;
import com.starcor.core.epgapi.params.CancelVideoWishParams;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsApiCancelVideoWishTask extends ServerApiTask
{
  private Gson gson;
  ISccmsApiCancelVideoWishTaskListener lsr;
  private VideoInfo videoinfo;

  public SccmsApiCancelVideoWishTask(VideoInfo paramVideoInfo, ISccmsApiCancelVideoWishTaskListener paramISccmsApiCancelVideoWishTaskListener)
  {
    this.videoinfo = paramVideoInfo;
    this.lsr = paramISccmsApiCancelVideoWishTaskListener;
    this.gson = new Gson();
  }

  public String getTaskName()
  {
    return "N40_G_3";
  }

  public String getUrl()
  {
    CancelVideoWishParams localCancelVideoWishParams = new CancelVideoWishParams(this.videoinfo);
    localCancelVideoWishParams.setResultFormat(1);
    return webUrlFormatter.i().formatUrl(localCancelVideoWishParams.toString(), localCancelVideoWishParams.getApiName());
  }

  public void perform(SCResponse paramSCResponse)
  {
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    try
    {
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), (CommonState)this.gson.fromJson(new String(paramSCResponse.getContents()), CommonState.class));
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public static abstract interface ISccmsApiCancelVideoWishTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, CommonState paramCommonState);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiCancelVideoWishTask
 * JD-Core Version:    0.6.2
 */