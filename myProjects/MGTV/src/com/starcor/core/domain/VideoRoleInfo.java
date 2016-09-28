package com.starcor.core.domain;

import java.io.Serializable;

public class VideoRoleInfo
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public int hasLabel = 0;
  public String labelID;
  public String name = "";

  public String toString()
  {
    return "VideoRoleInfo [name=" + this.name + ", hasLabel=" + this.hasLabel + ", labelID=" + this.labelID + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.VideoRoleInfo
 * JD-Core Version:    0.6.2
 */