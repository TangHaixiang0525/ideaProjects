package com.starcor.core.domain;

import java.io.Serializable;

public class AppDownloadUrl
  implements Serializable
{
  public String appId = "";
  public String reason = "";
  public String state = "";
  public String url = "";
  public String version = "";

  public String toString()
  {
    return "AppDownloadUrl{state='" + this.state + '\'' + ", reason='" + this.reason + '\'' + ", appId=" + this.appId + ", version=" + this.version + ", url=" + this.url + '}';
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.AppDownloadUrl
 * JD-Core Version:    0.6.2
 */