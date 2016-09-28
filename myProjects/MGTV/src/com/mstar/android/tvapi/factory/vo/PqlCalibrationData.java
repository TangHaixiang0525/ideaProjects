package com.mstar.android.tvapi.factory.vo;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class PqlCalibrationData
  implements Parcelable
{
  public static final Parcelable.Creator<PqlCalibrationData> CREATOR;
  public int blueGain;
  public int blueOffset;
  public int greenGain;
  public int greenOffset;
  public int redGain;
  public int redOffset;

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
 * Qualified Name:     com.mstar.android.tvapi.factory.vo.PqlCalibrationData
 * JD-Core Version:    0.6.2
 */