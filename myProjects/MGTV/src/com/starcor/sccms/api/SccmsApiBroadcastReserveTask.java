package com.starcor.sccms.api;

import com.starcor.core.domain.ReserveListItem;
import com.starcor.core.domain.VideoInfo;
import com.starcor.core.epgapi.params.BroadCastReserveParams;
import com.starcor.core.parser.sax.GetReplayOrLiveShowListSAXParse;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCachedTask;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;
import java.io.ByteArrayInputStream;

public class SccmsApiBroadcastReserveTask extends ServerApiCachedTask
{
  private String TAG = SccmsApiBroadcastReserveTask.class.getSimpleName();
  String begin_day = null;
  String begin_time = null;
  String channel_id = null;
  String filename = null;
  String liveshowid = null;
  String liveshowurl = null;
  ISccmsApiBroadCastReserveTaskListener lsr;
  int mtype;
  VideoInfo videoinfo = null;

  public SccmsApiBroadcastReserveTask(String paramString, int paramInt)
  {
    this.channel_id = paramString;
    this.mtype = paramInt;
  }

  public String getTaskName()
  {
    return "N40_h";
  }

  public String getUrl()
  {
    BroadCastReserveParams localBroadCastReserveParams = new BroadCastReserveParams(this.channel_id, this.mtype);
    localBroadCastReserveParams.setResultFormat(0);
    return webUrlFormatter.i().formatUrl(localBroadCastReserveParams.toString(), localBroadCastReserveParams.getApiName());
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
      if ((localReserveListItem.state == null) || (!localReserveListItem.state.equals("0")))
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
    Logger.i("SccmsApiReplayReserveTask", "LiveShow预约记录  result:" + localReserveListItem);
    this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localReserveListItem);
  }

  public void setListener(ISccmsApiBroadCastReserveTaskListener paramISccmsApiBroadCastReserveTaskListener)
  {
    this.lsr = paramISccmsApiBroadCastReserveTaskListener;
  }

  public static abstract interface ISccmsApiBroadCastReserveTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, ReserveListItem paramReserveListItem);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiBroadcastReserveTask
 * JD-Core Version:    0.6.2
 */