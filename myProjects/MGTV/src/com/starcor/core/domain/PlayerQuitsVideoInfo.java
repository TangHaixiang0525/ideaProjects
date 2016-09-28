package com.starcor.core.domain;

import java.io.Serializable;

public class PlayerQuitsVideoInfo
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String all_index = "";
  public String assets_import_id = "";
  public String category_id = "";
  public String collect_count = "";
  public String corner_mark = "";
  public String corner_mark_img = "";
  public String desc = "";
  public String id = "";
  public String img_h = "";
  public String img_v = "";
  public String media_assets_id = "";
  public String name = "";
  public String new_index = "";
  public String online_time = "";
  public String play_count = "";
  public String point = "";
  public String serial_id = "";
  public int time_len = 0;
  public String type = "";
  public String user_score = "";
  public String video_actor = "";
  public String video_director = "";
  public String video_type = "";
  public String view_type = "";

  public String toString()
  {
    return "PlayerQuitsVideoInfo{type=" + this.type + ", assets_import_id='" + this.assets_import_id + '\'' + ", media_assets_id='" + this.media_assets_id + '\'' + ", category_id='" + this.category_id + '\'' + ", serial_id='" + this.serial_id + '\'' + "id=" + this.id + "video_type=" + this.video_type + "view_type=" + this.view_type + ", name='" + this.name + '\'' + ", desc='" + this.desc + '\'' + ", img_v='" + this.img_v + '\'' + ", img_h='" + this.img_h + '\'' + ", corner_mark_img='" + this.corner_mark_img + '\'' + ", corner_mark='" + this.corner_mark + '\'' + ", user_score=" + this.user_score + "video_actor=" + this.video_actor + "video_director=" + this.video_director + ", all_index='" + this.all_index + '\'' + ", new_index='" + this.new_index + '\'' + ", collect_count=" + this.collect_count + ", online_time=" + this.online_time + ", play_count=" + this.play_count + ", time_len=" + this.time_len + ", point=" + this.point + '}';
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.PlayerQuitsVideoInfo
 * JD-Core Version:    0.6.2
 */