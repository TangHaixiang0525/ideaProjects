package com.mstar.android.tvapi.common.vo;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class ThreeDimensionHw2dTo3dPara
  implements Parcelable
{
  public static final Parcelable.Creator<ThreeDimensionHw2dTo3dPara> CREATOR;
  public int artificialGain;
  public int concave;
  public int eleSel;
  public int gain;
  public int hw2dTo3dParaVersion;
  public int modSel;
  public int offset;

  public ThreeDimensionHw2dTo3dPara()
  {
  }

  public ThreeDimensionHw2dTo3dPara(Parcel paramParcel)
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
 * Qualified Name:     com.mstar.android.tvapi.common.vo.ThreeDimensionHw2dTo3dPara
 * JD-Core Version:    0.6.2
 */