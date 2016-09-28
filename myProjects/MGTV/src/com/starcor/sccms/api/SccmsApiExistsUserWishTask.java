package com.starcor.sccms.api;

import com.google.gson.Gson;
import com.starcor.core.domain.CommonState;
import com.starcor.core.epgapi.params.ExistsUserWishParams;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsApiExistsUserWishTask extends ServerApiTask
{
  private Gson gson;
  ISccmsApiExistsUserWishTaskListener lsr;
  private String videoId;

  public SccmsApiExistsUserWishTask(String paramString, ISccmsApiExistsUserWishTaskListener paramISccmsApiExistsUserWishTaskListener)
  {
    this.videoId = paramString;
    this.lsr = paramISccmsApiExistsUserWishTaskListener;
    this.gson = new Gson();
  }

  public String getTaskName()
  {
    return "N40_G_2";
  }

  public String getUrl()
  {
    ExistsUserWishParams localExistsUserWishParams = new ExistsUserWishParams(this.videoId);
    localExistsUserWishParams.setResultFormat(1);
    return webUrlFormatter.i().formatUrl(localExistsUserWishParams.toString(), localExistsUserWishParams.getApiName());
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
      String str = new String(paramSCResponse.getContents());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), (CommonState)this.gson.fromJson(str, CommonState.class));
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public static abstract interface ISccmsApiExistsUserWishTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, CommonState paramCommonState);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiExistsUserWishTask
 * JD-Core Version:    0.6.2
 */