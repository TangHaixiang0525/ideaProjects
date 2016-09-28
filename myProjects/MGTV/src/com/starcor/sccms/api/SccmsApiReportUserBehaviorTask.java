package com.starcor.sccms.api;

import com.starcor.core.domain.UserAttr;
import com.starcor.core.epgapi.params.ReportUserBehaviorParams;
import com.starcor.core.parser.sax.GetUserAttrSAXParser;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;
import java.io.ByteArrayInputStream;

public class SccmsApiReportUserBehaviorTask extends ServerApiTask
{
  private String begin_time;
  private String category_id;
  ISccmsApiReportUserBehaviorListener lsr;
  private String media_asset_id;
  private String time_len;
  private String video_id;
  private int video_type;
  private int view_type;

  public SccmsApiReportUserBehaviorTask(int paramInt1, int paramInt2, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, ISccmsApiReportUserBehaviorListener paramISccmsApiReportUserBehaviorListener)
  {
    this.video_type = paramInt1;
    this.view_type = paramInt2;
    this.video_id = paramString1;
    this.begin_time = paramString2;
    this.time_len = paramString3;
    this.media_asset_id = paramString4;
    this.category_id = paramString5;
    this.lsr = paramISccmsApiReportUserBehaviorListener;
    setCacheLife(1800000L);
  }

  public String getTaskName()
  {
    return "N40_F_2";
  }

  public String getUrl()
  {
    ReportUserBehaviorParams localReportUserBehaviorParams = new ReportUserBehaviorParams(this.video_type, this.view_type, this.video_id, this.begin_time, this.time_len, this.media_asset_id, this.category_id);
    localReportUserBehaviorParams.setResultFormat(0);
    return webUrlFormatter.i().formatUrl(localReportUserBehaviorParams.toString(), localReportUserBehaviorParams.getApiName());
  }

  public void perform(SCResponse paramSCResponse)
  {
    GetUserAttrSAXParser localGetUserAttrSAXParser = new GetUserAttrSAXParser();
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    try
    {
      UserAttr localUserAttr = (UserAttr)localGetUserAttrSAXParser.parser(new ByteArrayInputStream(paramSCResponse.getContents()));
      Logger.i("SccmsApiReportUserBehaviorTask", " result:" + localUserAttr.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localUserAttr);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public static abstract interface ISccmsApiReportUserBehaviorListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, UserAttr paramUserAttr);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiReportUserBehaviorTask
 * JD-Core Version:    0.6.2
 */