package com.starcor.core.domain;

import java.io.Serializable;

public class Video
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String videoId = "";
  private int videoType;

  public Video(String paramString, int paramInt)
  {
    this.videoId = paramString;
    this.videoType = paramInt;
  }

  public String getVideoId()
  {
    return this.videoId;
  }

  public int getVideoType()
  {
    return this.videoType;
  }

  public String toString()
  {
    String str1 = "Video [" + "videoId: " + this.videoId;
    String str2 = str1 + ", videoType: " + this.videoType;
    return str2 + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.Video
 * JD-Core Version:    0.6.2
 */