package com.starcor.sccms.api;

import com.starcor.core.domain.HotWordList;
import com.starcor.core.epgapi.params.HotWordListParams;
import com.starcor.core.parser.json.HotWordListSAXParserJson;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsApiGetHotWordsTask extends ServerApiTask
{
  ISccmsApiGetHotWordsTaskListener lsr;
  int page_index = 0;
  int page_size = 0;

  public SccmsApiGetHotWordsTask(int paramInt1, int paramInt2)
  {
    this.page_index = paramInt1;
    this.page_size = paramInt2;
    setCacheLife(1800000L);
  }

  public String getTaskName()
  {
    return "N21_A_1";
  }

  public String getUrl()
  {
    HotWordListParams localHotWordListParams = new HotWordListParams(this.page_index, this.page_size);
    localHotWordListParams.setResultFormat(1);
    return webUrlFormatter.i().formatUrl(localHotWordListParams.toString(), localHotWordListParams.getApiName());
  }

  public void perform(SCResponse paramSCResponse)
  {
    HotWordListSAXParserJson localHotWordListSAXParserJson = new HotWordListSAXParserJson();
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    try
    {
      HotWordList localHotWordList = (HotWordList)localHotWordListSAXParserJson.parser(paramSCResponse.getContents());
      if (localHotWordList.count < 1)
        Logger.e("SccmsApiGetHotWordsTask", "返回错误的结果数");
      Logger.i("SccmsApiGetHotWordsTask", " result:" + localHotWordList.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localHotWordList);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiGetHotWordsTaskListener paramISccmsApiGetHotWordsTaskListener)
  {
    this.lsr = paramISccmsApiGetHotWordsTaskListener;
  }

  public static abstract interface ISccmsApiGetHotWordsTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, HotWordList paramHotWordList);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiGetHotWordsTask
 * JD-Core Version:    0.6.2
 */