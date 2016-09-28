package com.starcor.mgtv.api;

import com.starcor.core.parser.json.GetMQTTHttpPostParserJson;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.hunan.msgsys.data.http.MQTTHttpPostData;
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

public class MgtvApiGetMQTTClientInfoTask extends ServerApiTask
{
  private static final String TAG = MgtvApiGetMQTTClientInfoTask.class.getSimpleName();
  private IMgtvApiGetMQTTClientInfoTaskListener lsr;
  private String mMac = "";
  private String mStrategyNO = "";
  private String mVersion = "";

  public MgtvApiGetMQTTClientInfoTask(String paramString1, String paramString2, String paramString3)
  {
    this.mStrategyNO = paramString1;
    this.mVersion = paramString2;
    this.mMac = paramString3;
  }

  public HttpEntity getPostBody()
  {
    try
    {
      JSONObject localJSONObject = new JSONObject();
      localJSONObject.put("operator_strategy_no", this.mStrategyNO);
      localJSONObject.put("version", this.mVersion);
      localJSONObject.put("mac", this.mMac);
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
    return TAG;
  }

  public String getUrl()
  {
    return WebUrlBuilder.getMessageSystemV3HttpUrl();
  }

  public void perform(SCResponse paramSCResponse)
  {
    GetMQTTHttpPostParserJson localGetMQTTHttpPostParserJson = new GetMQTTHttpPostParserJson();
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    try
    {
      MQTTHttpPostData localMQTTHttpPostData = (MQTTHttpPostData)localGetMQTTHttpPostParserJson.parser(paramSCResponse.getContents());
      Logger.i(TAG, "parse后得到结果为" + localMQTTHttpPostData.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), paramSCResponse), localMQTTHttpPostData);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.getMessage()));
    }
  }

  public void setListener(IMgtvApiGetMQTTClientInfoTaskListener paramIMgtvApiGetMQTTClientInfoTaskListener)
  {
    this.lsr = paramIMgtvApiGetMQTTClientInfoTaskListener;
  }

  public static abstract interface IMgtvApiGetMQTTClientInfoTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, MQTTHttpPostData paramMQTTHttpPostData);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.mgtv.api.MgtvApiGetMQTTClientInfoTask
 * JD-Core Version:    0.6.2
 */