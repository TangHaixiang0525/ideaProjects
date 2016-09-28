package com.starcor.core.parser.json;

import com.starcor.core.domain.SpeedStruct;
import com.starcor.core.interfaces.IXmlParser;
import com.starcor.core.utils.Logger;
import java.io.InputStream;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class GetSpeedTestMissionInfoSAXParserJson<Result>
  implements IXmlParser<Result>
{
  ArrayList<SpeedStruct> arraySpeedTestMissionInfo = new ArrayList();
  private SpeedStruct speedTestMissionInfo;

  public Result parser(InputStream paramInputStream)
  {
    return null;
  }

  public Result parser(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte == null)
      return null;
    try
    {
      String str = new String(paramArrayOfByte);
      Logger.i("GetSpeedTestMissionInfoSAXParserJson", "服务器返回数据：" + str);
      JSONArray localJSONArray = new JSONArray(str);
      int i = 0;
      while (true)
        if (i < localJSONArray.length())
        {
          this.speedTestMissionInfo = new SpeedStruct();
          JSONObject localJSONObject = localJSONArray.getJSONObject(i);
          if (localJSONObject.has("name"))
            this.speedTestMissionInfo.name = localJSONObject.getString("name");
          if (localJSONArray.getJSONObject(i).has("type"))
            this.speedTestMissionInfo.type = localJSONObject.getString("type");
          if (localJSONArray.getJSONObject(i).has("id"))
            this.speedTestMissionInfo.id = localJSONObject.getString("id");
          if (localJSONArray.getJSONObject(i).has("url"))
            this.speedTestMissionInfo.url = localJSONObject.getString("url");
          boolean bool = localJSONArray.getJSONObject(i).has("max_time");
          if (bool);
          try
          {
            this.speedTestMissionInfo.time = Integer.valueOf(localJSONObject.getString("max_time")).intValue();
            this.arraySpeedTestMissionInfo.add(this.speedTestMissionInfo);
            i++;
          }
          catch (NumberFormatException localNumberFormatException)
          {
            while (true)
              this.speedTestMissionInfo.time = 10;
          }
        }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return this.arraySpeedTestMissionInfo;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.json.GetSpeedTestMissionInfoSAXParserJson
 * JD-Core Version:    0.6.2
 */