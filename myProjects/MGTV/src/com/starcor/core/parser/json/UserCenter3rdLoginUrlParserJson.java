package com.starcor.core.parser.json;

import com.starcor.core.domain.UserCenter3rdLoginUrl;
import com.starcor.core.interfaces.IXmlParser;
import com.starcor.core.utils.Logger;
import java.io.InputStream;
import org.json.JSONObject;

public class UserCenter3rdLoginUrlParserJson<Result>
  implements IXmlParser<Result>
{
  UserCenter3rdLoginUrl info = new UserCenter3rdLoginUrl();

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
        if (localJSONObject2.has("rcode"))
          this.info.rcode = localJSONObject2.getString("rcode");
        if (localJSONObject2.has("url"))
          this.info.url = localJSONObject2.getString("url");
        Logger.i("UserCenter3rdLoginUrlParserJson", "UserCenter3rdLoginUrlParserJson解析器解析得到的对象：UserCenter3rdLoginUrl=" + this.info.toString());
      }
      return this.info;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.json.UserCenter3rdLoginUrlParserJson
 * JD-Core Version:    0.6.2
 */