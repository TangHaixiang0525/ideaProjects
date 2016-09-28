package com.starcor.sccms.api;

import com.starcor.core.epgapi.params.ReportSccmsSpeedTestResultParams;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class SccmsApiReportSpeedTestResultTask extends ServerApiTask
{
  ISccmsApiReportSpeedTestResultTaskListener lsr = null;
  private String nns_data;

  public SccmsApiReportSpeedTestResultTask(String paramString)
  {
    this.nns_data = paramString;
  }

  public List<NameValuePair> getPostForm()
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(new BasicNameValuePair("nns_data", this.nns_data));
    Logger.i("SccmsApiReportSpeedTestResultTask", "nns_data:" + this.nns_data);
    return localArrayList;
  }

  public int getRequestMethod()
  {
    return 1;
  }

  public String getTaskName()
  {
    return "N26_A_2";
  }

  public String getUrl()
  {
    ReportSccmsSpeedTestResultParams localReportSccmsSpeedTestResultParams = new ReportSccmsSpeedTestResultParams();
    localReportSccmsSpeedTestResultParams.setResultFormat(1);
    return webUrlFormatter.i().formatUrl(localReportSccmsSpeedTestResultParams.toString(), localReportSccmsSpeedTestResultParams.getApiName());
  }

  public void perform(SCResponse paramSCResponse)
  {
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      Logger.i("SccmsApiReportSpeedTestResultTask", "ResponseError getHttpCode:" + paramSCResponse.getHttpCode() + ", reason:" + paramSCResponse.getHttpReason() + ", runReason:" + paramSCResponse.getRunReason());
      return;
    }
    this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse));
  }

  public void setListener(ISccmsApiReportSpeedTestResultTaskListener paramISccmsApiReportSpeedTestResultTaskListener)
  {
    this.lsr = paramISccmsApiReportSpeedTestResultTaskListener;
  }

  public static abstract interface ISccmsApiReportSpeedTestResultTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiReportSpeedTestResultTask
 * JD-Core Version:    0.6.2
 */