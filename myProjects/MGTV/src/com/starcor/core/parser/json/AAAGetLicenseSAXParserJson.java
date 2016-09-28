package com.starcor.core.parser.json;

import com.starcor.core.domain.AAAGetLicense;
import com.starcor.core.interfaces.IXmlParser;
import com.starcor.core.utils.Logger;
import java.io.InputStream;
import org.json.JSONObject;

public class AAAGetLicenseSAXParserJson<Result>
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
    AAAGetLicense localAAAGetLicense = new AAAGetLicense();
    try
    {
      JSONObject localJSONObject1 = new JSONObject(new String(paramArrayOfByte));
      if (localJSONObject1.has("data"))
        localJSONObject1 = new JSONObject(localJSONObject1.getString("data"));
      if (localJSONObject1.has("err"))
        localAAAGetLicense.err = Integer.valueOf(localJSONObject1.getString("err")).intValue();
      if (localJSONObject1.has("status"))
        localAAAGetLicense.status = localJSONObject1.getString("status");
      if (localJSONObject1.has("msg"))
      {
        JSONObject localJSONObject2 = new JSONObject(localJSONObject1.getString("msg"));
        if (localJSONObject2.has("license"))
          localAAAGetLicense.license = localJSONObject2.getString("license");
        if (localJSONObject2.has("ip"))
          localAAAGetLicense.ip = localJSONObject2.getString("ip");
        if (localJSONObject2.has("net_id"))
          localAAAGetLicense.netId = localJSONObject2.getString("net_id");
      }
      Logger.i("AAAGetLicenseSAXParserJson", "AAAGetLicenseSAXParserJson解析器解析得到的对象：gl=" + localAAAGetLicense.toString());
      return localAAAGetLicense;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.json.AAAGetLicenseSAXParserJson
 * JD-Core Version:    0.6.2
 */