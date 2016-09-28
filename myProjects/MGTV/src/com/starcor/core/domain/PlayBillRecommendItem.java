package com.starcor.core.domain;

import java.io.Serializable;

public class PlayBillRecommendItem extends PlayBillItem
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String channel_name = "";
  public String videoId = "";

  public String toString()
  {
    return "PlayBillRecommendItem [desc=" + this.desc + ", timeLen=" + this.timeLen + ", begin=" + this.begin + ", img_big=" + this.img_big + ", img_normal=" + this.img_normal + ", img_small=" + this.img_small + ", can_play=" + this.can_play + ", summary=" + this.summary + "huawei_cid=" + this.huawei_cid + "channle_name=" + this.channel_name + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.PlayBillRecommendItem
 * JD-Core Version:    0.6.2
 */