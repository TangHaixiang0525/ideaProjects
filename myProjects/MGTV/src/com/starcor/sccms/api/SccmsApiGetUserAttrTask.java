package com.starcor.sccms.api;

import com.starcor.core.domain.UserAttr;
import com.starcor.core.epgapi.params.GetUserAttrParams;
import com.starcor.core.parser.sax.GetUserAttrSAXParser;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;
import java.io.ByteArrayInputStream;

public class SccmsApiGetUserAttrTask extends ServerApiTask
{
  ISccmsApiGetUserAttrTaskListener lsr;

  public SccmsApiGetUserAttrTask()
  {
    setCacheLife(1800000L);
  }

  public String getTaskName()
  {
    return "N40_F_1";
  }

  public String getUrl()
  {
    GetUserAttrParams localGetUserAttrParams = new GetUserAttrParams();
    localGetUserAttrParams.setResultFormat(0);
    return webUrlFormatter.i().formatUrl(localGetUserAttrParams.toString(), localGetUserAttrParams.getApiName());
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
      Logger.i("SccmsApiGetUserAttrTask", " result:" + localUserAttr.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localUserAttr);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiGetUserAttrTaskListener paramISccmsApiGetUserAttrTaskListener)
  {
    this.lsr = paramISccmsApiGetUserAttrTaskListener;
  }

  public static abstract interface ISccmsApiGetUserAttrTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, UserAttr paramUserAttr);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiGetUserAttrTask
 * JD-Core Version:    0.6.2
 */