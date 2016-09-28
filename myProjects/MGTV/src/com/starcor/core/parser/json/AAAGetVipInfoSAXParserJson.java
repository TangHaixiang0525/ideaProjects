package com.starcor.core.parser.json;

import com.starcor.core.domain.AAAVipInfo;
import com.starcor.core.interfaces.IXmlParser;
import com.starcor.core.utils.Logger;
import java.io.InputStream;
import org.json.JSONObject;

public class AAAGetVipInfoSAXParserJson<Result>
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
    AAAVipInfo localAAAVipInfo = new AAAVipInfo();
    try
    {
      String str = new String(paramArrayOfByte);
      Logger.i("AAAGetVipInfoSAXParserJson", "jsonString=" + str);
      JSONObject localJSONObject1 = new JSONObject(str);
      Logger.i("AAAGetVipInfoSAXParserJson", "jsRt=" + localJSONObject1.toString());
      if (localJSONObject1.has("state"))
        localAAAVipInfo.state = localJSONObject1.getString("state");
      if (localJSONObject1.has("reason"))
        localAAAVipInfo.reason = localJSONObject1.getString("reason");
      if (localJSONObject1.has("data"))
      {
        JSONObject localJSONObject2 = new JSONObject(localJSONObject1.getString("data"));
        Logger.i("AAAGetVipInfoSAXParserJson", "joData=" + localJSONObject2.toString());
        if (localJSONObject2.has("err"))
          localAAAVipInfo.err = Integer.valueOf(localJSONObject2.getString("err")).intValue();
        if (localJSONObject2.has("status"))
          localAAAVipInfo.status = localJSONObject2.getString("status");
        if (localJSONObject2.has("msg"))
        {
          JSONObject localJSONObject3 = new JSONObject(localJSONObject2.getString("msg"));
          Logger.i("AAAGetVipInfoSAXParserJson", "joMsg=" + localJSONObject3.toString());
          if (localJSONObject3.has("name"))
            localAAAVipInfo.name = localJSONObject3.getString("name");
          if (localJSONObject3.has("subname"))
            localAAAVipInfo.subname = localJSONObject3.getString("subname");
          if (localJSONObject3.has("description"))
            localAAAVipInfo.description = localJSONObject3.getString("description");
          if (localJSONObject3.has("location"))
            localAAAVipInfo.location = Integer.valueOf(localJSONObject3.getString("location")).intValue();
          if (localJSONObject3.has("power"))
            localAAAVipInfo.power = Integer.valueOf(localJSONObject3.getString("power")).intValue();
          if (localJSONObject3.has("asset_count"))
          {
            JSONObject localJSONObject4 = new JSONObject(localJSONObject3.getString("asset_count"));
            Logger.i("AAAGetVipInfoSAXParserJson", "joCount=" + localJSONObject4.toString());
            if (localJSONObject4.has("movie"))
              localAAAVipInfo.movieNum = Integer.valueOf(localJSONObject4.getString("movie")).intValue();
            if (localJSONObject4.has("tvplays"))
              localAAAVipInfo.tvplaysNum = Integer.valueOf(localJSONObject4.getString("tvplays")).intValue();
            if (localJSONObject4.has("anime"))
              localAAAVipInfo.animeNum = Integer.valueOf(localJSONObject4.getString("anime")).intValue();
            if (localJSONObject4.has("music"))
              localAAAVipInfo.musicNum = Integer.valueOf(localJSONObject4.getString("music")).intValue();
          }
        }
      }
      Logger.i("AAAGetVipInfoSAXParserJson", "AAAGetVipInfoSAXParserJson解析器解析得到的对象：vi=" + localAAAVipInfo.toString());
      return localAAAVipInfo;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.json.AAAGetVipInfoSAXParserJson
 * JD-Core Version:    0.6.2
 */