package com.starcor.core.domain;

import java.io.Serializable;

public class VideoScoreInfoOnUser
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String count;
  public String score;
  public String user_ip;
  public String user_score;
  public String user_score_time;
  public String video_id;

  public String toString()
  {
    return "socre_info [video_id=" + this.video_id + ", user_score=" + this.user_score + ", score=" + this.score + ", count=" + this.count + ", user_ip=" + this.user_ip + ", user_score_time=" + this.user_score_time + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.VideoScoreInfoOnUser
 * JD-Core Version:    0.6.2
 */