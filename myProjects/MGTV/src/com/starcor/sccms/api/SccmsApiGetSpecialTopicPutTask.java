package com.starcor.sccms.api;

import com.starcor.core.domain.SpecialTopicPutInfo;
import com.starcor.core.epgapi.params.GetSpecialTopicPutParams;
import com.starcor.core.parser.json.GetSpecialTopicPutSAXParserJson;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;
import java.util.ArrayList;

public class SccmsApiGetSpecialTopicPutTask extends ServerApiTask
{
  ISccmsApiGetSpecialTopicPutTaskListener lsr = null;
  private String nns_category_id = "";
  private String nns_packet_id;
  private String nns_packet_type;
  private String nns_special_id = "";

  public SccmsApiGetSpecialTopicPutTask(String paramString1, String paramString2)
  {
    this.nns_packet_id = paramString1;
    this.nns_packet_type = paramString2;
    setCacheLife(1800000L);
  }

  public SccmsApiGetSpecialTopicPutTask(String paramString1, String paramString2, String paramString3)
  {
    this(paramString2, paramString3);
    this.nns_special_id = paramString1;
  }

  public SccmsApiGetSpecialTopicPutTask(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    this(paramString1, paramString2, paramString3);
    this.nns_category_id = paramString4;
  }

  public String getTaskName()
  {
    return "N24_A_4";
  }

  public String getUrl()
  {
    GetSpecialTopicPutParams localGetSpecialTopicPutParams = new GetSpecialTopicPutParams(this.nns_special_id, this.nns_packet_id, this.nns_packet_type, this.nns_category_id);
    localGetSpecialTopicPutParams.setResultFormat(1);
    return webUrlFormatter.i().formatUrl(localGetSpecialTopicPutParams.toString(), localGetSpecialTopicPutParams.getApiName());
  }

  public void perform(SCResponse paramSCResponse)
  {
    GetSpecialTopicPutSAXParserJson localGetSpecialTopicPutSAXParserJson = new GetSpecialTopicPutSAXParserJson();
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    try
    {
      ArrayList localArrayList = (ArrayList)localGetSpecialTopicPutSAXParserJson.parser(paramSCResponse.getContents());
      Logger.i("SccmsApiGetSpecialTopicPutTask", " result:" + localArrayList.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localArrayList);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiGetSpecialTopicPutTaskListener paramISccmsApiGetSpecialTopicPutTaskListener)
  {
    this.lsr = paramISccmsApiGetSpecialTopicPutTaskListener;
  }

  public static abstract interface ISccmsApiGetSpecialTopicPutTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, ArrayList<SpecialTopicPutInfo> paramArrayList);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiGetSpecialTopicPutTask
 * JD-Core Version:    0.6.2
 */