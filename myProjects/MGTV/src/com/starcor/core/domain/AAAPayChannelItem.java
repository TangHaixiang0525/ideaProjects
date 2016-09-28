package com.starcor.core.domain;

import java.io.Serializable;

public class AAAPayChannelItem
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String code = "";
  public String description = "";
  public String discount = "";
  public String location = "";
  public String name = "";

  public String toString()
  {
    return "AAAPayChannelItem{code='" + this.code + '\'' + ", location='" + this.location + '\'' + ", name='" + this.name + '\'' + ", description='" + this.description + '\'' + ", discount='" + this.discount + '\'' + '}';
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.AAAPayChannelItem
 * JD-Core Version:    0.6.2
 */