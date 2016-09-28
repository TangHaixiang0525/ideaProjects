package com.starcor.core.domain;

import java.io.Serializable;

public class SpeedTestUrlInfo
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String id = "";
  public int time;
  public String url = "";

  public String toString()
  {
    return "SpeedStruct [id=" + this.id + ", url=" + this.url + ", time=" + this.time + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.SpeedTestUrlInfo
 * JD-Core Version:    0.6.2
 */