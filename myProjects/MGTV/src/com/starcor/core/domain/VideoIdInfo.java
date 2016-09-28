package com.starcor.core.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class VideoIdInfo
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public int apisodeId;
  public int apisodeIndex;
  public String apisodeName = "";
  public int dimensions;
  public int fileId;
  public ArrayList<MediaInfo> mediaInfo;
  public String quality = "";
  public String videoId = "";
  public String videoName = "";

  public String toString()
  {
    return "VideoIdInfo [videoId=" + this.videoId + ", videoName=" + this.videoName + ", apisodeId=" + this.apisodeId + ", apisodeIndex=" + this.apisodeIndex + ", apisodeName=" + this.apisodeName + ", fileId=" + this.fileId + ", quality=" + this.quality + ", dimensions=" + this.dimensions + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.VideoIdInfo
 * JD-Core Version:    0.6.2
 */