package com.starcor.sccms.api;

import com.starcor.core.domain.SpecialTopicTreeInfo;
import com.starcor.core.epgapi.params.GetSpecialTopicColumnTreePaeams;
import com.starcor.core.parser.json.GetSpecialTopicColumnTreeSAXParserJson;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsApiGetSpecialTopicColumnTreeTask extends ServerApiTask
{
  ISccmsApiGetSpecialTopicColumnTreeTaskListener lsr = null;
  private String nns_special_id;

  public SccmsApiGetSpecialTopicColumnTreeTask(String paramString)
  {
    this.nns_special_id = paramString;
    setCacheLife(1800000L);
  }

  public String getTaskName()
  {
    return "N24_A_1";
  }

  public String getUrl()
  {
    GetSpecialTopicColumnTreePaeams localGetSpecialTopicColumnTreePaeams = new GetSpecialTopicColumnTreePaeams(this.nns_special_id);
    localGetSpecialTopicColumnTreePaeams.setResultFormat(1);
    return webUrlFormatter.i().formatUrl(localGetSpecialTopicColumnTreePaeams.toString(), localGetSpecialTopicColumnTreePaeams.getApiName());
  }

  public void perform(SCResponse paramSCResponse)
  {
    GetSpecialTopicColumnTreeSAXParserJson localGetSpecialTopicColumnTreeSAXParserJson = new GetSpecialTopicColumnTreeSAXParserJson();
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    try
    {
      SpecialTopicTreeInfo localSpecialTopicTreeInfo = (SpecialTopicTreeInfo)localGetSpecialTopicColumnTreeSAXParserJson.parser(paramSCResponse.getContents());
      Logger.i("SccmsApiGetSpecialTopicColumnTreeTask", " result:" + localSpecialTopicTreeInfo.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localSpecialTopicTreeInfo);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiGetSpecialTopicColumnTreeTaskListener paramISccmsApiGetSpecialTopicColumnTreeTaskListener)
  {
    this.lsr = paramISccmsApiGetSpecialTopicColumnTreeTaskListener;
  }

  public static abstract interface ISccmsApiGetSpecialTopicColumnTreeTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, SpecialTopicTreeInfo paramSpecialTopicTreeInfo);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiGetSpecialTopicColumnTreeTask
 * JD-Core Version:    0.6.2
 */