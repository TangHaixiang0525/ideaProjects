package com.starcor.sccms.api;

import com.starcor.core.domain.StarGuestLabelList;
import com.starcor.core.epgapi.params.GetStarGuestLableListParams;
import com.starcor.core.parser.sax.GetStarGuestLabelSAXParser;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;
import java.io.ByteArrayInputStream;

public class SccmsApiGetStarGuestListByLabelTask extends ServerApiTask
{
  ISccmsApiGetStarGusetListByLabelTaskListener lsr;
  private String nns_category_id;
  private String nns_label_id;
  private String nns_media_assets_category_id;
  private String nns_packet_id;
  private int nns_page_index;
  private int nns_page_size;

  public SccmsApiGetStarGuestListByLabelTask(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2, String paramString4)
  {
    this.nns_packet_id = paramString1;
    this.nns_category_id = paramString2;
    this.nns_media_assets_category_id = paramString3;
    this.nns_page_index = paramInt1;
    this.nns_page_size = paramInt2;
    this.nns_label_id = paramString4;
    setCacheLife(1800000L);
  }

  public String getTaskName()
  {
    return "N39_A_11";
  }

  public String getUrl()
  {
    GetStarGuestLableListParams localGetStarGuestLableListParams = new GetStarGuestLableListParams(this.nns_packet_id, this.nns_category_id, this.nns_media_assets_category_id, this.nns_page_index, this.nns_page_size, this.nns_label_id);
    localGetStarGuestLableListParams.setResultFormat(0);
    return webUrlFormatter.i().formatUrl(localGetStarGuestLableListParams.toString(), localGetStarGuestLableListParams.getApiName());
  }

  public void perform(SCResponse paramSCResponse)
  {
    GetStarGuestLabelSAXParser localGetStarGuestLabelSAXParser = new GetStarGuestLabelSAXParser();
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    try
    {
      StarGuestLabelList localStarGuestLabelList = (StarGuestLabelList)localGetStarGuestLabelSAXParser.parser(new ByteArrayInputStream(paramSCResponse.getContents()));
      Logger.i("SccmsApiGetStarGuestListByLabelTask", " result:" + localStarGuestLabelList.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localStarGuestLabelList);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiGetStarGusetListByLabelTaskListener paramISccmsApiGetStarGusetListByLabelTaskListener)
  {
    this.lsr = paramISccmsApiGetStarGusetListByLabelTaskListener;
  }

  public static abstract interface ISccmsApiGetStarGusetListByLabelTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, StarGuestLabelList paramStarGuestLabelList);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiGetStarGuestListByLabelTask
 * JD-Core Version:    0.6.2
 */