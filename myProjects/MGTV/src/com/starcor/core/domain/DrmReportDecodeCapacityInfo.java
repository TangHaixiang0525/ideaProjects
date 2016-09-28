package com.starcor.core.domain;

import java.io.Serializable;

public class DrmReportDecodeCapacityInfo
  implements Serializable
{
  public String reason = "";
  public int state = -1;

  public String toString()
  {
    return "DrmReportDecodeCapacity{state=" + this.state + ", reason='" + this.reason + '\'' + '}';
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.DrmReportDecodeCapacityInfo
 * JD-Core Version:    0.6.2
 */