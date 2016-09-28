package com.starcor.mgtv.api;

import com.starcor.core.domain.SystemMessage;
import com.starcor.core.parser.json.GetSystemMessageSAXParserJson;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.WebUrlBuilder;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;
import java.io.UnsupportedEncodingException;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

public class MgtvApiGetSystemMessageTask extends ServerApiTask
{
  String handler = "";
  IMgtvApiGetSystemMessageTaskListener lsr;
  String mac = "";
  String messageID = "";
  String platform = "";
  String version = "";

  public MgtvApiGetSystemMessageTask(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    this.messageID = paramString1;
    this.handler = paramString2;
    this.version = paramString3;
    this.mac = paramString4;
    this.platform = "itv";
  }

  public HttpEntity getPostBody()
  {
    try
    {
      JSONObject localJSONObject = new JSONObject();
      localJSONObject.put("platform", this.platform);
      localJSONObject.put("lmsgid", this.messageID);
      localJSONObject.put("handler", this.handler);
      localJSONObject.put("version", this.version);
      localJSONObject.put("macid", this.mac);
      String str = localJSONObject.toString();
      Logger.i("MgtvApiGetSystemMessageTask", "获取系统消息接口请求参数：" + localJSONObject.toString());
      StringEntity local1 = new StringEntity(str)
      {
        public Header getContentType()
        {
          return ServerApiTools.buildHttpHeader("Content-Type", "application/json;charset=utf-8");
        }
      };
      return local1;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      localUnsupportedEncodingException.printStackTrace();
      return null;
    }
    catch (JSONException localJSONException)
    {
      while (true)
        localJSONException.printStackTrace();
    }
  }

  public List<NameValuePair> getPostForm()
  {
    return null;
  }

  public int getRequestMethod()
  {
    return 1;
  }

  public String getTaskName()
  {
    return "MgtvApiGetSystemMessageTask";
  }

  public String getUrl()
  {
    return WebUrlBuilder.getSystemMessageUrl();
  }

  public void perform(SCResponse paramSCResponse)
  {
    GetSystemMessageSAXParserJson localGetSystemMessageSAXParserJson = new GetSystemMessageSAXParserJson();
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    try
    {
      SystemMessage localSystemMessage = (SystemMessage)localGetSystemMessageSAXParserJson.parser(paramSCResponse.getContents());
      if (localSystemMessage.errorCode != 0)
        Logger.e("MgtvApiGetSystemMessageTask", "返回错误");
      Logger.i("MgtvApiGetSystemMessageTask", " result.errorCode:" + localSystemMessage.errorCode + ", result.errorMsg:" + localSystemMessage.errorMsg);
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), paramSCResponse), localSystemMessage);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.getMessage()));
    }
  }

  public void setListener(IMgtvApiGetSystemMessageTaskListener paramIMgtvApiGetSystemMessageTaskListener)
  {
    this.lsr = paramIMgtvApiGetSystemMessageTaskListener;
  }

  public static abstract interface IMgtvApiGetSystemMessageTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, SystemMessage paramSystemMessage);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.mgtv.api.MgtvApiGetSystemMessageTask
 * JD-Core Version:    0.6.2
 */