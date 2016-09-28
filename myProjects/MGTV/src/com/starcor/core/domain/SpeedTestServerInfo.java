package com.starcor.core.domain;

import java.io.Serializable;

public class SpeedTestServerInfo
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String serverName = "";
  public String serverType = "";
  public String valId = "";

  public String toString()
  {
    return "SpeedStruct [serverName=" + this.serverName + ", serverType=" + this.serverType + ", valId=" + this.valId + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.SpeedTestServerInfo
 * JD-Core Version:    0.6.2
 */