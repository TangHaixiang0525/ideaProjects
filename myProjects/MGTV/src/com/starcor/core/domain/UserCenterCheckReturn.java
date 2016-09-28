package com.starcor.core.domain;

import java.io.Serializable;

public class UserCenterCheckReturn
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String avatar = "";
  public String email = "";
  public int err = -1;
  public int expire = -1;
  public String loginaccount = "";
  public String logintype = "";
  public String mobile = "";
  public String nickname = "";
  public String reason = "";
  public String rtype = "";
  public String state = "";
  public String status = "";
  public String ticket = "";
  public String uid = "";
  public String wechat_type = "";

  public String toString()
  {
    return "UserCenterCheckReturn [err=" + this.err + ", status=" + this.status + ", ticket=" + this.ticket + ", expire=" + this.expire + ", loginaccount=" + this.loginaccount + ", uid=" + this.uid + ", mobile=" + this.mobile + ", logintype=" + this.logintype + ", rtype=" + this.rtype + ", nickname=" + this.nickname + ", avatar=" + this.avatar + ", wechat_type=" + this.wechat_type + ", email=" + this.email + ", state=" + this.state + ", reason=" + this.reason + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.UserCenterCheckReturn
 * JD-Core Version:    0.6.2
 */