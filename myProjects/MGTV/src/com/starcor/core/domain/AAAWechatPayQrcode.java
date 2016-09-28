package com.starcor.core.domain;

import java.io.Serializable;

public class AAAWechatPayQrcode
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public int err = -1;
  public String reason = "";
  public int state = -1;
  public String status = "";
  public String url = "";

  public String toString()
  {
    return "AAAWechatPayQrcode [state=" + this.state + ", reason=" + this.reason + ", err=" + this.err + ", status=" + this.status + ", url=" + this.url + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.AAAWechatPayQrcode
 * JD-Core Version:    0.6.2
 */