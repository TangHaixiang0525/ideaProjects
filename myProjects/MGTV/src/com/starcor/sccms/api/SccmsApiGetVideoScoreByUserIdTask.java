package com.starcor.sccms.api;

import android.text.TextUtils;
import com.starcor.core.domain.VideoScoreInfoOnUser;
import com.starcor.core.epgapi.params.GetVideoScroeByUserIdParams;
import com.starcor.core.parser.json.GetVideoScoreByUserIdSAXParserJson;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsApiGetVideoScoreByUserIdTask extends ServerApiTask
{
  ISccmsApiGetVideoScoreByUserIdTaskListener lsr = null;
  String nns_user_id = null;
  String nns_video_id = null;

  public SccmsApiGetVideoScoreByUserIdTask(String paramString1, String paramString2)
  {
    this.nns_video_id = paramString1;
    this.nns_user_id = paramString2;
  }

  public String getTaskName()
  {
    return "N3_A_A_15";
  }

  public String getUrl()
  {
    GetVideoScroeByUserIdParams localGetVideoScroeByUserIdParams = new GetVideoScroeByUserIdParams(this.nns_video_id, this.nns_user_id);
    localGetVideoScroeByUserIdParams.setResultFormat(1);
    return webUrlFormatter.i().formatUrl(localGetVideoScroeByUserIdParams.toString(), localGetVideoScroeByUserIdParams.getApiName());
  }

  public void perform(SCResponse paramSCResponse)
  {
    GetVideoScoreByUserIdSAXParserJson localGetVideoScoreByUserIdSAXParserJson = new GetVideoScoreByUserIdSAXParserJson();
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    try
    {
      VideoScoreInfoOnUser localVideoScoreInfoOnUser = (VideoScoreInfoOnUser)localGetVideoScoreByUserIdSAXParserJson.parser(paramSCResponse.getContents());
      if (TextUtils.isEmpty(localVideoScoreInfoOnUser.video_id))
        Logger.e("SccmsApiGetVideoScoreByUserIdTask", "返回错误的结果数");
      Logger.i("SccmsApiGetVideoScoreByUserIdTask", " result:" + localVideoScoreInfoOnUser.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localVideoScoreInfoOnUser);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiGetVideoScoreByUserIdTaskListener paramISccmsApiGetVideoScoreByUserIdTaskListener)
  {
    this.lsr = paramISccmsApiGetVideoScoreByUserIdTaskListener;
  }

  public static abstract interface ISccmsApiGetVideoScoreByUserIdTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, VideoScoreInfoOnUser paramVideoScoreInfoOnUser);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiGetVideoScoreByUserIdTask
 * JD-Core Version:    0.6.2
 */