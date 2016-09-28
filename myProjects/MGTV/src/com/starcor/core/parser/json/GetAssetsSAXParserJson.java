package com.starcor.core.parser.json;

import com.starcor.core.domain.AssetsInfo;
import com.starcor.core.interfaces.IXmlParser;
import java.io.InputStream;
import org.json.JSONArray;
import org.json.JSONObject;

public class GetAssetsSAXParserJson<Result>
  implements IXmlParser<Result>
{
  private static final String TAG = "GetAssetsSAXParserJson";
  private AssetsInfo assetsInfo = new AssetsInfo();

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
      JSONObject localJSONObject1 = new JSONObject(new String(paramArrayOfByte));
      if (localJSONObject1.has("0"))
      {
        JSONObject localJSONObject2 = new JSONObject(localJSONObject1.getString("0"));
        this.assetsInfo.packageId = localJSONObject2.getString("id");
        if (localJSONObject2.has("arg_list"))
        {
          JSONArray localJSONArray = localJSONObject2.getJSONArray("arg_list");
          if (localJSONArray.length() > 0)
          {
            JSONObject localJSONObject3 = localJSONArray.getJSONObject(0);
            if (localJSONObject3.has("category"))
              this.assetsInfo.categoryId = localJSONObject3.getString("category");
          }
        }
      }
      return this.assetsInfo;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.json.GetAssetsSAXParserJson
 * JD-Core Version:    0.6.2
 */