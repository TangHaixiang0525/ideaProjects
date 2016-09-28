package com.starcor.core.parser.json;

import android.util.Log;
import com.starcor.core.domain.LabelSort;
import com.starcor.core.interfaces.IXmlParser;
import com.starcor.core.utils.StreamTools;
import java.io.InputStream;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class GetSortLabelSAXParserJson<Result>
  implements IXmlParser<Result>
{
  private static final String TAG = "GetSortLabelSAXParserJson";
  private LabelSort labelSort;
  private ArrayList<LabelSort> labelSortList = new ArrayList();

  public Result parser(InputStream paramInputStream)
  {
    if (paramInputStream == null)
      return null;
    try
    {
      JSONObject localJSONObject1 = new JSONObject(new String(StreamTools.getBytes(paramInputStream)));
      if (localJSONObject1.has("video_label_type"))
      {
        JSONArray localJSONArray = localJSONObject1.getJSONArray("video_label_type");
        int i = 0;
        while (true)
          if (i < localJSONArray.length())
          {
            this.labelSort = new LabelSort();
            JSONObject localJSONObject2 = localJSONArray.getJSONObject(i);
            if (localJSONObject2.has("type_name"))
              this.labelSort.type_name = localJSONObject2.getString("type_name");
            if (localJSONObject2.has("type_id"))
              this.labelSort.label_id = localJSONObject2.getString("type_id");
            try
            {
              if (localJSONObject2.has("video_label_count"))
                this.labelSort.label_count = Integer.valueOf(localJSONObject2.getString("video_label_count")).intValue();
              this.labelSortList.add(this.labelSort);
              i++;
            }
            catch (Exception localException2)
            {
              while (true)
              {
                localException2.printStackTrace();
                this.labelSort.label_count = 0;
              }
            }
          }
      }
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
    }
    return this.labelSortList;
  }

  public Result parser(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte == null)
      return null;
    try
    {
      JSONObject localJSONObject1 = new JSONObject(new String(paramArrayOfByte));
      if (localJSONObject1.has("video_label_type"))
      {
        JSONArray localJSONArray = new JSONArray(localJSONObject1.getString("video_label_type"));
        int i = 0;
        while (true)
          if (i < localJSONArray.length())
          {
            this.labelSort = new LabelSort();
            JSONObject localJSONObject2 = localJSONArray.getJSONObject(i);
            if (localJSONObject2.has("type_name"))
              this.labelSort.type_name = localJSONObject2.getString("type_name");
            if (localJSONObject2.has("type_id"))
              this.labelSort.label_id = localJSONObject2.getString("type_id");
            try
            {
              if (localJSONObject2.has("video_label_count"))
                this.labelSort.label_count = Integer.valueOf(localJSONObject2.getString("video_label_count")).intValue();
              this.labelSortList.add(this.labelSort);
              i++;
            }
            catch (Exception localException2)
            {
              while (true)
              {
                localException2.printStackTrace();
                this.labelSort.label_count = 0;
              }
            }
          }
      }
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      Log.e("GetSortLabelSAXParserJson", "GetSortLabelSAXParserJson解析器解析得到的对象：labelSortList=" + paramArrayOfByte.toString());
    }
    return this.labelSortList;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.json.GetSortLabelSAXParserJson
 * JD-Core Version:    0.6.2
 */