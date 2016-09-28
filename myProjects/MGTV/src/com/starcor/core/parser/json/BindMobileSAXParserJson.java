package com.starcor.core.parser.json;

import com.starcor.core.domain.UserCenterBindMobile;
import com.starcor.core.interfaces.IXmlParser;
import com.starcor.core.utils.Logger;
import java.io.InputStream;
import org.json.JSONObject;

public class BindMobileSAXParserJson<Result>
  implements IXmlParser<Result>
{
  private UserCenterBindMobile uc;

  public Result parser(InputStream paramInputStream)
  {
    return null;
  }

  public Result parser(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte == null)
      return null;
    this.uc = new UserCenterBindMobile();
    try
    {
      JSONObject localJSONObject1 = new JSONObject(new String(paramArrayOfByte));
      if (localJSONObject1.has("err"))
        this.uc.err = Integer.valueOf(localJSONObject1.getString("err")).intValue();
      if (localJSONObject1.has("status"))
        this.uc.status = localJSONObject1.getString("status");
      if (localJSONObject1.has("msg"))
      {
        JSONObject localJSONObject2 = new JSONObject(localJSONObject1.getString("msg"));
        if (localJSONObject2.has("operation"))
          this.uc.operation = localJSONObject2.getString("operation");
      }
      Logger.i("BindMobileSAXParserJson", "BindMobileSAXParserJson解析器解析得到的对象：uc=" + this.uc.toString());
      return this.uc;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.json.BindMobileSAXParserJson
 * JD-Core Version:    0.6.2
 */