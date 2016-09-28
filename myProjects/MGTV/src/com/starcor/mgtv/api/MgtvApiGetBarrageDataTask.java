package com.starcor.mgtv.api;

import android.text.TextUtils;
import com.starcor.core.domain.BarrageResponse;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.parser.json.GetBarrageDataParserJson;
import com.starcor.core.utils.Logger;
import com.starcor.core.utils.URLAnalysis;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;
import java.util.HashMap;

public class MgtvApiGetBarrageDataTask extends ServerApiTask
{
  private static final String HOST = "http://211.151.133.52";
  private static final String PATH = "/barrage/v2/client/liveRead";
  private static final String TAG = MgtvApiGetBarrageDataTask.class.getSimpleName();
  private static final String TASK_NAME = TAG;
  private String category;
  private String curTimeKey;
  private IGetBarrageDataTaskListener listener;
  private String videoId;

  public MgtvApiGetBarrageDataTask(String paramString1, String paramString2, String paramString3)
  {
    this.videoId = paramString1;
    this.category = paramString2;
    this.curTimeKey = paramString3;
  }

  private String buildUrl()
  {
    String str1 = GlobalLogic.getInstance().getReqBarrageDataUrl();
    if (TextUtils.isEmpty(str1))
    {
      str1 = "http://211.151.133.52/barrage/v2/client/liveRead";
      Logger.e(TAG, "获取弹幕任务URL为空，暂时用测试URL：" + str1 + ", DEBUG THIS!!!");
    }
    if (!str1.contains("?"))
      str1 = str1 + "?";
    HashMap localHashMap = new HashMap();
    if (!TextUtils.isEmpty(this.videoId))
      localHashMap.put("videoId", this.videoId);
    if (!TextUtils.isEmpty(this.category))
      localHashMap.put("category", this.category);
    if (!TextUtils.isEmpty(this.curTimeKey))
      localHashMap.put("currentTimeKey", this.curTimeKey);
    localHashMap.put("device", "ott");
    String str2 = str1 + URLAnalysis.paramsToUrlString(localHashMap);
    Logger.d(TAG, "url: " + str2);
    return str2;
  }

  public String getTaskName()
  {
    return TASK_NAME;
  }

  public String getUrl()
  {
    return buildUrl();
  }

  public void perform(SCResponse paramSCResponse)
  {
    super.perform(paramSCResponse);
    if (this.listener != null)
    {
      if (ServerApiTools.isSCResponseError(paramSCResponse))
        this.listener.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
    }
    else
      return;
    GetBarrageDataParserJson localGetBarrageDataParserJson = new GetBarrageDataParserJson();
    try
    {
      BarrageResponse localBarrageResponse = (BarrageResponse)localGetBarrageDataParserJson.parser(paramSCResponse.getContents());
      if (localBarrageResponse != null)
      {
        this.listener.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localBarrageResponse);
        return;
      }
    }
    catch (Exception localException)
    {
      this.listener.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
      return;
    }
    this.listener.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, "parse result error."));
  }

  public void setListener(IGetBarrageDataTaskListener paramIGetBarrageDataTaskListener)
  {
    this.listener = paramIGetBarrageDataTaskListener;
  }

  public static abstract interface IGetBarrageDataTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, BarrageResponse paramBarrageResponse);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.mgtv.api.MgtvApiGetBarrageDataTask
 * JD-Core Version:    0.6.2
 */