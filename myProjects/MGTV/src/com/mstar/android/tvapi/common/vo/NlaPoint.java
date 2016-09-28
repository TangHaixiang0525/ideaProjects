package com.mstar.android.tvapi.common.vo;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class NlaPoint
  implements Parcelable
{
  public static final Parcelable.Creator<NlaPoint> CREATOR;
  public short osdV0;
  public short osdV100;
  public short osdV25;
  public short osdV50;
  public short osdV75;

  public NlaPoint()
  {
  }

  public NlaPoint(Parcel paramParcel)
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
 * Qualified Name:     com.mstar.android.tvapi.common.vo.NlaPoint
 * JD-Core Version:    0.6.2
 */