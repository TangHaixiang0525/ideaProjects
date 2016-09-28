package com.starcor.core.domain;

import java.io.Serializable;

public class ConPlayMedia
  implements Serializable
{
  public String assetId = "";
  public String videoId = "";
  public int videoIndex;
  public String videoName = "";
  public int videoType;

  public String toString()
  {
    return "ConPlayMedia[videoId: " + this.videoId + ", videoName: " + this.videoName + ", videoType: " + this.videoType + ", videoIndex: " + this.videoIndex + ", assetId: " + this.assetId + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.ConPlayMedia
 * JD-Core Version:    0.6.2
 */