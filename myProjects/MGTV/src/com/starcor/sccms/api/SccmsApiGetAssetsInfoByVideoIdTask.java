package com.starcor.sccms.api;

import com.starcor.core.domain.MediaAssetsInfo;
import com.starcor.core.epgapi.params.GetAssetsInfoByVideoIdParams;
import com.starcor.core.parser.json.GetAssetsInfoSAXParserJson;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsApiGetAssetsInfoByVideoIdTask extends ServerApiTask
{
  ISccmsApiGetAssetsInfoByVideoIdTaskListener lsr;
  String nns_video_id = null;

  public SccmsApiGetAssetsInfoByVideoIdTask(String paramString)
  {
    this.nns_video_id = paramString;
    setCacheLife(1800000L);
  }

  public String getTaskName()
  {
    return "N3_A_D_11";
  }

  public String getUrl()
  {
    GetAssetsInfoByVideoIdParams localGetAssetsInfoByVideoIdParams = new GetAssetsInfoByVideoIdParams(this.nns_video_id);
    localGetAssetsInfoByVideoIdParams.setResultFormat(1);
    return webUrlFormatter.i().formatUrl(localGetAssetsInfoByVideoIdParams.toString(), localGetAssetsInfoByVideoIdParams.getApiName());
  }

  public void perform(SCResponse paramSCResponse)
  {
    GetAssetsInfoSAXParserJson localGetAssetsInfoSAXParserJson = new GetAssetsInfoSAXParserJson();
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    try
    {
      MediaAssetsInfo localMediaAssetsInfo = (MediaAssetsInfo)localGetAssetsInfoSAXParserJson.parser(paramSCResponse.getContents());
      Logger.i("SccmsApiGetHotWordsTask", " result:" + localMediaAssetsInfo.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localMediaAssetsInfo);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiGetAssetsInfoByVideoIdTaskListener paramISccmsApiGetAssetsInfoByVideoIdTaskListener)
  {
    this.lsr = paramISccmsApiGetAssetsInfoByVideoIdTaskListener;
  }

  public static abstract interface ISccmsApiGetAssetsInfoByVideoIdTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, MediaAssetsInfo paramMediaAssetsInfo);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiGetAssetsInfoByVideoIdTask
 * JD-Core Version:    0.6.2
 */