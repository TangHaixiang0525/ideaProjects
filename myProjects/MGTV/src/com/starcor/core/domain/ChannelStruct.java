package com.starcor.core.domain;

import java.io.Serializable;

public class ChannelStruct
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public int categoryNO = -1;
  public int channelNO = -1;
  public String channelName = null;
  public int index = -1;
  public int type = -1;

  public String toString()
  {
    return "ChannelStruct [index=" + this.index + ", channelNO=" + this.channelNO + ", channelName=" + this.channelName + ", categoryNO=" + this.categoryNO + ", type=" + this.type + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.ChannelStruct
 * JD-Core Version:    0.6.2
 */