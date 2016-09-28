package com.starcor.core.parser.json;

import com.starcor.core.domain.AAAUseMovieCouponResult;
import com.starcor.core.interfaces.IXmlParser;
import com.starcor.core.utils.Logger;
import java.io.InputStream;
import org.json.JSONException;
import org.json.JSONObject;

public class AAAUseMovieCouponSAXParseJson<Result>
  implements IXmlParser<Result>
{
  private AAAUseMovieCouponResult info = new AAAUseMovieCouponResult();

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
      String str = new String(paramArrayOfByte);
      Logger.i("AAAUseMovieCouponSAXParseJson", "jsonString=" + str);
      JSONObject localJSONObject1 = new JSONObject(str);
      if (localJSONObject1.has("state"))
        this.info.state = localJSONObject1.getString("state");
      if (localJSONObject1.has("reason"))
        this.info.reason = localJSONObject1.getString("reason");
      JSONObject localJSONObject2;
      if (localJSONObject1.has("data"))
      {
        localJSONObject2 = new JSONObject(localJSONObject1.getString("data"));
        boolean bool = localJSONObject2.has("err");
        if (!bool);
      }
      try
      {
        this.info.err = Integer.valueOf(localJSONObject2.getString("err")).intValue();
        if (localJSONObject2.has("status"))
          this.info.status = localJSONObject2.getString("status");
        if (localJSONObject2.has("msg"))
        {
          JSONObject localJSONObject3 = new JSONObject(localJSONObject2.getString("msg"));
          if (localJSONObject3.has("coupon_id"))
            this.info.coupon_id = localJSONObject3.getString("coupon_id");
        }
        return this.info;
      }
      catch (Exception localException)
      {
        while (true)
          this.info.err = 0;
      }
    }
    catch (JSONException localJSONException)
    {
      while (true)
        localJSONException.printStackTrace();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.json.AAAUseMovieCouponSAXParseJson
 * JD-Core Version:    0.6.2
 */