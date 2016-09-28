package com.starcor.hunan.service.apidetect.task.subtask;

import com.starcor.config.DeviceInfo;
import com.starcor.core.domain.MetadataGoup;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.hunan.service.apidetect.data.ApiDetectIniEPGURLParams;
import com.starcor.hunan.service.apidetect.parser.ApiDetectIniEPGUrlSAXParser;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class ApiDetectSccmsN3A2GetEpgDataTask extends ServerApiTask
{
  private final String TAG = ApiDetectSccmsN3A2GetEpgDataTask.class.getSimpleName();
  private IApiDetectSccmsApiN3A2GetEpgDataTaskListener lsr;

  public String getTaskName()
  {
    return "N3_A_2";
  }

  public String getUrl()
  {
    ApiDetectIniEPGURLParams localApiDetectIniEPGURLParams = new ApiDetectIniEPGURLParams(GlobalLogic.getInstance().getNnToken(), GlobalLogic.getInstance().getWebToken(), GlobalLogic.getInstance().getDeviceId(), GlobalLogic.getInstance().getUserId(), DeviceInfo.getMac());
    localApiDetectIniEPGURLParams.setResultFormat(0);
    return webUrlFormatter.i().formatUrl(localApiDetectIniEPGURLParams.toString(), localApiDetectIniEPGURLParams.getApiName());
  }

  public void perform(SCResponse paramSCResponse)
  {
    if (this.lsr == null)
      return;
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    try
    {
      ArrayList localArrayList = (ArrayList)new ApiDetectIniEPGUrlSAXParser().parser(new ByteArrayInputStream(paramSCResponse.getContents()));
      Logger.i(this.TAG, " result:" + localArrayList.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), paramSCResponse), localArrayList);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.getMessage()));
    }
  }

  public void setListener(IApiDetectSccmsApiN3A2GetEpgDataTaskListener paramIApiDetectSccmsApiN3A2GetEpgDataTaskListener)
  {
    this.lsr = paramIApiDetectSccmsApiN3A2GetEpgDataTaskListener;
  }

  public static abstract interface IApiDetectSccmsApiN3A2GetEpgDataTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, ArrayList<MetadataGoup> paramArrayList);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.service.apidetect.task.subtask.ApiDetectSccmsN3A2GetEpgDataTask
 * JD-Core Version:    0.6.2
 */