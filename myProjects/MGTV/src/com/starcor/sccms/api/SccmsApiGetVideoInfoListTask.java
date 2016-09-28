package com.starcor.sccms.api;

import com.starcor.core.domain.VideoInfoListItem;
import com.starcor.core.epgapi.params.GetVideoInfoListParams;
import com.starcor.core.parser.sax.GetVideoInfoListSAXParser;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class SccmsApiGetVideoInfoListTask extends ServerApiTask
{
  ISccmsApiGetVideoInfoListTaskListener lsr;
  String nns_info_type;
  String nns_video_ids;

  public SccmsApiGetVideoInfoListTask(String paramString1, String paramString2)
  {
    this.nns_info_type = paramString2;
    this.nns_video_ids = paramString1;
    setCacheLife(1800000L);
  }

  public String getTaskName()
  {
    return "N39_a_20";
  }

  public String getUrl()
  {
    GetVideoInfoListParams localGetVideoInfoListParams = new GetVideoInfoListParams(this.nns_video_ids, this.nns_info_type);
    localGetVideoInfoListParams.setResultFormat(0);
    return webUrlFormatter.i().formatUrl(localGetVideoInfoListParams.toString(), localGetVideoInfoListParams.getApiName());
  }

  public void perform(SCResponse paramSCResponse)
  {
    GetVideoInfoListSAXParser localGetVideoInfoListSAXParser = new GetVideoInfoListSAXParser();
    if (ServerApiTools.isSCResponseError(paramSCResponse))
      if (this.lsr != null)
        this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
    do
      while (true)
      {
        return;
        try
        {
          ArrayList localArrayList = (ArrayList)localGetVideoInfoListSAXParser.parser(new ByteArrayInputStream(paramSCResponse.getContents()));
          if (localArrayList != null)
            Logger.i("SccmsApiGetVideoInfoListTask", " result:" + localArrayList.toString());
          if (this.lsr != null)
          {
            this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localArrayList);
            return;
          }
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
        }
      }
    while (this.lsr == null);
    this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
  }

  public void setListener(ISccmsApiGetVideoInfoListTaskListener paramISccmsApiGetVideoInfoListTaskListener)
  {
    this.lsr = paramISccmsApiGetVideoInfoListTaskListener;
  }

  public static abstract interface ISccmsApiGetVideoInfoListTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, ArrayList<VideoInfoListItem> paramArrayList);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiGetVideoInfoListTask
 * JD-Core Version:    0.6.2
 */