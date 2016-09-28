package com.starcor.core.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class VideoIndex
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String actor = "";
  public String desc = "";
  public String director = "";
  public String id = "";
  public String imgUrl = "";
  public String import_id = "";
  public String import_name = "";
  public int index;
  public String index_name = "";
  public ArrayList<MediaInfo> mediaInfo;
  public String name = "";
  public String onlineTime = "";
  public String preview_type = "";
  public String serial_id = "";
  public int timeLen;
  public int userScore;

  public String toString()
  {
    return "VideoIndex{index=" + this.index + "id=" + this.id + ", name='" + this.name + '\'' + ", desc='" + this.desc + '\'' + ", imgUrl='" + this.imgUrl + '\'' + ", userScore=" + this.userScore + ", onlineTime='" + this.onlineTime + '\'' + ", index_name='" + this.index_name + '\'' + ", import_id='" + this.import_id + '\'' + ", serial_id='" + this.serial_id + '\'' + ", import_name='" + this.import_name + '\'' + ", mediaInfo=" + this.mediaInfo + ", timeLen=" + this.timeLen + ", actor='" + this.actor + '\'' + ", director='" + this.director + '\'' + ", preview_type='" + this.preview_type + '\'' + '}';
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.VideoIndex
 * JD-Core Version:    0.6.2
 */