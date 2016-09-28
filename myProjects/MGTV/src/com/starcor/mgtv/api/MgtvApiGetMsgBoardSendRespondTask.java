package com.starcor.mgtv.api;

import com.starcor.core.domain.MessageBoardSendRepond;
import com.starcor.core.parser.json.GetMsgBoardSendRespondSAXParserJson;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
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
import org.json.JSONObject;

public class MgtvApiGetMsgBoardSendRespondTask extends ServerApiTask
{
  IMgtvApiGetMsgBoardSendRespondTaskListener lsr;
  String phoneUrl = "";

  public MgtvApiGetMsgBoardSendRespondTask(String paramString)
  {
    this.phoneUrl = paramString;
  }

  public HttpEntity getPostBody()
  {
    try
    {
      JSONObject localJSONObject = new JSONObject();
      String str = localJSONObject.toString();
      Logger.i("MgtvApiGetMsgBoardSendRespondTask", "获取系统消息接口请求参数：" + localJSONObject.toString());
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
    }
    return null;
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
    return "MgtvApiGetMsgBoardSendRespondTask";
  }

  public String getUrl()
  {
    return this.phoneUrl;
  }

  public void perform(SCResponse paramSCResponse)
  {
    GetMsgBoardSendRespondSAXParserJson localGetMsgBoardSendRespondSAXParserJson = new GetMsgBoardSendRespondSAXParserJson();
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    try
    {
      MessageBoardSendRepond localMessageBoardSendRepond = (MessageBoardSendRepond)localGetMsgBoardSendRespondSAXParserJson.parser(paramSCResponse.getContents());
      Logger.i("MgtvApiGetMsgBoardSendRespondTask", "GetMsgBoardSendRespondSAXParserJson :" + localMessageBoardSendRepond.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), paramSCResponse), localMessageBoardSendRepond);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.getMessage()));
    }
  }

  public void setListener(IMgtvApiGetMsgBoardSendRespondTaskListener paramIMgtvApiGetMsgBoardSendRespondTaskListener)
  {
    this.lsr = paramIMgtvApiGetMsgBoardSendRespondTaskListener;
  }

  public static abstract interface IMgtvApiGetMsgBoardSendRespondTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, MessageBoardSendRepond paramMessageBoardSendRepond);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.mgtv.api.MgtvApiGetMsgBoardSendRespondTask
 * JD-Core Version:    0.6.2
 */