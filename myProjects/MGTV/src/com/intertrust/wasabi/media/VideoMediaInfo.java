package com.intertrust.wasabi.media;

public class VideoMediaInfo extends MediaInfo
{
  private int depth;
  private int height;
  private int width;

  VideoMediaInfo(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7)
  {
    super(MediaInfo.Type.VIDEO, paramInt1, paramInt2, paramInt3, paramInt4);
    this.width = paramInt5;
    this.height = paramInt6;
    this.depth = paramInt7;
  }

  public int getDepth()
  {
    return this.depth;
  }

  public int getHeight()
  {
    return this.height;
  }

  public int getWidth()
  {
    return this.width;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.intertrust.wasabi.media.VideoMediaInfo
 * JD-Core Version:    0.6.2
 */