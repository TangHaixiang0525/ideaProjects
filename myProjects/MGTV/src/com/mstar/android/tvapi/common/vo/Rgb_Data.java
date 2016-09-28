package com.mstar.android.tvapi.common.vo;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class Rgb_Data
  implements Parcelable
{
  public static final Parcelable.Creator<Rgb_Data> CREATOR;
  public int b;
  public int g;
  public int r;

  public Rgb_Data()
  {
  }

  public Rgb_Data(Parcel paramParcel)
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
 * Qualified Name:     com.mstar.android.tvapi.common.vo.Rgb_Data
 * JD-Core Version:    0.6.2
 */