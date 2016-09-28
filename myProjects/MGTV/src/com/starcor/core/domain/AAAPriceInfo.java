package com.starcor.core.domain;

import java.io.Serializable;

public class AAAPriceInfo
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public int err = -1;
  public int price = -1;
  public String reason = "";
  public String state = "";
  public String status = "";

  public String toString()
  {
    return "AAAPriceInfo{state='" + this.state + '\'' + ", reason='" + this.reason + '\'' + ", err=" + this.err + ", status='" + this.status + '\'' + ", price=" + this.price + '}';
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.AAAPriceInfo
 * JD-Core Version:    0.6.2
 */