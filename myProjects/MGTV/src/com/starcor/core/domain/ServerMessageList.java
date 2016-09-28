package com.starcor.core.domain;

import java.io.Serializable;
import java.util.List;

public class ServerMessageList
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public List<ServerMessage> msgList;
  public String reason = "";
  public String state = "";

  public String toString()
  {
    return "GetMessage [state=" + this.state + ", reason=" + this.reason + ", msgList=" + this.msgList + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.ServerMessageList
 * JD-Core Version:    0.6.2
 */