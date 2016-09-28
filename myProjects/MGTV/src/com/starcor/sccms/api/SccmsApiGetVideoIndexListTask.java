package com.starcor.sccms.api;

import com.starcor.core.domain.FilmListPageInfo;
import com.starcor.core.epgapi.params.GetVideoIndexListParams;
import com.starcor.core.parser.sax.GetVideoIndexListSAXParser;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCachedTask;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsApiGetVideoIndexListTask extends ServerApiCachedTask
{
  private static final String TAG = SccmsApiGetVideoIndexListTask.class.getSimpleName();
  GetVideoIndexListParams _params;
  ISccmsApiGetVideoIndexListTaskListener lsr;

  public SccmsApiGetVideoIndexListTask(String paramString, int paramInt1, int paramInt2, int paramInt3)
  {
    this._params = new GetVideoIndexListParams(paramString, paramInt1, paramInt2, paramInt3);
    setCacheLife(1800000L);
  }

  public SccmsApiGetVideoIndexListTask(String paramString, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
  {
    this(paramString, paramInt1, paramInt2, paramInt3);
    if (paramBoolean)
      setForceUpdate(true);
  }

  public String getTaskName()
  {
    return "N3_A_A_12";
  }

  public String getUrl()
  {
    try
    {
      String str = webUrlFormatter.i().formatUrl(this._params.toString(), this._params.getApiName());
      return str;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return "";
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
      FilmListPageInfo localFilmListPageInfo = (FilmListPageInfo)new GetVideoIndexListSAXParser().parser(paramSCResponse.getContents());
      if (localFilmListPageInfo != null)
      {
        this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localFilmListPageInfo);
        return;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
      return;
    }
    this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, "parse result error"));
  }

  public void setListener(ISccmsApiGetVideoIndexListTaskListener paramISccmsApiGetVideoIndexListTaskListener)
  {
    this.lsr = paramISccmsApiGetVideoIndexListTaskListener;
  }

  public static abstract interface ISccmsApiGetVideoIndexListTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, FilmListPageInfo paramFilmListPageInfo);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiGetVideoIndexListTask
 * JD-Core Version:    0.6.2
 */