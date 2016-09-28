package com.starcor.core.domain;

import java.io.Serializable;

public class AAAOrderInfo
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public int err = -1;
  public String mobileType = "";
  public String msg = "";
  public String orderId = "";
  public String orderMsg = "";
  public String orderPrice = "";
  public String orderType = "";
  public String reason = "";
  public String state = "";
  public String status = "";

  public String toString()
  {
    return "AAAOrderInfo{state='" + this.state + '\'' + "msg='" + this.msg + '\'' + ", reason='" + this.reason + '\'' + ", err=" + this.err + ", status='" + this.status + '\'' + ", orderId='" + this.orderId + '\'' + ", orderType='" + this.orderType + '\'' + ", orderPrice='" + this.orderPrice + '\'' + ", orderMsg='" + this.orderMsg + '\'' + ", mobileType='" + this.mobileType + '\'' + '}';
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.AAAOrderInfo
 * JD-Core Version:    0.6.2
 */