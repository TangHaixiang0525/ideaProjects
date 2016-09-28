package com.starcor.core.domain;

import java.io.Serializable;

public class AAAGetLicense
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public int err = -1;
  public String ip = "";
  public String license = "";
  public String netId = "";
  public String status = "";

  public String toString()
  {
    return "AAAGetLicense [err=" + this.err + ", status=" + this.status + ", license=" + this.license + ", ip=" + this.ip + ", netId=" + this.netId + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.AAAGetLicense
 * JD-Core Version:    0.6.2
 */