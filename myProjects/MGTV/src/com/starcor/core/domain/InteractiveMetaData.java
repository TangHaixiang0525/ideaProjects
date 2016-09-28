package com.starcor.core.domain;

import java.io.Serializable;

public class InteractiveMetaData
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public int begin;
  public String content = "";
  public int end;
  public String img = "";
  public String name = "";
  public int type;
  public String url = "";

  public MediaTimeNode toMediaTimeNode()
  {
    MediaTimeNode localMediaTimeNode = new MediaTimeNode();
    localMediaTimeNode.type = this.type;
    localMediaTimeNode.img = this.img;
    localMediaTimeNode.begin = this.begin;
    localMediaTimeNode.end = this.end;
    localMediaTimeNode.name = this.name;
    return localMediaTimeNode;
  }

  public String toString()
  {
    return "InteractiveMetaData{name='" + this.name + '\'' + ", img='" + this.img + '\'' + ", type=" + this.type + ", begin=" + this.begin + ", end=" + this.end + ", url=" + this.url + ", content=" + this.content + " }";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.InteractiveMetaData
 * JD-Core Version:    0.6.2
 */