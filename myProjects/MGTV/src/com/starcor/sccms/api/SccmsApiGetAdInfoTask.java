package com.starcor.sccms.api;

import com.starcor.core.domain.AdPos;
import com.starcor.core.epgapi.params.GetAdInfoPaeams;
import com.starcor.core.parser.json.GetAdInfoSAXParserJson;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;
import java.util.ArrayList;

public class SccmsApiGetAdInfoTask extends ServerApiTask
{
  ISccmsApiGetAdInfoTaskListener lsr = null;
  private String nns_ad_id;

  public SccmsApiGetAdInfoTask(String paramString)
  {
    this.nns_ad_id = paramString;
    setCacheLife(1800000L);
  }

  public String getTaskName()
  {
    return "N7_A";
  }

  public String getUrl()
  {
    GetAdInfoPaeams localGetAdInfoPaeams = new GetAdInfoPaeams(this.nns_ad_id, null);
    localGetAdInfoPaeams.setResultFormat(1);
    return webUrlFormatter.i().formatUrl(localGetAdInfoPaeams.toString(), localGetAdInfoPaeams.getApiName());
  }

  public void perform(SCResponse paramSCResponse)
  {
    GetAdInfoSAXParserJson localGetAdInfoSAXParserJson = new GetAdInfoSAXParserJson();
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    try
    {
      ArrayList localArrayList = (ArrayList)localGetAdInfoSAXParserJson.parser(paramSCResponse.getContents());
      Logger.i("SccmsApiGetAdInfoTask", " result:" + localArrayList.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localArrayList);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiGetAdInfoTaskListener paramISccmsApiGetAdInfoTaskListener)
  {
    this.lsr = paramISccmsApiGetAdInfoTaskListener;
  }

  public static abstract interface ISccmsApiGetAdInfoTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, ArrayList<AdPos> paramArrayList);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiGetAdInfoTask
 * JD-Core Version:    0.6.2
 */