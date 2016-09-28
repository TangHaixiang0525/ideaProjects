package com.mstar.android.tvapi.factory.vo;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class WbGainOffset
  implements Parcelable
{
  public static final Parcelable.Creator<WbGainOffset> CREATOR;
  public short blueGain;
  public short blueOffset;
  public short greenGain;
  public short greenOffset;
  public short redGain;
  public short redOffset;

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
 * Qualified Name:     com.mstar.android.tvapi.factory.vo.WbGainOffset
 * JD-Core Version:    0.6.2
 */