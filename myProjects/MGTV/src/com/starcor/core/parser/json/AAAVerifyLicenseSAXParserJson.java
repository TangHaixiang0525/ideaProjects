package com.starcor.core.parser.json;

import com.starcor.core.domain.AAAVerifyLicense;
import com.starcor.core.interfaces.IXmlParser;
import com.starcor.core.utils.Logger;
import java.io.InputStream;
import org.json.JSONObject;

public class AAAVerifyLicenseSAXParserJson<Result>
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
    AAAVerifyLicense localAAAVerifyLicense = new AAAVerifyLicense();
    try
    {
      JSONObject localJSONObject = new JSONObject(new String(paramArrayOfByte));
      if (localJSONObject.has("data"))
        localJSONObject = new JSONObject(localJSONObject.getString("data"));
      if (localJSONObject.has("err"))
        localAAAVerifyLicense.err = Integer.valueOf(localJSONObject.getString("err")).intValue();
      if (localJSONObject.has("status"))
        localAAAVerifyLicense.status = localJSONObject.getString("status");
      if (localJSONObject.has("msg"))
        localAAAVerifyLicense.msg = localJSONObject.getString("msg");
      Logger.i("AAAVerifyLicenseSAXParserJson", "AAAVerifyLicenseSAXParserJson解析器解析得到的对象：vl=" + localAAAVerifyLicense.toString());
      return localAAAVerifyLicense;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.json.AAAVerifyLicenseSAXParserJson
 * JD-Core Version:    0.6.2
 */