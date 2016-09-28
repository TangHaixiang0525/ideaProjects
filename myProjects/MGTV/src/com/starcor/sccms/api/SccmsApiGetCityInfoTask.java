package com.starcor.sccms.api;

import com.starcor.core.domain.CityInfoById;
import com.starcor.core.epgapi.params.GetCityInfoByIdParams;
import com.starcor.core.parser.sax.CityItemSAXParser;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsApiGetCityInfoTask extends ServerApiTask
{
  private static final String TAG = SccmsApiGetCityInfoTask.class.getSimpleName();
  GetCityInfoByIdParams _params;
  ISccmsApiGetCityListTaskListener lsr;

  public SccmsApiGetCityInfoTask(String paramString)
  {
    this._params = new GetCityInfoByIdParams(paramString);
  }

  public String getTaskName()
  {
    return "N100_A_2";
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
      CityInfoById localCityInfoById = (CityInfoById)new CityItemSAXParser().parser(paramSCResponse.getContents());
      Logger.i(TAG, " result:" + localCityInfoById.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localCityInfoById);
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

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, CityInfoById paramCityInfoById);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiGetCityInfoTask
 * JD-Core Version:    0.6.2
 */