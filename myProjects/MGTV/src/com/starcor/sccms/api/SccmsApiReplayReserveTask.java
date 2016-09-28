package com.starcor.sccms.api;

import com.starcor.core.domain.PlayBillItem;
import com.starcor.core.domain.ReserveListItem;
import com.starcor.core.domain.VideoInfo;
import com.starcor.core.epgapi.params.ReplyPlayReserveParams;
import com.starcor.core.parser.sax.GetReplayOrLiveShowListSAXParse;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCachedTask;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;
import java.io.ByteArrayInputStream;

public class SccmsApiReplayReserveTask extends ServerApiCachedTask
{
  private String TAG = SccmsApiReplayReserveTask.class.getSimpleName();
  ISccmsApiReplayReserveTaskListener lsr;
  PlayBillItem mbill = null;
  int mtype;
  VideoInfo videoInfo = null;

  public SccmsApiReplayReserveTask(VideoInfo paramVideoInfo, PlayBillItem paramPlayBillItem, int paramInt)
  {
    this.videoInfo = paramVideoInfo;
    this.mbill = paramPlayBillItem;
    this.mtype = paramInt;
  }

  public String getTaskName()
  {
    return "N40_i";
  }

  public String getUrl()
  {
    ReplyPlayReserveParams localReplyPlayReserveParams = new ReplyPlayReserveParams(this.mbill, this.videoInfo, this.mtype);
    localReplyPlayReserveParams.setResultFormat(0);
    return webUrlFormatter.i().formatUrl(localReplyPlayReserveParams.toString(), localReplyPlayReserveParams.getApiName());
  }

  public void perform(SCResponse paramSCResponse)
  {
    GetReplayOrLiveShowListSAXParse localGetReplayOrLiveShowListSAXParse = new GetReplayOrLiveShowListSAXParse();
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    ReserveListItem localReserveListItem;
    try
    {
      localReserveListItem = (ReserveListItem)localGetReplayOrLiveShowListSAXParse.parser(new ByteArrayInputStream(paramSCResponse.getContents()));
      if ((localReserveListItem == null) || (localReserveListItem.state == null) || (!localReserveListItem.state.equals("0")))
      {
        this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
        return;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
      return;
    }
    Logger.i("SccmsApiReplayReserveTask", "回看预约记录  result:" + localReserveListItem);
    this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localReserveListItem);
  }

  public void setListener(ISccmsApiReplayReserveTaskListener paramISccmsApiReplayReserveTaskListener)
  {
    this.lsr = paramISccmsApiReplayReserveTaskListener;
  }

  public static abstract interface ISccmsApiReplayReserveTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, ReserveListItem paramReserveListItem);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiReplayReserveTask
 * JD-Core Version:    0.6.2
 */