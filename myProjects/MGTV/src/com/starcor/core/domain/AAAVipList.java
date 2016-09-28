package com.starcor.core.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class AAAVipList
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public int err = -1;
  public String reason = "";
  public String state = "";
  public String status = "";
  public ArrayList<AAAVipItem> vipList = null;

  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder().append("AAAVipList{state='").append(this.state).append('\'').append(", reason='").append(this.reason).append('\'').append(", err=").append(this.err).append(", status='").append(this.status).append('\'').append(", vipList=");
    if (this.vipList == null);
    for (Object localObject = "null"; ; localObject = this.vipList)
      return localObject + '}';
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.AAAVipList
 * JD-Core Version:    0.6.2
 */