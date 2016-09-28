package com.starcor.sccms.api;

import android.text.TextUtils;
import com.starcor.core.domain.VideoInfo;
import com.starcor.core.epgapi.params.GetFilmInfoV2Params;
import com.starcor.core.parser.json.GetFilmInfoV2SAXParserJson;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsApiGetVideoInfoV2Task extends ServerApiTask
{
  ISccmsApiGetVideoInfoV2TaskListener lsr = null;
  int nns_page_size = 2;
  String nns_video_id = null;
  int nns_video_type = 0;

  public SccmsApiGetVideoInfoV2Task(String paramString, int paramInt)
  {
    this.nns_video_id = paramString;
    this.nns_video_type = paramInt;
    setCacheLife(1800000L);
  }

  public String getTaskName()
  {
    return "N3_A_A_14";
  }

  public String getUrl()
  {
    GetFilmInfoV2Params localGetFilmInfoV2Params = new GetFilmInfoV2Params(this.nns_video_id, this.nns_video_type, this.nns_page_size);
    localGetFilmInfoV2Params.setResultFormat(1);
    return webUrlFormatter.i().formatUrl(localGetFilmInfoV2Params.toString(), localGetFilmInfoV2Params.getApiName());
  }

  public void perform(SCResponse paramSCResponse)
  {
    GetFilmInfoV2SAXParserJson localGetFilmInfoV2SAXParserJson = new GetFilmInfoV2SAXParserJson();
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    try
    {
      VideoInfo localVideoInfo = (VideoInfo)localGetFilmInfoV2SAXParserJson.parser(paramSCResponse.getContents());
      if (TextUtils.isEmpty(localVideoInfo.videoId))
        Logger.e("SccmsApiGetVideoInfoV2Task", "返回错误的结果数");
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localVideoInfo);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiGetVideoInfoV2TaskListener paramISccmsApiGetVideoInfoV2TaskListener)
  {
    this.lsr = paramISccmsApiGetVideoInfoV2TaskListener;
  }

  public static abstract interface ISccmsApiGetVideoInfoV2TaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, VideoInfo paramVideoInfo);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiGetVideoInfoV2Task
 * JD-Core Version:    0.6.2
 */