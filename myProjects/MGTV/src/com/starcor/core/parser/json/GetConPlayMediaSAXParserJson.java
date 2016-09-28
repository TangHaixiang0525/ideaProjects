package com.starcor.core.parser.json;

import android.text.TextUtils;
import com.starcor.core.domain.ConPlayMedia;
import com.starcor.core.interfaces.IXmlParser;
import java.io.InputStream;
import org.json.JSONException;
import org.json.JSONObject;

public class GetConPlayMediaSAXParserJson<Result>
  implements IXmlParser<Result>
{
  ConPlayMedia result = new ConPlayMedia();

  public Result parser(InputStream paramInputStream)
  {
    return null;
  }

  public Result parser(byte[] paramArrayOfByte)
  {
    if ((paramArrayOfByte == null) || (paramArrayOfByte.length <= 0));
    while (true)
    {
      return null;
      try
      {
        JSONObject localJSONObject = new JSONObject(new String(paramArrayOfByte)).getJSONObject("item");
        this.result.videoId = localJSONObject.getString("video_id");
        if (!TextUtils.isEmpty(this.result.videoId))
        {
          this.result.assetId = localJSONObject.optString("asset_id");
          this.result.videoName = localJSONObject.optString("video_name");
          this.result.videoType = localJSONObject.optInt("video_type");
          this.result.videoIndex = localJSONObject.optInt("video_index");
          ConPlayMedia localConPlayMedia = this.result;
          return localConPlayMedia;
        }
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
      }
    }
    return null;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.json.GetConPlayMediaSAXParserJson
 * JD-Core Version:    0.6.2
 */