package com.starcor.sccms.api;

import com.starcor.core.domain.SearchStruct;
import com.starcor.core.epgapi.params.SearchMediaAssetsItemV2Params;
import com.starcor.core.parser.json.SearchMediaAssetsItemSAXParserJson;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsApiSearchVideoWorkSetByPinyinTask extends ServerApiTask
{
  int label_id_search_type = 0;
  ISccmsApiSearchVideoWorkSetByPinyinTaskListener lsr = null;
  String nns_label_id = null;
  String nns_media_assets_category_id;
  String nns_media_assets_id = null;
  int nns_page_index = 0;
  int nns_page_size = 0;
  String nns_search_key = null;
  SearchMediaAssetsItemV2Params para;

  public SccmsApiSearchVideoWorkSetByPinyinTask(String paramString1, int paramInt1, int paramInt2, String paramString2, String paramString3, int paramInt3)
  {
    this.nns_media_assets_category_id = paramString1;
    this.nns_search_key = paramString2;
    this.nns_label_id = paramString3;
    this.nns_page_index = paramInt1;
    this.nns_page_size = paramInt2;
    this.label_id_search_type = paramInt3;
    setCacheLife(1800000L);
    this.para = new SearchMediaAssetsItemV2Params(this.nns_media_assets_category_id, this.nns_page_index, this.nns_page_size, this.nns_search_key, this.nns_label_id, this.label_id_search_type);
  }

  public String getTaskName()
  {
    return "N39_A_31";
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

  public void setListener(ISccmsApiSearchVideoWorkSetByPinyinTaskListener paramISccmsApiSearchVideoWorkSetByPinyinTaskListener)
  {
    this.lsr = paramISccmsApiSearchVideoWorkSetByPinyinTaskListener;
  }

  public static abstract interface ISccmsApiSearchVideoWorkSetByPinyinTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, SearchStruct paramSearchStruct);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiSearchVideoWorkSetByPinyinTask
 * JD-Core Version:    0.6.2
 */