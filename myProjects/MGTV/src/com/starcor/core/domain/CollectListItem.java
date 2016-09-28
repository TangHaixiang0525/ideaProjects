package com.starcor.core.domain;

import android.text.TextUtils;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CollectListItem
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public long add_local_time;
  public String add_time = "";
  public String category_id = "";
  public String corner_mark_img = "";
  public String current_index_release_time = "0";
  public int duration;
  public String id = "";
  public String img_h = "";
  public String img_s = "";
  public String img_v = "";
  public String index_ui_style = "0";
  public String info = "";
  public String mark = "";
  public String media_assets_id = "";
  public String media_assets_name = "";
  public String new_index_release_time = "0";
  public int online = 1;
  public int played_time;
  public String quality = "";
  public String reason = "";
  public String state = "";
  public String sub_name = "";
  public String ts_begin = "";
  public String ts_day = "";
  public String ts_time_len = "";
  public String type = "";
  public int uiStyle = 0;
  public int update_index;
  public String version = "";
  public String videoIndexName = "";
  public String video_actor = "";
  public int video_all_index;
  public String video_director = "";
  public String video_id = "";
  public int video_index;
  public String video_kind = "";
  public String video_name = "";
  public int video_type;
  public int view_type;

  public boolean equals(Object paramObject)
  {
    CollectListItem localCollectListItem = (CollectListItem)paramObject;
    return this.video_id == localCollectListItem.video_id;
  }

  public long getDateInSeconds()
  {
    if (TextUtils.isEmpty(this.add_time))
      return 0L;
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    try
    {
      Date localDate2 = localSimpleDateFormat.parse(this.add_time);
      localDate1 = localDate2;
      return localDate1.getTime();
    }
    catch (ParseException localParseException)
    {
      while (true)
      {
        localParseException.printStackTrace();
        Date localDate1 = new Date();
      }
    }
  }

  public boolean hasChanged(CollectListItem paramCollectListItem)
  {
    if (paramCollectListItem == null);
    while ((!this.video_id.equals(paramCollectListItem.video_id)) || (this.played_time != paramCollectListItem.played_time))
      return true;
    return false;
  }

  public String toString()
  {
    return "CollectListItem [state=" + this.state + ", reason=" + this.reason + ", img_h=" + this.img_h + ", img_s=" + this.img_s + ", img_v=" + this.img_v + ", info=" + this.info + ", quality=" + this.quality + ", mark=" + this.mark + ", sub_name=" + this.sub_name + ", version=" + this.version + ", video_all_index=" + this.video_all_index + ", video_actor=" + this.video_actor + ", video_director=" + this.video_director + ", video_kind=" + this.video_kind + ", id=" + this.id + ", video_id=" + this.video_id + ", video_type=" + this.video_type + ", video_name=" + this.video_name + ", media_assets_id=" + this.media_assets_id + ", new_index_release_time=" + this.new_index_release_time + ", current_index_release_time=" + this.current_index_release_time + ", media_assets_name=" + this.media_assets_name + ", category_id=" + this.category_id + ", type=" + this.type + ", video_index=" + this.video_index + ", add_time=" + this.add_time + ", ts_begin=" + this.ts_begin + ", ts_day=" + this.ts_day + ", ts_time_len=" + this.ts_time_len + ", played_time=" + this.played_time + ", duration=" + this.duration + ", uiStyle=" + this.uiStyle + ", videoIndexName=" + this.videoIndexName + ", add_local_time=" + this.add_local_time + ", online=" + this.online + ", view_type=" + this.view_type + ", update_index=" + this.update_index + ",index_ui_style=" + this.index_ui_style + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.CollectListItem
 * JD-Core Version:    0.6.2
 */