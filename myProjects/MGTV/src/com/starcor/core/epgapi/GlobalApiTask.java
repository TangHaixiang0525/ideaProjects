package com.starcor.core.epgapi;

import android.content.Context;
import android.os.Handler;
import com.starcor.core.domain.ApiTask;
import com.starcor.core.epgapi.params.AddUserScoreParams;
import com.starcor.core.epgapi.params.CheckVersionUpdataParams;
import com.starcor.core.epgapi.params.GetFilmInfoParams;
import com.starcor.core.epgapi.params.IniEPGURLParams;
import com.starcor.core.interfaces.IXmlParser;
import com.starcor.core.parser.sax.AddUserScoreSAXParser;
import com.starcor.core.parser.sax.CheckVersionUpdataParser;
import com.starcor.core.parser.sax.GetFilmInfoSAXParser;
import com.starcor.core.parser.sax.IniEPGUrlSAXParser;
import com.starcor.core.service.DownLoadService;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.App;

public class GlobalApiTask
{
  private static final String TAG = "GlobalApiTask";
  private static GlobalApiTask globalApiTask = null;
  private Context appContext = null;

  public static GlobalApiTask getInstance()
  {
    if (globalApiTask == null)
    {
      Logger.i("GlobalApiTask", "GlobalApi First Create");
      globalApiTask = new GlobalApiTask();
    }
    return globalApiTask;
  }

  public int N22A_CheckVersionUpdata(Handler paramHandler, int paramInt, CheckVersionUpdataParams paramCheckVersionUpdataParams)
  {
    Logger.i("GlobalApiTask", "N22A_CheckVersionUpdata API:" + paramCheckVersionUpdataParams.toString());
    return runCommonTask(paramCheckVersionUpdataParams, paramHandler, paramInt, new CheckVersionUpdataParser());
  }

  public int N3A2_GetEpg(Handler paramHandler, int paramInt, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    return runCommonTask(new IniEPGURLParams(paramString2, paramString1, paramString4, paramString3, paramString5), paramHandler, paramInt, new IniEPGUrlSAXParser());
  }

  public int N3AA3_GetVideoInfo(Handler paramHandler, int paramInt1, String paramString, int paramInt2, int paramInt3, int paramInt4)
  {
    return runCommonTask(new GetFilmInfoParams(paramString, paramInt2, paramInt3, paramInt4), paramHandler, paramInt1, new GetFilmInfoSAXParser());
  }

  public int N3D4_AddUserScore(Handler paramHandler, int paramInt1, String paramString1, int paramInt2, String paramString2)
  {
    return runCommonTask(new AddUserScoreParams(paramString1, paramInt2, paramString2), paramHandler, paramInt1, new AddUserScoreSAXParser());
  }

  public void init(Context paramContext)
  {
    Logger.i("GlobalApiTask", "init context:" + paramContext.toString());
    this.appContext = paramContext;
  }

  public int runCommonTask(Api paramApi, Handler paramHandler, int paramInt, IXmlParser<?> paramIXmlParser)
  {
    ApiTask localApiTask = new ApiTask();
    localApiTask.setApi(paramApi);
    localApiTask.setParser(paramIXmlParser);
    localApiTask.setHandler(paramHandler);
    localApiTask.setMessageNumber(paramInt);
    if ((App.getInstance() != null) && (App.getInstance().getTaskService() != null))
    {
      int i = App.getInstance().getTaskService().addTask(localApiTask);
      Logger.i("GlobalApiTask", "1taskId:" + i + ", runCommonTask name:" + paramApi.getApiName() + ", args:" + paramApi.toString());
      return i;
    }
    Logger.e("GlobalApiTask", "runCommonTask TaskService null");
    return -1;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.GlobalApiTask
 * JD-Core Version:    0.6.2
 */