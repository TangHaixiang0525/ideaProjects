package com.starcor.sccms.api;

import android.text.TextUtils;
import com.starcor.core.domain.PlayInfoV2;
import com.starcor.core.epgapi.IntegerParams;
import com.starcor.core.epgapi.StringParams;
import com.starcor.core.epgapi.params.GetPlayInfoV2Params;
import com.starcor.core.parser.sax.GetPlayInfoV2SAXParser;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;
import java.io.ByteArrayInputStream;

public class SccmsApiRequestVideoPlayV2Task extends ServerApiTask
{
  ISccmsApiRequestVideoPlayV2TaskListener lsr = null;
  String nns_begin = null;
  String nns_caps = null;
  String nns_category_id = null;
  private String nns_clt_data = "";
  String nns_day = null;
  String nns_huawei_cid = null;
  String nns_media_id = null;
  String nns_packed_id = null;
  private String nns_preview = "";
  String nns_quality = null;
  private String nns_retry_time = "";
  String nns_time_len = null;
  String nns_video_id = null;
  int nns_video_index = 0;
  private String nns_video_index_id = null;
  private String nns_video_preview_type = null;
  int nns_video_type = 0;

  public SccmsApiRequestVideoPlayV2Task(String paramString1, int paramInt1, String paramString2, String paramString3, int paramInt2, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12)
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
    this.nns_caps = paramString10;
    if (!TextUtils.isEmpty(paramString11))
      this.nns_clt_data = (this.nns_clt_data + "svrip=" + paramString11);
    this.nns_retry_time = paramString12;
  }

  public SccmsApiRequestVideoPlayV2Task(String paramString1, int paramInt1, String paramString2, String paramString3, String paramString4, int paramInt2, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12)
  {
    this.nns_video_type = paramInt1;
    this.nns_video_id = paramString1;
    this.nns_packed_id = paramString3;
    this.nns_category_id = paramString4;
    this.nns_video_index = paramInt2;
    this.nns_media_id = paramString5;
    this.nns_quality = paramString6;
    this.nns_day = paramString7;
    this.nns_begin = paramString8;
    this.nns_time_len = paramString9;
    this.nns_huawei_cid = paramString10;
    this.nns_preview = paramString2;
    if (!TextUtils.isEmpty(paramString11))
      this.nns_clt_data = (this.nns_clt_data + "svrip=" + paramString11);
    this.nns_retry_time = paramString12;
  }

  public SccmsApiRequestVideoPlayV2Task(String paramString1, int paramInt1, String paramString2, String paramString3, String paramString4, int paramInt2, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, String paramString13, String paramString14)
  {
    this.nns_video_type = paramInt1;
    this.nns_video_id = paramString1;
    this.nns_packed_id = paramString3;
    this.nns_category_id = paramString4;
    this.nns_video_index = paramInt2;
    this.nns_media_id = paramString5;
    this.nns_quality = paramString6;
    this.nns_day = paramString7;
    this.nns_begin = paramString8;
    this.nns_time_len = paramString9;
    this.nns_huawei_cid = paramString10;
    this.nns_preview = paramString2;
    if (!TextUtils.isEmpty(paramString11))
      this.nns_clt_data = (this.nns_clt_data + "svrip=" + paramString11);
    this.nns_retry_time = paramString12;
    this.nns_video_preview_type = paramString13;
    this.nns_video_index_id = paramString14;
  }

  public String getTaskName()
  {
    return "N50_A_2";
  }

  public String getUrl()
  {
    GetPlayInfoV2Params localGetPlayInfoV2Params = new GetPlayInfoV2Params(this.nns_video_id, this.nns_video_type, this.nns_preview, this.nns_media_id, this.nns_quality, this.nns_day, this.nns_begin, this.nns_time_len, this.nns_huawei_cid, this.nns_caps);
    localGetPlayInfoV2Params.getVideo_index().setValue(this.nns_video_index);
    localGetPlayInfoV2Params.getPackage_id().setValue(this.nns_packed_id);
    localGetPlayInfoV2Params.getCategory_id().setValue(this.nns_category_id);
    if (!TextUtils.isEmpty(this.nns_clt_data))
      localGetPlayInfoV2Params.getClt_data().setValue(this.nns_clt_data);
    if (!TextUtils.isEmpty(this.nns_retry_time))
      localGetPlayInfoV2Params.getRetry_time().setValue(this.nns_retry_time);
    if (!TextUtils.isEmpty(this.nns_video_preview_type))
      localGetPlayInfoV2Params.getNns_video_preview_type().setValue(this.nns_video_preview_type);
    if (!TextUtils.isEmpty(this.nns_video_index_id))
      localGetPlayInfoV2Params.getNns_video_index_id().setValue(this.nns_video_index_id);
    localGetPlayInfoV2Params.setResultFormat(0);
    String str = webUrlFormatter.i().formatUrl(localGetPlayInfoV2Params.toString(), localGetPlayInfoV2Params.getApiName());
    com.starcor.report.ReportState.url = str;
    return str;
  }

  public void perform(SCResponse paramSCResponse)
  {
    GetPlayInfoV2SAXParser localGetPlayInfoV2SAXParser = new GetPlayInfoV2SAXParser();
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    try
    {
      PlayInfoV2 localPlayInfoV2 = (PlayInfoV2)localGetPlayInfoV2SAXParser.parser(new ByteArrayInputStream(paramSCResponse.getContents()));
      if (localPlayInfoV2 != null)
      {
        Logger.i("SccmsApiRequestVideoPlayV2Task", "SccmsApiRequestVideoPlayV2Task result:" + localPlayInfoV2.toString());
        this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localPlayInfoV2);
        return;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
      return;
    }
    this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, "result null"));
  }

  public void setListener(ISccmsApiRequestVideoPlayV2TaskListener paramISccmsApiRequestVideoPlayV2TaskListener)
  {
    this.lsr = paramISccmsApiRequestVideoPlayV2TaskListener;
  }

  public static abstract interface ISccmsApiRequestVideoPlayV2TaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, PlayInfoV2 paramPlayInfoV2);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiRequestVideoPlayV2Task
 * JD-Core Version:    0.6.2
 */