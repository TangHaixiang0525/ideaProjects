package com.mstar.android.tvapi.common.vo;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.mstar.android.tvapi.common.exception.TvCommonException;

public class VideoInfo
  implements Parcelable
{
  public static final Parcelable.Creator<VideoInfo> CREATOR;
  public int frameRate;
  public int hResolution;
  public int modeIndex;
  public int vResolution;

  public VideoInfo()
  {
  }

  public VideoInfo(Parcel paramParcel)
  {
  }

  public int describeContents()
  {
    throw new RuntimeException("stub");
  }

  public EnumScanType getScanType()
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public void setScanType(EnumScanType paramEnumScanType)
  {
    throw new RuntimeException("stub");
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    throw new RuntimeException("stub");
  }

  public static enum EnumScanType
  {
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.tvapi.common.vo.VideoInfo
 * JD-Core Version:    0.6.2
 */