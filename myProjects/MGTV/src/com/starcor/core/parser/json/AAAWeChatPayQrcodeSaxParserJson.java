package com.starcor.core.parser.json;

import com.starcor.core.domain.AAAWechatPayQrcode;
import com.starcor.core.interfaces.IXmlParser;
import com.starcor.core.utils.Logger;
import java.io.InputStream;
import org.json.JSONException;
import org.json.JSONObject;

public class AAAWeChatPayQrcodeSaxParserJson<Result>
  implements IXmlParser<Result>
{
  private AAAWechatPayQrcode result;

  public Result parser(InputStream paramInputStream)
  {
    return null;
  }

  public Result parser(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte == null)
      return null;
    String str = new String(paramArrayOfByte);
    try
    {
      JSONObject localJSONObject1 = new JSONObject(str);
      this.result = new AAAWechatPayQrcode();
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
          JSONObject localJSONObject3 = new JSONObject(localJSONObject2.getString("msg"));
          if (localJSONObject3.has("pic_url"))
            this.result.url = localJSONObject3.getString("pic_url");
        }
      }
      Logger.i("AAAWeChatPayQrcodeSaxParserJson", "AAAWeChatPayQrcodeSaxParserJson解析器解析得到的对象：result=" + this.result.toString());
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
 * Qualified Name:     com.starcor.core.parser.json.AAAWeChatPayQrcodeSaxParserJson
 * JD-Core Version:    0.6.2
 */