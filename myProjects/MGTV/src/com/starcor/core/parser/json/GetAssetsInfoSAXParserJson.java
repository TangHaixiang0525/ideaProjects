package com.starcor.core.parser.json;

import android.util.Log;
import com.starcor.core.domain.CategoryItem;
import com.starcor.core.domain.CategoryItem.Item;
import com.starcor.core.domain.MediaAssetsInfo;
import com.starcor.core.interfaces.IXmlParser;
import java.io.InputStream;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class GetAssetsInfoSAXParserJson<Result>
  implements IXmlParser<Result>
{
  private static final String TAG = "GetAssetsInfoSAXParserJson";
  private MediaAssetsInfo assetsInfo = new MediaAssetsInfo();

  public Result parser(InputStream paramInputStream)
  {
    return null;
  }

  public Result parser(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte == null)
      return null;
    while (true)
    {
      int i;
      CategoryItem localCategoryItem;
      try
      {
        JSONObject localJSONObject1 = new JSONObject(new String(paramArrayOfByte));
        if (localJSONObject1.has("id"))
          this.assetsInfo.package_id = localJSONObject1.getString("id");
        if (localJSONObject1.has("name"))
          this.assetsInfo.package_name = localJSONObject1.getString("name");
        if (localJSONObject1.has("category"))
        {
          JSONArray localJSONArray1 = new JSONArray(localJSONObject1.getString("category"));
          this.assetsInfo.cList = new ArrayList();
          i = 0;
          if (i < localJSONArray1.length())
          {
            JSONObject localJSONObject2 = localJSONArray1.getJSONObject(i);
            localCategoryItem = new CategoryItem();
            if (localJSONObject2.has("id"))
              localCategoryItem.id = localJSONObject2.getString("id");
            if (localJSONObject2.has("name"))
              localCategoryItem.name = localJSONObject2.getString("name");
            if (localJSONObject2.has("type"))
              localCategoryItem.type = Integer.parseInt(localJSONObject2.getString("type"));
            try
            {
              localCategoryItem.uiStyle = Integer.valueOf(localJSONObject2.getString("ui_style")).intValue();
              if (localJSONObject2.has("category"))
              {
                JSONArray localJSONArray2 = new JSONArray(localJSONObject2.getString("category"));
                int j = 0;
                if (j < localJSONArray2.length())
                {
                  JSONObject localJSONObject3 = localJSONArray2.getJSONObject(j);
                  localCategoryItem.getClass();
                  CategoryItem.Item localItem = new CategoryItem.Item(localCategoryItem);
                  if (localJSONObject3.has("id"))
                    localItem.id = localJSONObject3.getString("id");
                  if (localJSONObject3.has("name"))
                    localItem.name = localJSONObject3.getString("name");
                  if (localJSONObject3.has("type"))
                    localItem.type = Integer.parseInt(localJSONObject3.getString("type"));
                  localCategoryItem.items.add(localItem);
                  j++;
                  continue;
                }
              }
            }
            catch (NumberFormatException localNumberFormatException)
            {
              localCategoryItem.uiStyle = 0;
              continue;
            }
          }
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        Log.e("GetAssetsInfoSAXParserJson", "GetAssetsInfoSAXParserJson解析器解析得到的对象：assetsInfo=" + this.assetsInfo);
        return this.assetsInfo;
      }
      this.assetsInfo.cList.add(localCategoryItem);
      i++;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.json.GetAssetsInfoSAXParserJson
 * JD-Core Version:    0.6.2
 */