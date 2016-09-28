package com.starcor.sccms.api;

import com.starcor.core.domain.LabelSortItem;
import com.starcor.core.epgapi.params.GetSortLabelItemParams;
import com.starcor.core.parser.json.GetSortLabelItemSAXParserJson;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;
import java.util.ArrayList;

public class SccmsApiGetFilterLabelByTypeTask extends ServerApiTask
{
  ISccmsApiGetFilterLabelByTypeTask lsr;
  private String nns_label_id;
  private int nns_max_size;
  private String nns_packet_id;
  private int nns_page_index;

  public SccmsApiGetFilterLabelByTypeTask(String paramString1, String paramString2, int paramInt1, int paramInt2)
  {
    this.nns_packet_id = paramString1;
    this.nns_label_id = paramString2;
    this.nns_page_index = paramInt1;
    this.nns_max_size = paramInt2;
    setCacheLife(1800000L);
  }

  public String getTaskName()
  {
    return "N3_A_G_2";
  }

  public String getUrl()
  {
    GetSortLabelItemParams localGetSortLabelItemParams = new GetSortLabelItemParams(this.nns_packet_id, this.nns_label_id, this.nns_page_index, this.nns_max_size);
    localGetSortLabelItemParams.setResultFormat(1);
    return webUrlFormatter.i().formatUrl(localGetSortLabelItemParams.toString(), localGetSortLabelItemParams.getApiName());
  }

  public void perform(SCResponse paramSCResponse)
  {
    GetSortLabelItemSAXParserJson localGetSortLabelItemSAXParserJson = new GetSortLabelItemSAXParserJson();
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    try
    {
      ArrayList localArrayList = (ArrayList)localGetSortLabelItemSAXParserJson.parser(paramSCResponse.getContents());
      Logger.i("SccmsApiGetFilterLabelByTypeTask", " result:" + localArrayList.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localArrayList);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiGetFilterLabelByTypeTask paramISccmsApiGetFilterLabelByTypeTask)
  {
    this.lsr = paramISccmsApiGetFilterLabelByTypeTask;
  }

  public static abstract interface ISccmsApiGetFilterLabelByTypeTask
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, ArrayList<LabelSortItem> paramArrayList);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiGetFilterLabelByTypeTask
 * JD-Core Version:    0.6.2
 */