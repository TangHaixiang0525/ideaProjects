package com.starcor.core.domain;

import android.text.TextUtils;
import java.io.Serializable;

public class FilmListItem
  implements Serializable
{
  public static final int UITYPE_CATCHVIDEO = 3;
  public static final int UITYPE_COLLECT = 1;
  public static final int UITYPE_PLAY_RECORD = 2;
  private static final long serialVersionUID = 1L;
  public String artithmeticId = "";
  public String big_img_url = "";
  public int billing = 0;
  public String categoryName = "";
  public String category_id = "";
  public int category_index;
  public int channel_index;
  public String corner_mark = "";
  public String corner_mark_img_url = "";
  public String desc = "";
  public String estimateId = "";
  public String film_name = "";
  public String h_img_url = "";
  public int id;
  public String import_id = "";
  public int index;
  public String item_id = "";
  public String media_mode = "";
  public int my_meida_type;
  public String normal_img_url = "";
  public String package_id = "";
  public int play_count;
  public int point;
  public String s_img_url = "";
  public String serial_id = "";
  public String small_img_url = "";
  public String specialid = "";
  public int uiStyle = 0;
  public String updateInfo = "";
  public int update_index;
  public int user_score;
  public String v_img_url = "";
  public String video_ex_type = "";
  public String video_id = "";
  public int video_type;
  public int viewType = -1;

  public boolean equals(Object paramObject)
  {
    if (paramObject == null);
    while ((TextUtils.isEmpty(this.video_id)) || (!(paramObject instanceof FilmListItem)))
      return false;
    return this.video_id.equals(((FilmListItem)paramObject).video_id);
  }

  public String toString()
  {
    return "FilmListItem{id=" + this.id + ", index=" + this.index + ", package_id='" + this.package_id + '\'' + ", category_id='" + this.category_id + '\'' + ", category_index=" + this.category_index + ", item_id='" + this.item_id + '\'' + ", film_name='" + this.film_name + '\'' + ", big_img_url='" + this.big_img_url + '\'' + ", normal_img_url='" + this.normal_img_url + '\'' + ", small_img_url='" + this.small_img_url + '\'' + ", v_img_url='" + this.v_img_url + '\'' + ", h_img_url='" + this.h_img_url + '\'' + ", s_img_url='" + this.s_img_url + '\'' + ", corner_mark_img_url='" + this.corner_mark_img_url + '\'' + ", desc='" + this.desc + '\'' + ", video_id='" + this.video_id + '\'' + ", video_type=" + this.video_type + ", play_count=" + this.play_count + ", user_score=" + this.user_score + ", point=" + this.point + ", uiStyle=" + this.uiStyle + ", billing=" + this.billing + ", corner_mark='" + this.corner_mark + '\'' + ", updateInfo='" + this.updateInfo + '\'' + ", channel_index=" + this.channel_index + ", video_ex_type='" + this.video_ex_type + '\'' + ", categoryName='" + this.categoryName + '\'' + ", my_meida_type=" + this.my_meida_type + ", update_index=" + this.update_index + ", specialid='" + this.specialid + '\'' + ", media_mode='" + this.media_mode + '\'' + ", serial_id='" + this.serial_id + '\'' + ", import_id='" + this.import_id + '\'' + ", estimateId='" + this.estimateId + '\'' + ", artithmeticId='" + this.artithmeticId + '\'' + ", viewType='" + this.viewType + '\'' + '}';
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.FilmListItem
 * JD-Core Version:    0.6.2
 */