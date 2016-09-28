package com.starcor.core.parser.json;

import android.util.Log;
import com.starcor.core.domain.AdInfo;
import com.starcor.core.domain.AdPos;
import com.starcor.core.domain.AdVideoInfo;
import com.starcor.core.domain.AdVideoTypeInfo;
import com.starcor.core.interfaces.IXmlParser;
import com.starcor.core.utils.JsonUtils;
import java.io.InputStream;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class GetAdInfoSAXParserJson<Result>
  implements IXmlParser<Result>
{
  private static final String TAG = "GetAdInfoSAXParserJson";
  private AdInfo adInfo;
  private ArrayList<AdPos> adPoslist = new ArrayList();
  private AdVideoInfo adVideoInfo;
  private AdVideoTypeInfo adVideoList;

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
      int j;
      int i2;
      int k;
      try
      {
        String str1 = new String(paramArrayOfByte);
        JSONObject localJSONObject1 = JsonUtils.getJsonObject(str1);
        if (localJSONObject1.has("ad_pos_list"))
        {
          JSONObject localJSONObject2 = new JSONObject(localJSONObject1.getString("ad_pos_list"));
          if (localJSONObject2.has("ad_pos"))
          {
            JSONArray localJSONArray1 = JsonUtils.getJsonArray(localJSONObject2.getString("ad_pos"));
            int i = 0;
            if (i < localJSONArray1.length())
            {
              AdPos localAdPos = new AdPos();
              this.adInfo = new AdInfo();
              localAdPos.ad_info = this.adInfo;
              JSONObject localJSONObject3 = localJSONArray1.getJSONObject(i);
              if (localJSONObject3.has("id"))
                localAdPos.id = localJSONObject3.getString("id");
              if (localJSONObject3.has("name"))
                localAdPos.name = localJSONObject3.getString("name");
              if (localJSONObject3.has("ad_info"))
              {
                JSONObject localJSONObject4 = new JSONObject(localJSONObject3.getString("ad_info"));
                j = 0;
                if (j < localJSONArray1.length())
                {
                  if (localJSONObject4.has("id"))
                    this.adInfo.id = localJSONObject4.getString("id");
                  if (localJSONObject4.has("name"))
                    this.adInfo.name = localJSONObject4.getString("name");
                  this.adInfo.adVideoTypeInfo = new ArrayList();
                  if (localJSONObject4.has("video_list"))
                  {
                    String str3 = localJSONObject4.getString("video_list");
                    JSONObject localJSONObject7 = new JSONObject(str3);
                    this.adVideoList = new AdVideoTypeInfo();
                    this.adInfo.adVideoTypeInfo.add(this.adVideoList);
                    i2 = 0;
                    int i3 = localJSONArray1.length();
                    if (i2 < i3)
                    {
                      if (localJSONObject7.has("action"))
                        this.adVideoList.action = localJSONObject7.getString("action");
                      if (localJSONObject7.has("interval"))
                        this.adVideoList.interval = localJSONObject7.getString("interval");
                      if (localJSONObject7.has("alpha"))
                        this.adVideoList.alpha = localJSONObject7.getString("alpha");
                      if (localJSONObject7.has("scale"))
                        this.adVideoList.scale = localJSONObject7.getString("scale");
                      if (!localJSONObject7.has("item"))
                        break label917;
                      JSONArray localJSONArray3 = JsonUtils.getJsonArray(localJSONObject7.getString("item"));
                      int i4 = 0;
                      int i5 = localJSONArray3.length();
                      if (i4 >= i5)
                        break label917;
                      JSONObject localJSONObject8 = localJSONArray3.getJSONObject(i4);
                      this.adVideoInfo = new AdVideoInfo();
                      this.adVideoList.adVideoInfoslist = new ArrayList();
                      if (localJSONObject8.has("video_id"))
                        this.adVideoInfo.video_id = localJSONObject8.getString("video_id");
                      if (localJSONObject8.has("video_type"))
                        this.adVideoInfo.video_type = localJSONObject8.getString("video_type");
                      this.adVideoList.adVideoInfoslist.add(this.adVideoInfo);
                      i4++;
                      continue;
                    }
                  }
                  if (!localJSONObject4.has("image_list"))
                    break label929;
                  String str2 = localJSONObject4.getString("image_list");
                  JSONObject localJSONObject5 = new JSONObject(str2);
                  this.adVideoList = new AdVideoTypeInfo();
                  this.adInfo.adVideoTypeInfo.add(this.adVideoList);
                  k = 0;
                  int m = localJSONArray1.length();
                  if (k >= m)
                    break label929;
                  if (localJSONObject5.has("action"))
                    this.adVideoList.action = localJSONObject5.getString("action");
                  if (localJSONObject5.has("interval"))
                    this.adVideoList.interval = localJSONObject5.getString("interval");
                  if (localJSONObject5.has("alpha"))
                    this.adVideoList.alpha = localJSONObject5.getString("alpha");
                  if (localJSONObject5.has("scale"))
                    this.adVideoList.scale = localJSONObject5.getString("scale");
                  if (!localJSONObject5.has("item"))
                    break label923;
                  JSONArray localJSONArray2 = JsonUtils.getJsonArray(localJSONObject5.getString("item"));
                  int n = 0;
                  int i1 = localJSONArray2.length();
                  if (n >= i1)
                    break label923;
                  JSONObject localJSONObject6 = localJSONArray2.getJSONObject(n);
                  this.adVideoInfo = new AdVideoInfo();
                  this.adVideoList.adVideoInfoslist = new ArrayList();
                  if (localJSONObject6.has("url"))
                    this.adVideoInfo.img_url = localJSONObject6.getString("url");
                  this.adVideoList.adVideoInfoslist.add(this.adVideoInfo);
                  n++;
                  continue;
                }
              }
              this.adPoslist.add(localAdPos);
              i++;
              continue;
            }
          }
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        Log.d("GetAdInfoSAXParserJson", "GetAdInfoSAXParserJson解析器解析得到的对象：adInfoItem=" + this.adPoslist);
        return this.adPoslist;
      }
      label917: i2++;
      continue;
      label923: k++;
      continue;
      label929: j++;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.json.GetAdInfoSAXParserJson
 * JD-Core Version:    0.6.2
 */