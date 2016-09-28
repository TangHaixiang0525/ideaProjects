package com.starcor.sccms.api;

import com.starcor.core.domain.AppList;
import com.starcor.core.epgapi.params.GetAppSearchParams;
import com.starcor.core.parser.sax.GetAppListSAXParser;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;
import java.io.ByteArrayInputStream;

public class SccmsApiSearchAppTask extends ServerApiTask
{
  private String categoryId;
  private ISccmsApiSearchAppTaskListener lsr;
  private int pageIndex;
  private int pageSize;
  private int searchType;
  private String searchValue;

  public SccmsApiSearchAppTask(int paramInt1, int paramInt2, int paramInt3, String paramString1, String paramString2)
  {
    this.searchType = paramInt1;
    this.pageIndex = paramInt2;
    this.pageSize = paramInt3;
    this.searchValue = paramString1;
    this.categoryId = paramString2;
  }

  public String getTaskName()
  {
    return "N650_A_4";
  }

  public String getUrl()
  {
    GetAppSearchParams localGetAppSearchParams = new GetAppSearchParams(this.searchType, this.pageIndex, this.pageSize, this.searchValue, this.categoryId);
    localGetAppSearchParams.setResultFormat(0);
    return webUrlFormatter.i().formatUrl(localGetAppSearchParams.toString(), localGetAppSearchParams.getApiName());
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

  public void setListener(ISccmsApiSearchAppTaskListener paramISccmsApiSearchAppTaskListener)
  {
    this.lsr = paramISccmsApiSearchAppTaskListener;
  }

  public static abstract interface ISccmsApiSearchAppTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, AppList paramAppList);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiSearchAppTask
 * JD-Core Version:    0.6.2
 */