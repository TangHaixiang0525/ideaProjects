package com.starcor.core.domain;

import java.io.Serializable;

public class SearchListItem
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String big_img_url = "";
  public int billing = 0;
  public String category_id = "";
  public String corner_mark = "";
  public String corner_mark_img_url = "";
  public String img_h = "";
  public String img_s = "";
  public String img_v = "";
  public String import_id = "";
  public String item_id = "";
  public String name = "";
  public String normal_img_url = "";
  public String package_id = "";
  public int play_count;
  public int point;
  public String serial_id = "";
  public String small_img_url = "";
  public int ui_style;
  public int user_score;
  public String video_id = "";
  public int video_type;

  public String toString()
  {
    return "SearchListItem [item_id=" + this.item_id + ", name=" + this.name + ", import_id=" + this.import_id + ", serial_id=" + this.serial_id + ", video_id=" + this.video_id + ", big_img_url=" + this.big_img_url + ", normal_img_url=" + this.normal_img_url + ", small_img_url=" + this.small_img_url + ", video_type=" + this.video_type + ", play_count=" + this.play_count + ", user_score=" + this.user_score + ", point=" + this.point + ", package_id=" + this.package_id + ", category_id=" + this.category_id + ", ui_style=" + this.ui_style + ", billing=" + this.billing + ", corner_mark=" + this.corner_mark + ",corner_mark_img_url=" + this.corner_mark_img_url + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.SearchListItem
 * JD-Core Version:    0.6.2
 */