package com.starcor.core.domain;

import java.io.Serializable;
import java.util.List;

public class AAAMovieCouponInfo
  implements Serializable
{
  public int all_count = -1;
  public int common_count = 0;
  public List<AAACouponInfoItem> coupon_list;
  public int err = -1;
  public String reason = "";
  public int special_count = 0;
  public String state = "";
  public String status = "";

  public String toString()
  {
    return "AAAMovieCouponInfo{state='" + this.state + '\'' + ", reason='" + this.reason + '\'' + ", err=" + this.err + ", status='" + this.status + '\'' + ", all_count=" + this.all_count + ", common_count=" + this.common_count + ", special_count=" + this.special_count + ", coupon_list=" + this.coupon_list + '}';
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.AAAMovieCouponInfo
 * JD-Core Version:    0.6.2
 */