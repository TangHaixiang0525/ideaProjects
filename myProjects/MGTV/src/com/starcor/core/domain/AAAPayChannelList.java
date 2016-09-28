package com.starcor.core.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class AAAPayChannelList
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public ArrayList<AAAPayChannelItem> channelList = null;
  public int err = -1;
  public String reason = "";
  public String state = "";
  public String status = "";

  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder().append("AAAPayChannelList{state='").append(this.state).append('\'').append(", reason='").append(this.reason).append('\'').append(", err=").append(this.err).append(", status='").append(this.status).append('\'').append(", channelList=");
    if (this.channelList == null);
    for (Object localObject = "null"; ; localObject = this.channelList)
      return localObject + '}';
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.AAAPayChannelList
 * JD-Core Version:    0.6.2
 */