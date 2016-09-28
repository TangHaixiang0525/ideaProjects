package com.starcor.core.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class AAAPriceList
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public int err = -1;
  public ArrayList<Integer> priceList = null;
  public String reason = "";
  public String state = "";
  public String status = "";

  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder().append("AAAPriceList{state='").append(this.state).append('\'').append(", reason='").append(this.reason).append('\'').append(", err=").append(this.err).append(", status='").append(this.status).append('\'').append(", priceList=");
    if (this.priceList == null);
    for (Object localObject = "null"; ; localObject = this.priceList)
      return localObject + '}';
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.AAAPriceList
 * JD-Core Version:    0.6.2
 */