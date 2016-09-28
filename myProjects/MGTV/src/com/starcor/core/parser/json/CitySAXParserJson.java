package com.starcor.core.parser.json;

import android.util.Log;
import com.starcor.core.domain.CityItem;
import com.starcor.core.domain.CityStruct;
import com.starcor.core.interfaces.IXmlParser;
import com.starcor.core.utils.StreamTools;
import java.io.InputStream;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class CitySAXParserJson<Result>
  implements IXmlParser<Result>
{
  private static final String TAG = "CitySAXParserJson";
  ArrayList<CityItem> arrCityItem = new ArrayList();
  private CityItem cityItem;
  private CityStruct cityStruct = new CityStruct();

  public Result parser(InputStream paramInputStream)
  {
    if (paramInputStream == null)
      return null;
    try
    {
      JSONObject localJSONObject1 = new JSONObject(new String(StreamTools.getBytes(paramInputStream)));
      JSONObject localJSONObject2;
      if (localJSONObject1.has("citylist"))
      {
        localJSONObject2 = new JSONObject(localJSONObject1.getString("citylist"));
        if (localJSONObject2.has("city_type"))
          this.cityStruct.setCity_type(localJSONObject2.getString("city_type"));
        boolean bool = localJSONObject2.has("count");
        if (!bool);
      }
      try
      {
        this.cityStruct.setCount(Integer.valueOf(localJSONObject2.getString("count")).intValue());
        if (localJSONObject1.has("list"))
        {
          JSONArray localJSONArray = new JSONArray(localJSONObject1.getString("list"));
          for (int i = 0; i < localJSONArray.length(); i++)
          {
            this.cityItem = new CityItem();
            JSONObject localJSONObject3 = localJSONArray.getJSONObject(i);
            if (localJSONObject3.has("id"))
              this.cityItem.setId(localJSONObject3.getString("id"));
            if (localJSONObject3.has("name"))
              this.cityItem.setName(localJSONObject3.getString("name"));
            if (localJSONObject3.has("pid"))
              this.cityItem.setPid(localJSONObject3.getString("pid"));
            if (localJSONObject3.has("pinyin_pre"))
              this.cityItem.setPinyin_pre(localJSONObject3.getString("pinyin_pre"));
            if (localJSONObject3.has("type"))
              this.cityItem.setType(localJSONObject3.getString("type"));
            this.arrCityItem.add(this.cityItem);
          }
        }
      }
      catch (Exception localException2)
      {
        while (true)
        {
          localException2.printStackTrace();
          this.cityStruct.setCount(0);
        }
      }
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
    }
    while (true)
    {
      Log.e("CitySAXParserJson", "CitySAXParserJson解析器解析得到的对象：cityStruct=" + this.cityStruct);
      return this.cityStruct;
      this.cityStruct.setCity_item(this.arrCityItem);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.json.CitySAXParserJson
 * JD-Core Version:    0.6.2
 */