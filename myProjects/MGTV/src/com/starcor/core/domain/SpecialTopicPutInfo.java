package com.starcor.core.domain;

import java.io.Serializable;

public class SpecialTopicPutInfo
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String binding_id = "";
  public String category_id = "";
  public String click = "";
  public String ex_intor = "";
  public String ex_name = "";
  public String id = "";
  public String img0 = "";
  public String img1 = "";
  public String img2 = "";
  public String name = "";
  public String nns_ex_data = "";
  public String online_mode = "";
  public String play_type = "";
  public String poster = "";
  public String summary = "";
  public String type = "";
  public String url = "";

  public String toString()
  {
    return "SpecialTopicPutInfo{id='" + this.id + '\'' + "binding_id='" + this.binding_id + '\'' + ", name='" + this.name + '\'' + ", ex_name='" + this.ex_name + '\'' + ", ex_intor='" + this.ex_intor + '\'' + ", poster='" + this.poster + '\'' + ", img0='" + this.img0 + '\'' + ", img1='" + this.img1 + '\'' + ", img2='" + this.img2 + '\'' + ", click='" + this.click + '\'' + ", summary='" + this.summary + '\'' + ", nns_ex_data='" + this.nns_ex_data + '\'' + ", play_type='" + this.play_type + '\'' + ", category_id='" + this.category_id + '\'' + ", online_mode='" + this.online_mode + '\'' + ", type='" + this.type + '\'' + ", url='" + this.url + '\'' + '}';
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.SpecialTopicPutInfo
 * JD-Core Version:    0.6.2
 */