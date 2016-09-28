package com.starcor.core.domain;

import java.io.Serializable;

public class UserCenterBindMobile
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public int err = -1;
  public String operation = "";
  public String status = "";

  public String toString()
  {
    return "UserCenterBindMobile{err=" + this.err + ", status='" + this.status + '\'' + ", operation='" + this.operation + '\'' + '}';
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.UserCenterBindMobile
 * JD-Core Version:    0.6.2
 */