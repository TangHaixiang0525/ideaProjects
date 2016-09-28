package com.starcor.core.domain;

import java.io.Serializable;

public class MediaInfo
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String caps = "";
  public String media_id = "";
  public String onLineTime = "";
  public String type = "";

  public String toString()
  {
    return "MediaInfo [media_id=" + this.media_id + ", type=" + this.type + ", onLineTime=" + this.onLineTime + ", caps=" + this.caps + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.MediaInfo
 * JD-Core Version:    0.6.2
 */