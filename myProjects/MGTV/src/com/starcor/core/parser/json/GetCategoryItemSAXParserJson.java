package com.starcor.core.parser.json;

import android.util.Log;
import com.starcor.core.domain.CategoryItem;
import com.starcor.core.domain.MediaAssetsInfo;
import com.starcor.core.interfaces.IXmlParser;
import com.starcor.core.utils.StreamTools;
import java.io.InputStream;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class GetCategoryItemSAXParserJson<Result>
  implements IXmlParser<Result>
{
  private static final String TAG = "GetCategoryItemSAXParserJson";
  private MediaAssetsInfo cInfo;
  private ArrayList<CategoryItem> cList;
  private String package_id;
  private CategoryItem pd;

  public GetCategoryItemSAXParserJson(String paramString)
  {
    this.package_id = paramString;
    this.cInfo = new MediaAssetsInfo();
  }

  public Result parser(InputStream paramInputStream)
  {
    if (paramInputStream == null)
      return null;
    if (this.cList == null)
      this.cList = new ArrayList();
    try
    {
      while (true)
      {
        JSONObject localJSONObject1 = new JSONObject(new String(StreamTools.getBytes(paramInputStream)));
        if (!localJSONObject1.has("category"))
          break;
        JSONArray localJSONArray = new JSONArray(localJSONObject1.getString("category"));
        for (int i = 0; i < localJSONArray.length(); i++)
        {
          JSONObject localJSONObject2 = localJSONArray.getJSONObject(i);
          this.pd = new CategoryItem();
          if (localJSONObject2.has("id"))
            this.pd.id = localJSONObject2.getString("id");
          if (localJSONObject2.has("name"))
            this.pd.name = localJSONObject2.getString("name");
          if (localJSONObject2.has("type"))
            this.pd.type = Integer.parseInt(localJSONObject2.getString("type"));
          if (localJSONObject2.has("img2"))
            this.pd.img_url = localJSONObject2.getString("img2");
          this.cList.add(this.pd);
        }
        this.cList.clear();
      }
      this.cInfo.package_id = this.package_id;
      this.cInfo.cList = this.cList;
      Log.e("GetCategoryItemSAXParserJson", "GetCategoryItemSAXParserJson解析器解析得到的对象：cInfo=" + this.cInfo);
      return this.cInfo;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.json.GetCategoryItemSAXParserJson
 * JD-Core Version:    0.6.2
 */