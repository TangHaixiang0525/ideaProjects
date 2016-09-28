package com.starcor.sccms.api;

import com.starcor.core.domain.LabelStarList;
import com.starcor.core.epgapi.params.GetLabelStarListParams;
import com.starcor.core.parser.sax.GetLabelStarListSAXParser;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;
import java.io.ByteArrayInputStream;

public class SccmsApiGetLabelStarListTask extends ServerApiTask
{
  ISccmsApiGetLabelStarListTaskListener lsr;

  public SccmsApiGetLabelStarListTask()
  {
    setCacheLife(1800000L);
  }

  public String getTaskName()
  {
    return "N39_A_16";
  }

  public String getUrl()
  {
    GetLabelStarListParams localGetLabelStarListParams = new GetLabelStarListParams();
    localGetLabelStarListParams.setResultFormat(0);
    return webUrlFormatter.i().formatUrl(localGetLabelStarListParams.toString(), localGetLabelStarListParams.getApiName());
  }

  public void perform(SCResponse paramSCResponse)
  {
    GetLabelStarListSAXParser localGetLabelStarListSAXParser = new GetLabelStarListSAXParser();
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    try
    {
      LabelStarList localLabelStarList = (LabelStarList)localGetLabelStarListSAXParser.parser(new ByteArrayInputStream(paramSCResponse.getContents()));
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localLabelStarList);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiGetLabelStarListTaskListener paramISccmsApiGetLabelStarListTaskListener)
  {
    this.lsr = paramISccmsApiGetLabelStarListTaskListener;
  }

  public static abstract interface ISccmsApiGetLabelStarListTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, LabelStarList paramLabelStarList);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiGetLabelStarListTask
 * JD-Core Version:    0.6.2
 */