package com.starcor.sccms.api;

import com.starcor.core.domain.FAQStruct;
import com.starcor.core.epgapi.params.GetFrequentlyAskedQuestionsParams;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.parser.sax.GetFrequentlyAskedQuestionsSAXParser;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCachedTask;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;
import java.util.ArrayList;

public class SccmsApiGetFAQListTask extends ServerApiCachedTask
{
  private static final String TAG = SccmsApiGetFAQListTask.class.getSimpleName();
  GetFrequentlyAskedQuestionsParams _params = new GetFrequentlyAskedQuestionsParams(GlobalLogic.getInstance().getNnToken(), GlobalLogic.getInstance().getWebToken());
  ISccmsApiGetFAQListTaskListener lsr;

  public SccmsApiGetFAQListTask()
  {
    setCacheLife(1800000L);
  }

  public String getTaskName()
  {
    return "N23_A_1";
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
      ArrayList localArrayList = (ArrayList)new GetFrequentlyAskedQuestionsSAXParser().parser(paramSCResponse.getContents());
      Logger.i(TAG, " result:" + localArrayList.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localArrayList);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiGetFAQListTaskListener paramISccmsApiGetFAQListTaskListener)
  {
    this.lsr = paramISccmsApiGetFAQListTaskListener;
  }

  public static abstract interface ISccmsApiGetFAQListTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, ArrayList<FAQStruct> paramArrayList);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiGetFAQListTask
 * JD-Core Version:    0.6.2
 */