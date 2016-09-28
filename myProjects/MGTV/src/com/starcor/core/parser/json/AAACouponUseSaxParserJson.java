package com.starcor.core.parser.json;

import com.starcor.core.domain.AAACouponUse;
import com.starcor.core.interfaces.IXmlParser;
import com.starcor.core.utils.Logger;
import java.io.InputStream;
import org.json.JSONException;
import org.json.JSONObject;

public class AAACouponUseSaxParserJson<Result>
  implements IXmlParser<Result>
{
  private AAACouponUse result;

  public Result parser(InputStream paramInputStream)
  {
    return null;
  }

  public Result parser(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte == null)
      return null;
    String str1 = new String(paramArrayOfByte);
    try
    {
      JSONObject localJSONObject1 = new JSONObject(str1);
      this.result = new AAACouponUse();
      if (localJSONObject1.has("state"))
        this.result.state = localJSONObject1.getInt("state");
      if (localJSONObject1.has("reason"))
        this.result.reason = localJSONObject1.getString("reason");
      if (localJSONObject1.has("data"))
      {
        JSONObject localJSONObject2 = new JSONObject(localJSONObject1.getString("data"));
        if (localJSONObject2.has("err"))
          this.result.err = localJSONObject2.getInt("err");
        if (localJSONObject2.has("status"))
          this.result.status = localJSONObject2.getString("status");
        if (localJSONObject2.has("msg"))
        {
          String str2 = localJSONObject2.getString("msg");
          this.result.msg = str2;
          JSONObject localJSONObject3 = new JSONObject(str2);
          if (localJSONObject3.has("days"))
            this.result.days = localJSONObject3.getString("days");
          if (localJSONObject3.has("start_date"))
            this.result.start_date = localJSONObject3.getString("start_date");
          if (localJSONObject3.has("end_date"))
            this.result.end_date = localJSONObject3.getString("end_date");
        }
      }
      Logger.i("AAACouponUseSaxParserJson", "AAACouponUseSaxParserJson解析器解析得到的对象：result=" + this.result.toString());
      return this.result;
    }
    catch (JSONException localJSONException)
    {
      while (true)
        localJSONException.printStackTrace();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.json.AAACouponUseSaxParserJson
 * JD-Core Version:    0.6.2
 */