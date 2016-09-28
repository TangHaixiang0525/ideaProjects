package com.starcor.core.domain;

import java.io.Serializable;

public class UserCenterLogin
  implements Serializable
{
  public String avatar = "";
  public String email = "";
  public int err = -1;
  public int expire = -1;
  public String loginaccount = "";
  public String logintype = "";
  public String mi_access_token;
  public String mi_email;
  public String mi_mac_key;
  public String mi_mobile;
  public String mi_userid;
  public String mobile = "";
  public String msg = "";
  public String nickname = "";
  public String reason = "";
  public String rtype = "";
  public String state = "";
  public String status = "";
  public String ticket = "";
  public String uid = "";
  public String wechat_type;

  public String toString()
  {
    return "UserCenterLogin [state=" + this.state + ", reason=" + this.reason + ", err=" + this.err + ", status=" + this.status + ", ticket=" + this.ticket + ", expire=" + this.expire + ", loginaccount=" + this.loginaccount + ", uid=" + this.uid + ", mobile=" + this.mobile + ", logintype=" + this.logintype + ", rtype=" + this.rtype + ", avatar=" + this.avatar + ", nickname=" + this.nickname + ", email=" + this.email + ", mi_mac_key='" + this.mi_mac_key + ", mi_userid='" + this.mi_userid + ", mi_mobile='" + this.mi_mobile + ", mi_email='" + this.mi_email + ", mi_access_token='" + this.mi_access_token + ", wechat_type=" + this.wechat_type + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.UserCenterLogin
 * JD-Core Version:    0.6.2
 */