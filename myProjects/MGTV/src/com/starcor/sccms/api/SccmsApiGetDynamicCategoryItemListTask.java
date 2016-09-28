package com.starcor.sccms.api;

import com.starcor.core.domain.CategoryPosterList;
import com.starcor.core.epgapi.params.GetDynamicCategoryItemListParams;
import com.starcor.core.parser.json.CategoryPosterListParserJson;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;
import java.util.LinkedHashMap;

public class SccmsApiGetDynamicCategoryItemListTask extends ServerApiTask
{
  ISccmsApiGetDynamicCategoryItemListTaskListener lsr;
  private int[] nns_category;
  private String package_id;

  public SccmsApiGetDynamicCategoryItemListTask(String paramString, int[] paramArrayOfInt)
  {
    this.package_id = paramString;
    this.nns_category = paramArrayOfInt;
  }

  public String getTaskName()
  {
    return "N39_A_26";
  }

  public String getUrl()
  {
    GetDynamicCategoryItemListParams localGetDynamicCategoryItemListParams = new GetDynamicCategoryItemListParams(this.package_id, this.nns_category);
    localGetDynamicCategoryItemListParams.setResultFormat(1);
    return webUrlFormatter.i().formatUrl(localGetDynamicCategoryItemListParams.toString(), localGetDynamicCategoryItemListParams.getApiName());
  }

  public void perform(SCResponse paramSCResponse)
  {
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    try
    {
      LinkedHashMap localLinkedHashMap = (LinkedHashMap)new CategoryPosterListParserJson().parserDynamic(paramSCResponse.getContents());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localLinkedHashMap);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiGetDynamicCategoryItemListTaskListener paramISccmsApiGetDynamicCategoryItemListTaskListener)
  {
    this.lsr = paramISccmsApiGetDynamicCategoryItemListTaskListener;
  }

  public static abstract interface ISccmsApiGetDynamicCategoryItemListTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, LinkedHashMap<String, CategoryPosterList> paramLinkedHashMap);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiGetDynamicCategoryItemListTask
 * JD-Core Version:    0.6.2
 */