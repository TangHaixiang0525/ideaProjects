package com.starcor.sccms.api;

import com.starcor.core.domain.PlayInfo;
import com.starcor.core.epgapi.IntegerParams;
import com.starcor.core.epgapi.StringParams;
import com.starcor.core.epgapi.params.GetPlayInfoParams;
import com.starcor.core.parser.json.GetPlayInfoSAXParserJson;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsApiRequestVideoPlayTask extends ServerApiTask
{
  ISccmsApiRequestVideoPlayTaskListener lsr = null;
  String nns_begin = null;
  String nns_caps = null;
  String nns_category_id = null;
  String nns_day = null;
  String nns_huawei_cid = null;
  String nns_media_id = null;
  String nns_packed_id = null;
  String nns_quality = null;
  String nns_time_len = null;
  String nns_video_id = null;
  int nns_video_index = 0;
  int nns_video_type = 0;

  public SccmsApiRequestVideoPlayTask(String paramString1, int paramInt1, String paramString2, String paramString3, int paramInt2, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9)
  {
    this.nns_video_type = paramInt1;
    this.nns_video_id = paramString1;
    this.nns_packed_id = paramString2;
    this.nns_category_id = paramString3;
    this.nns_video_index = paramInt2;
    this.nns_media_id = paramString4;
    this.nns_quality = paramString5;
    this.nns_day = paramString6;
    this.nns_begin = paramString7;
    this.nns_time_len = paramString8;
    this.nns_huawei_cid = paramString9;
  }

  public SccmsApiRequestVideoPlayTask(String paramString1, int paramInt1, String paramString2, String paramString3, int paramInt2, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10)
  {
    this(paramString1, paramInt1, paramString2, paramString3, paramInt2, paramString4, paramString5, paramString6, paramString7, paramString8, paramString9);
    this.nns_caps = paramString10;
  }

  public String getTaskName()
  {
    return "N3_A_A_4";
  }

  public String getUrl()
  {
    GetPlayInfoParams localGetPlayInfoParams = new GetPlayInfoParams(this.nns_video_id, this.nns_video_type, this.nns_media_id, this.nns_quality, this.nns_day, this.nns_begin, this.nns_time_len, this.nns_huawei_cid, this.nns_caps);
    localGetPlayInfoParams.getVideo_index().setValue(this.nns_video_index);
    localGetPlayInfoParams.getPackage_id().setValue(this.nns_packed_id);
    localGetPlayInfoParams.getCategory_id().setValue(this.nns_category_id);
    localGetPlayInfoParams.setResultFormat(1);
    return webUrlFormatter.i().formatUrl(localGetPlayInfoParams.toString(), localGetPlayInfoParams.getApiName());
  }

  public void perform(SCResponse paramSCResponse)
  {
    GetPlayInfoSAXParserJson localGetPlayInfoSAXParserJson = new GetPlayInfoSAXParserJson();
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    try
    {
      PlayInfo localPlayInfo = (PlayInfo)localGetPlayInfoSAXParserJson.parser(paramSCResponse.getContents());
      Logger.i("SccmsApiRequestVideoPlayTask", " result:" + localPlayInfo.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localPlayInfo);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiRequestVideoPlayTaskListener paramISccmsApiRequestVideoPlayTaskListener)
  {
    this.lsr = paramISccmsApiRequestVideoPlayTaskListener;
  }

  public static abstract interface ISccmsApiRequestVideoPlayTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, PlayInfo paramPlayInfo);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiRequestVideoPlayTask
 * JD-Core Version:    0.6.2
 */