package com.starcor.core.domain;

import java.io.Serializable;

public class UserInfo
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String account;
  public String avatar;
  public String balance;
  public int common_count = 0;
  public String device_id;
  public String email;
  public String ex_data;
  public int expires_in;
  public String loginMode;
  public String mac_id;
  public String mi_access_token = "";
  public String mi_email = "";
  public String mi_mac_key = "";
  public String mi_mobile = "";
  public String mi_userid = "";
  public String mobile;
  public String name;
  public String net_id;
  public String nn_token;
  public String reason;
  public String rtype;
  public int special_count = 0;
  public String state;
  public String status;
  public String user_id;
  public String valid_time;
  public String vip_days;
  public String vip_end_date;
  public String vip_id = "";
  public int vip_power;
  public String web_token;
  public String wechat_type;

  public String toString()
  {
    return "UserInfo [loginMode=" + this.loginMode + ", state=" + this.state + ", email=" + this.email + ", nn_token=" + this.nn_token + ", web_token=" + this.web_token + ", device_id=" + this.device_id + ", mac_id=" + this.mac_id + ", user_id=" + this.user_id + ", name=" + this.name + ", account=" + this.account + ", valid_time=" + this.valid_time + ", reason=" + this.reason + ", net_id=" + this.net_id + ", ex_data=" + this.ex_data + ", status=" + this.status + ", mobile=" + this.mobile + ", expires_in=" + this.expires_in + ", rtype=" + this.rtype + ", vip_id=" + this.vip_id + ", vip_power=" + this.vip_power + ", vip_end_date=" + this.vip_end_date + ", vip_days=" + ", mi_mac_key=" + this.mi_mac_key + ", mi_userid=" + this.mi_userid + ", mi_access_token=" + this.mi_access_token + ", mi_mobile=" + this.mi_mobile + ",mi_email=" + this.mi_email + this.vip_days + ", balance=" + this.balance + ", avatar=" + this.avatar + ", wechat_type=" + this.wechat_type + "common_count=" + this.common_count + "special_count=" + this.special_count + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.UserInfo
 * JD-Core Version:    0.6.2
 */