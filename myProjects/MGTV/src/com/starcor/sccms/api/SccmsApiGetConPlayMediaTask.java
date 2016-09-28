package com.starcor.sccms.api;

import android.text.TextUtils;
import com.starcor.core.domain.AppInfo;
import com.starcor.core.domain.ConPlayMedia;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.StringParams;
import com.starcor.core.parser.json.GetConPlayMediaSAXParserJson;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsApiGetConPlayMediaTask extends ServerApiTask
{
  private static final String TASK_NAME = "N39_A_33";
  private String excludeVideoIds;
  private IGetConPlayMediaTaskListener listener;
  private String videoId;
  private String videoType;

  public SccmsApiGetConPlayMediaTask(String paramString1, String paramString2, String[] paramArrayOfString)
  {
    this.videoId = paramString1;
    this.videoType = paramString2;
    if ((paramArrayOfString != null) && (paramArrayOfString.length > 0))
    {
      String str1 = "";
      for (int i = 0; i < paramArrayOfString.length; i++)
        if (!TextUtils.isEmpty(paramArrayOfString[i]))
        {
          String str2 = str1 + paramArrayOfString[i];
          str1 = str2 + ",";
        }
      this.excludeVideoIds = str1;
    }
  }

  public String getTaskName()
  {
    return "N39_A_33";
  }

  public String getUrl()
  {
    GetConPlayMediaTaskParam localGetConPlayMediaTaskParam = new GetConPlayMediaTaskParam(this.videoId, this.videoType, this.excludeVideoIds);
    localGetConPlayMediaTaskParam.setResultFormat(1);
    String str = webUrlFormatter.i().formatUrl(localGetConPlayMediaTaskParam.toString(), localGetConPlayMediaTaskParam.getApiName());
    Logger.i("N39_A_33", "url: " + str);
    return str;
  }

  public void perform(SCResponse paramSCResponse)
  {
    if (this.listener == null)
      return;
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.listener.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    try
    {
      ConPlayMedia localConPlayMedia = (ConPlayMedia)new GetConPlayMediaSAXParserJson().parser(paramSCResponse.getContents());
      this.listener.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localConPlayMedia);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.listener.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(IGetConPlayMediaTaskListener paramIGetConPlayMediaTaskListener)
  {
    this.listener = paramIGetConPlayMediaTaskListener;
  }

  class GetConPlayMediaTaskParam extends Api
  {
    StringParams nns_exclude_video_ids = new StringParams("nns_exclude_video_ids");
    StringParams nns_video_id = new StringParams("nns_video_id");
    StringParams nns_video_type = new StringParams("nns_video_type");

    GetConPlayMediaTaskParam(String paramString1, String paramString2, String arg4)
    {
      this.prefix = AppInfo.URL_n39_a;
      this.nns_func.setValue("get_video_play_after_info");
      this.nns_video_id.setValue(paramString1);
      this.nns_video_type.setValue(paramString2);
      String str;
      this.nns_exclude_video_ids.setValue(str);
    }

    public String getApiName()
    {
      return "N39_A_33";
    }

    public String toString()
    {
      String str = this.prefix;
      if (!str.contains("?"))
        str = str + "?";
      return str + this.nns_func + this.nns_video_id + this.nns_video_type + this.nns_exclude_video_ids + this.suffix;
    }
  }

  public static abstract interface IGetConPlayMediaTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, ConPlayMedia paramConPlayMedia);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiGetConPlayMediaTask
 * JD-Core Version:    0.6.2
 */