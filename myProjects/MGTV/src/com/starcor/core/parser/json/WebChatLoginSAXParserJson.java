package com.starcor.core.parser.json;

import com.starcor.core.domain.WebChatLogin;
import com.starcor.core.interfaces.IXmlParser;
import java.io.InputStream;
import org.json.JSONException;
import org.json.JSONObject;

public class WebChatLoginSAXParserJson<Result>
  implements IXmlParser<Result>
{
  WebChatLogin info = new WebChatLogin();

  public Result parser(InputStream paramInputStream)
  {
    return null;
  }

  public Result parser(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte == null)
      return null;
    try
    {
      JSONObject localJSONObject1 = new JSONObject(new String(paramArrayOfByte));
      if (localJSONObject1.has("state"))
        this.info.state = localJSONObject1.getString("state");
      if (localJSONObject1.has("reason"))
        this.info.reason = localJSONObject1.getString("reason");
      if (localJSONObject1.has("data"))
        localJSONObject1 = new JSONObject(localJSONObject1.getString("data"));
      if (localJSONObject1.has("err"))
        this.info.err = Integer.valueOf(localJSONObject1.getString("err")).intValue();
      if (localJSONObject1.has("status"))
        this.info.status = localJSONObject1.getString("status");
      if (localJSONObject1.has("msg"))
      {
        JSONObject localJSONObject2 = new JSONObject(localJSONObject1.getString("msg"));
        if (localJSONObject2.has("scene_id"))
          this.info.scene_id = localJSONObject2.getString("scene_id");
        if (localJSONObject2.has("url"))
          this.info.url = localJSONObject2.getString("url");
        if (localJSONObject2.has("rcode"))
          this.info.rcode = localJSONObject2.getString("rcode");
      }
      return this.info;
    }
    catch (JSONException localJSONException)
    {
      while (true)
        localJSONException.printStackTrace();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.json.WebChatLoginSAXParserJson
 * JD-Core Version:    0.6.2
 */