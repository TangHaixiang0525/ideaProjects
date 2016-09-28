package com.starcor.core.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class ChannelInfoV2
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public ArrayList<ChannelItemInfoV2> channelList;
  public String reason = "";
  public String state = "";

  public String toString()
  {
    return "ChannelInfoV2{state='" + this.state + '\'' + ", reason='" + this.reason + '\'' + ", channelList=" + this.channelList.toString() + '}';
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.ChannelInfoV2
 * JD-Core Version:    0.6.2
 */