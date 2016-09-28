package com.starcor.core.domain;

import java.io.Serializable;

public class TerminalRealtimeParam
  implements Serializable
{
  public String group = "";
  public String key = "";
  public String value = "";

  public String toString()
  {
    return "TerminalRealtimeParam [key=" + this.key + ", value=" + this.value + ", group=" + this.group + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.TerminalRealtimeParam
 * JD-Core Version:    0.6.2
 */