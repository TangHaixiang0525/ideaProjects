package com.starcor.sccms.api;

import com.starcor.core.domain.NewDetailedFilmData;
import com.starcor.core.epgapi.params.GetNewDetailedDataParams;
import com.starcor.core.parser.sax.GetNewDetailedDataSAXParser;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;
import java.io.ByteArrayInputStream;

public class SccmsAPIGetNewDetailedDataByVideoIdTask extends ServerApiTask
{
  private static final String TAG = SccmsAPIGetNewDetailedDataByVideoIdTask.class.getSimpleName();
  private IGetNewDetailedDataByVideoIdListener mListener = null;
  private String nns_video_id = null;

  public SccmsAPIGetNewDetailedDataByVideoIdTask(String paramString)
  {
    this.nns_video_id = paramString;
    setCacheLife(1800000L);
  }

  public String getTaskName()
  {
    return "N39_A_6";
  }

  public String getUrl()
  {
    GetNewDetailedDataParams localGetNewDetailedDataParams = new GetNewDetailedDataParams(this.nns_video_id);
    localGetNewDetailedDataParams.setResultFormat(0);
    return webUrlFormatter.i().formatUrl(localGetNewDetailedDataParams.toString(), localGetNewDetailedDataParams.getApiName());
  }

  public void perform(SCResponse paramSCResponse)
  {
    Logger.i(TAG, "ServerApiTools.isSCResponseError(rep)=" + ServerApiTools.isSCResponseError(paramSCResponse));
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.mListener.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    GetNewDetailedDataSAXParser localGetNewDetailedDataSAXParser = new GetNewDetailedDataSAXParser();
    try
    {
      NewDetailedFilmData localNewDetailedFilmData = (NewDetailedFilmData)localGetNewDetailedDataSAXParser.parser(new ByteArrayInputStream(paramSCResponse.getContents()));
      if (localNewDetailedFilmData != null)
        Logger.i(TAG, " result:" + localNewDetailedFilmData.toString());
      this.mListener.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localNewDetailedFilmData);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.mListener.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(IGetNewDetailedDataByVideoIdListener paramIGetNewDetailedDataByVideoIdListener)
  {
    this.mListener = paramIGetNewDetailedDataByVideoIdListener;
  }

  public static abstract interface IGetNewDetailedDataByVideoIdListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, NewDetailedFilmData paramNewDetailedFilmData);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsAPIGetNewDetailedDataByVideoIdTask
 * JD-Core Version:    0.6.2
 */