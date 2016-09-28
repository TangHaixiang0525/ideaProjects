package com.starcor.core.parser.json;

import com.starcor.core.domain.MobileCode;
import com.starcor.core.interfaces.IXmlParser;
import com.starcor.core.utils.Logger;
import java.io.InputStream;
import org.json.JSONObject;

public class GetMobileCodeSAXParserJson<Result>
  implements IXmlParser<Result>
{
  MobileCode info = new MobileCode();

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
      String str;
      if (localJSONObject1.has("msg"))
      {
        str = localJSONObject1.getString("msg");
        if (this.info.err == 0)
          break label210;
        this.info.msg = str;
      }
      while (true)
      {
        Logger.i("GetMobileMsgAuthSAXParserJson", "GetMobileMsgAuthSAXParserJson解析器解析得到的对象：info=" + this.info.toString());
        return this.info;
        label210: JSONObject localJSONObject2 = new JSONObject(str);
        if (localJSONObject2.has("count"))
          this.info.count = Integer.valueOf(localJSONObject2.getString("count")).intValue();
      }
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.json.GetMobileCodeSAXParserJson
 * JD-Core Version:    0.6.2
 */