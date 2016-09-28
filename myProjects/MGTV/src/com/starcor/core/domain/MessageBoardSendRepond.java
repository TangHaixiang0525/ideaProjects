package com.starcor.core.domain;

import java.io.Serializable;

public class MessageBoardSendRepond
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String code = "";
  public String err = "";
  public String msg = "";

  public String toString()
  {
    return "MessageBoardSendRepond [err=" + this.err + ", code=" + this.code + ", msg=" + this.msg + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.MessageBoardSendRepond
 * JD-Core Version:    0.6.2
 */