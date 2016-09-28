package com.starcor.core.domain;

import java.io.Serializable;

public class HotWord
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String cornermark = "";
  public int count;
  public String image0 = "";
  public String image1 = "";
  public String image2 = "";
  public String name = "";
  public String pinyin = "";
  public String video_id = "";

  public String toString()
  {
    return "HotWord [name=" + this.name + ", pinyin=" + this.pinyin + ", count=" + this.count + ", video_id=" + this.video_id + ", image0=" + this.image0 + ", image1=" + this.image1 + ", image2=" + this.image2 + ", cornermark=" + this.cornermark + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.HotWord
 * JD-Core Version:    0.6.2
 */