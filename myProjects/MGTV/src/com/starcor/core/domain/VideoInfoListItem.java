package com.starcor.core.domain;

import java.io.Serializable;

public class VideoInfoListItem
  implements Serializable
{
  public String alias_name = "";
  public String artithmetic_id = "";
  public String corner_mark = "";
  public String corner_mark_img = "";
  public String estimate_id = "";
  public String float_info = "";
  public String id = "";
  public String img_h = "";
  public String img_s = "";
  public String img_v = "";
  public String import_id = "";
  public String info = "";
  public String kind = "";
  public String name = "";
  public String reason = "";
  public String serial_id = "";
  public String state = "";
  public String type = "";

  public String toString()
  {
    return "VideoInfoListItem{state='" + this.state + '\'' + ", reason='" + this.reason + '\'' + ", type='" + this.type + '\'' + ", id='" + this.id + '\'' + ", name='" + this.name + '\'' + ", alias_name='" + this.alias_name + '\'' + ", img_h='" + this.img_h + '\'' + ", img_s='" + this.img_s + '\'' + ", img_v='" + this.img_v + '\'' + ", info='" + this.info + '\'' + ", kind='" + this.kind + '\'' + ", import_id='" + this.import_id + '\'' + ", serial_id='" + this.serial_id + '\'' + ", estimate_id='" + this.estimate_id + '\'' + ", artithmetic_id='" + this.artithmetic_id + '\'' + ", corner_mark='" + this.corner_mark + '\'' + ", corner_mark_img='" + this.corner_mark_img + '\'' + ", float_info='" + this.float_info + '\'' + '}';
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.VideoInfoListItem
 * JD-Core Version:    0.6.2
 */