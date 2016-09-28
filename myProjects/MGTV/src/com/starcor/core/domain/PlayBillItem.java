package com.starcor.core.domain;

import java.io.Serializable;

public class PlayBillItem
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String begin = "";
  public int can_play;
  public String desc = "";
  public String huawei_cid = "";
  public String img_big = "";
  public String img_normal = "";
  public String img_small = "";
  public boolean isFrist;
  public String summary = "";
  public int timeLen;

  public String toString()
  {
    return "PlayBillItem [desc=" + this.desc + ", timeLen=" + this.timeLen + ", begin=" + this.begin + ", img_big=" + this.img_big + ", img_normal=" + this.img_normal + ", img_small=" + this.img_small + ", can_play=" + this.can_play + ", summary=" + this.summary + "huawei_cid=" + this.huawei_cid + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.PlayBillItem
 * JD-Core Version:    0.6.2
 */