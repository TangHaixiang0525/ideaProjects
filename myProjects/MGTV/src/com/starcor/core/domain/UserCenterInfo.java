package com.starcor.core.domain;

import java.io.Serializable;

public class UserCenterInfo
  implements Serializable
{
  public int account_type = -1;
  public float balance = -1.0F;
  public int err = -1;
  public String loginaccount = "";
  public String reason = "";
  public String state = "";
  public String status = "";
  public int viPower = -1;
  public String vipEndDate = "";
  public int vipEndDays = -1;
  public int vipId = -1;
  public String vipName = "";

  public String toString()
  {
    return "UserCenterInfo [state=" + this.state + ", reason=" + this.reason + ", err=" + this.err + ", status=" + this.status + ", vipId=" + this.vipId + ", vipName=" + this.vipName + ", viPower=" + this.viPower + ", balance=" + this.balance + ", loginaccount=" + this.loginaccount + ", vipEndDate=" + this.vipEndDate + ", vipEndDays=" + this.vipEndDays + ", account_type=" + this.account_type + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.UserCenterInfo
 * JD-Core Version:    0.6.2
 */