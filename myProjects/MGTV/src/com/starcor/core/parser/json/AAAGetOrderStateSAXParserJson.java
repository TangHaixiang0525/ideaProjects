package com.starcor.core.parser.json;

import com.starcor.core.domain.AAAOrderState;
import com.starcor.core.interfaces.IXmlParser;
import com.starcor.core.utils.Logger;
import java.io.InputStream;
import org.json.JSONObject;

public class AAAGetOrderStateSAXParserJson<Result>
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
    AAAOrderState localAAAOrderState = new AAAOrderState();
    try
    {
      JSONObject localJSONObject1 = new JSONObject(new String(paramArrayOfByte));
      Logger.i("AAAGetOrderStateSAXParserJson", "jo=" + localJSONObject1.toString());
      if (localJSONObject1.has("state"))
        localAAAOrderState.state = localJSONObject1.getString("state");
      if (localJSONObject1.has("reason"))
        localAAAOrderState.reason = localJSONObject1.getString("reason");
      if (localJSONObject1.has("data"))
      {
        JSONObject localJSONObject2 = new JSONObject(localJSONObject1.getString("data"));
        if (localJSONObject2.has("err"))
          localAAAOrderState.err = Integer.valueOf(localJSONObject2.getString("err")).intValue();
        if (localJSONObject2.has("status"))
          localAAAOrderState.status = localJSONObject2.getString("status");
        if (localJSONObject2.has("msg"))
        {
          JSONObject localJSONObject3 = new JSONObject(localJSONObject2.getString("msg"));
          if (localJSONObject3.has("status"))
            localAAAOrderState.orderStatus = localJSONObject3.getString("status");
          if (localJSONObject3.has("begin_date"))
            localAAAOrderState.begin_date = localJSONObject3.getString("begin_date");
          if (localJSONObject3.has("end_date"))
            localAAAOrderState.end_date = localJSONObject3.getString("end_date");
          if (localJSONObject3.has("coupon_count"))
            localAAAOrderState.coupon = localJSONObject3.getInt("coupon_count");
          if (localJSONObject3.has("service_days"))
            localAAAOrderState.service_days = localJSONObject3.getString("service_days");
          if (localJSONObject3.has("coupon_end_date"))
            localAAAOrderState.coupon_end_date = localJSONObject3.getString("coupon_end_date");
        }
      }
      Logger.i("AAAGetOrderStateSAXParserJson", "AAAGetOrderStateSAXParserJson解析器解析得到的对象：os=" + localAAAOrderState.toString());
      return localAAAOrderState;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.json.AAAGetOrderStateSAXParserJson
 * JD-Core Version:    0.6.2
 */