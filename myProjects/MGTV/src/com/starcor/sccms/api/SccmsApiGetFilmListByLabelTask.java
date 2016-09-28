package com.starcor.sccms.api;

import com.starcor.core.domain.FilmItem;
import com.starcor.core.epgapi.params.GetFilmListByLabelParams;
import com.starcor.core.parser.json.GetFilmItemSAXParserJson;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsApiGetFilmListByLabelTask extends ServerApiTask
{
  ISccmsApiGetFilmListByLabelTaskListener lsr = null;
  String nns_category_id = null;
  String nns_label_id = null;
  String nns_media_assets_id = null;
  int nns_page_index = 0;
  int nns_page_size = 0;
  String nns_sort_type = null;
  int nns_video_type = 0;

  public SccmsApiGetFilmListByLabelTask(String paramString1, int paramInt1, int paramInt2, int paramInt3, String paramString2)
  {
    this.nns_label_id = paramString1;
    this.nns_video_type = paramInt1;
    this.nns_page_index = paramInt2;
    this.nns_page_size = paramInt3;
    this.nns_sort_type = paramString2;
    setCacheLife(1800000L);
  }

  public String getTaskName()
  {
    return "N3_A_D_10";
  }

  public String getUrl()
  {
    GetFilmListByLabelParams localGetFilmListByLabelParams = new GetFilmListByLabelParams(this.nns_label_id, this.nns_video_type, this.nns_page_index, this.nns_page_size, this.nns_sort_type);
    localGetFilmListByLabelParams.setResultFormat(1);
    return webUrlFormatter.i().formatUrl(localGetFilmListByLabelParams.toString(), localGetFilmListByLabelParams.getApiName());
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
        Logger.e("SccmsApiGetFilmListByLabelTask", "返回错误的结果数");
      Logger.i("SccmsApiGetFilmListByLabelTask", " result:" + localFilmItem.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localFilmItem);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiGetFilmListByLabelTaskListener paramISccmsApiGetFilmListByLabelTaskListener)
  {
    this.lsr = paramISccmsApiGetFilmListByLabelTaskListener;
  }

  public static abstract interface ISccmsApiGetFilmListByLabelTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, FilmItem paramFilmItem);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiGetFilmListByLabelTask
 * JD-Core Version:    0.6.2
 */