package com.sohu.app.ads.sdk.model;

public class VideoProgressUpdate
{
  public static final VideoProgressUpdate VIDEO_TIME_NOT_READY = new VideoProgressUpdate(-1L, -1L);
  private float a;
  private float b;

  public VideoProgressUpdate()
  {
    this(-1L, -1L);
  }

  public VideoProgressUpdate(long paramLong1, long paramLong2)
  {
    this.a = ((float)paramLong1 / 1000.0F);
    this.b = ((float)paramLong2 / 1000.0F);
  }

  public float getCurrentTime()
  {
    return this.a;
  }

  public float getDuration()
  {
    return this.b;
  }

  public String toString()
  {
    return "VideoProgressUpdate [currentTime=" + this.a + ", duration=" + this.b + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.app.ads.sdk.model.VideoProgressUpdate
 * JD-Core Version:    0.6.2
 */