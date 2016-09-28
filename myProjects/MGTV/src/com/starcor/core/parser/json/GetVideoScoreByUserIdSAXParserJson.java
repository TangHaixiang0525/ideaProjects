package com.starcor.core.parser.json;

import com.starcor.core.domain.VideoScoreInfoOnUser;
import com.starcor.core.interfaces.IXmlParser;
import com.starcor.core.utils.Logger;
import java.io.InputStream;
import org.json.JSONObject;

public class GetVideoScoreByUserIdSAXParserJson<Result>
  implements IXmlParser<Result>
{
  private static final String TAG = "GetVideoScoreByUserIdSAXParserJson";
  private VideoScoreInfoOnUser score = new VideoScoreInfoOnUser();

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
      if (localJSONObject1.has("video"))
        localJSONObject1.getString("video");
      JSONObject localJSONObject2 = localJSONObject1.getJSONObject("video");
      if (localJSONObject2.has("id"))
        this.score.video_id = localJSONObject2.getString("id");
      if (localJSONObject2.has("user_ip"))
        this.score.user_ip = localJSONObject2.getString("user_ip");
      if (localJSONObject2.has("user_score_time"))
        this.score.user_score_time = localJSONObject2.getString("user_score_time");
      if (localJSONObject2.has("count"))
        this.score.count = localJSONObject2.getString("count");
      if (localJSONObject2.has("score"))
        this.score.score = localJSONObject2.getString("score");
      if (localJSONObject2.has("user_score"))
        this.score.user_score = localJSONObject2.getString("user_score");
      Logger.e("GetVideoScoreByUserIdSAXParserJson", "GetVideoScoreByUserIdSAXParserJson解析器解析得到的对象：score=" + paramArrayOfByte.toString());
      return this.score;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.json.GetVideoScoreByUserIdSAXParserJson
 * JD-Core Version:    0.6.2
 */