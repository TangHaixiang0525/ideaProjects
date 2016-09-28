package com.starcor.sccms.api;

import com.starcor.core.domain.FilmItem;
import com.starcor.core.epgapi.params.GetRelevantFilmsParams;
import com.starcor.core.parser.json.GetRelevantFilmsSAXParserJson;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsApiGetRelevantFilmsTask extends ServerApiTask
{
  ISccmsApiGetRelevantFilmsTaskListener lsr = null;
  String nns_category_id = null;
  String nns_media_assets_id = null;
  int nns_page_index = 0;
  int nns_page_size = 0;
  String nns_video_id = null;
  int nns_video_type = 0;

  public SccmsApiGetRelevantFilmsTask(String paramString1, int paramInt1, String paramString2, String paramString3, int paramInt2, int paramInt3)
  {
    this.nns_video_id = paramString1;
    this.nns_video_type = paramInt1;
    this.nns_media_assets_id = paramString2;
    this.nns_category_id = paramString3;
    this.nns_page_index = paramInt2;
    this.nns_page_size = paramInt3;
    setCacheLife(1800000L);
  }

  public String getTaskName()
  {
    return "N3_A_D_9";
  }

  public String getUrl()
  {
    GetRelevantFilmsParams localGetRelevantFilmsParams = new GetRelevantFilmsParams(this.nns_video_id, this.nns_video_type, this.nns_media_assets_id, this.nns_category_id, this.nns_page_index, this.nns_page_size);
    localGetRelevantFilmsParams.setResultFormat(1);
    return webUrlFormatter.i().formatUrl(localGetRelevantFilmsParams.toString(), localGetRelevantFilmsParams.getApiName());
  }

  public void perform(SCResponse paramSCResponse)
  {
    GetRelevantFilmsSAXParserJson localGetRelevantFilmsSAXParserJson = new GetRelevantFilmsSAXParserJson();
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    try
    {
      FilmItem localFilmItem = (FilmItem)localGetRelevantFilmsSAXParserJson.parser(paramSCResponse.getContents());
      if (localFilmItem.film_num < 1)
        Logger.e("SccmsApiGetRelevantFilmsTask", "返回错误的结果数");
      Logger.i("SccmsApiGetRelevantFilmsTask", " result:count" + localFilmItem.film_num);
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localFilmItem);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiGetRelevantFilmsTaskListener paramISccmsApiGetRelevantFilmsTaskListener)
  {
    this.lsr = paramISccmsApiGetRelevantFilmsTaskListener;
  }

  public static abstract interface ISccmsApiGetRelevantFilmsTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, FilmItem paramFilmItem);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiGetRelevantFilmsTask
 * JD-Core Version:    0.6.2
 */