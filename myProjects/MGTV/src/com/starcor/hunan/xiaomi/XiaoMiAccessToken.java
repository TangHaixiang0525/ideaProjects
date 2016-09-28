package com.starcor.hunan.xiaomi;

public class XiaoMiAccessToken
{
  public String access_token;
  public String expires_in;
  public String mac_algorithm;
  public String mac_key;
  public String openId;
  public String refresh_token;
  public String scope;
  public String token_type;

  public String toString()
  {
    return "AccessTokenInfo{mi_access_token='" + this.access_token + '\'' + ", expires_in='" + this.expires_in + '\'' + ", refresh_token='" + this.refresh_token + '\'' + ", scope='" + this.scope + '\'' + ", token_type='" + this.token_type + '\'' + ", mi_mac_key='" + this.mac_key + '\'' + ", mac_algorithm='" + this.mac_algorithm + '\'' + ", openId='" + this.openId + '\'' + '}';
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.xiaomi.XiaoMiAccessToken
 * JD-Core Version:    0.6.2
 */