package com.starcor.core.domain;

public class UserCenterRegister
{
  public String account = "";
  public String email = "";
  public int err = -1;
  public String msg = "";
  public String reason = "";
  public String state = "";
  public String status = "";

  public String toString()
  {
    return "UserCenterRigster [err=" + this.err + ", status=" + this.status + ", email=" + this.email + ", account=" + this.account + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.UserCenterRegister
 * JD-Core Version:    0.6.2
 */