package com.starcor.sccms.api;

import com.starcor.core.domain.LabelSort;
import com.starcor.core.epgapi.params.GetSortLabelParams;
import com.starcor.core.parser.json.GetSortLabelSAXParserJson;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;
import java.util.List;

public class SccmsApiGetVideoLabelTypeTask extends ServerApiTask
{
  ISccmsApiGetVideoLabelTypeTaskListener lsr;
  private String nns_packet_id;

  public SccmsApiGetVideoLabelTypeTask(String paramString)
  {
    this.nns_packet_id = paramString;
    setCacheLife(1800000L);
  }

  public String getTaskName()
  {
    return "N3_A_G_1";
  }

  public String getUrl()
  {
    GetSortLabelParams localGetSortLabelParams = new GetSortLabelParams(this.nns_packet_id);
    localGetSortLabelParams.setResultFormat(1);
    return webUrlFormatter.i().formatUrl(localGetSortLabelParams.toString(), localGetSortLabelParams.getApiName());
  }

  public void perform(SCResponse paramSCResponse)
  {
    GetSortLabelSAXParserJson localGetSortLabelSAXParserJson = new GetSortLabelSAXParserJson();
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    try
    {
      List localList = (List)localGetSortLabelSAXParserJson.parser(paramSCResponse.getContents());
      Logger.i("SccmsApiGetVideoLabelTypeTask", " result:" + localList.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localList);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiGetVideoLabelTypeTaskListener paramISccmsApiGetVideoLabelTypeTaskListener)
  {
    this.lsr = paramISccmsApiGetVideoLabelTypeTaskListener;
  }

  public static abstract interface ISccmsApiGetVideoLabelTypeTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, List<LabelSort> paramList);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiGetVideoLabelTypeTask
 * JD-Core Version:    0.6.2
 */