package com.starcor.core.parser.json;

import android.util.Log;
import com.starcor.core.domain.SpeedStruct;
import com.starcor.core.interfaces.IXmlParser;
import com.starcor.core.utils.StreamTools;
import java.io.InputStream;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class SpeedSAXParserJson<Result>
  implements IXmlParser<Result>
{
  ArrayList<SpeedStruct> arrSpeed = new ArrayList();
  private SpeedStruct speedStruct;

  public Result parser(InputStream paramInputStream)
  {
    if (paramInputStream == null)
      return null;
    try
    {
      String str = new String(StreamTools.getBytes(paramInputStream));
      Log.e("speed", "服务器返回数据：" + str);
      JSONObject localJSONObject1 = new JSONObject(str);
      if (localJSONObject1.has("msg"))
      {
        JSONArray localJSONArray = new JSONArray(localJSONObject1.getString("msg"));
        int i = 0;
        while (true)
          if (i < localJSONArray.length())
          {
            this.speedStruct = new SpeedStruct();
            JSONObject localJSONObject2 = localJSONArray.getJSONObject(i);
            if (localJSONObject2.has("name"))
              this.speedStruct.name = localJSONObject2.getString("name");
            if (localJSONArray.getJSONObject(i).has("id"))
              this.speedStruct.id = localJSONObject2.getString("id");
            if (localJSONArray.getJSONObject(i).has("url"))
              this.speedStruct.url = localJSONObject2.getString("url");
            boolean bool = localJSONArray.getJSONObject(i).has("time");
            if (bool);
            try
            {
              this.speedStruct.time = Integer.valueOf(localJSONObject2.getString("time")).intValue();
              this.arrSpeed.add(this.speedStruct);
              i++;
            }
            catch (Exception localException2)
            {
              while (true)
              {
                localException2.printStackTrace();
                this.speedStruct.time = 0;
              }
            }
          }
      }
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
    }
    return this.arrSpeed;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.json.SpeedSAXParserJson
 * JD-Core Version:    0.6.2
 */