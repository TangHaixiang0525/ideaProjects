package com.starcor.core.parser.json;

import com.starcor.core.interfaces.IXmlParser;
import com.starcor.core.utils.Logger;
import java.io.InputStream;
import org.json.JSONObject;

public class OpRecordSAXParserJson<Result>
  implements IXmlParser<Result>
{
  private String collectId = "";

  public Result parser(InputStream paramInputStream)
  {
    Integer.valueOf(0);
    if (paramInputStream == null)
      return Integer.valueOf(-1);
    return Integer.valueOf(1);
  }

  public Result parser(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte == null)
      return null;
    try
    {
      JSONObject localJSONObject = new JSONObject(new String(paramArrayOfByte));
      if (localJSONObject.has("id"))
        this.collectId = localJSONObject.getString("id");
      Logger.i("OpRecordSAXParserJson", "OpRecordSAXParserJson：collectId=" + this.collectId);
      return this.collectId;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.json.OpRecordSAXParserJson
 * JD-Core Version:    0.6.2
 */