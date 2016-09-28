package com.starcor.sccms.api;

import com.starcor.core.epgapi.params.InitMainURLParams;
import com.starcor.core.parser.sax.InitMainURLSAXParser;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;
import java.io.ByteArrayInputStream;

public class SccmsApiGetEpgDataTask extends ServerApiTask
{
  private ISccmsApiGetEpgDataTaskListener lsr;

  public String getTaskName()
  {
    return "N1_A_1";
  }

  public String getUrl()
  {
    InitMainURLParams localInitMainURLParams = new InitMainURLParams();
    localInitMainURLParams.setResultFormat(0);
    return webUrlFormatter.i().formatUrl(localInitMainURLParams.toString(), localInitMainURLParams.getApiName());
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
    InitMainURLSAXParser localInitMainURLSAXParser = new InitMainURLSAXParser();
    try
    {
      Integer localInteger = (Integer)localInitMainURLSAXParser.parser(new ByteArrayInputStream(paramSCResponse.getContents()));
      Logger.i("SccmsApiGetEpgDataTask", " result:" + localInteger.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localInteger);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiGetEpgDataTaskListener paramISccmsApiGetEpgDataTaskListener)
  {
    this.lsr = paramISccmsApiGetEpgDataTaskListener;
  }

  public static abstract interface ISccmsApiGetEpgDataTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, Integer paramInteger);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiGetEpgDataTask
 * JD-Core Version:    0.6.2
 */