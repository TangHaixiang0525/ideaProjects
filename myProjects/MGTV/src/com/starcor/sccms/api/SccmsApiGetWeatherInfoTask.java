package com.starcor.sccms.api;

import com.starcor.core.domain.WeatherList;
import com.starcor.core.epgapi.params.GetWeatherParams;
import com.starcor.core.parser.sax.GetWeatherSAXParser;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCachedTask;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsApiGetWeatherInfoTask extends ServerApiCachedTask
{
  private static final String TAG = SccmsApiGetWeatherInfoTask.class.getSimpleName();
  GetWeatherParams _params;
  ISccmsApiGetWeatherInfoTaskListener lsr;

  public SccmsApiGetWeatherInfoTask(String paramString)
  {
    this._params = new GetWeatherParams(paramString);
  }

  public String getTaskName()
  {
    return "N100";
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
      WeatherList localWeatherList = (WeatherList)new GetWeatherSAXParser().parser(paramSCResponse.getContents());
      Logger.i(TAG, " result:" + localWeatherList.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localWeatherList);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiGetWeatherInfoTaskListener paramISccmsApiGetWeatherInfoTaskListener)
  {
    this.lsr = paramISccmsApiGetWeatherInfoTaskListener;
  }

  public static abstract interface ISccmsApiGetWeatherInfoTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, WeatherList paramWeatherList);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiGetWeatherInfoTask
 * JD-Core Version:    0.6.2
 */