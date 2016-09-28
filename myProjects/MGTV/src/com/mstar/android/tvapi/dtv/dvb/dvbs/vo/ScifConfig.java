package com.mstar.android.tvapi.dtv.dvb.dvbs.vo;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class ScifConfig
  implements Parcelable
{
  public static final Parcelable.Creator<ScifConfig> CREATOR;
  public short numBank;
  public short numStaPos;
  public short numUB;
  public EnumRfType rfType;

  public int describeContents()
  {
    throw new RuntimeException("stub");
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    throw new RuntimeException("stub");
  }

  public static enum EnumRfType
  {
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.tvapi.dtv.dvb.dvbs.vo.ScifConfig
 * JD-Core Version:    0.6.2
 */