package com.starcor.core.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class QuitVideoIndexsParams
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String artithmeticId = "";
  public String estimateId = "";
  public ArrayList<PlayerQuitsVideoInfo> videoList = new ArrayList();
  public int video_count = 0;

  public String toString()
  {
    return "QuitVideoIndexsParams{video_count='" + this.video_count + '\'' + ", estimateId=" + this.estimateId + ", artithmeticId=" + this.artithmeticId + ", videoList='" + this.videoList + '\'' + '}';
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.QuitVideoIndexsParams
 * JD-Core Version:    0.6.2
 */