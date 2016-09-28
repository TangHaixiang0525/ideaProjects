package com.mstar.android.tvapi.common.vo;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class VideoArcInfo
  implements Parcelable
{
  public static final Parcelable.Creator<VideoArcInfo> CREATOR;
  public short adjArcDown;
  public short adjArcLeft;
  public short adjArcRight;
  public short adjArcUp;
  public boolean bSetCusWin;

  public VideoArcInfo(Parcel paramParcel)
  {
  }

  public int describeContents()
  {
    throw new RuntimeException("stub");
  }

  public Enum3dAspectRatioType get3dArcType()
  {
    throw new RuntimeException("stub");
  }

  public EnumVideoArcType getArcType()
  {
    throw new RuntimeException("stub");
  }

  public void set3dArcType(Enum3dAspectRatioType paramEnum3dAspectRatioType)
  {
    throw new RuntimeException("stub");
  }

  public void setArcType(EnumVideoArcType paramEnumVideoArcType)
  {
    throw new RuntimeException("stub");
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    throw new RuntimeException("stub");
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.tvapi.common.vo.VideoArcInfo
 * JD-Core Version:    0.6.2
 */