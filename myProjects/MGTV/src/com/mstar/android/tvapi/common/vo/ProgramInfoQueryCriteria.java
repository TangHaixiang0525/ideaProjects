package com.mstar.android.tvapi.common.vo;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class ProgramInfoQueryCriteria
  implements Parcelable
{
  public static final Parcelable.Creator<ProgramInfoQueryCriteria> CREATOR;
  public int number;
  public int queryIndex;
  protected short serviceType;

  public ProgramInfoQueryCriteria()
  {
  }

  public ProgramInfoQueryCriteria(int paramInt1, int paramInt2, EnumServiceType paramEnumServiceType)
  {
  }

  public ProgramInfoQueryCriteria(Parcel paramParcel)
  {
  }

  public int describeContents()
  {
    throw new RuntimeException("stub");
  }

  public EnumServiceType getServiceType()
  {
    throw new RuntimeException("stub");
  }

  public void setServiceType(EnumServiceType paramEnumServiceType)
  {
    throw new RuntimeException("stub");
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    throw new RuntimeException("stub");
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.tvapi.common.vo.ProgramInfoQueryCriteria
 * JD-Core Version:    0.6.2
 */