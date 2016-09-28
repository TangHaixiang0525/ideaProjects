package com.starcor.core.parser.json;

import com.starcor.core.domain.AAAProductInfo;
import com.starcor.core.interfaces.IXmlParser;
import com.starcor.core.utils.Logger;
import java.io.InputStream;
import org.json.JSONObject;

public class AAAGetProductInfoSAXParserJson<Result>
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
    AAAProductInfo localAAAProductInfo = new AAAProductInfo();
    try
    {
      JSONObject localJSONObject1 = new JSONObject(new String(paramArrayOfByte));
      Logger.i("AAAGetProductInfoSAXParserJson", "jo=" + localJSONObject1.toString());
      if (localJSONObject1.has("state"))
        localAAAProductInfo.state = localJSONObject1.getString("state");
      if (localJSONObject1.has("reason"))
        localAAAProductInfo.reason = localJSONObject1.getString("reason");
      if (localJSONObject1.has("data"))
      {
        JSONObject localJSONObject2 = new JSONObject(localJSONObject1.getString("data"));
        Logger.i("AAAGetProductInfoSAXParserJson", "joData=" + localJSONObject2.toString());
        if (localJSONObject2.has("err"))
          localAAAProductInfo.err = Integer.valueOf(localJSONObject2.getString("err")).intValue();
        if (localJSONObject2.has("status"))
          localAAAProductInfo.status = localJSONObject2.getString("status");
        if (localJSONObject2.has("msg"))
        {
          JSONObject localJSONObject3 = new JSONObject(localJSONObject2.getString("msg"));
          Logger.i("AAAGetProductInfoSAXParserJson", "joMsg=" + localJSONObject3.toString());
          if (localJSONObject3.has("product_detail"))
          {
            JSONObject localJSONObject4 = new JSONObject(localJSONObject3.getString("product_detail"));
            Logger.i("AAAGetProductInfoSAXParserJson", "joDetail=" + localJSONObject4.toString());
            if (localJSONObject4.has("product_id"))
              localAAAProductInfo.productId = localJSONObject4.getInt("product_id");
            if (localJSONObject4.has("name"))
              localAAAProductInfo.name = localJSONObject4.getString("name");
            if (localJSONObject4.has("time"))
              localAAAProductInfo.time = localJSONObject4.getString("time");
            if (localJSONObject4.has("price"))
              localAAAProductInfo.price = localJSONObject4.getInt("price");
            if (localJSONObject4.has("begin_date"))
              localAAAProductInfo.beginDate = localJSONObject4.getString("begin_date");
            if (localJSONObject4.has("end_date"))
              localAAAProductInfo.endDate = localJSONObject4.getString("end_date");
          }
        }
      }
      Logger.i("AAAGetProductInfoSAXParserJson", "AAAGetProductInfoSAXParserJson：pi=" + localAAAProductInfo.toString());
      return localAAAProductInfo;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.json.AAAGetProductInfoSAXParserJson
 * JD-Core Version:    0.6.2
 */