package com.starcor.sccms.api;

import com.starcor.core.domain.VideoIndex;
import com.starcor.core.epgapi.params.GetVideoIndexInfoParams;
import com.starcor.core.parser.sax.GetVideoIndexInfoSAXParser;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCachedTask;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsApiGetVideoIndexInfoTask extends ServerApiCachedTask
{
  private static final String TAG = SccmsApiGetVideoIndexInfoTask.class.getSimpleName();
  ISccmsApiGetVideoIndexInfoTaskListener lsr;
  GetVideoIndexInfoParams params;

  public SccmsApiGetVideoIndexInfoTask(String paramString, int paramInt1, int paramInt2)
  {
    this.params = new GetVideoIndexInfoParams(paramString, paramInt1, paramInt2);
  }

  public String getTaskName()
  {
    return "N3_A_A_13";
  }

  public String getUrl()
  {
    try
    {
      String str = webUrlFormatter.i().formatUrl(this.params.toString(), this.params.getApiName());
      return str;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return "";
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
      VideoIndex localVideoIndex = (VideoIndex)new GetVideoIndexInfoSAXParser().parser(paramSCResponse.getContents());
      Logger.i(TAG, " result:" + localVideoIndex.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localVideoIndex);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiGetVideoIndexInfoTaskListener paramISccmsApiGetVideoIndexInfoTaskListener)
  {
    this.lsr = paramISccmsApiGetVideoIndexInfoTaskListener;
  }

  public static abstract interface ISccmsApiGetVideoIndexInfoTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, VideoIndex paramVideoIndex);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiGetVideoIndexInfoTask
 * JD-Core Version:    0.6.2
 */