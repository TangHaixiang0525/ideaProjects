package com.starcor.core.parser.json;

import com.starcor.core.domain.UserCenterCheckReturn;
import com.starcor.core.interfaces.IXmlParser;
import com.starcor.core.utils.Logger;
import java.io.InputStream;
import org.json.JSONObject;

public class UserCenterCheckReturnSAXParserJson<Result>
  implements IXmlParser<Result>
{
  private UserCenterCheckReturn cr;

  public Result parser(InputStream paramInputStream)
  {
    return null;
  }

  public Result parser(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte == null)
      return null;
    this.cr = new UserCenterCheckReturn();
    try
    {
      String str = new String(paramArrayOfByte);
      JSONObject localJSONObject1 = new JSONObject(str);
      Logger.i("UserCenterCheckReturn jsonString=" + str);
      if (localJSONObject1.has("state"))
        this.cr.state = localJSONObject1.getString("state");
      if (localJSONObject1.has("reason"))
        this.cr.reason = localJSONObject1.getString("reason");
      if (localJSONObject1.has("data"))
        localJSONObject1 = new JSONObject(localJSONObject1.getString("data"));
      if (localJSONObject1.has("err"))
        this.cr.err = Integer.valueOf(localJSONObject1.getString("err")).intValue();
      if (localJSONObject1.has("status"))
        this.cr.status = localJSONObject1.getString("status");
      JSONObject localJSONObject2;
      if (localJSONObject1.has("msg"))
      {
        localJSONObject2 = new JSONObject(localJSONObject1.getString("msg"));
        if (localJSONObject2.has("ticket"))
          this.cr.ticket = localJSONObject2.getString("ticket");
        boolean bool = localJSONObject2.has("expire");
        if (!bool);
      }
      try
      {
        this.cr.expire = Integer.valueOf(localJSONObject2.getString("expire")).intValue();
        if (localJSONObject2.has("userinfo"))
        {
          JSONObject localJSONObject3 = new JSONObject(localJSONObject2.getString("userinfo"));
          if (localJSONObject3.has("loginaccount"))
            this.cr.loginaccount = localJSONObject3.getString("loginaccount");
          if (localJSONObject3.has("uid"))
            this.cr.uid = localJSONObject3.getString("uid");
          if (localJSONObject3.has("mobile"))
            this.cr.mobile = localJSONObject3.getString("mobile");
          if (localJSONObject3.has("logintype"))
            this.cr.logintype = localJSONObject3.getString("logintype");
          if (localJSONObject3.has("rtype"))
            this.cr.rtype = localJSONObject3.getString("rtype");
          if (localJSONObject3.has("nickname"))
            this.cr.nickname = localJSONObject3.getString("nickname");
          if (localJSONObject3.has("wechat_type"))
            this.cr.wechat_type = localJSONObject3.getString("wechat_type");
          if (localJSONObject3.has("email"))
            this.cr.email = localJSONObject3.getString("email");
          if (localJSONObject3.has("avatar"))
            this.cr.avatar = localJSONObject3.getString("avatar");
        }
        label498: Logger.i("UserCenterCheckReturnSAXParserJson", "UserCenterCheckReturnSAXParserJson解析器解析得到的对象：cr=" + this.cr.toString());
        return this.cr;
      }
      catch (Exception localException2)
      {
        while (true)
          this.cr.expire = 0;
      }
    }
    catch (Exception localException1)
    {
      break label498;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.json.UserCenterCheckReturnSAXParserJson
 * JD-Core Version:    0.6.2
 */