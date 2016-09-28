package com.mstar.android.tvapi.dtv.vo;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class CaLockService
  implements Parcelable
{
  public static final Parcelable.Creator<CaLockService> CREATOR;
  public CaComponent[] m_CompArr;
  public short m_ComponentNum;
  public short m_Modulation;
  public int m_dwFrequency;
  public short m_fec_inner;
  public short m_fec_outer;
  public int m_symbol_rate;
  public short m_wPcrPid;

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
 * Qualified Name:     com.mstar.android.tvapi.dtv.vo.CaLockService
 * JD-Core Version:    0.6.2
 */