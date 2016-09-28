package com.mstar.android.tvapi.factory.vo;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class PictureModeValue
  implements Parcelable
{
  public static final Parcelable.Creator<PictureModeValue> CREATOR;
  public short brightness;
  public short contrast;
  public short hue;
  public short saturation;
  public short sharpness;

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
 * Qualified Name:     com.mstar.android.tvapi.factory.vo.PictureModeValue
 * JD-Core Version:    0.6.2
 */