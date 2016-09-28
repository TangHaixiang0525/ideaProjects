package com.starcor.core.domain;

import java.io.Serializable;

public class AAAVerifyLicense
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public int err = -1;
  public String msg = "";
  public String status = "";

  public String toString()
  {
    return "UserCenterBindMobile{err=" + this.err + ", status='" + this.status + '\'' + ", msg='" + this.msg + '\'' + '}';
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.AAAVerifyLicense
 * JD-Core Version:    0.6.2
 */