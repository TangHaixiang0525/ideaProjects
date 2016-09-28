package com.starcor.sccms.api;

import com.starcor.core.domain.SearchActorStarList;
import com.starcor.core.epgapi.params.SearchActorStarListParams;
import com.starcor.core.parser.sax.SearchActorStarListSAXParser;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;
import java.io.ByteArrayInputStream;

public class SccmsApiSearchActorStarListTask extends ServerApiTask
{
  ISccmsApiSearchActorStarListTaskListener lsr;
  private String nns_label_id;
  private int nns_page_index;
  private int nns_page_size;
  private String nns_search_key;

  public SccmsApiSearchActorStarListTask(int paramInt1, int paramInt2, String paramString1, String paramString2)
  {
    this.nns_page_index = paramInt1;
    this.nns_page_size = paramInt2;
    this.nns_label_id = paramString1;
    this.nns_search_key = paramString2;
    setCacheLife(1800000L);
  }

  public String getTaskName()
  {
    return "N39_A_14";
  }

  public String getUrl()
  {
    SearchActorStarListParams localSearchActorStarListParams = new SearchActorStarListParams(this.nns_page_index, this.nns_page_size, this.nns_search_key, this.nns_label_id);
    localSearchActorStarListParams.setResultFormat(0);
    return webUrlFormatter.i().formatUrl(localSearchActorStarListParams.toString(), localSearchActorStarListParams.getApiName());
  }

  public void perform(SCResponse paramSCResponse)
  {
    SearchActorStarListSAXParser localSearchActorStarListSAXParser = new SearchActorStarListSAXParser();
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    try
    {
      SearchActorStarList localSearchActorStarList = (SearchActorStarList)localSearchActorStarListSAXParser.parser(new ByteArrayInputStream(paramSCResponse.getContents()));
      Logger.i("SccmsApiGetStarGuestListByLabelTask", " result:" + localSearchActorStarList.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localSearchActorStarList);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiSearchActorStarListTaskListener paramISccmsApiSearchActorStarListTaskListener)
  {
    this.lsr = paramISccmsApiSearchActorStarListTaskListener;
  }

  public static abstract interface ISccmsApiSearchActorStarListTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, SearchActorStarList paramSearchActorStarList);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiSearchActorStarListTask
 * JD-Core Version:    0.6.2
 */