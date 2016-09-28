package com.starcor.sccms.api;

import com.starcor.core.domain.FilmItem;
import com.starcor.core.epgapi.StringParams;
import com.starcor.core.epgapi.params.GetFilmItemParams;
import com.starcor.core.parser.json.GetFilmItemSAXParserJson;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsApiGetVideoListTask extends ServerApiTask
{
  ISccmsApiGetVideoListTaskListener lsr;
  private String nns_category_id;
  private String nns_packet_id;
  private int nns_page_index;
  private int nns_page_size;
  private String nns_sort_type;

  public SccmsApiGetVideoListTask(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2)
  {
    this.nns_packet_id = paramString1;
    this.nns_category_id = paramString2;
    this.nns_sort_type = paramString3;
    this.nns_page_index = paramInt1;
    this.nns_page_size = paramInt2;
    setCacheLife(1800000L);
  }

  public String getTaskName()
  {
    return "N3_A_D_2";
  }

  public String getUrl()
  {
    GetFilmItemParams localGetFilmItemParams = new GetFilmItemParams(this.nns_packet_id, this.nns_category_id, this.nns_page_index, this.nns_page_size);
    localGetFilmItemParams.getSort_type().setValue(this.nns_sort_type);
    localGetFilmItemParams.setResultFormat(1);
    return webUrlFormatter.i().formatUrl(localGetFilmItemParams.toString(), localGetFilmItemParams.getApiName());
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
        Logger.e("SccmsApiGetVideoListTask", "返回错误的结果数");
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localFilmItem);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiGetVideoListTaskListener paramISccmsApiGetVideoListTaskListener)
  {
    this.lsr = paramISccmsApiGetVideoListTaskListener;
  }

  public static abstract interface ISccmsApiGetVideoListTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, FilmItem paramFilmItem);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiGetVideoListTask
 * JD-Core Version:    0.6.2
 */