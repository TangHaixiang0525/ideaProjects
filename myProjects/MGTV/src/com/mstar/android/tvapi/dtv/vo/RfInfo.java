package com.mstar.android.tvapi.dtv.vo;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class RfInfo
  implements Parcelable
{
  public static final Parcelable.Creator<RfInfo> CREATOR;
  public int frequency;
  public boolean isVHF;
  public String rfName;
  public short rfPhyNum;

  public int describeContents()
  {
    throw new RuntimeException("stub");
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    throw new RuntimeException("stub");
  }

  public static enum EnumInfoType
  {
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.tvapi.dtv.vo.RfInfo
 * JD-Core Version:    0.6.2
 */