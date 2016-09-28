package com.starcor.sccms.api;

import com.starcor.core.domain.AdvertisementPosInfo;
import com.starcor.core.epgapi.params.GetAdvertisementPosInfoParams;
import com.starcor.core.parser.sax.GetAdvertisementPosInfoParser;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;
import java.util.ArrayList;

public class SccmsApiGetAdInfoByVideoIdTask extends ServerApiTask
{
  private String categoryId;
  private ISccmsApiGetAdInfoByVideoIdTaskListener lsr;
  private String mediaAssetsId;
  private String serviceId;
  private String videoId;
  private int videoType;

  public SccmsApiGetAdInfoByVideoIdTask(String paramString1, int paramInt, String paramString2, String paramString3, String paramString4)
  {
    this.videoId = paramString1;
    this.videoType = paramInt;
    this.mediaAssetsId = paramString2;
    this.serviceId = paramString3;
    this.categoryId = paramString4;
  }

  public String getTaskName()
  {
    return "N7_A_2";
  }

  public String getUrl()
  {
    GetAdvertisementPosInfoParams localGetAdvertisementPosInfoParams = new GetAdvertisementPosInfoParams(this.videoId, this.videoType, this.mediaAssetsId, this.serviceId, this.categoryId);
    localGetAdvertisementPosInfoParams.setResultFormat(0);
    return webUrlFormatter.i().formatUrl(localGetAdvertisementPosInfoParams.toString(), localGetAdvertisementPosInfoParams.getApiName());
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
      ArrayList localArrayList = (ArrayList)new GetAdvertisementPosInfoParser().parser(paramSCResponse.getContents());
      Logger.i("SccmsApiGetAdInfoByVideoIdTask", " result:" + localArrayList.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localArrayList);
      return;
    }
    catch (Exception localException)
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiGetAdInfoByVideoIdTaskListener paramISccmsApiGetAdInfoByVideoIdTaskListener)
  {
    this.lsr = paramISccmsApiGetAdInfoByVideoIdTaskListener;
  }

  public static abstract interface ISccmsApiGetAdInfoByVideoIdTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, ArrayList<AdvertisementPosInfo> paramArrayList);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiGetAdInfoByVideoIdTask
 * JD-Core Version:    0.6.2
 */