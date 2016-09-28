package com.mstar.android.tvapi.dtv.dvb.dvbs.vo;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class DvbsTransponderInfo
  implements Parcelable
{
  public static final Parcelable.Creator<DvbsTransponderInfo> CREATOR;
  public int frequency;
  public byte polarity;
  public short rf;
  public short satelliteId;
  public int symbolRate;
  public int tpId;

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
 * Qualified Name:     com.mstar.android.tvapi.dtv.dvb.dvbs.vo.DvbsTransponderInfo
 * JD-Core Version:    0.6.2
 */