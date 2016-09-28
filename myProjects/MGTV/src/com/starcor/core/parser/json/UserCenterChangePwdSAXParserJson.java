package com.starcor.core.parser.json;

import com.starcor.core.domain.UserCenterChangePwd;
import com.starcor.core.interfaces.IXmlParser;
import com.starcor.core.utils.Logger;
import java.io.InputStream;
import org.json.JSONObject;

public class UserCenterChangePwdSAXParserJson<Result>
  implements IXmlParser<Result>
{
  UserCenterChangePwd info = new UserCenterChangePwd();

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
          break label180;
        this.info.msg = str;
      }
      while (true)
      {
        return this.info;
        label180: JSONObject localJSONObject2 = new JSONObject(str);
        if (localJSONObject2.has("operation"))
          this.info.operation = localJSONObject2.getString("operation");
        Logger.i("UserCenterChangePwdSAXParserJson", "UserCenterChangePwdSAXParserJson解析器解析得到的对象：UserCenterChangePwd=" + this.info.toString());
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
 * Qualified Name:     com.starcor.core.parser.json.UserCenterChangePwdSAXParserJson
 * JD-Core Version:    0.6.2
 */