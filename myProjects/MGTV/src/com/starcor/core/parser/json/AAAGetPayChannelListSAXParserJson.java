package com.starcor.core.parser.json;

import com.starcor.core.domain.AAAPayChannelItem;
import com.starcor.core.domain.AAAPayChannelList;
import com.starcor.core.interfaces.IXmlParser;
import com.starcor.core.utils.Logger;
import java.io.InputStream;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class AAAGetPayChannelListSAXParserJson<Result>
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
    AAAPayChannelList localAAAPayChannelList = new AAAPayChannelList();
    ArrayList localArrayList = new ArrayList();
    localAAAPayChannelList.channelList = localArrayList;
    try
    {
      JSONObject localJSONObject1 = new JSONObject(new String(paramArrayOfByte));
      Logger.i("AAAGetPayChannelListSAXParserJson", "jo=" + localJSONObject1.toString());
      if (localJSONObject1.has("state"))
        localAAAPayChannelList.state = localJSONObject1.getString("state");
      if (localJSONObject1.has("reason"))
        localAAAPayChannelList.reason = localJSONObject1.getString("reason");
      if (localJSONObject1.has("data"))
      {
        JSONObject localJSONObject2 = new JSONObject(localJSONObject1.getString("data"));
        Logger.i("AAAGetPayChannelListSAXParserJson", "joData=" + localJSONObject2.toString());
        if (localJSONObject2.has("err"))
          localAAAPayChannelList.err = Integer.valueOf(localJSONObject2.getString("err")).intValue();
        if (localJSONObject2.has("status"))
          localAAAPayChannelList.status = localJSONObject2.getString("status");
        if (localJSONObject2.has("msg"))
        {
          JSONArray localJSONArray = new JSONArray(localJSONObject2.getString("msg"));
          Logger.i("AAAGetPayChannelListSAXParserJson", "joChannelList=" + localJSONArray.toString());
          for (int i = 0; i < localJSONArray.length(); i++)
          {
            JSONObject localJSONObject3 = localJSONArray.getJSONObject(i);
            AAAPayChannelItem localAAAPayChannelItem = new AAAPayChannelItem();
            if (localJSONObject3.has("code"))
              localAAAPayChannelItem.code = localJSONObject3.getString("code");
            if (localJSONObject3.has("location"))
              localAAAPayChannelItem.location = localJSONObject3.getString("location");
            if (localJSONObject3.has("name"))
              localAAAPayChannelItem.name = localJSONObject3.getString("name");
            if (localJSONObject3.has("discount"))
              localAAAPayChannelItem.discount = localJSONObject3.getString("discount");
            if (localJSONObject3.has("description"))
              localAAAPayChannelItem.description = localJSONObject3.getString("description");
            localArrayList.add(localAAAPayChannelItem);
          }
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      Logger.i("AAAGetPayChannelListSAXParserJson", "AAAGetPayChannelListSAXParserJson解析器解析得到的对象：cl=" + localAAAPayChannelList.toString());
    }
    return localAAAPayChannelList;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.json.AAAGetPayChannelListSAXParserJson
 * JD-Core Version:    0.6.2
 */