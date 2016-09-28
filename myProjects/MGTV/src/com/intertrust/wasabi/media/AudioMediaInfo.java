package com.intertrust.wasabi.media;

public class AudioMediaInfo extends MediaInfo
{
  private int channelCount;
  private int sampleRate;
  private int sampleSize;

  AudioMediaInfo(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7)
  {
    super(MediaInfo.Type.AUDIO, paramInt1, paramInt2, paramInt3, paramInt4);
    this.channelCount = paramInt5;
    this.sampleRate = paramInt7;
    this.sampleSize = paramInt6;
  }

  public int getChannelCount()
  {
    return this.channelCount;
  }

  public int getSampleRate()
  {
    return this.sampleRate;
  }

  public int getSampleSize()
  {
    return this.sampleSize;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.intertrust.wasabi.media.AudioMediaInfo
 * JD-Core Version:    0.6.2
 */