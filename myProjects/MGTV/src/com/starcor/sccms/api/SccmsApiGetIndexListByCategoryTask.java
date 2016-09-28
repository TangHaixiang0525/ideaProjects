package com.starcor.sccms.api;

import com.starcor.core.domain.CategoryIndexList;
import com.starcor.core.epgapi.params.GetIndexListByCategoryParams;
import com.starcor.core.parser.sax.GetIndexListByCategorySAXParser;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;
import java.io.ByteArrayInputStream;

public class SccmsApiGetIndexListByCategoryTask extends ServerApiTask
{
  private ISccmsApiGetIndexListByCategoryListener lsr;
  private String nns_category_id;
  private String nns_media_asset_id;
  private int nns_page_index;
  private int nns_page_size;
  private String nns_sort_type;

  public SccmsApiGetIndexListByCategoryTask(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2)
  {
    this.nns_media_asset_id = paramString1;
    this.nns_category_id = paramString2;
    this.nns_sort_type = paramString3;
    this.nns_page_index = paramInt1;
    this.nns_page_size = paramInt2;
  }

  public String getTaskName()
  {
    return "N39_A_9";
  }

  public String getUrl()
  {
    GetIndexListByCategoryParams localGetIndexListByCategoryParams = new GetIndexListByCategoryParams(this.nns_media_asset_id, this.nns_category_id, this.nns_sort_type, this.nns_page_index, this.nns_page_size);
    localGetIndexListByCategoryParams.setResultFormat(0);
    return webUrlFormatter.i().formatUrl(localGetIndexListByCategoryParams.toString(), localGetIndexListByCategoryParams.getApiName());
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
    GetIndexListByCategorySAXParser localGetIndexListByCategorySAXParser = new GetIndexListByCategorySAXParser();
    try
    {
      CategoryIndexList localCategoryIndexList = (CategoryIndexList)localGetIndexListByCategorySAXParser.parser(new ByteArrayInputStream(paramSCResponse.getContents()));
      Logger.i("SccmsApiGetIndexListByCategoryTask", " result:" + localCategoryIndexList.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localCategoryIndexList);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiGetIndexListByCategoryListener paramISccmsApiGetIndexListByCategoryListener)
  {
    this.lsr = paramISccmsApiGetIndexListByCategoryListener;
  }

  public static abstract interface ISccmsApiGetIndexListByCategoryListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, CategoryIndexList paramCategoryIndexList);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiGetIndexListByCategoryTask
 * JD-Core Version:    0.6.2
 */