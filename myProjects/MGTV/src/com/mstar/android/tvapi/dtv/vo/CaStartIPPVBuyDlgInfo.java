package com.mstar.android.tvapi.dtv.vo;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.io.Serializable;

public class CaStartIPPVBuyDlgInfo
  implements Serializable, Parcelable
{
  public static final Parcelable.Creator<CaStartIPPVBuyDlgInfo> CREATOR;
  public int dwProductID;
  public CaIPPVPrice[] m_Price;
  public short wEcmPid;
  public short wExpiredDate;
  public short wTvsID;
  public short wyMessageType;
  public short wyPriceNum;
  public short wySlotID;

  public int describeContents()
  {
    throw new RuntimeException("stub");
  }

  public int getDwProductID()
  {
    throw new RuntimeException("stub");
  }

  public CaIPPVPrice[] getM_Price()
  {
    throw new RuntimeException("stub");
  }

  public short getWyMessageType()
  {
    throw new RuntimeException("stub");
  }

  public short getWyPriceNum()
  {
    throw new RuntimeException("stub");
  }

  public short getWySlotID()
  {
    throw new RuntimeException("stub");
  }

  public short getwEcmPid()
  {
    throw new RuntimeException("stub");
  }

  public short getwExpiredDate()
  {
    throw new RuntimeException("stub");
  }

  public short getwTvsID()
  {
    throw new RuntimeException("stub");
  }

  public void setDwProductID(int paramInt)
  {
    throw new RuntimeException("stub");
  }

  public void setM_Price(CaIPPVPrice[] paramArrayOfCaIPPVPrice)
  {
    throw new RuntimeException("stub");
  }

  public void setWyMessageType(short paramShort)
  {
    throw new RuntimeException("stub");
  }

  public void setWyPriceNum(short paramShort)
  {
    throw new RuntimeException("stub");
  }

  public void setWySlotID(short paramShort)
  {
    throw new RuntimeException("stub");
  }

  public void setwEcmPid(short paramShort)
  {
    throw new RuntimeException("stub");
  }

  public void setwExpiredDate(short paramShort)
  {
    throw new RuntimeException("stub");
  }

  public void setwTvsID(short paramShort)
  {
    throw new RuntimeException("stub");
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    throw new RuntimeException("stub");
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.tvapi.dtv.vo.CaStartIPPVBuyDlgInfo
 * JD-Core Version:    0.6.2
 */