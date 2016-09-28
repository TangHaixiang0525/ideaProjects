package com.starcor.core.domain;

import java.io.Serializable;

public class CommonVideoIDInfo
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String mAssetId = "";
  public String mAssetName = "";
  public String mClipId = "";
  public String mClipIndex = "";
  public String mClipName = "";
  public String mClipVideoId = "";
  public String mFileClipId = "";
  public String mFileClipName = "";
  public String mFileDimensions = "";
  public String mFileId = "";
  public String mFileQuality = "";
  public String mFileVideoID = "";
  public String mLiveId = "";
  public String mLiveName = "";

  public String toString()
  {
    return "CommonVideoIDInfo asset_id=" + this.mAssetId + " asset_name=" + this.mAssetName + " clip_id=" + this.mClipId + " clip_name=" + this.mClipName + " clip_index=" + this.mClipIndex + " clip_video_id=" + this.mClipVideoId + " file_id=" + this.mFileId + " file_quality=" + this.mFileQuality + " file_dimensions=" + this.mFileDimensions + " file_clip_id=" + this.mFileClipId + " file_clip_name=" + this.mFileClipName + " file_video_id=" + this.mFileVideoID + " live_id=" + this.mLiveId + " live_name=" + this.mLiveName;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.CommonVideoIDInfo
 * JD-Core Version:    0.6.2
 */