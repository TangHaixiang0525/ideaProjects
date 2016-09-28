package com.starcor.sccms.api;

import com.starcor.core.domain.MediaAssetsInfo;
import com.starcor.core.epgapi.params.GetMediaAssetsInfoParams;
import com.starcor.core.parser.json.GetAssetsInfoSAXParserJson;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCachedTask;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsApiGetMediaAssetsInfoTask extends ServerApiCachedTask
{
  ISccmsApiGetMediaAssetsInfoTaskListener lsr;
  String nns_package_id = null;

  public SccmsApiGetMediaAssetsInfoTask(String paramString)
  {
    this.nns_package_id = paramString;
    setCacheLife(1800000L);
  }

  public String getTaskName()
  {
    return "N3_A_D_1";
  }

  public String getUrl()
  {
    GetMediaAssetsInfoParams localGetMediaAssetsInfoParams = new GetMediaAssetsInfoParams(this.nns_package_id);
    localGetMediaAssetsInfoParams.setResultFormat(1);
    return webUrlFormatter.i().formatUrl(localGetMediaAssetsInfoParams.toString(), localGetMediaAssetsInfoParams.getApiName());
  }

  protected byte[] localPerform(SCResponse paramSCResponse)
  {
    GetAssetsInfoSAXParserJson localGetAssetsInfoSAXParserJson = new GetAssetsInfoSAXParserJson();
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return null;
    }
    try
    {
      MediaAssetsInfo localMediaAssetsInfo = (MediaAssetsInfo)localGetAssetsInfoSAXParserJson.parser(paramSCResponse.getContents());
      Logger.i("SccmsApiGetMediaAssetsInfoTask", " result:" + localMediaAssetsInfo.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localMediaAssetsInfo);
      byte[] arrayOfByte = paramSCResponse.getContents();
      return arrayOfByte;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
    return null;
  }

  public void perform(SCResponse paramSCResponse)
  {
    byte[] arrayOfByte = localPerform(paramSCResponse);
    if (arrayOfByte != null)
      serializeApiData(arrayOfByte);
  }

  public void setListener(ISccmsApiGetMediaAssetsInfoTaskListener paramISccmsApiGetMediaAssetsInfoTaskListener)
  {
    this.lsr = paramISccmsApiGetMediaAssetsInfoTaskListener;
  }

  public static abstract interface ISccmsApiGetMediaAssetsInfoTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, MediaAssetsInfo paramMediaAssetsInfo);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiGetMediaAssetsInfoTask
 * JD-Core Version:    0.6.2
 */