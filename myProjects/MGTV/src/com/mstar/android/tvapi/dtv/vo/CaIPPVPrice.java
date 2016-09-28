package com.mstar.android.tvapi.dtv.vo;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.io.Serializable;

public class CaIPPVPrice
  implements Serializable, Parcelable
{
  public static final Parcelable.Creator<CaIPPVPrice> CREATOR;
  public short m_byPriceCode;
  public short m_wPrice;

  public int describeContents()
  {
    throw new RuntimeException("stub");
  }

  public short getM_byPriceCode()
  {
    throw new RuntimeException("stub");
  }

  public short getM_wPrice()
  {
    throw new RuntimeException("stub");
  }

  public void setM_byPriceCode(short paramShort)
  {
    throw new RuntimeException("stub");
  }

  public void setM_wPrice(short paramShort)
  {
    throw new RuntimeException("stub");
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    throw new RuntimeException("stub");
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.tvapi.dtv.vo.CaIPPVPrice
 * JD-Core Version:    0.6.2
 */