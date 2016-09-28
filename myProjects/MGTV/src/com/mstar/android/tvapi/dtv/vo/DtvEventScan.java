package com.mstar.android.tvapi.dtv.vo;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class DtvEventScan
  implements Parcelable
{
  public static final Parcelable.Creator<DtvEventScan> CREATOR;
  public int currFrequency;
  public short currRFCh;
  public short dataSrvCount;
  public short dtvSrvCount;
  public short radioSrvCount;
  public short scanPercentageNum;
  public int scanStatus;
  public short signalQuality;
  public short signalStrength;
  public int userData;

  public DtvEventScan()
  {
  }

  public DtvEventScan(Parcel paramParcel)
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
 * Qualified Name:     com.mstar.android.tvapi.dtv.vo.DtvEventScan
 * JD-Core Version:    0.6.2
 */