package com.starcor.mgtv.api;

import com.starcor.core.domain.MessageBoardData;
import com.starcor.core.parser.json.GetMessageBoardDataSAXParserJson;
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
import org.json.JSONObject;

public class MgtvApiGetMessageBoardDataTask extends ServerApiTask
{
  IMgtvApiGetMessageBoardDataTaskListener lsr;

  public HttpEntity getPostBody()
  {
    try
    {
      JSONObject localJSONObject = new JSONObject();
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
    return "MgtvApiGetMessageBoardDataTask";
  }

  public String getUrl()
  {
    return WebUrlBuilder.getMessageBoardDataUrl();
  }

  public void perform(SCResponse paramSCResponse)
  {
    GetMessageBoardDataSAXParserJson localGetMessageBoardDataSAXParserJson = new GetMessageBoardDataSAXParserJson();
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    try
    {
      MessageBoardData localMessageBoardData = (MessageBoardData)localGetMessageBoardDataSAXParserJson.parser(paramSCResponse.getContents());
      Logger.i("MgtvApiGetMessageBoardDataTask", ", result.messageBoardDataList :" + localMessageBoardData.messageBoardDataList);
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), paramSCResponse), localMessageBoardData);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.getMessage()));
    }
  }

  public void setListener(IMgtvApiGetMessageBoardDataTaskListener paramIMgtvApiGetMessageBoardDataTaskListener)
  {
    this.lsr = paramIMgtvApiGetMessageBoardDataTaskListener;
  }

  public static abstract interface IMgtvApiGetMessageBoardDataTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, MessageBoardData paramMessageBoardData);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.mgtv.api.MgtvApiGetMessageBoardDataTask
 * JD-Core Version:    0.6.2
 */