package com.starcor.core.parser.json;

import android.util.Log;
import com.starcor.core.domain.CityInfoById;
import com.starcor.core.domain.CityItemById;
import com.starcor.core.interfaces.IXmlParser;
import com.starcor.core.utils.StreamTools;
import java.io.InputStream;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class CityItemSAXParserJson<Result>
  implements IXmlParser<Result>
{
  private static final String TAG = "CityItemSAXParserJson";
  ArrayList<CityItemById> arrCityItem = new ArrayList();
  private CityInfoById cityInfo = new CityInfoById();
  private CityItemById cityItem;

  public Result parser(InputStream paramInputStream)
  {
    if (paramInputStream == null)
      return null;
    try
    {
      JSONObject localJSONObject1 = new JSONObject(new String(StreamTools.getBytes(paramInputStream)));
      if (localJSONObject1.has("citinfo"))
      {
        JSONObject localJSONObject2 = new JSONObject(localJSONObject1.getString("citinfo"));
        boolean bool = localJSONObject2.has("count");
        if (bool)
          try
          {
            this.cityInfo.setCount(Integer.valueOf(localJSONObject2.getString("count")).intValue());
            if (localJSONObject1.has("list"))
            {
              JSONArray localJSONArray = new JSONArray(localJSONObject1.getString("list"));
              for (int i = 0; i < localJSONArray.length(); i++)
              {
                this.cityItem = new CityItemById();
                JSONObject localJSONObject3 = localJSONArray.getJSONObject(i);
                if (localJSONObject3.has("level"))
                  this.cityItem.setLevel(localJSONObject3.getString("level"));
                if (localJSONObject3.has("id"))
                  this.cityItem.setId(localJSONObject3.getString("id"));
                if (localJSONObject3.has("name"))
                  this.cityItem.setName(localJSONObject3.getString("name"));
                if (localJSONObject3.has("type"))
                  this.cityItem.setType(localJSONObject3.getString("type"));
                this.arrCityItem.add(this.cityItem);
              }
            }
          }
          catch (Exception localException2)
          {
            while (true)
              this.cityInfo.setCount(0);
          }
      }
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
    }
    while (true)
    {
      Log.d("CityItemSAXParserJson", "CityItemSAXParserJson解析器解析得到的对象：cityInfo=" + this.cityInfo.toString());
      return this.cityInfo;
      this.cityInfo.setCount(0);
      break;
      this.cityInfo.setCount(0);
      break;
      this.cityInfo.setCityItems(this.arrCityItem);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.json.CityItemSAXParserJson
 * JD-Core Version:    0.6.2
 */