package com.mstar.android.tvapi.dtv.dvb.vo;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class DvbTeritaryRegionInfo
  implements Parcelable
{
  public static final Parcelable.Creator<DvbTeritaryRegionInfo> CREATOR;
  public int code;
  public String regionName;

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
 * Qualified Name:     com.mstar.android.tvapi.dtv.dvb.vo.DvbTeritaryRegionInfo
 * JD-Core Version:    0.6.2
 */