package com.starcor.sccms.api;

import com.starcor.core.domain.StarInfoCollect;
import com.starcor.core.epgapi.params.GetStarCollectParams;
import com.starcor.core.parser.sax.GetStarCollectDataSAXParser;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;
import java.io.ByteArrayInputStream;
import java.util.List;

public class SccmsApiGetStarCollectDataTask extends ServerApiTask
{
  private static final String TAG = SccmsApiGetStarCollectDataTask.class.getSimpleName();
  private ISccmsApiGetStarCollectDataTaskListener mListener = null;
  private GetStarCollectParamType mType = GetStarCollectParamType.GET_PARAM_BY_VIDEO_ID;
  private List<String> nns_label_id = null;
  private String nns_video_id = "";
  private String nns_video_type = "";

  public SccmsApiGetStarCollectDataTask(String paramString1, String paramString2)
  {
    this.nns_video_id = paramString1;
    this.nns_video_type = paramString2;
    setCacheLife(1800000L);
  }

  public SccmsApiGetStarCollectDataTask(List<String> paramList)
  {
    this.nns_label_id = paramList;
    setCacheLife(1800000L);
  }

  public String getTaskName()
  {
    return "N39_A_4";
  }

  public String getUrl()
  {
    GetStarCollectParams localGetStarCollectParams;
    if (GetStarCollectParamType.GET_PARAM_BY_LABEL_ID.equals(this.mType))
    {
      Logger.i(TAG, "GET_PARAM_BY_LABEL_ID");
      localGetStarCollectParams = new GetStarCollectParams(this.nns_label_id);
    }
    while (localGetStarCollectParams != null)
    {
      localGetStarCollectParams.setResultFormat(0);
      return webUrlFormatter.i().formatUrl(localGetStarCollectParams.toString(), localGetStarCollectParams.getApiName());
      boolean bool = GetStarCollectParamType.GET_PARAM_BY_VIDEO_ID.equals(this.mType);
      localGetStarCollectParams = null;
      if (bool)
      {
        Logger.i(TAG, "GET_PARAM_BY_VIDEO_ID");
        localGetStarCollectParams = new GetStarCollectParams(this.nns_video_id, this.nns_video_type);
      }
    }
    Logger.i(TAG, "param is null!");
    return "";
  }

  public void perform(SCResponse paramSCResponse)
  {
    Logger.i(TAG, "ServerApiTools.isSCResponseError(rep)=" + ServerApiTools.isSCResponseError(paramSCResponse));
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.mListener.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    GetStarCollectDataSAXParser localGetStarCollectDataSAXParser = new GetStarCollectDataSAXParser();
    try
    {
      StarInfoCollect localStarInfoCollect = (StarInfoCollect)localGetStarCollectDataSAXParser.parser(new ByteArrayInputStream(paramSCResponse.getContents()));
      if (localStarInfoCollect != null);
      this.mListener.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localStarInfoCollect);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.mListener.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiGetStarCollectDataTaskListener paramISccmsApiGetStarCollectDataTaskListener)
  {
    this.mListener = paramISccmsApiGetStarCollectDataTaskListener;
  }

  public static enum GetStarCollectParamType
  {
    static
    {
      GetStarCollectParamType[] arrayOfGetStarCollectParamType = new GetStarCollectParamType[2];
      arrayOfGetStarCollectParamType[0] = GET_PARAM_BY_LABEL_ID;
      arrayOfGetStarCollectParamType[1] = GET_PARAM_BY_VIDEO_ID;
    }
  }

  public static abstract interface ISccmsApiGetStarCollectDataTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, StarInfoCollect paramStarInfoCollect);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiGetStarCollectDataTask
 * JD-Core Version:    0.6.2
 */