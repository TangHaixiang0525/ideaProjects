package com.starcor.sccms.api;

import com.starcor.core.domain.SearchStruct;
import com.starcor.core.epgapi.params.SearchMediaAssetsItemParams;
import com.starcor.core.parser.json.SearchMediaAssetsItemSAXParserJson;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsApiSearchVideoByPinyinTask extends ServerApiTask
{
  ISccmsApiSearchVideoByPinyinTaskListener lsr = null;
  String nns_label_id = null;
  String nns_media_assets_category_id;
  String nns_media_assets_id = null;
  int nns_page_index = 0;
  int nns_page_size = 0;
  String nns_search_key = null;
  private String nns_search_type = null;
  SearchMediaAssetsItemParams para;

  public SccmsApiSearchVideoByPinyinTask(String paramString1, int paramInt1, int paramInt2, String paramString2, String paramString3)
  {
    this.nns_media_assets_category_id = paramString1;
    this.nns_search_key = paramString2;
    this.nns_label_id = paramString3;
    this.nns_page_index = paramInt1;
    this.nns_page_size = paramInt2;
    setCacheLife(1800000L);
    this.para = new SearchMediaAssetsItemParams(this.nns_media_assets_category_id, this.nns_page_index, this.nns_page_size, this.nns_search_key, this.nns_label_id);
  }

  public SccmsApiSearchVideoByPinyinTask(String paramString1, String paramString2, int paramInt1, int paramInt2)
  {
    this.nns_media_assets_id = paramString1;
    this.nns_search_key = paramString2;
    this.nns_page_index = paramInt1;
    this.nns_page_size = paramInt2;
    setCacheLife(1800000L);
    this.para = new SearchMediaAssetsItemParams(this.nns_media_assets_id, this.nns_search_key, this.nns_page_index, this.nns_page_size);
  }

  public SccmsApiSearchVideoByPinyinTask(String paramString1, String paramString2, String paramString3, String paramString4, int paramInt1, int paramInt2)
  {
    this.nns_media_assets_id = paramString1;
    this.nns_media_assets_category_id = paramString2;
    this.nns_search_key = paramString3;
    this.nns_page_index = paramInt1;
    this.nns_page_size = paramInt2;
    this.nns_search_type = paramString4;
    setCacheLife(1800000L);
    this.para = new SearchMediaAssetsItemParams(paramString2, this.nns_media_assets_id, this.nns_search_key, "", this.nns_search_type, this.nns_page_index, this.nns_page_size);
  }

  public String getTaskName()
  {
    return "N3_A_D_6";
  }

  public String getUrl()
  {
    this.para.setResultFormat(1);
    return webUrlFormatter.i().formatUrl(this.para.toString(), this.para.getApiName());
  }

  public void perform(SCResponse paramSCResponse)
  {
    SearchMediaAssetsItemSAXParserJson localSearchMediaAssetsItemSAXParserJson = new SearchMediaAssetsItemSAXParserJson();
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    try
    {
      SearchStruct localSearchStruct = (SearchStruct)localSearchMediaAssetsItemSAXParserJson.parser(paramSCResponse.getContents());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localSearchStruct);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiSearchVideoByPinyinTaskListener paramISccmsApiSearchVideoByPinyinTaskListener)
  {
    this.lsr = paramISccmsApiSearchVideoByPinyinTaskListener;
  }

  public static abstract interface ISccmsApiSearchVideoByPinyinTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, SearchStruct paramSearchStruct);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiSearchVideoByPinyinTask
 * JD-Core Version:    0.6.2
 */