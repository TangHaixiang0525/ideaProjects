package com.starcor.core.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class StarGuestLabelList
  implements Serializable
{
  public int cur_page;
  public ArrayList<StarGuest> lists = new ArrayList();
  public String reason;
  public String state;
  public int total_page;
  public int total_rows;

  public String toString()
  {
    return "StarGuestLabelList{state='" + this.state + '\'' + ", reason='" + this.reason + '\'' + ", cur_page=" + this.cur_page + ", total_page=" + this.total_page + ", total_rows=" + this.total_rows + ", lists=" + this.lists + '}';
  }

  public class StarGuest
    implements Serializable
  {
    public String actor;
    public String alias_name;
    public String id;
    public String img_h;
    public String img_s;
    public String img_v;
    public String info;
    public String name;
    public String release_time;
    public String type;
    public String video_id;
    public String video_index;
    public String video_type;
    public String watch_focus;

    public StarGuest()
    {
    }

    public String toString()
    {
      return "StarGuest{type='" + this.type + '\'' + ", id='" + this.id + '\'' + ", name='" + this.name + '\'' + ", alias_name='" + this.alias_name + '\'' + ", img_h='" + this.img_h + '\'' + ", img_s='" + this.img_s + '\'' + ", img_v='" + this.img_v + '\'' + ", info='" + this.info + '\'' + ", video_id='" + this.video_id + '\'' + ", video_type='" + this.video_type + '\'' + ", video_index='" + this.video_index + '\'' + ", actor='" + this.actor + '\'' + ", watch_focus='" + this.watch_focus + '\'' + ", release_time='" + this.release_time + '\'' + '}';
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.StarGuestLabelList
 * JD-Core Version:    0.6.2
 */