package com.starcor.core.parser.json;

import com.starcor.core.interfaces.IXmlParser;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.msgsys.data.http.MQTTCredentials;
import com.starcor.hunan.msgsys.data.http.MQTTHttpPostData;
import com.starcor.hunan.msgsys.data.http.MQTTLevels;
import java.io.InputStream;
import org.json.JSONObject;

public class GetMQTTHttpPostParserJson<Result>
  implements IXmlParser<Result>
{
  private static final String TAG = GetMQTTHttpPostParserJson.class.getSimpleName();
  private MQTTHttpPostData mMqttHttpPostData = null;

  public Result parser(InputStream paramInputStream)
  {
    return null;
  }

  public Result parser(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte == null)
    {
      Logger.i(TAG, "parser() data 为 null");
      return null;
    }
    try
    {
      String str = new String(paramArrayOfByte);
      Logger.i(TAG, "jsonString:" + str);
      JSONObject localJSONObject1 = new JSONObject(str);
      if (localJSONObject1 != null)
      {
        if (localJSONObject1.has("client_id"))
        {
          this.mMqttHttpPostData.setClient_id(localJSONObject1.optString("client_id"));
          Logger.i(TAG, "client id = " + this.mMqttHttpPostData.getClient_id());
        }
        if (localJSONObject1.has("levels"))
        {
          JSONObject localJSONObject3 = localJSONObject1.optJSONObject("levels");
          if (localJSONObject3.has("message"))
            this.mMqttHttpPostData.getLevels().setMessage(localJSONObject3.optInt("message"));
          if (localJSONObject3.has("detail"))
            this.mMqttHttpPostData.getLevels().setDetail(localJSONObject3.optInt("detail"));
          if (localJSONObject3.has("page"))
            this.mMqttHttpPostData.getLevels().setPage(localJSONObject3.optInt("page"));
          if (localJSONObject3.has("video"))
            this.mMqttHttpPostData.getLevels().setVideo(localJSONObject3.optInt("video"));
          if (localJSONObject3.has("topic"))
            this.mMqttHttpPostData.getLevels().setTopic(localJSONObject3.optInt("topic"));
        }
        if (localJSONObject1.has("credentials"))
        {
          JSONObject localJSONObject2 = localJSONObject1.optJSONObject("credentials");
          if (localJSONObject2.has("username"))
            this.mMqttHttpPostData.getCredentials().setUsername(localJSONObject2.optString("username"));
          if (localJSONObject2.has("password"))
            this.mMqttHttpPostData.getCredentials().setPassword(localJSONObject2.optString("password"));
        }
        Logger.i(TAG, "解析完毕后的MQTTHttpData为" + this.mMqttHttpPostData.toString());
      }
      return this.mMqttHttpPostData;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.json.GetMQTTHttpPostParserJson
 * JD-Core Version:    0.6.2
 */