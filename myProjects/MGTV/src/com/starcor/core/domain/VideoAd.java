package com.starcor.core.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class VideoAd
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private int alpha;
  private int scale;
  private ArrayList<Video> videos;

  public int getAlpha()
  {
    return this.alpha;
  }

  public int getScale()
  {
    return this.scale;
  }

  public ArrayList<Video> getVideos()
  {
    return this.videos;
  }

  public void setAlpha(int paramInt)
  {
    this.alpha = paramInt;
  }

  public void setScale(int paramInt)
  {
    this.scale = paramInt;
  }

  public void setVideos(ArrayList<Video> paramArrayList)
  {
    this.videos = paramArrayList;
  }

  public String toString()
  {
    String str1 = "VideoAd [" + " alpha: " + this.alpha;
    String str2 = str1 + ", scale:" + this.scale;
    String str3 = str2 + ", videos:" + this.videos;
    return str3 + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.VideoAd
 * JD-Core Version:    0.6.2
 */