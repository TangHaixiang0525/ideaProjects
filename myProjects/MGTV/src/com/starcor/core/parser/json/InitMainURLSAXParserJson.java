package com.starcor.core.parser.json;

import com.starcor.core.domain.AppInfo;
import com.starcor.core.interfaces.IXmlParser;
import com.starcor.core.utils.JsonUtils;
import com.starcor.core.utils.Logger;
import com.starcor.core.utils.StreamTools;
import java.io.InputStream;
import org.json.JSONArray;
import org.json.JSONObject;

public class InitMainURLSAXParserJson<Result>
  implements IXmlParser<Result>
{
  private static final String TAG = "InitMainURLSAXParserJson";
  Integer i = Integer.valueOf(0);

  public Result parser(InputStream paramInputStream)
  {
    if (paramInputStream == null)
      return this.i;
    try
    {
      JSONObject localJSONObject1 = JsonUtils.getJsonObject(new String(StreamTools.getBytes(paramInputStream)));
      if (localJSONObject1.has("n2_a"))
      {
        JSONObject localJSONObject6 = localJSONObject1.getJSONObject("n2_a");
        if (localJSONObject6.has("url"))
          AppInfo.URL_n2_a = localJSONObject6.getString("url");
      }
      Logger.i("n2_a--->url", AppInfo.URL_n2_a);
      if (localJSONObject1.has("n3_a_2"))
      {
        JSONObject localJSONObject5 = localJSONObject1.getJSONObject("n3_a_2");
        if (localJSONObject5.has("url"))
          AppInfo.URL_n3_a = localJSONObject5.getString("url");
      }
      Logger.i("n3_a_2--->url", AppInfo.URL_n3_a);
      if (localJSONObject1.has("n7_a"))
      {
        localJSONObject1.getString("n7_a");
        JSONObject localJSONObject4 = localJSONObject1.getJSONObject("n7_a");
        if (localJSONObject4.has("url"))
          AppInfo.URL_n7_a = localJSONObject4.getString("url");
      }
      Logger.i("n7_a--->url", AppInfo.URL_n7_a);
      if (localJSONObject1.has("n100_a"))
      {
        JSONObject localJSONObject3 = localJSONObject1.getJSONObject("n100_a");
        if (localJSONObject3.has("url"))
          AppInfo.URL_n100 = localJSONObject3.getString("url");
      }
      Logger.i("n100_a--->url", AppInfo.URL_n100);
      if (localJSONObject1.has("n200_a"))
      {
        JSONObject localJSONObject2 = localJSONObject1.getJSONObject("n200_a");
        if (localJSONObject2.has("url"))
        {
          AppInfo.URL_n200 = localJSONObject2.getString("url");
          Logger.i("n200_a--->url", AppInfo.URL_n200);
        }
      }
      if (localJSONObject1.has("parameter_list"))
      {
        Logger.d("parameter_list");
        localJSONObject1.getString("parameter_list");
        JSONArray localJSONArray = localJSONObject1.getJSONArray("parameter_list");
        for (int j = 0; ; j++)
        {
          int k = localJSONArray.length();
          if (j >= k)
            break;
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      Logger.i("InitMainURLSAXParserJson", "InitMainURLSAXParserJson解析器解析得到的对象：i=" + this.i);
    }
    return this.i;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.json.InitMainURLSAXParserJson
 * JD-Core Version:    0.6.2
 */