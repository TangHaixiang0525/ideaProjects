package com.starcor.core.domain;

import java.io.Serializable;

public class AAAUseMovieCouponResult
  implements Serializable
{
  public String coupon_id = "";
  public int err = -1;
  public String reason = "";
  public String state = "";
  public String status = "";

  public String toString()
  {
    return "AAAUseMovieCouponResult{state='" + this.state + '\'' + ", reason='" + this.reason + '\'' + ", err=" + this.err + ", status='" + this.status + '\'' + ", coupon_id='" + this.coupon_id + '\'' + '}';
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.AAAUseMovieCouponResult
 * JD-Core Version:    0.6.2
 */