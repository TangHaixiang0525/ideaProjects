package com.starcor.core.parser.json;

import android.util.Log;
import com.starcor.core.domain.SpecialTopicTreeCategory;
import com.starcor.core.domain.SpecialTopicTreeCategorylast;
import com.starcor.core.domain.SpecialTopicTreeInfo;
import com.starcor.core.interfaces.IXmlParser;
import com.starcor.core.utils.JsonUtils;
import java.io.InputStream;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class GetSpecialTopicColumnTreeSAXParserJson<Result>
  implements IXmlParser<Result>
{
  private static final String TAG = "GetSpecialTopicColumnTreeSAXParserJson";
  private SpecialTopicTreeCategorylast sCategorylast;
  private SpecialTopicTreeInfo sTopicTreeInfo = new SpecialTopicTreeInfo();
  private SpecialTopicTreeCategory specialTpcTreeCatge;

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
      JSONObject localJSONObject1 = JsonUtils.getJsonObject(new String(paramArrayOfByte));
      if (localJSONObject1.has("id"))
        this.sTopicTreeInfo.id = localJSONObject1.getString("id");
      if (localJSONObject1.has("name"))
        this.sTopicTreeInfo.name = localJSONObject1.getString("name");
      if (localJSONObject1.has("img0"))
        this.sTopicTreeInfo.img0 = localJSONObject1.getString("img0");
      if (localJSONObject1.has("img1"))
        this.sTopicTreeInfo.img1 = localJSONObject1.getString("img1");
      if (localJSONObject1.has("img2"))
        this.sTopicTreeInfo.img2 = localJSONObject1.getString("img2");
      if (localJSONObject1.has("display_num"))
        this.sTopicTreeInfo.display_num = localJSONObject1.getString("display_num");
      if (localJSONObject1.has("index_ui"))
        this.sTopicTreeInfo.index_ui = localJSONObject1.getString("index_ui");
      if (localJSONObject1.has("category"))
      {
        JSONArray localJSONArray1 = JsonUtils.getJsonArray(localJSONObject1.getString("category"));
        this.sTopicTreeInfo.specialTpcTreeCatge = new ArrayList();
        for (int i = 0; i < localJSONArray1.length(); i++)
        {
          JSONObject localJSONObject2 = localJSONArray1.getJSONObject(i);
          this.specialTpcTreeCatge = new SpecialTopicTreeCategory();
          if (localJSONObject2.has("id"))
            this.specialTpcTreeCatge.id = localJSONObject2.getString("id");
          if (localJSONObject2.has("name"))
            this.specialTpcTreeCatge.name = localJSONObject2.getString("name");
          if (localJSONObject2.has("img0"))
            this.specialTpcTreeCatge.img0 = localJSONObject2.getString("img0");
          if (localJSONObject2.has("img1"))
            this.specialTpcTreeCatge.img1 = localJSONObject2.getString("img1");
          if (localJSONObject2.has("img2"))
            this.specialTpcTreeCatge.img2 = localJSONObject2.getString("img2");
          if (localJSONObject2.has("category"))
          {
            JSONArray localJSONArray2 = JsonUtils.getJsonArray(localJSONObject2.getString("category"));
            this.specialTpcTreeCatge.sTopicTreeCgylst = new ArrayList();
            for (int j = 0; j < localJSONArray2.length(); j++)
            {
              JSONObject localJSONObject3 = localJSONArray2.getJSONObject(j);
              this.sCategorylast = new SpecialTopicTreeCategorylast();
              if (localJSONObject3.has("id"))
                this.sCategorylast.id = localJSONObject3.getString("id");
              if (localJSONObject3.has("name"))
                this.sCategorylast.name = localJSONObject3.getString("name");
              if (localJSONObject3.has("img0"))
                this.sCategorylast.img0 = localJSONObject3.getString("img0");
              if (localJSONObject3.has("img1"))
                this.sCategorylast.img1 = localJSONObject3.getString("img1");
              if (localJSONObject3.has("img2"))
                this.sCategorylast.img2 = localJSONObject3.getString("img2");
              this.specialTpcTreeCatge.sTopicTreeCgylst.add(this.sCategorylast);
            }
          }
          this.sTopicTreeInfo.specialTpcTreeCatge.add(this.specialTpcTreeCatge);
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      Log.d("GetSpecialTopicColumnTreeSAXParserJson", "GetSpecialTopicColumnTreeSAXParserJson解析器解析得到的对象：adInfoItem=" + this.sTopicTreeInfo);
    }
    return this.sTopicTreeInfo;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.json.GetSpecialTopicColumnTreeSAXParserJson
 * JD-Core Version:    0.6.2
 */