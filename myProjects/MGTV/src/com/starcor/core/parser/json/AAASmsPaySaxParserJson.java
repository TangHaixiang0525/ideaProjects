package com.starcor.core.parser.json;

import com.starcor.core.domain.AAASmsPay;
import com.starcor.core.interfaces.IXmlParser;
import com.starcor.core.utils.Logger;
import java.io.InputStream;
import org.json.JSONException;
import org.json.JSONObject;

public class AAASmsPaySaxParserJson<Result>
  implements IXmlParser<Result>
{
  private AAASmsPay result;

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
      this.result = new AAASmsPay();
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
          if (localJSONObject3.has("type"))
            this.result.type = localJSONObject3.getString("type");
          if (localJSONObject3.has("channel"))
            this.result.channel = localJSONObject3.getString("channel");
          if (localJSONObject3.has("product_id"))
            this.result.product_id = localJSONObject3.getString("product_id");
          if (localJSONObject3.has("product_name"))
            this.result.product_name = localJSONObject3.getString("product_name");
          if (localJSONObject3.has("product_id"))
            this.result.price = localJSONObject3.getString("price");
        }
      }
      Logger.i("AAASmsPaySaxParserJson", "AAASmsPaySaxParserJson：result=" + this.result.toString());
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
 * Qualified Name:     com.starcor.core.parser.json.AAASmsPaySaxParserJson
 * JD-Core Version:    0.6.2
 */