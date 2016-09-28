package com.starcor.core.parser.json;

import com.starcor.core.domain.SpeedTestUrlInfo;
import com.starcor.core.interfaces.IXmlParser;
import com.starcor.core.utils.Logger;
import com.starcor.core.utils.StreamTools;
import java.io.InputStream;
import java.util.ArrayList;
import org.json.JSONObject;

public class GetSpeedTestUrlInfoSAXParserJson<Result>
  implements IXmlParser<Result>
{
  ArrayList<SpeedTestUrlInfo> arrayUrlInfo = new ArrayList();
  private SpeedTestUrlInfo urlInfo;

  public Result parser(InputStream paramInputStream)
  {
    if (paramInputStream == null)
      return null;
    try
    {
      String str = new String(StreamTools.getBytes(paramInputStream));
      Logger.i("GetSpeedTestUrlInfoSAXParserJson", "服务器返回数据：" + str);
      JSONObject localJSONObject1 = new JSONObject(str);
      JSONObject localJSONObject2;
      if (localJSONObject1.has("msg"))
      {
        Logger.i("GetSpeedTestUrlInfoSAXParserJson", "有msg这个节点");
        localJSONObject2 = new JSONObject(localJSONObject1.getString("msg"));
        this.urlInfo = new SpeedTestUrlInfo();
        if (localJSONObject2.has("id"))
          this.urlInfo.id = localJSONObject2.getString("id");
        if (localJSONObject2.has("url"))
          this.urlInfo.url = localJSONObject2.getString("url");
        boolean bool = localJSONObject2.has("time");
        if (!bool);
      }
      try
      {
        this.urlInfo.time = Integer.valueOf(localJSONObject2.getString("time")).intValue();
        this.arrayUrlInfo.add(this.urlInfo);
        return this.arrayUrlInfo;
      }
      catch (Exception localException2)
      {
        while (true)
        {
          localException2.printStackTrace();
          this.urlInfo.time = 0;
        }
      }
    }
    catch (Exception localException1)
    {
      while (true)
        localException1.printStackTrace();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.json.GetSpeedTestUrlInfoSAXParserJson
 * JD-Core Version:    0.6.2
 */