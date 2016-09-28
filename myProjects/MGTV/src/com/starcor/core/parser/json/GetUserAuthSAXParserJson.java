package com.starcor.core.parser.json;

import android.util.Log;
import com.starcor.core.domain.UserAuth;
import com.starcor.core.interfaces.IXmlParser;
import java.io.InputStream;
import org.json.JSONObject;

public class GetUserAuthSAXParserJson<Result>
  implements IXmlParser<Result>
{
  private static final String TAG = "GetUserAuthSAXParserJson";
  private UserAuth userAuth = new UserAuth();

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
      JSONObject localJSONObject = new JSONObject(new String(paramArrayOfByte));
      boolean bool = localJSONObject.has("state");
      if (bool);
      try
      {
        this.userAuth.setState(Integer.parseInt(localJSONObject.getString("state")));
        if (localJSONObject.has("reason"))
          this.userAuth.setReason(localJSONObject.getString("reason"));
        if (localJSONObject.has("order_url"))
          this.userAuth.setOrder_url(localJSONObject.getString("order_url"));
        Log.e("GetUserAuthSAXParserJson", "GetUserAuthSAXParserJson解析器解析得到的对象：userAuth=" + this.userAuth);
        return this.userAuth;
      }
      catch (Exception localException2)
      {
        while (true)
          this.userAuth.setState(0);
      }
    }
    catch (Exception localException1)
    {
      while (true)
        localException1.printStackTrace();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.json.GetUserAuthSAXParserJson
 * JD-Core Version:    0.6.2
 */