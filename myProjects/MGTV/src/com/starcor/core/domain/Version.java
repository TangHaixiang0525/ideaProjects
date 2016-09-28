package com.starcor.core.domain;

import java.io.Serializable;

public class Version
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public AppVersion appVersion;

  public String toString()
  {
    return "Version [appVersion=" + this.appVersion + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.Version
 * JD-Core Version:    0.6.2
 */