package com.starcor.sccms.api;

import com.starcor.core.domain.PreviewList;
import com.starcor.core.epgapi.params.GetPreviewListParams;
import com.starcor.core.parser.sax.GetPreviewListSAXParser;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;
import java.io.ByteArrayInputStream;

public class SccmsApiGetPreviewListTask extends ServerApiTask
{
  ISccmsApiGetPreviewListTaskListener lsr;
  private int pageIndex;
  private int pageSize;
  private String videoId;

  public SccmsApiGetPreviewListTask(ISccmsApiGetPreviewListTaskListener paramISccmsApiGetPreviewListTaskListener, String paramString, int paramInt1, int paramInt2)
  {
    this.lsr = paramISccmsApiGetPreviewListTaskListener;
    this.videoId = paramString;
    this.pageIndex = paramInt1;
    this.pageSize = paramInt2;
    setCacheLife(1800000L);
  }

  public String getTaskName()
  {
    return "N39_A_21";
  }

  public String getUrl()
  {
    GetPreviewListParams localGetPreviewListParams = new GetPreviewListParams(this.videoId, this.pageIndex, this.pageSize);
    localGetPreviewListParams.setResultFormat(0);
    return webUrlFormatter.i().formatUrl(localGetPreviewListParams.toString(), localGetPreviewListParams.getApiName());
  }

  public void perform(SCResponse paramSCResponse)
  {
    GetPreviewListSAXParser localGetPreviewListSAXParser = new GetPreviewListSAXParser();
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    try
    {
      PreviewList localPreviewList = (PreviewList)localGetPreviewListSAXParser.parser(new ByteArrayInputStream(paramSCResponse.getContents()));
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localPreviewList);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public static abstract interface ISccmsApiGetPreviewListTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, PreviewList paramPreviewList);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiGetPreviewListTask
 * JD-Core Version:    0.6.2
 */