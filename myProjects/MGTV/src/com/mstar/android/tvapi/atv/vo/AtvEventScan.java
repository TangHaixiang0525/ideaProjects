package com.mstar.android.tvapi.atv.vo;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class AtvEventScan
  implements Parcelable
{
  public static final Parcelable.Creator<AtvEventScan> CREATOR;
  public boolean bIsScaningEnable;
  public short curScannedChannel;
  public int frequencyKHz;
  public short percent;
  public short scannedChannelNum;

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
 * Qualified Name:     com.mstar.android.tvapi.atv.vo.AtvEventScan
 * JD-Core Version:    0.6.2
 */