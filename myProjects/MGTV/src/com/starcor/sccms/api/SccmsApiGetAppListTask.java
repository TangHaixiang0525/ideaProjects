package com.starcor.sccms.api;

import com.starcor.core.domain.AppList;
import com.starcor.core.epgapi.params.GetAppListParams;
import com.starcor.core.parser.sax.GetAppListSAXParser;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;
import java.io.ByteArrayInputStream;

public class SccmsApiGetAppListTask extends ServerApiTask
{
  private ISccmsApiGetAppListTaskListener lsr;
  private String nns_category_id;
  private int nns_page_index;
  private int nns_page_size;

  public SccmsApiGetAppListTask(String paramString, int paramInt1, int paramInt2)
  {
    this.nns_category_id = paramString;
    this.nns_page_index = paramInt1;
    this.nns_page_size = paramInt2;
  }

  public String getTaskName()
  {
    return "N650_A_1";
  }

  public String getUrl()
  {
    GetAppListParams localGetAppListParams = new GetAppListParams(this.nns_category_id, this.nns_page_index, this.nns_page_size);
    localGetAppListParams.setResultFormat(0);
    return webUrlFormatter.i().formatUrl(localGetAppListParams.toString(), localGetAppListParams.getApiName());
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
    GetAppListSAXParser localGetAppListSAXParser = new GetAppListSAXParser();
    try
    {
      AppList localAppList = (AppList)localGetAppListSAXParser.parser(new ByteArrayInputStream(paramSCResponse.getContents()));
      Logger.i("SccmsApiGetAppListTask", " result:" + localAppList.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localAppList);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiGetAppListTaskListener paramISccmsApiGetAppListTaskListener)
  {
    this.lsr = paramISccmsApiGetAppListTaskListener;
  }

  public static abstract interface ISccmsApiGetAppListTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, AppList paramAppList);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiGetAppListTask
 * JD-Core Version:    0.6.2
 */