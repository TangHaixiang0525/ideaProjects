package com.starcor.core.domain;

import java.io.Serializable;

public class MediaTimeNode
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public int begin;
  public int end;
  public String img = "";
  public String name = "";
  public int type;

  public String toString()
  {
    return "MediaTimeNode{name='" + this.name + '\'' + ", img='" + this.img + '\'' + ", type=" + this.type + ", begin=" + this.begin + ", end=" + this.end + '}';
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.MediaTimeNode
 * JD-Core Version:    0.6.2
 */