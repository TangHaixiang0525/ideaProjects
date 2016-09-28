package com.starcor.sccms.api;

import com.starcor.core.domain.CommonVideoIDInfo;
import com.starcor.core.epgapi.params.GetCommonVideoIdParams;
import com.starcor.core.parser.sax.GetCommonVideoIdSAXParser;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;
import java.io.ByteArrayInputStream;

public class SccmsApiGetCommonVideoIdTask extends ServerApiTask
{
  private static final String TAG = SccmsApiGetCommonVideoIdTask.class.getSimpleName();
  private ISccmsApiGetCommonVideoIdTaskListener lsr;
  private String nns_asset_id = null;
  private String nns_clip_id = null;
  private String nns_file_id = null;
  private String nns_live_id = null;

  public SccmsApiGetCommonVideoIdTask(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    this.nns_asset_id = paramString1;
    this.nns_clip_id = paramString2;
    this.nns_file_id = paramString3;
    this.nns_live_id = paramString4;
  }

  public String getTaskName()
  {
    return "N200_A_22";
  }

  public String getUrl()
  {
    GetCommonVideoIdParams localGetCommonVideoIdParams = new GetCommonVideoIdParams(this.nns_asset_id, this.nns_clip_id, this.nns_file_id, this.nns_live_id);
    localGetCommonVideoIdParams.setResultFormat(0);
    return webUrlFormatter.i().formatUrl(localGetCommonVideoIdParams.toString(), localGetCommonVideoIdParams.getApiName());
  }

  public void perform(SCResponse paramSCResponse)
  {
    GetCommonVideoIdSAXParser localGetCommonVideoIdSAXParser = new GetCommonVideoIdSAXParser();
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    try
    {
      CommonVideoIDInfo localCommonVideoIDInfo = (CommonVideoIDInfo)localGetCommonVideoIdSAXParser.parser(new ByteArrayInputStream(paramSCResponse.getContents()));
      if (localCommonVideoIDInfo != null)
        Logger.i("SccmsApiGetCommonVideoIdTask", " result:" + localCommonVideoIDInfo.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localCommonVideoIDInfo);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiGetCommonVideoIdTaskListener paramISccmsApiGetCommonVideoIdTaskListener)
  {
    this.lsr = paramISccmsApiGetCommonVideoIdTaskListener;
  }

  public static abstract interface ISccmsApiGetCommonVideoIdTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, CommonVideoIDInfo paramCommonVideoIDInfo);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiGetCommonVideoIdTask
 * JD-Core Version:    0.6.2
 */