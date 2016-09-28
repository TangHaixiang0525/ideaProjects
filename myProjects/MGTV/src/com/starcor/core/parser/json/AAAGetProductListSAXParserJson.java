package com.starcor.core.parser.json;

import com.starcor.core.domain.AAAProductItem;
import com.starcor.core.domain.AAAProductList;
import com.starcor.core.interfaces.IXmlParser;
import com.starcor.core.utils.Logger;
import java.io.InputStream;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class AAAGetProductListSAXParserJson<Result>
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
    AAAProductList localAAAProductList = new AAAProductList();
    ArrayList localArrayList = new ArrayList();
    localAAAProductList.productList = localArrayList;
    try
    {
      JSONObject localJSONObject1 = new JSONObject(new String(paramArrayOfByte));
      Logger.i("AAAGetProductListSAXParserJson", "jo=" + localJSONObject1.toString());
      if (localJSONObject1.has("state"))
        localAAAProductList.state = localJSONObject1.getString("state");
      if (localJSONObject1.has("reason"))
        localAAAProductList.reason = localJSONObject1.getString("reason");
      if (localJSONObject1.has("data"))
      {
        JSONObject localJSONObject2 = new JSONObject(localJSONObject1.getString("data"));
        Logger.i("AAAGetProductListSAXParserJson", "joData=" + localJSONObject2.toString());
        if (localJSONObject2.has("err"))
          localAAAProductList.err = Integer.valueOf(localJSONObject2.getString("err")).intValue();
        if (localJSONObject2.has("status"))
          localAAAProductList.status = localJSONObject2.getString("status");
        if (localJSONObject2.has("msg"))
        {
          JSONObject localJSONObject3 = new JSONObject(localJSONObject2.getString("msg"));
          if (localJSONObject3.has("type"))
            localAAAProductList.type = localJSONObject3.getString("type");
          if (localJSONObject3.has("vip_end_date"))
            localAAAProductList.vipEndDate = localJSONObject3.getString("vip_end_date");
          if (localJSONObject3.has("product_list"))
          {
            JSONArray localJSONArray2 = localJSONObject3.getJSONArray("product_list");
            Logger.i("AAAGetProductListSAXParserJson", "product_list=" + localJSONArray2.toString());
            for (int j = 0; j < localJSONArray2.length(); j++)
            {
              JSONObject localJSONObject5 = localJSONArray2.getJSONObject(j);
              AAAProductItem localAAAProductItem2 = new AAAProductItem();
              if (localJSONObject5.has("product_id"))
                localAAAProductItem2.productId = localJSONObject5.getInt("product_id");
              if (localJSONObject5.has("name"))
                localAAAProductItem2.name = localJSONObject5.getString("name");
              if (localJSONObject5.has("button_name"))
                localAAAProductItem2.button_name = localJSONObject5.getString("button_name");
              if (localJSONObject5.has("time"))
                localAAAProductItem2.time = localJSONObject5.getString("time");
              if (localJSONObject5.has("price"))
                localAAAProductItem2.price = Float.valueOf(localJSONObject5.getString("price")).floatValue();
              if (localJSONObject5.has("price_show"))
                localAAAProductItem2.price_show = Float.valueOf(localJSONObject5.getString("price_show")).floatValue();
              if (localJSONObject5.has("type"))
                localAAAProductItem2.type = Integer.valueOf(localJSONObject5.getString("type")).intValue();
              if (localJSONObject5.has("begin_date"))
                localAAAProductItem2.beginDate = localJSONObject5.getString("begin_date");
              if (localJSONObject5.has("end_date"))
                localAAAProductItem2.endDate = localJSONObject5.getString("end_date");
              if (localJSONObject5.has("description"))
                localAAAProductItem2.desc = localJSONObject5.getString("description");
              localAAAProductItem2.product_type = 0;
              localArrayList.add(localAAAProductItem2);
            }
          }
          if (localJSONObject3.has("coupon_list"))
          {
            JSONArray localJSONArray1 = localJSONObject3.getJSONArray("coupon_list");
            for (int i = 0; i < localJSONArray1.length(); i++)
            {
              JSONObject localJSONObject4 = localJSONArray1.getJSONObject(i);
              AAAProductItem localAAAProductItem1 = new AAAProductItem();
              if (localJSONObject4.has("product_id"))
                localAAAProductItem1.productId = localJSONObject4.getInt("product_id");
              if (localJSONObject4.has("asset_name"))
                localAAAProductItem1.asset_name = localJSONObject4.getString("asset_name");
              if (localJSONObject4.has("description"))
                localAAAProductItem1.desc = localJSONObject4.getString("description");
              localAAAProductItem1.product_type = 1;
              if (localJSONObject4.has("time"))
                localAAAProductItem1.time = localJSONObject4.getString("time");
              if (localJSONObject4.has("price"))
                localAAAProductItem1.price = Float.valueOf(localJSONObject4.getString("price")).floatValue();
              if (localJSONObject4.has("price_show"))
                localAAAProductItem1.price_show = Float.valueOf(localJSONObject4.getString("price_show")).floatValue();
              if (localJSONObject4.has("count"))
                localAAAProductItem1.count = localJSONObject4.getInt("count");
              if (localJSONObject4.has("type"))
                localAAAProductItem1.type = Integer.valueOf(localJSONObject4.getString("type")).intValue();
              if (localJSONObject4.has("button_name"))
                localAAAProductItem1.button_name = localJSONObject4.getString("button_name");
              if (localJSONObject4.has("description"))
                localAAAProductItem1.desc = localJSONObject4.getString("description");
              localArrayList.add(localAAAProductItem1);
            }
          }
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      Logger.i("AAAGetProductListSAXParserJson", "AAAGetProductListSAXParserJson解析器解析得到的对象：pl=" + localAAAProductList.toString());
    }
    return localAAAProductList;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.json.AAAGetProductListSAXParserJson
 * JD-Core Version:    0.6.2
 */