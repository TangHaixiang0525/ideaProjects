package com.starcor.sccms.api;

import com.starcor.core.domain.AssetsInfo;
import com.starcor.core.epgapi.params.GetAssetsByVideoIdParams;
import com.starcor.core.parser.json.GetAssetsSAXParserJson;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsApiGetAssetsByVideoIdTask extends ServerApiTask
{
  ISccmsApiGetAssetsByVideoIdTaskListener lsr;
  String nns_video_id = null;

  public SccmsApiGetAssetsByVideoIdTask(String paramString)
  {
    this.nns_video_id = paramString;
    setCacheLife(1800000L);
  }

  public String getTaskName()
  {
    return "N39_A_2";
  }

  public String getUrl()
  {
    GetAssetsByVideoIdParams localGetAssetsByVideoIdParams = new GetAssetsByVideoIdParams(this.nns_video_id);
    localGetAssetsByVideoIdParams.setResultFormat(1);
    return webUrlFormatter.i().formatUrl(localGetAssetsByVideoIdParams.toString(), localGetAssetsByVideoIdParams.getApiName());
  }

  public void perform(SCResponse paramSCResponse)
  {
    GetAssetsSAXParserJson localGetAssetsSAXParserJson = new GetAssetsSAXParserJson();
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    try
    {
      AssetsInfo localAssetsInfo = (AssetsInfo)localGetAssetsSAXParserJson.parser(paramSCResponse.getContents());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localAssetsInfo);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiGetAssetsByVideoIdTaskListener paramISccmsApiGetAssetsByVideoIdTaskListener)
  {
    this.lsr = paramISccmsApiGetAssetsByVideoIdTaskListener;
  }

  public static abstract interface ISccmsApiGetAssetsByVideoIdTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, AssetsInfo paramAssetsInfo);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiGetAssetsByVideoIdTask
 * JD-Core Version:    0.6.2
 */