package com.mstar.android.tvapi.dtv.dvb.vo;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class DvbCountryInfo
  implements Parcelable
{
  public static final Parcelable.Creator<DvbCountryInfo> CREATOR;
  public char[] countryCode;
  public int primaryRegionCount;
  public DvbPrimaryRegionInfo[] primaryRegionInfos;

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
 * Qualified Name:     com.mstar.android.tvapi.dtv.dvb.vo.DvbCountryInfo
 * JD-Core Version:    0.6.2
 */