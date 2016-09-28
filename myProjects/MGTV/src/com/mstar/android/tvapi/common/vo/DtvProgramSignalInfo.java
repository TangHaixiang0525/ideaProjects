package com.mstar.android.tvapi.common.vo;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.mstar.android.tvapi.common.exception.TvOutOfBoundException;

public class DtvProgramSignalInfo
  implements Parcelable
{
  public static final Parcelable.Creator<DtvProgramSignalInfo> CREATOR;
  public short amMode;
  public String networkName;
  public int quality;
  public short rfNumber;
  public int strength;
  public int symbolRate;

  public DtvProgramSignalInfo()
  {
  }

  public DtvProgramSignalInfo(Parcel paramParcel)
  {
  }

  public int describeContents()
  {
    throw new RuntimeException("stub");
  }

  public EnumProgramDemodType getModulationMode()
    throws TvOutOfBoundException
  {
    throw new RuntimeException("stub");
  }

  public void setModulationMode(EnumProgramDemodType paramEnumProgramDemodType)
  {
    throw new RuntimeException("stub");
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    throw new RuntimeException("stub");
  }

  public static enum EnumProgramDemodType
  {
    public static int getOrdinalThroughValue(int paramInt)
      throws TvOutOfBoundException
    {
      throw new RuntimeException("stub");
    }

    public int getValue()
    {
      throw new RuntimeException("stub");
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.tvapi.common.vo.DtvProgramSignalInfo
 * JD-Core Version:    0.6.2
 */