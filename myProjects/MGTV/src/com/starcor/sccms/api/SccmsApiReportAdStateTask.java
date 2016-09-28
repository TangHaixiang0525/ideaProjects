package com.starcor.sccms.api;

import com.starcor.core.epgapi.params.ReportAdStateParams;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCachedTask;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsApiReportAdStateTask extends ServerApiCachedTask
{
  private String adEvent;
  private String adId;
  private String adPosId;
  ISccmsApiReportAdStateTaskListener lsr;
  private String userId;

  public SccmsApiReportAdStateTask(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    this.adPosId = paramString1;
    this.adId = paramString2;
    this.adEvent = paramString3;
    this.userId = paramString4;
  }

  public String getTaskName()
  {
    return "N7_A_4";
  }

  public String getUrl()
  {
    ReportAdStateParams localReportAdStateParams = new ReportAdStateParams(this.adPosId, this.adId, this.adEvent, this.userId);
    localReportAdStateParams.setResultFormat(0);
    return webUrlFormatter.i().formatUrl(localReportAdStateParams.toString(), localReportAdStateParams.getApiName());
  }

  public void perform(SCResponse paramSCResponse)
  {
    if (this.lsr == null)
      return;
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), null);
  }

  public void setListener(ISccmsApiReportAdStateTaskListener paramISccmsApiReportAdStateTaskListener)
  {
    this.lsr = paramISccmsApiReportAdStateTaskListener;
  }

  public static abstract interface ISccmsApiReportAdStateTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, String paramString);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiReportAdStateTask
 * JD-Core Version:    0.6.2
 */