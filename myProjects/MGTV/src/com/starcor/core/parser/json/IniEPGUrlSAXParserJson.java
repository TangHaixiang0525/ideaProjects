package com.starcor.core.parser.json;

import com.starcor.core.domain.MetadataInfo;
import com.starcor.core.interfaces.IXmlParser;
import com.starcor.core.utils.JsonUtils;
import com.starcor.core.utils.Logger;
import com.starcor.core.utils.StreamTools;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class IniEPGUrlSAXParserJson<Result>
  implements IXmlParser<Result>
{
  private static final String TAG = "IniEPGUrlSAXParserJson";
  private MetadataInfo metaInfo;
  private ArrayList<Serializable> metaList = null;

  public Result parser(InputStream paramInputStream)
  {
    if (paramInputStream == null)
      return null;
    if (this.metaList == null)
      this.metaList = new ArrayList();
    try
    {
      while (true)
      {
        JSONObject localJSONObject1 = JsonUtils.getJsonObject(new String(StreamTools.getBytes(paramInputStream)));
        if (localJSONObject1.has("n3_a"))
        {
          JSONObject localJSONObject2 = new JSONObject(localJSONObject1.getString("n3_a"));
          if (localJSONObject2.has("n3_a_a"))
          {
            JSONObject localJSONObject10 = localJSONObject2.getJSONObject("n3_a_a");
            if (localJSONObject10.has("url"))
              com.starcor.core.domain.AppInfo.URL_n3_a_i[0] = localJSONObject10.getString("url");
          }
          if (localJSONObject2.has("n3_a_b"))
          {
            JSONObject localJSONObject9 = localJSONObject2.getJSONObject("n3_a_b");
            if (localJSONObject9.has("url"))
              com.starcor.core.domain.AppInfo.URL_n3_a_i[1] = localJSONObject9.getString("url");
          }
          if (localJSONObject2.has("n3_a_c"))
          {
            JSONObject localJSONObject8 = localJSONObject2.getJSONObject("n3_a_c");
            if (localJSONObject8.has("url"))
              com.starcor.core.domain.AppInfo.URL_n3_a_i[2] = localJSONObject8.getString("url");
          }
          if (localJSONObject2.has("n3_a_d"))
          {
            JSONObject localJSONObject7 = localJSONObject2.getJSONObject("n3_a_d");
            if (localJSONObject7.has("url"))
            {
              com.starcor.core.domain.AppInfo.URL_n3_a_i[3] = localJSONObject7.getString("url");
              Logger.i(" ---> 初始化      N3_A_D总入口地址获取成功");
            }
          }
          if (localJSONObject2.has("n3_a_e"))
          {
            JSONObject localJSONObject6 = localJSONObject2.getJSONObject("n3_a_e");
            if (localJSONObject6.has("url"))
              com.starcor.core.domain.AppInfo.URL_n3_a_i[4] = localJSONObject6.getString("url");
          }
          if (localJSONObject2.has("n3_a_g"))
          {
            JSONObject localJSONObject5 = localJSONObject2.getJSONObject("n3_a_g");
            if (localJSONObject5.has("url"))
              com.starcor.core.domain.AppInfo.URL_n3_a_i[5] = localJSONObject5.getString("url");
          }
        }
        if (!localJSONObject1.has("metadata"))
          break;
        JSONObject localJSONObject3 = localJSONObject1.getJSONObject("metadata");
        if (!localJSONObject3.has("items"))
          break;
        JSONArray localJSONArray = localJSONObject3.getJSONArray("items");
        for (int i = 0; i < localJSONArray.length(); i++)
        {
          this.metaInfo = new MetadataInfo();
          JSONObject localJSONObject4 = localJSONArray.getJSONObject(i);
          if (localJSONObject4.has("type"))
            this.metaInfo.type = localJSONObject4.getString("type");
          if (localJSONObject4.has("packet_type"))
            this.metaInfo.packet_type = localJSONObject4.getString("packet_type");
          if (localJSONObject4.has("packet_id"))
            this.metaInfo.packet_id = localJSONObject4.getString("packet_id");
          if (localJSONObject4.has("category_id"))
            this.metaInfo.category_id = localJSONObject4.getString("category_id");
          if (localJSONObject4.has("action_type"))
            this.metaInfo.action_type = localJSONObject4.getString("action_type");
          if (localJSONObject4.has("name"))
            this.metaInfo.name = localJSONObject4.getString("name");
          if (localJSONObject4.has("image"))
            this.metaInfo.img_url = localJSONObject4.getString("image");
          if (localJSONObject4.has("image1"))
            this.metaInfo.img_url1 = localJSONObject4.getString("image1");
          if (localJSONObject4.has("image2"))
            this.metaInfo.img_url2 = localJSONObject4.getString("image2");
          if (localJSONObject4.has("grou"))
            this.metaInfo.group = localJSONObject4.getString("group");
          if (localJSONObject4.has("url"))
            this.metaInfo.url = localJSONObject4.getString("url");
          this.metaList.add(this.metaInfo);
        }
        this.metaList.clear();
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      Logger.i("IniEPGUrlSAXParserJson", "IniEPGUrlSAXParserJson解析器解析得到的对象：metaList=" + this.metaList);
    }
    return this.metaList;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.json.IniEPGUrlSAXParserJson
 * JD-Core Version:    0.6.2
 */