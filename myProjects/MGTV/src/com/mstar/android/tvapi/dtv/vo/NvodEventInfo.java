package com.mstar.android.tvapi.dtv.vo;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.mstar.android.tvapi.common.vo.DtvTripleId;

public class NvodEventInfo
  implements Parcelable
{
  public static final Parcelable.Creator<NvodEventInfo> CREATOR;
  public EpgEventInfo epgEventInfo;
  public DtvTripleId timeShiftedServiceIds;

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
 * Qualified Name:     com.mstar.android.tvapi.dtv.vo.NvodEventInfo
 * JD-Core Version:    0.6.2
 */