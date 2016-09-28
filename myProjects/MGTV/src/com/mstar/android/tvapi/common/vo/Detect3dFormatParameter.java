package com.mstar.android.tvapi.common.vo;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class Detect3dFormatParameter
  implements Parcelable
{
  public static final Parcelable.Creator<Detect3dFormatParameter> CREATOR;
  public int bCbPixelThreshold;
  public int detect3DFormatPara_Version;
  public int gYPixelThreshold;
  public int hitPixelPercentage;
  public int horSampleCount;
  public int horSearchRange;
  public int maxCheckingFrameCount;
  public int rCrPixelThreshold;
  public int verSampleCount;
  public int verSearchRange;

  public Detect3dFormatParameter()
  {
  }

  public Detect3dFormatParameter(Parcel paramParcel)
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
 * Qualified Name:     com.mstar.android.tvapi.common.vo.Detect3dFormatParameter
 * JD-Core Version:    0.6.2
 */