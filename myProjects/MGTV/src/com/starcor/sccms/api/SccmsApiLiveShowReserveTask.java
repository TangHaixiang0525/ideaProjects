package com.starcor.sccms.api;

import com.starcor.core.domain.ReserveListItem;
import com.starcor.core.domain.VideoInfo;
import com.starcor.core.epgapi.params.LiveShowReserveParams;
import com.starcor.core.parser.sax.GetReplayOrLiveShowListSAXParse;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCachedTask;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;
import java.io.ByteArrayInputStream;

public class SccmsApiLiveShowReserveTask extends ServerApiCachedTask
{
  private String TAG = SccmsApiLiveShowReserveTask.class.getSimpleName();
  String begin_day = null;
  String begin_time = null;
  String filename = null;
  String liveshowid = null;
  String liveshowurl = null;
  ISccmsApiLiveShowReserveTaskListener lsr;
  int mtype;
  String special_id = null;
  VideoInfo videoinfo = null;

  public SccmsApiLiveShowReserveTask(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, int paramInt)
  {
    this.special_id = paramString1;
    this.filename = paramString2;
    this.liveshowid = paramString3;
    this.liveshowurl = paramString4;
    this.begin_day = paramString5;
    this.begin_time = paramString6;
    this.mtype = paramInt;
  }

  public String getTaskName()
  {
    return "N40_h";
  }

  public String getUrl()
  {
    LiveShowReserveParams localLiveShowReserveParams = new LiveShowReserveParams(this.special_id, this.filename, this.liveshowid, this.liveshowurl, this.begin_day, this.begin_time, this.mtype);
    localLiveShowReserveParams.setResultFormat(0);
    return webUrlFormatter.i().formatUrl(localLiveShowReserveParams.toString(), localLiveShowReserveParams.getApiName());
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
    Logger.i("SccmsApiLiveShowReserveTask", "LiveShow预约记录  result:" + localReserveListItem);
    this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localReserveListItem);
  }

  public void setListener(ISccmsApiLiveShowReserveTaskListener paramISccmsApiLiveShowReserveTaskListener)
  {
    this.lsr = paramISccmsApiLiveShowReserveTaskListener;
  }

  public static abstract interface ISccmsApiLiveShowReserveTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, ReserveListItem paramReserveListItem);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiLiveShowReserveTask
 * JD-Core Version:    0.6.2
 */