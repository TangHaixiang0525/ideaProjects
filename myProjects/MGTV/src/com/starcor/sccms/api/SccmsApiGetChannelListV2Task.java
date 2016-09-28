package com.starcor.sccms.api;

import com.starcor.core.domain.ChannelInfoV2;
import com.starcor.core.epgapi.params.GetChannelListV2Params;
import com.starcor.core.parser.json.GetChannelListV2SAXParserJson;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCachedTask;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;
import java.util.ArrayList;

public class SccmsApiGetChannelListV2Task extends ServerApiCachedTask
{
  ISccmsApiGetChannelListV2TaskListener lsr;
  String nns_category_id = null;
  String nns_media_assets_id = null;

  public SccmsApiGetChannelListV2Task(String paramString1, String paramString2)
  {
    this.nns_media_assets_id = paramString1;
    this.nns_category_id = paramString2;
    setCacheLife(1800000L);
  }

  public String getTaskName()
  {
    return "N39_A_1";
  }

  public String getUrl()
  {
    GetChannelListV2Params localGetChannelListV2Params = new GetChannelListV2Params(this.nns_media_assets_id, this.nns_category_id);
    localGetChannelListV2Params.setResultFormat(1);
    return webUrlFormatter.i().formatUrl(localGetChannelListV2Params.toString(), localGetChannelListV2Params.getApiName());
  }

  protected byte[] localPerform(SCResponse paramSCResponse)
  {
    GetChannelListV2SAXParserJson localGetChannelListV2SAXParserJson = new GetChannelListV2SAXParserJson();
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return null;
    }
    try
    {
      ChannelInfoV2 localChannelInfoV2 = (ChannelInfoV2)localGetChannelListV2SAXParserJson.parser(paramSCResponse.getContents());
      if (localChannelInfoV2.channelList.size() < 1)
        Logger.e("SccmsApiGetChannelListV2Task", "返回错误的结果数");
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localChannelInfoV2);
      byte[] arrayOfByte = paramSCResponse.getContents();
      return arrayOfByte;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
    return null;
  }

  public void perform(SCResponse paramSCResponse)
  {
    byte[] arrayOfByte = localPerform(paramSCResponse);
    if (arrayOfByte != null)
      serializeApiData(arrayOfByte);
  }

  public void setListener(ISccmsApiGetChannelListV2TaskListener paramISccmsApiGetChannelListV2TaskListener)
  {
    this.lsr = paramISccmsApiGetChannelListV2TaskListener;
  }

  public static abstract interface ISccmsApiGetChannelListV2TaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, ChannelInfoV2 paramChannelInfoV2);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiGetChannelListV2Task
 * JD-Core Version:    0.6.2
 */