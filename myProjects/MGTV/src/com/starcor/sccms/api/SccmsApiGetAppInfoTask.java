package com.starcor.sccms.api;

import com.starcor.core.domain.AppDetail;
import com.starcor.core.epgapi.params.GetAppInfoParams;
import com.starcor.core.parser.sax.GetAppInfoSAXParser;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;
import java.io.ByteArrayInputStream;

public class SccmsApiGetAppInfoTask extends ServerApiTask
{
  private ISccmsApiGetAppInfoTaskListener lsr;
  private String nns_app_id;
  private String nns_version_id;

  public SccmsApiGetAppInfoTask(String paramString1, String paramString2)
  {
    this.nns_version_id = paramString1;
    this.nns_app_id = paramString2;
  }

  public String getTaskName()
  {
    return "N650_A_2";
  }

  public String getUrl()
  {
    GetAppInfoParams localGetAppInfoParams = new GetAppInfoParams(this.nns_version_id, this.nns_app_id);
    localGetAppInfoParams.setResultFormat(0);
    return webUrlFormatter.i().formatUrl(localGetAppInfoParams.toString(), localGetAppInfoParams.getApiName());
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
    GetAppInfoSAXParser localGetAppInfoSAXParser = new GetAppInfoSAXParser();
    try
    {
      AppDetail localAppDetail = (AppDetail)localGetAppInfoSAXParser.parser(new ByteArrayInputStream(paramSCResponse.getContents()));
      Logger.i("SccmsApiGetAppInfoTask", " result:" + localAppDetail.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localAppDetail);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiGetAppInfoTaskListener paramISccmsApiGetAppInfoTaskListener)
  {
    this.lsr = paramISccmsApiGetAppInfoTaskListener;
  }

  public static abstract interface ISccmsApiGetAppInfoTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, AppDetail paramAppDetail);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiGetAppInfoTask
 * JD-Core Version:    0.6.2
 */