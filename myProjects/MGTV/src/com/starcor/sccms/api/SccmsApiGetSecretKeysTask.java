package com.starcor.sccms.api;

import com.starcor.core.domain.GetSecretKeysInfo;
import com.starcor.core.epgapi.params.GetSecretKeysParams;
import com.starcor.core.parser.sax.GetSecretKeysSAXParser;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;
import java.io.ByteArrayInputStream;

public class SccmsApiGetSecretKeysTask extends ServerApiTask
{
  private ISccmsApiGetSecretKeysTaskListener lsr;

  public String getTaskName()
  {
    return "N41_A_1";
  }

  public String getUrl()
  {
    GetSecretKeysParams localGetSecretKeysParams = new GetSecretKeysParams();
    localGetSecretKeysParams.setResultFormat(0);
    return webUrlFormatter.i().formatUrl(localGetSecretKeysParams.toString(), localGetSecretKeysParams.getApiName());
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
    try
    {
      GetSecretKeysInfo localGetSecretKeysInfo = new GetSecretKeysSAXParser().parser(new ByteArrayInputStream(paramSCResponse.getContents()));
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localGetSecretKeysInfo);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiGetSecretKeysTaskListener paramISccmsApiGetSecretKeysTaskListener)
  {
    this.lsr = paramISccmsApiGetSecretKeysTaskListener;
  }

  public static abstract interface ISccmsApiGetSecretKeysTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, GetSecretKeysInfo paramGetSecretKeysInfo);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiGetSecretKeysTask
 * JD-Core Version:    0.6.2
 */