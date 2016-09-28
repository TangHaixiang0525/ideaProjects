package com.starcor.core.domain;

import java.io.Serializable;

public class SearchLiveListItem
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String day = "";
  public String desc = "";
  public String time_begin = "";
  public String time_len = "";
  public String video_id = "";
  public String video_name = "";
  public int video_type;

  public String toString()
  {
    return "desc=" + this.desc + ",video_id=" + this.video_id + ",video_name=" + this.video_name + ",video_type=" + this.video_type + ",day=" + this.day + ",time_begin=" + this.time_begin + ",time_len=" + this.time_len;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.SearchLiveListItem
 * JD-Core Version:    0.6.2
 */