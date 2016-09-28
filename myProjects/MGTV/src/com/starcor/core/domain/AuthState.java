package com.starcor.core.domain;

import java.io.Serializable;

public class AuthState
  implements Serializable
{
  public static final int AUTH_ERR_CONTENT_EXPIRED = 5;
  public static final int AUTH_ERR_CONTENT_OFFLINE = 14;
  public static final int AUTH_ERR_INSUFFICIENT_BALANCE = 4;
  public static final int AUTH_ERR_NOT_AUTHED = 1;
  public static final int AUTH_ERR_NO_URL = 2;
  public static final int AUTH_ERR_TIME_LIMITED = 3;
  public static final int AUTH_ERR_TOKEN_EXPIRED = 9;
  public static final int AUTH_ERR_USER_LIMITED = 7;
  public static final int AUTH_OK = 0;
  public static final int AUTH_OK_FREE = 6;
  public static final int AUTH_USER_EXPIRED = -101;
  public static final int AUTH_USER_KICKED = -100;
  public int is_url_jump;
  public String order_url = "";
  public String reason = "";
  public int state;

  public String toString()
  {
    return "AuthState [state=" + this.state + ", reason=" + this.reason + ", order_url=" + this.order_url + ", is_url_jump=" + this.is_url_jump + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.AuthState
 * JD-Core Version:    0.6.2
 */