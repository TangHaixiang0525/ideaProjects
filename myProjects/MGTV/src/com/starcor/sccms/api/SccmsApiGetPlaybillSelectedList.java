package com.starcor.sccms.api;

import com.starcor.core.domain.GetPlaybillSelectedListInfo;
import com.starcor.core.epgapi.params.GetPlaybillSelectedParams;
import com.starcor.core.parser.sax.GetPlaybillSelectedListSAXParser;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;
import java.io.ByteArrayInputStream;

public class SccmsApiGetPlaybillSelectedList extends ServerApiTask
{
  private String category_id;
  private ISccmsApiGetPlaybillSelectedListTaskListener lsr;
  private String media_asset_id;
  private int page_index;
  private int page_size;
  private GetPlaybillSelectedParams para;
  private String user_area;
  private String user_attr;
  private String video_type;

  public SccmsApiGetPlaybillSelectedList(String paramString1, String paramString2, int paramInt1, int paramInt2, ISccmsApiGetPlaybillSelectedListTaskListener paramISccmsApiGetPlaybillSelectedListTaskListener)
  {
    this.media_asset_id = paramString1;
    this.category_id = paramString2;
    this.page_size = paramInt1;
    this.page_index = paramInt2;
    this.lsr = paramISccmsApiGetPlaybillSelectedListTaskListener;
    setCacheLife(1800000L);
    this.para = new GetPlaybillSelectedParams(this.media_asset_id, this.category_id, this.page_size, this.page_index);
    this.para.setResultFormat(0);
  }

  public SccmsApiGetPlaybillSelectedList(String paramString1, String paramString2, int paramInt1, int paramInt2, String paramString3, String paramString4, String paramString5, ISccmsApiGetPlaybillSelectedListTaskListener paramISccmsApiGetPlaybillSelectedListTaskListener)
  {
    this.media_asset_id = paramString1;
    this.category_id = paramString2;
    this.page_size = paramInt1;
    this.page_index = paramInt2;
    this.user_area = paramString3;
    this.user_attr = paramString4;
    this.video_type = paramString5;
    this.lsr = paramISccmsApiGetPlaybillSelectedListTaskListener;
    setCacheLife(1800000L);
    this.para = new GetPlaybillSelectedParams(this.media_asset_id, this.category_id, this.page_size, this.page_index, this.user_area, this.user_attr, this.video_type);
    this.para.setResultFormat(0);
  }

  public String getTaskName()
  {
    return "N39_A_15";
  }

  public String getUrl()
  {
    return webUrlFormatter.i().formatUrl(this.para.toString(), this.para.getApiName());
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
    try
    {
      GetPlaybillSelectedListInfo localGetPlaybillSelectedListInfo = new GetPlaybillSelectedListSAXParser().parser(new ByteArrayInputStream(paramSCResponse.getContents()));
      localGetPlaybillSelectedListInfo.pageIndex = this.page_index;
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localGetPlaybillSelectedListInfo);
      return;
    }
    catch (Exception localException)
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public static abstract interface ISccmsApiGetPlaybillSelectedListTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, GetPlaybillSelectedListInfo paramGetPlaybillSelectedListInfo);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiGetPlaybillSelectedList
 * JD-Core Version:    0.6.2
 */