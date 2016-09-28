package com.mstar.android.tvapi.common.vo;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class ThreeDimensionSetting
  implements Parcelable
{
  public static final Parcelable.Creator<ThreeDimensionSetting> CREATOR;
  public Enum2dDisplayFormat en2DFormat;
  public Enum3dDisplayFormat en3DFormat;
  public int en3DTimerPeriod;
  public EnumAutoStart enAutoStart;
  public EnumDisplayMode enDisplayMode;

  public ThreeDimensionSetting()
  {
  }

  public ThreeDimensionSetting(Parcel paramParcel)
  {
  }

  public int describeContents()
  {
    throw new RuntimeException("stub");
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    throw new RuntimeException("stub");
  }

  public static enum Enum2dDisplayFormat
  {
  }

  public static enum EnumAutoStart
  {
  }

  public static enum EnumTimerPeriod
  {
    public int getValue()
    {
      throw new RuntimeException("stub");
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.tvapi.common.vo.ThreeDimensionSetting
 * JD-Core Version:    0.6.2
 */