package com.starcor.core.parser.json;

import com.starcor.core.domain.AAAOrderInfo;
import com.starcor.core.interfaces.IXmlParser;
import com.starcor.core.utils.Logger;
import java.io.InputStream;
import org.json.JSONObject;

public class AAABuyProductSAXParserJson<Result>
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
    AAAOrderInfo localAAAOrderInfo = new AAAOrderInfo();
    try
    {
      JSONObject localJSONObject1 = new JSONObject(new String(paramArrayOfByte));
      Logger.i("AAABuyProductSAXParserJson", "jo=" + localJSONObject1.toString());
      if (localJSONObject1.has("state"))
        localAAAOrderInfo.state = localJSONObject1.getString("state");
      if (localJSONObject1.has("reason"))
        localAAAOrderInfo.reason = localJSONObject1.getString("reason");
      if (localJSONObject1.has("data"))
      {
        JSONObject localJSONObject2 = new JSONObject(localJSONObject1.getString("data"));
        if (localJSONObject2.has("err"))
          localAAAOrderInfo.err = Integer.valueOf(localJSONObject2.getString("err")).intValue();
        if (localJSONObject2.has("status"))
          localAAAOrderInfo.status = localJSONObject2.getString("status");
        if (localJSONObject2.has("msg"))
        {
          JSONObject localJSONObject3 = new JSONObject(localJSONObject2.getString("msg"));
          localAAAOrderInfo.msg = localJSONObject2.getString("msg");
          if (localJSONObject3.has("order_id"))
            localAAAOrderInfo.orderId = localJSONObject3.getString("order_id");
          if (localJSONObject3.has("order_type"))
            localAAAOrderInfo.orderType = localJSONObject3.getString("order_type");
          if (localJSONObject3.has("order_price"))
            localAAAOrderInfo.orderPrice = localJSONObject3.getString("order_price");
          if (localJSONObject3.has("order_msg"))
            localAAAOrderInfo.orderMsg = localJSONObject3.getString("order_msg");
          if (localJSONObject3.has("mobile_type"))
            localAAAOrderInfo.mobileType = localJSONObject3.getString("mobile_type");
        }
      }
      Logger.i("AAABuyProductSAXParserJson", "AAABuyProductSAXParserJson解析器解析得到的对象：oi=" + localAAAOrderInfo.toString());
      return localAAAOrderInfo;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.json.AAABuyProductSAXParserJson
 * JD-Core Version:    0.6.2
 */