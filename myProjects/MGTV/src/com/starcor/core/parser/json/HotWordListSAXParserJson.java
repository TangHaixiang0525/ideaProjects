package com.starcor.core.parser.json;

import com.starcor.core.domain.HotWord;
import com.starcor.core.domain.HotWordList;
import com.starcor.core.interfaces.IXmlParser;
import com.starcor.core.utils.StreamTools;
import java.io.InputStream;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class HotWordListSAXParserJson<Result>
  implements IXmlParser<Result>
{
  private HotWord hotWord;
  private HotWordList htList = new HotWordList();

  public Result parser(InputStream paramInputStream)
  {
    if (paramInputStream == null)
      return null;
    try
    {
      JSONObject localJSONObject1 = new JSONObject(new String(StreamTools.getBytes(paramInputStream)));
      this.htList.hotWordList = new ArrayList();
      boolean bool1 = localJSONObject1.has("count");
      if (bool1);
      try
      {
        this.htList.count = Integer.parseInt(localJSONObject1.getString("count"));
        if (localJSONObject1.has("hot_word_list"))
        {
          JSONArray localJSONArray = localJSONObject1.getJSONArray("hot_word_list");
          i = 0;
          if (i < localJSONArray.length())
          {
            this.hotWord = new HotWord();
            localJSONObject2 = localJSONArray.getJSONObject(i);
            if (localJSONObject2.has("name"))
              this.hotWord.name = localJSONObject2.getString("name");
            if (localJSONObject2.has("pinyin"))
              this.hotWord.pinyin = localJSONObject2.getString("pinyin");
            if (localJSONObject2.has("video_id"))
              this.hotWord.video_id = localJSONObject2.getString("video_id");
            if (localJSONObject2.has("image0"))
              this.hotWord.image0 = localJSONObject2.getString("image0");
            if (localJSONObject2.has("image1"))
              this.hotWord.image1 = localJSONObject2.getString("image1");
            if (localJSONObject2.has("image2"))
              this.hotWord.image2 = localJSONObject2.getString("image2");
            if (localJSONObject2.has("cornermark"))
              this.hotWord.cornermark = localJSONObject2.getString("cornermark");
            boolean bool2 = localJSONObject2.has("count");
            if (!bool2);
          }
        }
      }
      catch (Exception localException3)
      {
        try
        {
          while (true)
          {
            int i;
            JSONObject localJSONObject2;
            this.hotWord.count = Integer.parseInt(localJSONObject2.getString("count"));
            this.htList.hotWordList.add(this.hotWord);
            i++;
          }
          localException3 = localException3;
          this.htList.count = 0;
        }
        catch (Exception localException2)
        {
          while (true)
            this.hotWord.count = 0;
        }
      }
      return this.htList;
    }
    catch (Exception localException1)
    {
    }
    return null;
  }

  public Result parser(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte == null)
      return null;
    try
    {
      JSONObject localJSONObject1 = new JSONObject(new String(paramArrayOfByte));
      this.htList.hotWordList = new ArrayList();
      boolean bool1 = localJSONObject1.has("count");
      if (bool1);
      try
      {
        this.htList.count = Integer.parseInt(localJSONObject1.getString("count"));
        if (localJSONObject1.has("hot_word_list"))
        {
          JSONArray localJSONArray = localJSONObject1.getJSONArray("hot_word_list");
          i = 0;
          if (i < localJSONArray.length())
          {
            this.hotWord = new HotWord();
            localJSONObject2 = localJSONArray.getJSONObject(i);
            if (localJSONObject2.has("name"))
              this.hotWord.name = localJSONObject2.getString("name");
            if (localJSONObject2.has("pinyin"))
              this.hotWord.pinyin = localJSONObject2.getString("pinyin");
            if (localJSONObject2.has("video_id"))
              this.hotWord.video_id = localJSONObject2.getString("video_id");
            if (localJSONObject2.has("image0"))
              this.hotWord.image0 = localJSONObject2.getString("image0");
            if (localJSONObject2.has("image1"))
              this.hotWord.image1 = localJSONObject2.getString("image1");
            if (localJSONObject2.has("image2"))
              this.hotWord.image2 = localJSONObject2.getString("image2");
            if (localJSONObject2.has("cornermark"))
              this.hotWord.cornermark = localJSONObject2.getString("cornermark");
            boolean bool2 = localJSONObject2.has("count");
            if (!bool2);
          }
        }
      }
      catch (Exception localException3)
      {
        try
        {
          while (true)
          {
            int i;
            JSONObject localJSONObject2;
            this.hotWord.count = Integer.parseInt(localJSONObject2.getString("count"));
            this.htList.hotWordList.add(this.hotWord);
            i++;
          }
          localException3 = localException3;
          this.htList.count = 0;
        }
        catch (Exception localException2)
        {
          while (true)
            this.hotWord.count = 0;
        }
      }
      return this.htList;
    }
    catch (Exception localException1)
    {
    }
    return null;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.json.HotWordListSAXParserJson
 * JD-Core Version:    0.6.2
 */