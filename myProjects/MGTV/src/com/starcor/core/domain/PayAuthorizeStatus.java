package com.starcor.core.domain;

import java.io.Serializable;

public class PayAuthorizeStatus
  implements Serializable
{
  public int err = -1;
  public String qrcode = "";
  public String reason = "";
  public String state = "";
  public String status = "";
  public String type = "";

  public String toString()
  {
    return "PayAuthorStatus [state=" + this.state + ", reason=" + this.reason + ", err=" + this.err + ", status=" + this.status + ", qrcode=" + this.qrcode + ", type=" + this.type + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.PayAuthorizeStatus
 * JD-Core Version:    0.6.2
 */