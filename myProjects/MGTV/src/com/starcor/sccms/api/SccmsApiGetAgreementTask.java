package com.starcor.sccms.api;

import com.starcor.core.domain.Agreements;
import com.starcor.core.epgapi.params.GetAgreementParams;
import com.starcor.core.parser.sax.GetAgreementSAXParser;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;
import java.io.ByteArrayInputStream;

public class SccmsApiGetAgreementTask extends ServerApiTask
{
  private ISccmsApiGetAgreementTaskListener lsr;

  public String getTaskName()
  {
    return "N39_A_13";
  }

  public String getUrl()
  {
    GetAgreementParams localGetAgreementParams = new GetAgreementParams();
    localGetAgreementParams.setResultFormat(0);
    return webUrlFormatter.i().formatUrl(localGetAgreementParams.toString(), localGetAgreementParams.getApiName());
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
    GetAgreementSAXParser localGetAgreementSAXParser = new GetAgreementSAXParser();
    try
    {
      Agreements localAgreements = (Agreements)localGetAgreementSAXParser.parser(new ByteArrayInputStream(paramSCResponse.getContents()));
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localAgreements);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiGetAgreementTaskListener paramISccmsApiGetAgreementTaskListener)
  {
    this.lsr = paramISccmsApiGetAgreementTaskListener;
  }

  public static abstract interface ISccmsApiGetAgreementTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, Agreements paramAgreements);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiGetAgreementTask
 * JD-Core Version:    0.6.2
 */