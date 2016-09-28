package com.starcor.sccms.api;

import android.text.TextUtils;
import com.starcor.core.domain.AdPosEntity;
import com.starcor.core.epgapi.params.GetAdInfoByAdPosListParams;
import com.starcor.core.parser.sax.GetAdInfoByAdPosListParser;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;
import java.util.ArrayList;

public class SccmsApiGetAdInfoByAdPosListTask extends ServerApiTask
{
  private String adPosIds = "";
  private ISccmsApiGetAdInfoByAdPosListTaskListener lsr;

  public SccmsApiGetAdInfoByAdPosListTask(String[] paramArrayOfString)
  {
    if (paramArrayOfString == null)
      this.adPosIds = "";
    do
    {
      return;
      int i = paramArrayOfString.length;
      int j = 0;
      if (j < i)
      {
        String str = paramArrayOfString[j];
        if (TextUtils.isEmpty(str));
        while (true)
        {
          j++;
          break;
          this.adPosIds = (this.adPosIds + str + "|");
        }
      }
    }
    while (!this.adPosIds.endsWith("|"));
    this.adPosIds = this.adPosIds.substring(0, this.adPosIds.lastIndexOf("|"));
  }

  public String getTaskName()
  {
    return "N7_A_5";
  }

  public String getUrl()
  {
    GetAdInfoByAdPosListParams localGetAdInfoByAdPosListParams = new GetAdInfoByAdPosListParams(this.adPosIds);
    localGetAdInfoByAdPosListParams.setResultFormat(0);
    return webUrlFormatter.i().formatUrl(localGetAdInfoByAdPosListParams.toString(), localGetAdInfoByAdPosListParams.getApiName());
  }

  public void perform(SCResponse paramSCResponse)
  {
    if (this.lsr == null)
      return;
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    try
    {
      ArrayList localArrayList = (ArrayList)new GetAdInfoByAdPosListParser().parser(paramSCResponse.getContents());
      Logger.i("SccmsApiGetAdInfoByAdPosListTask", " result:" + localArrayList.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localArrayList);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiGetAdInfoByAdPosListTaskListener paramISccmsApiGetAdInfoByAdPosListTaskListener)
  {
    this.lsr = paramISccmsApiGetAdInfoByAdPosListTaskListener;
  }

  public static abstract interface ISccmsApiGetAdInfoByAdPosListTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, ArrayList<AdPosEntity> paramArrayList);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiGetAdInfoByAdPosListTask
 * JD-Core Version:    0.6.2
 */