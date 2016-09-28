package com.starcor.sccms.api;

import com.starcor.core.epgapi.params.GetMediaAssetsBindLabelParams;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsApiGetMediaAssetsBindLabelTask extends ServerApiTask
{
  ISccmsApiGetMediaAssetsBindLabelTaskListener lsr;
  private String nns_packet_id;

  public SccmsApiGetMediaAssetsBindLabelTask(String paramString, ISccmsApiGetMediaAssetsBindLabelTaskListener paramISccmsApiGetMediaAssetsBindLabelTaskListener)
  {
    this.nns_packet_id = paramString;
    this.lsr = paramISccmsApiGetMediaAssetsBindLabelTaskListener;
    setCacheLife(1800000L);
  }

  public String getTaskName()
  {
    return "N39_A_23";
  }

  public String getUrl()
  {
    GetMediaAssetsBindLabelParams localGetMediaAssetsBindLabelParams = new GetMediaAssetsBindLabelParams(this.nns_packet_id);
    localGetMediaAssetsBindLabelParams.setResultFormat(0);
    return webUrlFormatter.i().formatUrl(localGetMediaAssetsBindLabelParams.toString(), localGetMediaAssetsBindLabelParams.getApiName());
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
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), paramSCResponse.getContents());
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public static abstract interface ISccmsApiGetMediaAssetsBindLabelTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, byte[] paramArrayOfByte);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiGetMediaAssetsBindLabelTask
 * JD-Core Version:    0.6.2
 */