package com.mstar.android.tvapi.dtv.vo;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class EpgCridStatus
  implements Parcelable
{
  public static final Parcelable.Creator<EpgCridStatus> CREATOR;
  public boolean isAlternate;
  public boolean isRecommend;
  public boolean isSeries;
  public boolean isSplit;
  public short seriesCount;

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
 * Qualified Name:     com.mstar.android.tvapi.dtv.vo.EpgCridStatus
 * JD-Core Version:    0.6.2
 */