package com.starcor.sccms.api;

import com.starcor.core.domain.FilmItem;
import com.starcor.core.epgapi.StringParams;
import com.starcor.core.epgapi.params.GetFilmLableListParams;
import com.starcor.core.parser.json.GetFilmItemSAXParserJson;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsApiGetVideoListByLabelTask extends ServerApiTask
{
  ISccmsApiGetVideoListByLabelTaskListener lsr;
  private String nns_category_id;
  private String nns_label_id;
  private String nns_packet_id;
  private int nns_page_index;
  private int nns_page_size;
  private String nns_sort_type;

  public SccmsApiGetVideoListByLabelTask(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2, String paramString4)
  {
    this.nns_packet_id = paramString1;
    this.nns_category_id = paramString2;
    this.nns_sort_type = paramString3;
    this.nns_page_index = paramInt1;
    this.nns_page_size = paramInt2;
    this.nns_label_id = paramString4;
    setCacheLife(1800000L);
  }

  public String getTaskName()
  {
    return "N3_A_D_7";
  }

  public String getUrl()
  {
    GetFilmLableListParams localGetFilmLableListParams = new GetFilmLableListParams(this.nns_packet_id, this.nns_category_id, this.nns_page_index, this.nns_page_size, this.nns_label_id);
    localGetFilmLableListParams.getSort_type().setValue(this.nns_sort_type);
    localGetFilmLableListParams.setResultFormat(1);
    return webUrlFormatter.i().formatUrl(localGetFilmLableListParams.toString(), localGetFilmLableListParams.getApiName());
  }

  public void perform(SCResponse paramSCResponse)
  {
    GetFilmItemSAXParserJson localGetFilmItemSAXParserJson = new GetFilmItemSAXParserJson();
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    try
    {
      FilmItem localFilmItem = (FilmItem)localGetFilmItemSAXParserJson.parser(paramSCResponse.getContents());
      if (localFilmItem.film_num < 1)
        Logger.e("SccmsApiGetVideoListByLabelTask", "返回错误的结果数");
      Logger.i("SccmsApiGetVideoListByLabelTask", " result:" + localFilmItem.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localFilmItem);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiGetVideoListByLabelTaskListener paramISccmsApiGetVideoListByLabelTaskListener)
  {
    this.lsr = paramISccmsApiGetVideoListByLabelTaskListener;
  }

  public static abstract interface ISccmsApiGetVideoListByLabelTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, FilmItem paramFilmItem);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiGetVideoListByLabelTask
 * JD-Core Version:    0.6.2
 */