package com.starcor.core.logic.domain;

import java.io.Serializable;

public class LocalPlayRecordValue
  implements Serializable
{
  private static final long serialVersionUID = 2L;
  public long createTime;
  public int duration;
  public int playedTime;
  public String videoName;

  public String toString()
  {
    return "LocalPlayRecordValue [playedTime=" + this.playedTime + ", createTime=" + this.createTime + ", videoName=" + this.videoName + ", duration=" + this.duration + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.logic.domain.LocalPlayRecordValue
 * JD-Core Version:    0.6.2
 */