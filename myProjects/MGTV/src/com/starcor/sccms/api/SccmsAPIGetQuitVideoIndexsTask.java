package com.starcor.sccms.api;

import com.starcor.core.domain.QuitVideoIndexsParams;
import com.starcor.core.epgapi.params.GetQuitVideoIndexsParam;
import com.starcor.core.parser.sax.GetQuitVideoIndexsParamsSAXParser;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsAPIGetQuitVideoIndexsTask extends ServerApiTask
{
  ISccmsAPIGetQuitVideoIndexsTaskListener lsr = null;
  String nns_category_id;
  String nns_media_assets_id;
  int nns_page_size;
  String nns_video_id;
  int nns_video_type;

  public SccmsAPIGetQuitVideoIndexsTask(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2)
  {
    this.nns_media_assets_id = paramString1;
    this.nns_category_id = paramString2;
    this.nns_video_id = paramString3;
    this.nns_video_type = paramInt1;
    this.nns_page_size = paramInt2;
  }

  public String getTaskName()
  {
    return "N39_A_35";
  }

  public String getUrl()
  {
    GetQuitVideoIndexsParam localGetQuitVideoIndexsParam = new GetQuitVideoIndexsParam(this.nns_media_assets_id, this.nns_category_id, this.nns_video_id, this.nns_video_type, this.nns_page_size);
    localGetQuitVideoIndexsParam.setResultFormat(0);
    return webUrlFormatter.i().formatUrl(localGetQuitVideoIndexsParam.toString(), localGetQuitVideoIndexsParam.getApiName());
  }

  public void perform(SCResponse paramSCResponse)
  {
    GetQuitVideoIndexsParamsSAXParser localGetQuitVideoIndexsParamsSAXParser = new GetQuitVideoIndexsParamsSAXParser();
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    try
    {
      QuitVideoIndexsParams localQuitVideoIndexsParams = (QuitVideoIndexsParams)localGetQuitVideoIndexsParamsSAXParser.parser(paramSCResponse.getContents());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localQuitVideoIndexsParams);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsAPIGetQuitVideoIndexsTaskListener paramISccmsAPIGetQuitVideoIndexsTaskListener)
  {
    this.lsr = paramISccmsAPIGetQuitVideoIndexsTaskListener;
  }

  public static abstract interface ISccmsAPIGetQuitVideoIndexsTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, QuitVideoIndexsParams paramQuitVideoIndexsParams);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsAPIGetQuitVideoIndexsTask
 * JD-Core Version:    0.6.2
 */