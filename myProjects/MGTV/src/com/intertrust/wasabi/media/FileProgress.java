package com.intertrust.wasabi.media;

public class FileProgress
{
  public static final int FLAG_PROGRESS_CAN_START = 1;
  private long available;
  private int flags;
  private long totalSize;

  FileProgress(int paramInt, long paramLong1, long paramLong2)
  {
    this.flags = paramInt;
    this.available = paramLong1;
    this.totalSize = paramLong2;
  }

  public long getAvailable()
  {
    return this.available;
  }

  public int getFlags()
  {
    return this.flags;
  }

  public long getTotalSize()
  {
    return this.totalSize;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.intertrust.wasabi.media.FileProgress
 * JD-Core Version:    0.6.2
 */