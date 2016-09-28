package com.starcor.core.domain;

import java.io.Serializable;

public class PlayRecordList
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String beginTime = "";
  public String videoId = "";
  public int videoIndex;
  public String videoName = "";

  public String getBeginTime()
  {
    return this.beginTime;
  }

  public String getVideoId()
  {
    return this.videoId;
  }

  public Integer getVideoIndex()
  {
    return Integer.valueOf(this.videoIndex);
  }

  public String getVideoName()
  {
    return this.videoName;
  }

  public void setBeginTime(String paramString)
  {
    this.beginTime = paramString;
  }

  public void setVideoId(String paramString)
  {
    this.videoId = paramString;
  }

  public void setVideoIndex(int paramInt)
  {
    this.videoIndex = paramInt;
  }

  public void setVideoName(String paramString)
  {
    this.videoName = paramString;
  }

  public String toString()
  {
    return "PlayRecordInfo{, beginTime='" + this.beginTime + '\'' + ", videoId='" + this.videoId + '\'' + ", videoName='" + this.videoName + '\'' + ", videoIndex=" + this.videoIndex + '}';
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.PlayRecordList
 * JD-Core Version:    0.6.2
 */