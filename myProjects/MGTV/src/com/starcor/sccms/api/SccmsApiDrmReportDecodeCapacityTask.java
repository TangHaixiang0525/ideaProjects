package com.starcor.sccms.api;

import com.starcor.core.domain.DrmReportDecodeCapacityInfo;
import com.starcor.core.epgapi.params.DrmReportDecodeCapacityParams;
import com.starcor.core.parser.sax.DrmReportDecodeCapacityParser;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;
import java.io.ByteArrayInputStream;

public class SccmsApiDrmReportDecodeCapacityTask extends ServerApiTask
{
  ISccmsApiReportDecodeCapacityTaskListener lsr;

  public SccmsApiDrmReportDecodeCapacityTask(ISccmsApiReportDecodeCapacityTaskListener paramISccmsApiReportDecodeCapacityTaskListener)
  {
    this.lsr = paramISccmsApiReportDecodeCapacityTaskListener;
  }

  public String getTaskName()
  {
    return "N41_A_2";
  }

  public String getUrl()
  {
    DrmReportDecodeCapacityParams localDrmReportDecodeCapacityParams = new DrmReportDecodeCapacityParams();
    return webUrlFormatter.i().formatUrl(localDrmReportDecodeCapacityParams.toString(), localDrmReportDecodeCapacityParams.getApiName());
  }

  public void perform(SCResponse paramSCResponse)
  {
    if (ServerApiTools.isSCResponseError(paramSCResponse))
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
    try
    {
      DrmReportDecodeCapacityInfo localDrmReportDecodeCapacityInfo = new DrmReportDecodeCapacityParser().parser(new ByteArrayInputStream(paramSCResponse.getContents()));
      Logger.i("SccmsApiDrmReportDecodeCapacityTask", " result:" + localDrmReportDecodeCapacityInfo.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localDrmReportDecodeCapacityInfo);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public static abstract interface ISccmsApiReportDecodeCapacityTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, DrmReportDecodeCapacityInfo paramDrmReportDecodeCapacityInfo);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiDrmReportDecodeCapacityTask
 * JD-Core Version:    0.6.2
 */