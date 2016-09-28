package com.starcor.core.domain;

import java.io.Serializable;

public class MetadataGoupItem
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String action_type = "";
  public String category_id = "";
  public String ex_url = "";
  public String img_url1 = "";
  public String img_url2 = "";
  public String img_url3 = "";
  public String info = "";
  public String name = "";
  public int order;
  public String packet_id = "";
  public String type = "";
  public String video_id = "";
  public String video_index;
  public String video_type = "";

  public String toString()
  {
    return "MetadataGoupItem [img_url1=" + this.img_url1 + ", img_url2=" + this.img_url2 + ", img_url3=" + this.img_url3 + ", type=" + this.type + ", video_id=" + this.video_id + ", video_type=" + this.video_type + ", video_index=" + this.video_index + ", name=" + this.name + ", info=" + this.info + ", packet_id=" + this.packet_id + ", action_type=" + this.action_type + ", category_id=" + this.category_id + ", ex_url=" + this.ex_url + ", order=" + this.order + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.MetadataGoupItem
 * JD-Core Version:    0.6.2
 */