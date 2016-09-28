package com.starcor.sccms.api;

import com.starcor.core.domain.VideoInfo;
import com.starcor.core.epgapi.params.GetFilmInfoV3Params;
import com.starcor.core.parser.json.GetFilmInfoV3SAXParserJson;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsApiGetVideoInfoV3Task extends ServerApiTask
{
  ISccmsApiGetVideoInfoV3TaskListener lsr = null;
  String nns_video_id = null;
  int nns_video_type = 0;

  public SccmsApiGetVideoInfoV3Task(String paramString, int paramInt)
  {
    this.nns_video_id = paramString;
    this.nns_video_type = paramInt;
    setCacheLife(1800000L);
  }

  public String getTaskName()
  {
    return "N39_A_24";
  }

  public String getUrl()
  {
    GetFilmInfoV3Params localGetFilmInfoV3Params = new GetFilmInfoV3Params(this.nns_video_id, this.nns_video_type);
    localGetFilmInfoV3Params.setResultFormat(1);
    return webUrlFormatter.i().formatUrl(localGetFilmInfoV3Params.toString(), localGetFilmInfoV3Params.getApiName());
  }

  public void perform(SCResponse paramSCResponse)
  {
    GetFilmInfoV3SAXParserJson localGetFilmInfoV3SAXParserJson = new GetFilmInfoV3SAXParserJson();
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    try
    {
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), (VideoInfo)localGetFilmInfoV3SAXParserJson.parser(paramSCResponse.getContents()));
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiGetVideoInfoV3TaskListener paramISccmsApiGetVideoInfoV3TaskListener)
  {
    this.lsr = paramISccmsApiGetVideoInfoV3TaskListener;
  }

  public static abstract interface ISccmsApiGetVideoInfoV3TaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, VideoInfo paramVideoInfo);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiGetVideoInfoV3Task
 * JD-Core Version:    0.6.2
 */