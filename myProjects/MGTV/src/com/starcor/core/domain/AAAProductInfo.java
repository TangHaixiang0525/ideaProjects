package com.starcor.core.domain;

import java.io.Serializable;

public class AAAProductInfo
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String beginDate = "";
  public String endDate = "";
  public int err = -1;
  public String name = "";
  public int price = -1;
  public int productId = -1;
  public String reason = "";
  public String state = "";
  public String status = "";
  public String time = "";
  public int type = -1;

  public String toString()
  {
    return "AAAProductInfo{state='" + this.state + '\'' + ", reason='" + this.reason + '\'' + ", err=" + this.err + ", status='" + this.status + '\'' + ", name='" + this.name + '\'' + ", time='" + this.time + '\'' + ", beginDate='" + this.beginDate + '\'' + ", endDate='" + this.endDate + '\'' + ", productId=" + this.productId + ", price=" + this.price + ", type=" + this.type + '}';
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.AAAProductInfo
 * JD-Core Version:    0.6.2
 */