package com.starcor.sccms.api;

import com.starcor.core.domain.CityStruct;
import com.starcor.core.epgapi.params.GetCityListParams;
import com.starcor.core.parser.sax.GetCityListSAXParser;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsApiGetCityListTask extends ServerApiTask
{
  private static final String TAG = SccmsApiGetCityListTask.class.getSimpleName();
  GetCityListParams _params;
  ISccmsApiGetCityListTaskListener lsr;

  public SccmsApiGetCityListTask(String paramString)
  {
    this._params = new GetCityListParams(paramString);
  }

  public String getTaskName()
  {
    return "N100_A_1";
  }

  public String getUrl()
  {
    try
    {
      String str = webUrlFormatter.i().formatUrl(this._params.toString(), this._params.getApiName());
      return str;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return "";
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
      CityStruct localCityStruct = (CityStruct)new GetCityListSAXParser().parser(paramSCResponse.getContents());
      Logger.i(TAG, " result:" + localCityStruct.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localCityStruct);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiGetCityListTaskListener paramISccmsApiGetCityListTaskListener)
  {
    this.lsr = paramISccmsApiGetCityListTaskListener;
  }

  public static abstract interface ISccmsApiGetCityListTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, CityStruct paramCityStruct);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiGetCityListTask
 * JD-Core Version:    0.6.2
 */