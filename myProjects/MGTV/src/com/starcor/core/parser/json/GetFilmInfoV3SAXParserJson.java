package com.starcor.core.parser.json;

import com.starcor.core.domain.VideoInfo;
import com.starcor.core.domain.VideoRoleInfo;
import com.starcor.core.interfaces.IXmlParser;
import java.io.InputStream;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class GetFilmInfoV3SAXParserJson<Result>
  implements IXmlParser<Result>
{
  private static final String TAG = "GetFilmInfoV3SAXParserJson";
  private VideoInfo videoInfo = new VideoInfo();

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
      String str = new String(paramArrayOfByte);
      JSONObject localJSONObject1 = new JSONObject(str).optJSONObject("i");
      this.videoInfo.videoId = localJSONObject1.optString("id");
      this.videoInfo.name = localJSONObject1.optString("name");
      this.videoInfo.imgHUrl = localJSONObject1.optString("img_h");
      this.videoInfo.imgVUrl = localJSONObject1.optString("img_v");
      JSONObject localJSONObject2 = localJSONObject1.optJSONObject("arg_list");
      if (localJSONObject2 != null)
      {
        JSONObject localJSONObject3 = localJSONObject2.optJSONObject("vod_info");
        this.videoInfo.import_id = localJSONObject3.optString("import_id");
        this.videoInfo.desc = localJSONObject3.optString("summary");
        this.videoInfo.abstractInfo = localJSONObject3.optString("abstract");
        this.videoInfo.watch_focus = localJSONObject3.optString("watch_focus");
        this.videoInfo.showTime = localJSONObject3.optString("show_time");
        this.videoInfo.indexCount = localJSONObject3.optInt("index_count");
        this.videoInfo.indexCurrent = localJSONObject3.optInt("video_new_index");
        this.videoInfo.play_count = localJSONObject3.optInt("play_count");
        this.videoInfo.kind = localJSONObject3.optString("kind");
        this.videoInfo.view_type = localJSONObject3.optInt("view_type");
        this.videoInfo.definition = localJSONObject3.optString("definition");
        JSONObject localJSONObject4 = localJSONObject3.optJSONObject("ext_info");
        if (localJSONObject4 != null)
        {
          this.videoInfo.serial_id = localJSONObject4.optString("series_id");
          this.videoInfo.is_show_new_index = localJSONObject4.optInt("is_show_new_index");
        }
        this.videoInfo.index_order = localJSONObject3.optString("index_order");
        this.videoInfo.actor = localJSONObject3.optString("actor");
        this.videoInfo.area = localJSONObject3.optString("area");
        this.videoInfo.timeLen = localJSONObject3.optString("time_len");
        this.videoInfo.director = localJSONObject3.optString("director");
        this.videoInfo.index_ui_type = localJSONObject3.optString("index_ui_style");
        JSONObject localJSONObject5 = localJSONObject3.optJSONObject("corner_mark");
        if (localJSONObject5 != null)
        {
          this.videoInfo.mark = localJSONObject5.optString("mark");
          this.videoInfo.corner_mark_img_url = localJSONObject5.optString("corner_mark_img");
        }
        JSONObject localJSONObject6 = localJSONObject3.optJSONObject("actor_label_list");
        this.videoInfo.actorList = new ArrayList();
        if (localJSONObject6 != null)
        {
          JSONArray localJSONArray1 = localJSONObject6.optJSONArray("actor_label");
          for (int i = 0; i < localJSONArray1.length(); i++)
          {
            VideoRoleInfo localVideoRoleInfo1 = new VideoRoleInfo();
            JSONObject localJSONObject7 = localJSONArray1.optJSONObject(i);
            localVideoRoleInfo1.name = localJSONObject7.optString("name");
            localVideoRoleInfo1.labelID = localJSONObject7.optString("label_id");
            this.videoInfo.actorList.add(localVideoRoleInfo1);
          }
        }
        JSONObject localJSONObject8 = localJSONObject3.optJSONObject("director_label_list");
        this.videoInfo.directorList = new ArrayList();
        if (localJSONObject8 != null)
        {
          JSONArray localJSONArray2 = localJSONObject8.optJSONArray("director_label");
          for (int j = 0; j < localJSONArray2.length(); j++)
          {
            VideoRoleInfo localVideoRoleInfo2 = new VideoRoleInfo();
            JSONObject localJSONObject9 = localJSONArray2.optJSONObject(j);
            localVideoRoleInfo2.name = localJSONObject9.optString("name");
            localVideoRoleInfo2.labelID = localJSONObject9.optString("label_id");
            this.videoInfo.directorList.add(localVideoRoleInfo2);
          }
        }
        this.videoInfo.user_score = localJSONObject3.optDouble("user_score");
        this.videoInfo.is_show_index = localJSONObject3.optInt("is_show_index");
      }
      return this.videoInfo;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.json.GetFilmInfoV3SAXParserJson
 * JD-Core Version:    0.6.2
 */