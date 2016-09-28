package com.starcor.core.domain;

import java.io.Serializable;

public class SpeedStruct
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String id = "";
  public String name = "";
  public int time;
  public String type = "";
  public String url = "";

  public String toString()
  {
    return "SpeedStruct [name=" + this.name + ", id=" + this.id + ", url=" + this.url + ", time=" + this.time + ", type" + this.type + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.SpeedStruct
 * JD-Core Version:    0.6.2
 */