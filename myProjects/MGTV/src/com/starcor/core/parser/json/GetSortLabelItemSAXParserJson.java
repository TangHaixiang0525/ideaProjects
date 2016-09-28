package com.starcor.core.parser.json;

import com.starcor.core.domain.LabelSortItem;
import com.starcor.core.interfaces.IXmlParser;
import java.io.InputStream;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class GetSortLabelItemSAXParserJson<Result>
  implements IXmlParser<Result>
{
  private static final String TAG = "GetSortLabelItemSAXParserJson";
  private LabelSortItem sortItem;
  private ArrayList<LabelSortItem> sortItemList = new ArrayList();

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
      if (localJSONObject1.has("video_label"))
      {
        JSONArray localJSONArray = localJSONObject1.getJSONArray("video_label");
        int i = 0;
        while (true)
          if (i < localJSONArray.length())
          {
            this.sortItem = new LabelSortItem();
            JSONObject localJSONObject2 = localJSONArray.getJSONObject(i);
            if (localJSONObject2.has("id"))
              this.sortItem.id = localJSONObject2.getString("id");
            if (localJSONObject2.has("name"))
              this.sortItem.name = localJSONObject2.getString("name");
            try
            {
              if (localJSONObject2.has("click"))
                this.sortItem.click_count = Integer.valueOf(localJSONObject2.getString("click")).intValue();
              if (localJSONObject2.has("source"))
                this.sortItem.source = localJSONObject2.getString("source");
              this.sortItemList.add(this.sortItem);
              i++;
            }
            catch (Exception localException2)
            {
              while (true)
              {
                localException2.printStackTrace();
                this.sortItem.click_count = 0;
              }
            }
          }
      }
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
    }
    return this.sortItemList;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.json.GetSortLabelItemSAXParserJson
 * JD-Core Version:    0.6.2
 */