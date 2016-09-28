package com.mstar.android.tvapi.common.vo;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class ColorTemperature
  implements Parcelable
{
  public static final Parcelable.Creator<ColorTemperature> CREATOR;
  public short blueOffset;
  public short buleGain;
  public short greenGain;
  public short greenOffset;
  public short redGain;
  public short redOffset;

  public ColorTemperature()
  {
  }

  public ColorTemperature(Parcel paramParcel)
  {
  }

  public ColorTemperature(short paramShort1, short paramShort2, short paramShort3, short paramShort4, short paramShort5, short paramShort6)
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
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.tvapi.common.vo.ColorTemperature
 * JD-Core Version:    0.6.2
 */