package com.mstar.android.tvapi.common.vo;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class Picture
  implements Parcelable
{
  public static final Parcelable.Creator<Picture> CREATOR;
  public short backlight;
  public short brightness;
  public int colorTemp;
  public short contrast;
  public int dynamicBacklight;
  public int dynamicContrast;
  public short hue;
  public int perfectClear;
  public short saturation;
  public short sharpness;
  public int vibrantColour;

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
 * Qualified Name:     com.mstar.android.tvapi.common.vo.Picture
 * JD-Core Version:    0.6.2
 */