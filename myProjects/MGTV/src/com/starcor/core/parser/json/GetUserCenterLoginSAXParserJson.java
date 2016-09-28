package com.starcor.core.parser.json;

import com.starcor.core.domain.UserCenterLogin;
import com.starcor.core.interfaces.IXmlParser;
import com.starcor.core.utils.Logger;
import java.io.InputStream;
import org.json.JSONObject;

public class GetUserCenterLoginSAXParserJson<Result>
  implements IXmlParser<Result>
{
  UserCenterLogin info = new UserCenterLogin();

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
      String str1 = new String(paramArrayOfByte);
      JSONObject localJSONObject1 = new JSONObject(str1);
      Logger.i("GetUserCenterLoginSAXParserJson", "jsonString=" + str1);
      if (localJSONObject1.has("state"))
        this.info.state = localJSONObject1.getString("state");
      if (localJSONObject1.has("reason"))
        this.info.reason = localJSONObject1.getString("reason");
      String str2;
      if (localJSONObject1.has("data"))
      {
        JSONObject localJSONObject2 = new JSONObject(localJSONObject1.getString("data"));
        Logger.i("GetUserCenterLoginSAXParserJson", "jsData=" + localJSONObject2.toString());
        if (localJSONObject2.has("err"))
          this.info.err = Integer.valueOf(localJSONObject2.getString("err")).intValue();
        if (localJSONObject2.has("status"))
          this.info.status = localJSONObject2.getString("status");
        if (localJSONObject2.has("msg"))
        {
          str2 = localJSONObject2.getString("msg");
          if (this.info.err == 0)
            break label271;
          this.info.msg = str2;
        }
      }
      while (true)
      {
        Logger.i("GetUserCenterLoginSAXParserJson", "GetUserCenterLoginSAXParserJson解析器解析得到的对象：UserCenterLogInInfo=" + this.info.toString());
        return this.info;
        label271: JSONObject localJSONObject3 = new JSONObject(str2);
        Logger.i("GetUserCenterLoginSAXParserJson", "jsMsg=" + localJSONObject3.toString());
        if (localJSONObject3.has("ticket"))
          this.info.ticket = localJSONObject3.getString("ticket");
        if (localJSONObject3.has("expire"))
          this.info.expire = localJSONObject3.getInt("expire");
        if (localJSONObject3.has("userinfo"))
        {
          JSONObject localJSONObject4 = new JSONObject(localJSONObject3.getString("userinfo"));
          Logger.i("GetUserCenterLoginSAXParserJson", " jsUser=" + localJSONObject4.toString());
          if (localJSONObject4.has("loginaccount"))
            this.info.loginaccount = localJSONObject4.getString("loginaccount");
          if (localJSONObject4.has("uid"))
            this.info.uid = localJSONObject4.getString("uid");
          if (localJSONObject4.has("rtype"))
            this.info.rtype = localJSONObject4.getString("rtype");
          if (localJSONObject4.has("mobile"))
            this.info.mobile = localJSONObject4.getString("mobile");
          if (localJSONObject4.has("logintype"))
            this.info.logintype = localJSONObject4.getString("logintype");
          if (localJSONObject4.has("nickname"))
            this.info.nickname = localJSONObject4.getString("nickname");
          if (localJSONObject4.has("email"))
            this.info.email = localJSONObject4.getString("email");
          if (localJSONObject4.has("avatar"))
            this.info.avatar = localJSONObject4.getString("avatar");
          if (localJSONObject4.has("wechat_type"))
            this.info.wechat_type = localJSONObject4.getString("wechat_type");
          if (localJSONObject4.has("access_token"))
            this.info.mi_access_token = localJSONObject4.getString("access_token");
          if (localJSONObject4.has("mac_key"))
            this.info.mi_mac_key = localJSONObject4.getString("mac_key");
          if (localJSONObject4.has("mi_userid"))
            this.info.mi_userid = localJSONObject4.getString("mi_userid");
          if (localJSONObject4.has("mi_email"))
            this.info.mi_email = localJSONObject4.getString("mi_email");
          if (localJSONObject4.has("mi_mobile"))
            this.info.mi_mobile = localJSONObject4.getString("mi_mobile");
        }
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
 * Qualified Name:     com.starcor.core.parser.json.GetUserCenterLoginSAXParserJson
 * JD-Core Version:    0.6.2
 */