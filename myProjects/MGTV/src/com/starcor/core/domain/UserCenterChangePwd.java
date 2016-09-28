package com.starcor.core.domain;

public class UserCenterChangePwd
{
  public int err = -1;
  public String msg = "";
  public String operation = "";
  public String reason = "";
  public String state = "";
  public String status = "";

  public String toString()
  {
    return "UserCenterChangePwd [err=" + this.err + ", status=" + this.status + ", operation=" + this.operation + ", state=" + this.state + ", reason=" + this.reason + ", msg=" + this.msg + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.UserCenterChangePwd
 * JD-Core Version:    0.6.2
 */