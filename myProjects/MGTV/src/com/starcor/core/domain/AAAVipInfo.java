package com.starcor.core.domain;

import java.io.Serializable;

public class AAAVipInfo
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public int animeNum = 0;
  public String description = "";
  public int err = -1;
  public int location = -1;
  public int movieNum = 0;
  public int musicNum = 0;
  public String name = "";
  public int power = -1;
  public String reason = "";
  public String state = "";
  public String status = "";
  public String subname = "";
  public int tvplaysNum = 0;
  public int vipId = -1;

  public String toString()
  {
    return "AAAVipInfo{state='" + this.state + '\'' + ", reason='" + this.reason + '\'' + ", err=" + this.err + ", status='" + this.status + '\'' + ", name='" + this.name + '\'' + ", subname='" + this.subname + '\'' + ", description='" + this.description + '\'' + ", vipId=" + this.vipId + ", location=" + this.location + ", power=" + this.power + ", movieNum=" + this.movieNum + ", tvplaysNum=" + this.tvplaysNum + ", animeNum=" + this.animeNum + ", musicNum=" + this.musicNum + '}';
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.AAAVipInfo
 * JD-Core Version:    0.6.2
 */