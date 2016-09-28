package com.starcor.sccms.api;

import com.starcor.core.domain.CategoryPosterList;
import com.starcor.core.domain.MediaAssetsInfo;
import com.starcor.core.epgapi.params.GetCategoryAndVideoListParams;
import com.starcor.core.parser.json.CategoryPosterListParserJson;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;
import java.util.LinkedHashMap;

public class SccmsApiGetStaticCategoryItemListTask extends ServerApiTask
{
  private static final String TAG = SccmsApiGetStaticCategoryItemListTask.class.getSimpleName();
  ISccmsApiGetStaticCategoryItemListTaskListener lsr;
  private int[] nns_category_type;
  private int nns_max_category;
  private String nns_packet_id;
  private int nns_special_size;
  private int nns_video_size;

  public SccmsApiGetStaticCategoryItemListTask(String paramString, int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfInt)
  {
    this.nns_packet_id = paramString;
    this.nns_video_size = paramInt1;
    this.nns_special_size = paramInt2;
    this.nns_max_category = paramInt3;
    this.nns_category_type = paramArrayOfInt;
    setCacheLife(9000000L);
  }

  public String getTaskName()
  {
    return "N39_A_22";
  }

  public String getUrl()
  {
    GetCategoryAndVideoListParams localGetCategoryAndVideoListParams = new GetCategoryAndVideoListParams(this.nns_packet_id, this.nns_video_size, this.nns_special_size, this.nns_max_category, this.nns_category_type);
    localGetCategoryAndVideoListParams.setResultFormat(1);
    return webUrlFormatter.i().formatUrl(localGetCategoryAndVideoListParams.toString(), localGetCategoryAndVideoListParams.getApiName());
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
      CategoryPosterListParserJson localCategoryPosterListParserJson = new CategoryPosterListParserJson();
      LinkedHashMap localLinkedHashMap = (LinkedHashMap)localCategoryPosterListParserJson.parserStatic(paramSCResponse.getContents());
      MediaAssetsInfo localMediaAssetsInfo = localCategoryPosterListParserJson.getMediaAssetInfo();
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localLinkedHashMap, localMediaAssetsInfo);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiGetStaticCategoryItemListTaskListener paramISccmsApiGetStaticCategoryItemListTaskListener)
  {
    this.lsr = paramISccmsApiGetStaticCategoryItemListTaskListener;
  }

  public static abstract interface ISccmsApiGetStaticCategoryItemListTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, LinkedHashMap<String, CategoryPosterList> paramLinkedHashMap, MediaAssetsInfo paramMediaAssetsInfo);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiGetStaticCategoryItemListTask
 * JD-Core Version:    0.6.2
 */