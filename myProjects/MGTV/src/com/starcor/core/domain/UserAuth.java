package com.starcor.core.domain;

import java.io.Serializable;

public class UserAuth
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String order_url;
  private String reason;
  private int state;

  public String getOrder_url()
  {
    return this.order_url;
  }

  public String getReason()
  {
    return this.reason;
  }

  public int getState()
  {
    return this.state;
  }

  public void setOrder_url(String paramString)
  {
    this.order_url = paramString;
  }

  public void setReason(String paramString)
  {
    this.reason = paramString;
  }

  public void setState(int paramInt)
  {
    this.state = paramInt;
  }

  public String toString()
  {
    return "UserAuth [state=" + this.state + ", reason=" + this.reason + ", order_url=" + this.order_url + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.UserAuth
 * JD-Core Version:    0.6.2
 */