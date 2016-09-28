package com.starcor.core.domain;

import java.io.Serializable;

public class ReserveListItem
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String reason = "";
  public String state = "";
  public String total = "";

  public String toString()
  {
    return "CollectListItem [state=" + this.state + ", reason=" + this.reason + ", total=" + this.total + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.ReserveListItem
 * JD-Core Version:    0.6.2
 */