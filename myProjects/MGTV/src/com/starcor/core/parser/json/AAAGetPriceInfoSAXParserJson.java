package com.starcor.core.parser.json;

import com.starcor.core.domain.AAAPriceInfo;
import com.starcor.core.interfaces.IXmlParser;
import com.starcor.core.utils.Logger;
import java.io.InputStream;
import org.json.JSONObject;

public class AAAGetPriceInfoSAXParserJson<Result>
  implements IXmlParser<Result>
{
  public Result parser(InputStream paramInputStream)
  {
    return null;
  }

  public Result parser(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte == null)
      return null;
    AAAPriceInfo localAAAPriceInfo = new AAAPriceInfo();
    try
    {
      JSONObject localJSONObject1 = new JSONObject(new String(paramArrayOfByte));
      Logger.i("AAAGetPriceInfoSAXParserJson", "jsRt=" + localJSONObject1.toString());
      if (localJSONObject1.has("state"))
        localAAAPriceInfo.state = localJSONObject1.getString("state");
      if (localJSONObject1.has("reason"))
        localAAAPriceInfo.reason = localJSONObject1.getString("reason");
      if (localJSONObject1.has("data"))
      {
        JSONObject localJSONObject2 = new JSONObject(localJSONObject1.getString("data"));
        Logger.i("AAAGetPriceInfoSAXParserJson", "joData=" + localJSONObject2.toString());
        if (localJSONObject2.has("err"))
          localAAAPriceInfo.err = Integer.valueOf(localJSONObject2.getString("err")).intValue();
        if (localJSONObject2.has("status"))
          localAAAPriceInfo.status = localJSONObject2.getString("status");
        if (localJSONObject2.has("msg"))
        {
          JSONObject localJSONObject3 = new JSONObject(localJSONObject2.getString("msg"));
          Logger.i("AAAGetPriceInfoSAXParserJson", "joMsg=" + localJSONObject3.toString());
          if (localJSONObject3.has("price"))
            localAAAPriceInfo.price = Integer.valueOf(localJSONObject3.getString("price")).intValue();
        }
      }
      Logger.i("AAAGetPriceInfoSAXParserJson", "AAAGetPriceInfoSAXParserJson解析器解析得到的对象：pi=" + localAAAPriceInfo.toString());
      return localAAAPriceInfo;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.json.AAAGetPriceInfoSAXParserJson
 * JD-Core Version:    0.6.2
 */