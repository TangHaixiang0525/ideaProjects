package com.mstar.android.tvapi.common.vo;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class CaptionOptionSetting
  implements Parcelable
{
  public static final Parcelable.Creator<CaptionOptionSetting> CREATOR;
  public short currProgInfoBGColor;
  public short currProgInfoBGOpacity;
  public short currProgInfoEdgeColor;
  public short currProgInfoEdgeStyle;
  public short currProgInfoFGColor;
  public short currProgInfoFGOpacity;
  public short currProgInfoFontSize;
  public short currProgInfoFontStyle;

  public CaptionOptionSetting(Parcel paramParcel)
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
 * Qualified Name:     com.mstar.android.tvapi.common.vo.CaptionOptionSetting
 * JD-Core Version:    0.6.2
 */