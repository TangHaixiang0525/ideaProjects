package com.starcor.core.parser.json;

import com.starcor.core.domain.UserCenterRegister;
import com.starcor.core.interfaces.IXmlParser;
import com.starcor.core.utils.Logger;
import java.io.InputStream;
import org.json.JSONObject;

public class GetUserCenterRegisterSAXParserJson<Result>
  implements IXmlParser<Result>
{
  UserCenterRegister info = new UserCenterRegister();

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
        str = localJSONObject1.getString("msg");
        if (this.info.err != 0)
          this.info.msg = str;
      }
      else
      {
        return this.info;
      }
    }
    catch (Exception localException1)
    {
      try
      {
        while (true)
        {
          JSONObject localJSONObject2 = new JSONObject(str);
          if (localJSONObject2.has("Account"))
            this.info.account = localJSONObject2.getString("Account");
          if (localJSONObject2.has("Email"))
            this.info.email = localJSONObject2.getString("Email");
          Logger.i("GetUserCenterRegisterSAXParserJson", "GetUserCenterRegisterSAXParserJson解析器解析得到的对象：UserCenterRigster=" + this.info.toString());
        }
        localException1 = localException1;
        localException1.printStackTrace();
      }
      catch (Exception localException2)
      {
        String str;
        this.info.reason = str;
        UserCenterRegister localUserCenterRegister = this.info;
        return localUserCenterRegister;
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.json.GetUserCenterRegisterSAXParserJson
 * JD-Core Version:    0.6.2
 */