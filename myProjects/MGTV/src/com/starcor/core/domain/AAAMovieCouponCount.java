package com.starcor.core.domain;

import java.io.Serializable;

public class AAAMovieCouponCount
  implements Serializable
{
  public int common_count = 0;
  public int err = -1;
  public String reason = "";
  public int special_count = 0;
  public String state = "";
  public String status = "";

  public String toString()
  {
    return "AAAMovieCouponCount{state='" + this.state + '\'' + ", reason='" + this.reason + '\'' + ", err=" + this.err + ", status='" + this.status + '\'' + ", common_count='" + this.common_count + '\'' + ", special_count='" + this.special_count + '\'' + '}';
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.AAAMovieCouponCount
 * JD-Core Version:    0.6.2
 */