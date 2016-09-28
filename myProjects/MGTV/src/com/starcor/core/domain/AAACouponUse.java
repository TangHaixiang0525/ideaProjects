package com.starcor.core.domain;

import java.io.Serializable;

public class AAACouponUse
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String days = "";
  public String end_date = "";
  public int err = -1;
  public String msg = "";
  public String reason = "";
  public String start_date = "";
  public int state = -1;
  public String status = "";

  public String toString()
  {
    return "AAACouponUse [state=" + this.state + ", reason=" + this.reason + ", err=" + this.err + ", status=" + this.status + ", msg=" + this.msg + ", days=" + this.days + ", start_date=" + this.start_date + ", end_date=" + this.end_date + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.AAACouponUse
 * JD-Core Version:    0.6.2
 */