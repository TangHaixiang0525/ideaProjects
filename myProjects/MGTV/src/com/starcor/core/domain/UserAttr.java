package com.starcor.core.domain;

import java.io.Serializable;

public class UserAttr
  implements Serializable
{
  public String areaCode = "";
  public String attr = "";
  public String reason = "";
  public String state = "";

  public String toString()
  {
    return "UserAttr{state='" + this.state + '\'' + ", reason='" + this.reason + '\'' + ", areaCode='" + this.areaCode + '\'' + ", attr='" + this.attr + '\'' + '}';
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.UserAttr
 * JD-Core Version:    0.6.2
 */