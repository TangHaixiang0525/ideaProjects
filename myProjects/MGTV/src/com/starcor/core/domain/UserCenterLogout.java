package com.starcor.core.domain;

public class UserCenterLogout
{
  public Integer err = Integer.valueOf(-1);
  public String operation = "";
  public String reason = "";
  public String state = "";
  public String status = "";

  public String toString()
  {
    return "UserCenterLogout [state=" + this.state + ", reason=" + this.reason + ", err=" + this.err + ", status=" + this.status + ", operation=" + this.operation + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.UserCenterLogout
 * JD-Core Version:    0.6.2
 */