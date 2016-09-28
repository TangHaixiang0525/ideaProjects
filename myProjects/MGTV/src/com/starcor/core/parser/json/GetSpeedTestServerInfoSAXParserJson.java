package com.starcor.core.parser.json;

import com.starcor.core.domain.SpeedTestServerInfo;
import com.starcor.core.interfaces.IXmlParser;
import com.starcor.core.utils.Logger;
import com.starcor.core.utils.StreamTools;
import java.io.InputStream;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class GetSpeedTestServerInfoSAXParserJson<Result>
  implements IXmlParser<Result>
{
  ArrayList<SpeedTestServerInfo> arrayServerInfo = new ArrayList();
  private SpeedTestServerInfo serverInfo;

  public Result parser(InputStream paramInputStream)
  {
    if (paramInputStream == null)
      return null;
    try
    {
      String str = new String(StreamTools.getBytes(paramInputStream));
      Logger.i("GetSpeedTestServerInfoSAXParserJson", "服务器返回数据：" + str);
      JSONObject localJSONObject1 = new JSONObject(str);
      if (localJSONObject1.has("msg"))
      {
        JSONArray localJSONArray = new JSONArray(localJSONObject1.getString("msg"));
        for (int i = 0; i < localJSONArray.length(); i++)
        {
          this.serverInfo = new SpeedTestServerInfo();
          JSONObject localJSONObject2 = localJSONArray.getJSONObject(i);
          if (localJSONObject2.has("name"))
            this.serverInfo.serverName = localJSONObject2.getString("name");
          if (localJSONArray.getJSONObject(i).has("type"))
            this.serverInfo.serverType = localJSONObject2.getString("type");
          if (localJSONArray.getJSONObject(i).has("val_id"))
            this.serverInfo.valId = localJSONObject2.getString("val_id");
          this.arrayServerInfo.add(this.serverInfo);
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return this.arrayServerInfo;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.json.GetSpeedTestServerInfoSAXParserJson
 * JD-Core Version:    0.6.2
 */