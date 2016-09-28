package com.starcor.sccms.api;

import com.starcor.core.domain.SpeedStruct;
import com.starcor.core.epgapi.params.GetSpeedTestMissionInfoParams;
import com.starcor.core.parser.json.GetSpeedTestMissionInfoSAXParserJson;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;
import java.util.ArrayList;

public class SccmsApiGetSpeedTestMissionInfoTask extends ServerApiTask
{
  ISccmsApiGetSpeedTestMissionInfoTaskListener lsr = null;

  public String getTaskName()
  {
    return "N26_A_1";
  }

  public String getUrl()
  {
    GetSpeedTestMissionInfoParams localGetSpeedTestMissionInfoParams = new GetSpeedTestMissionInfoParams();
    localGetSpeedTestMissionInfoParams.setResultFormat(1);
    return webUrlFormatter.i().formatUrl(localGetSpeedTestMissionInfoParams.toString(), localGetSpeedTestMissionInfoParams.getApiName());
  }

  public void perform(SCResponse paramSCResponse)
  {
    GetSpeedTestMissionInfoSAXParserJson localGetSpeedTestMissionInfoSAXParserJson = new GetSpeedTestMissionInfoSAXParserJson();
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    try
    {
      ArrayList localArrayList = (ArrayList)localGetSpeedTestMissionInfoSAXParserJson.parser(paramSCResponse.getContents());
      if (localArrayList.size() < 1)
        Logger.e("SccmsApiGetSpeedTestMissionInfoTask", "返回错误的结果数");
      Logger.i("SccmsApiGetSpeedTestMissionInfoTask", " result:" + localArrayList.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localArrayList);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiGetSpeedTestMissionInfoTaskListener paramISccmsApiGetSpeedTestMissionInfoTaskListener)
  {
    this.lsr = paramISccmsApiGetSpeedTestMissionInfoTaskListener;
  }

  public static abstract interface ISccmsApiGetSpeedTestMissionInfoTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, ArrayList<SpeedStruct> paramArrayList);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiGetSpeedTestMissionInfoTask
 * JD-Core Version:    0.6.2
 */