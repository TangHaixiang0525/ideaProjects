package com.starcor.sccms.api;

import com.starcor.config.DeviceInfo;
import com.starcor.core.domain.Version;
import com.starcor.core.epgapi.params.CheckVersionUpdataParams;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.parser.sax.CheckVersionUpdataParser;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;
import java.io.ByteArrayInputStream;

public class SccmsApiCheckUpdateTask extends ServerApiTask
{
  private ISccmsApiCheckUpdateTaskListener lsr;

  public String getTaskName()
  {
    return "N22_A_1";
  }

  public String getUrl()
  {
    CheckVersionUpdataParams localCheckVersionUpdataParams = new CheckVersionUpdataParams(DeviceInfo.getMGTVVersion(), DeviceInfo.getMac(), "", "", GlobalLogic.getInstance().getUserId());
    localCheckVersionUpdataParams.setResultFormat(0);
    return webUrlFormatter.i().formatUrl(localCheckVersionUpdataParams.toString(), localCheckVersionUpdataParams.getApiName());
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
    CheckVersionUpdataParser localCheckVersionUpdataParser = new CheckVersionUpdataParser();
    try
    {
      Version localVersion = (Version)localCheckVersionUpdataParser.parser(new ByteArrayInputStream(paramSCResponse.getContents()));
      Logger.i("SccmsApiCheckUpdateTask", " result:" + localVersion.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localVersion);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiCheckUpdateTaskListener paramISccmsApiCheckUpdateTaskListener)
  {
    this.lsr = paramISccmsApiCheckUpdateTaskListener;
  }

  public static abstract interface ISccmsApiCheckUpdateTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, Version paramVersion);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiCheckUpdateTask
 * JD-Core Version:    0.6.2
 */