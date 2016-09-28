package com.starcor.core.parser.json;

import com.starcor.core.domain.AAAOrderRecordItem;
import com.starcor.core.domain.AAAOrderRecordList;
import com.starcor.core.interfaces.IXmlParser;
import com.starcor.core.utils.Logger;
import java.io.InputStream;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class AAAGetOrderRecordListSAXParserJson<Result>
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
    AAAOrderRecordList localAAAOrderRecordList = new AAAOrderRecordList();
    ArrayList localArrayList = new ArrayList();
    localAAAOrderRecordList.recordList = localArrayList;
    try
    {
      JSONObject localJSONObject1 = new JSONObject(new String(paramArrayOfByte));
      Logger.i("AAAGetOrderRecordListSAXParserJson", "jo=" + localJSONObject1.toString());
      if (localJSONObject1.has("state"))
        localAAAOrderRecordList.state = localJSONObject1.getString("state");
      if (localJSONObject1.has("reason"))
        localAAAOrderRecordList.reason = localJSONObject1.getString("reason");
      if (localJSONObject1.has("data"))
      {
        JSONObject localJSONObject2 = new JSONObject(localJSONObject1.getString("data"));
        if (localJSONObject2.has("err"))
          localAAAOrderRecordList.err = Integer.valueOf(localJSONObject2.getString("err")).intValue();
        if (localJSONObject2.has("status"))
          localAAAOrderRecordList.status = localJSONObject2.getString("status");
        if (localJSONObject2.has("msg"))
        {
          JSONObject localJSONObject3 = new JSONObject(localJSONObject2.getString("msg"));
          if (localJSONObject3.has("total"))
            localAAAOrderRecordList.total = localJSONObject3.getInt("total");
          if (localJSONObject3.has("now_page"))
            localAAAOrderRecordList.nowPage = localJSONObject3.getInt("now_page");
          if (localJSONObject3.has("orders"))
          {
            JSONArray localJSONArray = localJSONObject3.getJSONArray("orders");
            for (int i = 0; i < localJSONArray.length(); i++)
            {
              JSONObject localJSONObject4 = localJSONArray.getJSONObject(i);
              AAAOrderRecordItem localAAAOrderRecordItem = new AAAOrderRecordItem();
              if (localJSONObject4.has("product_name"))
                localAAAOrderRecordItem.productName = localJSONObject4.getString("product_name");
              if (localJSONObject4.has("channel_name"))
                localAAAOrderRecordItem.channelName = localJSONObject4.getString("channel_name");
              if (localJSONObject4.has("begin_date"))
              {
                localAAAOrderRecordItem.beginDate = localJSONObject4.getString("begin_date");
                localAAAOrderRecordItem.beginDate = localAAAOrderRecordItem.beginDate.replace('-', '.');
              }
              if (localJSONObject4.has("end_date"))
              {
                localAAAOrderRecordItem.endDate = localJSONObject4.getString("end_date");
                localAAAOrderRecordItem.endDate = localAAAOrderRecordItem.endDate.replace('-', '.');
              }
              if (localJSONObject4.has("category_str"))
                localAAAOrderRecordItem.categoryStr = localJSONObject4.getString("category_str");
              if (localJSONObject4.has("price"))
                localAAAOrderRecordItem.price = Float.valueOf(localJSONObject4.getString("price")).floatValue();
              if (localJSONObject4.has("count"))
                localAAAOrderRecordItem.count = localJSONObject4.getInt("count");
              if (localJSONObject4.has("pay_time"))
              {
                localAAAOrderRecordItem.payTime = localJSONObject4.getString("pay_time");
                if (localAAAOrderRecordItem.payTime.length() > 10)
                  localAAAOrderRecordItem.payTime = localAAAOrderRecordItem.payTime.substring(0, 10);
                localAAAOrderRecordItem.payTime = localAAAOrderRecordItem.payTime.replace('-', '.');
              }
              localArrayList.add(localAAAOrderRecordItem);
            }
          }
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      Logger.i("AAAGetOrderRecordListSAXParserJson", "AAAGetOrderRecordListSAXParserJson解析器解析得到的对象：ol=" + localAAAOrderRecordList.toString());
    }
    return localAAAOrderRecordList;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.json.AAAGetOrderRecordListSAXParserJson
 * JD-Core Version:    0.6.2
 */