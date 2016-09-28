package com.starcor.core.domain;

import java.io.Serializable;
import java.util.List;

public class PlayInfo
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public int IsOtherCdn;
  public String begin_time = "";
  public int dimensions;
  public String fileId = "";
  public String fileType = "";
  public List<MediaTimeNode> mediaTimeNodeList;
  public int otherCdn = 0;
  public String playUrl = "";
  public String quality = "";
  public String reason = "";
  public int state;
  public String time_len = "";
  public String type = "";

  public String toString()
  {
    if (this.mediaTimeNodeList == null)
      return "PlayInfo{playUrl='" + this.playUrl + '\'' + ", fileType='" + this.fileType + '\'' + ", otherCdn=" + this.otherCdn + ", fileId='" + this.fileId + '\'' + ", state=" + this.state + ", reason='" + this.reason + '\'' + ", type='" + this.type + '\'' + ", begin_time='" + this.begin_time + '\'' + ", time_len='" + this.time_len + '\'' + ", quality='" + this.quality + '\'' + ", IsOtherCdn=" + this.IsOtherCdn + ", dimensions=" + this.dimensions + '}';
    return "PlayInfo{playUrl='" + this.playUrl + '\'' + ", fileType='" + this.fileType + '\'' + ", otherCdn=" + this.otherCdn + ", fileId='" + this.fileId + '\'' + ", state=" + this.state + ", reason='" + this.reason + '\'' + ", type='" + this.type + '\'' + ", begin_time='" + this.begin_time + '\'' + ", time_len='" + this.time_len + '\'' + ", quality='" + this.quality + '\'' + ", IsOtherCdn=" + this.IsOtherCdn + ", dimensions=" + this.dimensions + ", mediaTimeNodeList=" + this.mediaTimeNodeList.toString() + '}';
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.PlayInfo
 * JD-Core Version:    0.6.2
 */