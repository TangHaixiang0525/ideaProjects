package com.intertrust.wasabi.media;

public class TrackInfo
{
  private String contentId;
  private MediaInfo mediaInfo;
  private int trackId;

  TrackInfo(String paramString, int paramInt, MediaInfo paramMediaInfo)
  {
    this.contentId = paramString;
    this.trackId = paramInt;
    this.mediaInfo = paramMediaInfo;
  }

  public String getContentId()
  {
    return this.contentId;
  }

  public MediaInfo getMediaInfo()
  {
    return this.mediaInfo;
  }

  public int getTrackId()
  {
    return this.trackId;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.intertrust.wasabi.media.TrackInfo
 * JD-Core Version:    0.6.2
 */