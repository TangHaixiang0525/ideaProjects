package com.starcor.core.domain;

import java.io.Serializable;

public class MessageBoardDataBody
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String content = "";
  public String dt = "";
  public String phone = "";
  public String username = "";

  public String toString()
  {
    return "MessageBoardDataBody: username=" + this.username + ", content=" + this.content + ", dt :" + this.dt + ", phone :" + this.phone;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.MessageBoardDataBody
 * JD-Core Version:    0.6.2
 */