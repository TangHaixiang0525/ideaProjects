package com.starcor.core.domain;

import java.io.Serializable;

public class MobileCode
  implements Serializable
{
  public int count = -1;
  public int err = -1;
  public String mobile = "";
  public String msg = "";
  public String reason = "";
  public String state = "";
  public String status = "";

  public String toString()
  {
    return "MobileCode{err='" + this.err + '\'' + ", status='" + this.status + '\'' + ", mobile='" + this.mobile + '\'' + ", count=" + this.count + '}';
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.MobileCode
 * JD-Core Version:    0.6.2
 */