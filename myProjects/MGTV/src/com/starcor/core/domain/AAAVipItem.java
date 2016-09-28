package com.starcor.core.domain;

import java.io.Serializable;

public class AAAVipItem
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public int location = -1;
  public String name = "";
  public int power = -1;
  public int vipId = -1;

  public String toString()
  {
    return "AAAVipItem{name='" + this.name + '\'' + ", vipId=" + this.vipId + ", location=" + this.location + ", power=" + this.power + '}';
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.AAAVipItem
 * JD-Core Version:    0.6.2
 */