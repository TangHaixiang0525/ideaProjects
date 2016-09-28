package com.starcor.core.parser.json;

import com.starcor.core.interfaces.IXmlParser;
import com.starcor.core.utils.JsonUtils;
import com.starcor.core.utils.Logger;
import com.starcor.core.utils.StreamTools;
import java.io.InputStream;
import org.json.JSONObject;

public class UpdataServerTimeSAXParserJson<Result>
  implements IXmlParser<Result>
{
  private static final String TAG = "UpdataServerTimeSAXParserJson";
  public String time;

  public Result parser(InputStream paramInputStream)
  {
    if (paramInputStream == null)
      return null;
    this.time = "";
    try
    {
      JSONObject localJSONObject = JsonUtils.getJsonObject(new String(StreamTools.getBytes(paramInputStream)));
      if (localJSONObject.has("time"))
        this.time = localJSONObject.getString("time");
      Logger.i("UpdataServerTimeSAXParserJson", "UpdataServerTimeSAXParserJson解析器解析得到的对象：time=" + this.time);
      return this.time;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.json.UpdataServerTimeSAXParserJson
 * JD-Core Version:    0.6.2
 */