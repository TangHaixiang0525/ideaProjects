package com.starcor.core.parser.json;

import com.starcor.core.domain.AAAPriceList;
import com.starcor.core.interfaces.IXmlParser;
import com.starcor.core.utils.Logger;
import java.io.InputStream;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class AAAGetPriceListSAXParserJson<Result>
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
    AAAPriceList localAAAPriceList = new AAAPriceList();
    ArrayList localArrayList = new ArrayList();
    localAAAPriceList.priceList = localArrayList;
    try
    {
      JSONObject localJSONObject1 = new JSONObject(new String(paramArrayOfByte));
      Logger.i("AAAGetPriceListSAXParserJson", "jo=" + localJSONObject1.toString());
      if (localJSONObject1.has("state"))
        localAAAPriceList.state = localJSONObject1.getString("state");
      if (localJSONObject1.has("reason"))
        localAAAPriceList.reason = localJSONObject1.getString("reason");
      if (localJSONObject1.has("data"))
      {
        JSONObject localJSONObject2 = new JSONObject(localJSONObject1.getString("data"));
        if (localJSONObject2.has("err"))
          localAAAPriceList.err = Integer.valueOf(localJSONObject2.getString("err")).intValue();
        if (localJSONObject2.has("status"))
          localAAAPriceList.status = localJSONObject2.getString("status");
        if (localJSONObject2.has("msg"))
        {
          JSONObject localJSONObject3 = new JSONObject(localJSONObject2.getString("msg"));
          if (localJSONObject3.has("amount"))
          {
            JSONArray localJSONArray = localJSONObject3.getJSONArray("amount");
            for (int i = 0; i < localJSONArray.length(); i++)
              localArrayList.add(Integer.valueOf(localJSONArray.getInt(i)));
          }
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      Logger.i("AAAGetPriceListSAXParserJson", "AAAGetPriceListSAXParserJson解析器解析得到的对象：pl=" + localAAAPriceList.toString());
    }
    return localAAAPriceList;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.json.AAAGetPriceListSAXParserJson
 * JD-Core Version:    0.6.2
 */