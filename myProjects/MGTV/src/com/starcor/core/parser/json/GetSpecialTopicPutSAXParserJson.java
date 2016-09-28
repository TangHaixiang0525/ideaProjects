package com.starcor.core.parser.json;

import android.util.Log;
import com.starcor.core.domain.SpecialTopicPutInfo;
import com.starcor.core.interfaces.IXmlParser;
import com.starcor.core.utils.JsonUtils;
import java.io.InputStream;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class GetSpecialTopicPutSAXParserJson<Result>
  implements IXmlParser<Result>
{
  private static final String TAG = "GetSpecialTopicPutSAXParserJson";
  private SpecialTopicPutInfo sTopicPutInfo;
  private ArrayList<SpecialTopicPutInfo> sTopicPutInfolist = new ArrayList();

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
      JSONArray localJSONArray = JsonUtils.getJsonArray(new String(paramArrayOfByte));
      for (int i = 0; i < localJSONArray.length(); i++)
      {
        JSONObject localJSONObject = localJSONArray.getJSONObject(i);
        this.sTopicPutInfo = new SpecialTopicPutInfo();
        if (localJSONObject.has("id"))
          this.sTopicPutInfo.id = localJSONObject.getString("id");
        if (localJSONObject.has("binding_id"))
          this.sTopicPutInfo.binding_id = localJSONObject.getString("binding_id");
        if (localJSONObject.has("name"))
          this.sTopicPutInfo.name = localJSONObject.getString("name");
        if (localJSONObject.has("ex_name"))
          this.sTopicPutInfo.ex_name = localJSONObject.getString("ex_name");
        if (localJSONObject.has("ex_intor"))
          this.sTopicPutInfo.ex_intor = localJSONObject.getString("ex_intor");
        if (localJSONObject.has("poster"))
          this.sTopicPutInfo.poster = localJSONObject.getString("poster");
        if (localJSONObject.has("img0"))
          this.sTopicPutInfo.img0 = localJSONObject.getString("img0");
        if (localJSONObject.has("img1"))
          this.sTopicPutInfo.img1 = localJSONObject.getString("img1");
        if (localJSONObject.has("img2"))
          this.sTopicPutInfo.img2 = localJSONObject.getString("img2");
        if (localJSONObject.has("click"))
          this.sTopicPutInfo.click = localJSONObject.getString("click");
        if (localJSONObject.has("summary"))
          this.sTopicPutInfo.summary = localJSONObject.getString("summary");
        if (localJSONObject.has("nns_ex_data"))
          this.sTopicPutInfo.nns_ex_data = localJSONObject.getString("nns_ex_data");
        if (localJSONObject.has("play_type"))
          this.sTopicPutInfo.play_type = localJSONObject.getString("play_type");
        if (localJSONObject.has("category_id"))
          this.sTopicPutInfo.category_id = localJSONObject.getString("category_id");
        if (localJSONObject.has("online_mode"))
          this.sTopicPutInfo.online_mode = localJSONObject.getString("online_mode");
        if (localJSONObject.has("type"))
          this.sTopicPutInfo.type = localJSONObject.getString("type");
        if (localJSONObject.has("url"))
          this.sTopicPutInfo.url = localJSONObject.getString("url");
        this.sTopicPutInfolist.add(this.sTopicPutInfo);
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      Log.d("GetSpecialTopicPutSAXParserJson", "GetSpecialTopicPutSAXParserJson解析器解析得到的对象：adInfoItem=" + this.sTopicPutInfolist);
    }
    return this.sTopicPutInfolist;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.json.GetSpecialTopicPutSAXParserJson
 * JD-Core Version:    0.6.2
 */