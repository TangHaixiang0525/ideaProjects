package com.mstar.android.tvapi.dtv.dvb.vo;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class DvbSecondaryRegionInfo
  implements Parcelable
{
  public static final Parcelable.Creator<DvbSecondaryRegionInfo> CREATOR;
  public short code;
  public String regionName;
  public DvbTeritaryRegionInfo[] tertiaryRegionInfos;
  public int tertiaryRegionNum;

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
 * Qualified Name:     com.mstar.android.tvapi.dtv.dvb.vo.DvbSecondaryRegionInfo
 * JD-Core Version:    0.6.2
 */