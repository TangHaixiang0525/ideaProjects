package com.mstar.android.tvapi.dtv.dvb.vo;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class DvbMuxInfo
  implements Parcelable
{
  public static final Parcelable.Creator<DvbMuxInfo> CREATOR;
  public short bandwidth;
  public int cellID;
  public int frequency;
  public int lossSignalFrequency;
  public int lossSignalStartTime;
  public boolean lpCoding;
  public short modulationMode;
  public int networkId;
  public int networkTableID;
  public int originalNetworkId;
  public int plpID;
  public int polarityPilotsReserved;
  public int refCnt;
  public short rfNumber;
  public short satID;
  public int satTableId;
  public int symbRate;
  public int transportStreamId;

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
 * Qualified Name:     com.mstar.android.tvapi.dtv.dvb.vo.DvbMuxInfo
 * JD-Core Version:    0.6.2
 */