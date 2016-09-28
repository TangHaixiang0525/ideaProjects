package com.starcor.sccms.api;

import com.starcor.core.domain.VideoIdInfo;
import com.starcor.core.epgapi.params.GetVideoIdInfoParams;
import com.starcor.core.parser.json.GetVideoIdInfoSAXParserJson;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsApiGetVideoIdByMgtvAssetIdTask extends ServerApiTask
{
  ISccmsApiGetVideoIdByMgtvAssetIdTaskListener lsr = null;
  String nns_asset_id = null;
  String nns_clip_id = null;
  String nns_file_id = null;

  public SccmsApiGetVideoIdByMgtvAssetIdTask(String paramString1, String paramString2, String paramString3)
  {
    this.nns_asset_id = paramString1;
    this.nns_clip_id = paramString2;
    this.nns_file_id = paramString3;
    setCacheLife(1800000L);
  }

  public String getTaskName()
  {
    return "N200_A_22";
  }

  public String getUrl()
  {
    GetVideoIdInfoParams localGetVideoIdInfoParams = new GetVideoIdInfoParams(this.nns_asset_id, this.nns_clip_id, this.nns_file_id);
    localGetVideoIdInfoParams.setResultFormat(1);
    return webUrlFormatter.i().formatUrl(localGetVideoIdInfoParams.toString(), localGetVideoIdInfoParams.getApiName());
  }

  public void perform(SCResponse paramSCResponse)
  {
    GetVideoIdInfoSAXParserJson localGetVideoIdInfoSAXParserJson = new GetVideoIdInfoSAXParserJson();
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    try
    {
      VideoIdInfo localVideoIdInfo = (VideoIdInfo)localGetVideoIdInfoSAXParserJson.parser(paramSCResponse.getContents());
      Logger.i("SccmsApiGetVideoIdByMgtvAssetIdTask", " result:" + localVideoIdInfo.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localVideoIdInfo);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiGetVideoIdByMgtvAssetIdTaskListener paramISccmsApiGetVideoIdByMgtvAssetIdTaskListener)
  {
    this.lsr = paramISccmsApiGetVideoIdByMgtvAssetIdTaskListener;
  }

  public static abstract interface ISccmsApiGetVideoIdByMgtvAssetIdTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, VideoIdInfo paramVideoIdInfo);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiGetVideoIdByMgtvAssetIdTask
 * JD-Core Version:    0.6.2
 */