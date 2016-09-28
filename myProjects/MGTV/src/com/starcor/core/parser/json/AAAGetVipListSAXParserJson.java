package com.starcor.core.parser.json;

import com.starcor.core.domain.AAAVipItem;
import com.starcor.core.domain.AAAVipList;
import com.starcor.core.interfaces.IXmlParser;
import com.starcor.core.utils.Logger;
import java.io.InputStream;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class AAAGetVipListSAXParserJson<Result>
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
    AAAVipList localAAAVipList = new AAAVipList();
    ArrayList localArrayList = new ArrayList();
    localAAAVipList.vipList = localArrayList;
    try
    {
      JSONObject localJSONObject1 = new JSONObject(new String(paramArrayOfByte));
      if (localJSONObject1.has("state"))
        localAAAVipList.state = localJSONObject1.getString("state");
      if (localJSONObject1.has("reason"))
        localAAAVipList.reason = localJSONObject1.getString("reason");
      if (localJSONObject1.has("data"))
      {
        JSONObject localJSONObject2 = new JSONObject(localJSONObject1.getString("data"));
        Logger.i("AAAGetVipListSAXParserJson", "joData=" + localJSONObject2.toString());
        if (localJSONObject2.has("err"))
          localAAAVipList.err = Integer.valueOf(localJSONObject2.getString("err")).intValue();
        if (localJSONObject2.has("status"))
          localAAAVipList.status = localJSONObject2.getString("status");
        if (localJSONObject2.has("msg"))
        {
          JSONArray localJSONArray = new JSONArray(localJSONObject2.getString("msg"));
          Logger.i("AAAGetVipListSAXParserJson", "joMsg=" + localJSONArray.toString());
          for (int i = 0; i < localJSONArray.length(); i++)
          {
            JSONObject localJSONObject3 = localJSONArray.getJSONObject(i);
            AAAVipItem localAAAVipItem = new AAAVipItem();
            if (localJSONObject3.has("vip_id"))
              localAAAVipItem.vipId = Integer.valueOf(localJSONObject3.getString("vip_id")).intValue();
            if (localJSONObject3.has("location"))
              localAAAVipItem.location = Integer.valueOf(localJSONObject3.getString("location")).intValue();
            if (localJSONObject3.has("name"))
              localAAAVipItem.name = localJSONObject3.getString("name");
            if (localJSONObject3.has("power"))
              localAAAVipItem.power = Integer.valueOf(localJSONObject3.getString("power")).intValue();
            localArrayList.add(localAAAVipItem);
          }
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      Logger.i("AAAGetVipListSAXParserJson", "AAAGetVipListSAXParserJson解析器解析得到的对象：vl=" + localAAAVipList.toString());
    }
    return localAAAVipList;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.json.AAAGetVipListSAXParserJson
 * JD-Core Version:    0.6.2
 */