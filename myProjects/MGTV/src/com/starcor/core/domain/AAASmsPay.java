package com.starcor.core.domain;

import java.io.Serializable;

public class AAASmsPay
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String channel = "";
  public int err = -1;
  public String msg = "";
  public String price = "";
  public String product_id = "";
  public String product_name = "";
  public String reason = "";
  public int state = -1;
  public String status = "";
  public String type = "";

  public String toString()
  {
    return "AAASmsPay{state=" + this.state + ", reason='" + this.reason + '\'' + ", err=" + this.err + ", status='" + this.status + '\'' + ", msg='" + this.msg + '\'' + ", type='" + this.type + '\'' + ", channel='" + this.channel + '\'' + ", product_id='" + this.product_id + '\'' + ", product_name='" + this.product_name + '\'' + ", price='" + this.price + '\'' + '}';
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.AAASmsPay
 * JD-Core Version:    0.6.2
 */