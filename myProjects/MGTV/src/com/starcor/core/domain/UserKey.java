package com.starcor.core.domain;

import java.io.Serializable;

public class UserKey
  implements Serializable
{
  public String key = "";
  public String value = "";

  public String toString()
  {
    return "UserKey [key=" + this.key + ", value=" + this.value + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.UserKey
 * JD-Core Version:    0.6.2
 */