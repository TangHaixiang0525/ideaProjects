package com.starcor.core.domain;

import java.io.Serializable;

public class AAAOrderState
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String begin_date = "";
  public int coupon = 0;
  public String coupon_end_date = "";
  public String end_date = "";
  public int err = -1;
  public String orderStatus = "";
  public String reason = "";
  public String service_days = "";
  public String state = "";
  public String status = "";

  public String toString()
  {
    return "AAAOrderState{state='" + this.state + '\'' + ", reason='" + this.reason + '\'' + ", err=" + this.err + ", status='" + this.status + '\'' + ", orderStatus='" + this.orderStatus + '\'' + ", begin_date='" + this.begin_date + '\'' + ", end_date='" + this.end_date + '\'' + ", coupon=" + this.coupon + ", coupon_end_date='" + this.coupon_end_date + '\'' + ", service_days='" + this.service_days + '\'' + '}';
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.AAAOrderState
 * JD-Core Version:    0.6.2
 */