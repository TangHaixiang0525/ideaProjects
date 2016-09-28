package com.starcor.core.domain;

import java.io.Serializable;

public class UserRecommendItem
  implements Serializable
{
  public String assist_id;
  public String category_id;
  public String id;
  public String img_h;
  public String img_s;
  public String img_v;
  public String info;
  public String name;
  public String reason;
  public String recom_time;
  public String state;
  public String tstv_begin_time;
  public String tstv_time_len;
  public String type;
  public String user_id;
  public int video_type;

  public String toString()
  {
    return "UserRecommendItem [type=" + this.type + ", id = " + this.id + ", name=" + this.name + ", img_h=" + this.img_h + ", img_s=" + this.img_s + ", img_v=" + this.img_v + ", info=" + this.info + ", video_type=" + this.video_type + ", tstv_begin_time=" + this.tstv_begin_time + ",tstv_time_len" + this.tstv_time_len + ", assets_id=" + this.assist_id + ", category_id=" + this.category_id + ", recom_time=" + this.recom_time + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.UserRecommendItem
 * JD-Core Version:    0.6.2
 */