package com.mstar.android.tvapi.dtv.vo;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.mstar.android.tvapi.common.exception.TvOutOfBoundException;

public class EpgEventInfo
  implements Parcelable
{
  public static final Parcelable.Creator<EpgEventInfo> CREATOR;
  public String description;
  public int durationTime;
  public int endTime;
  public int eventId;
  protected int functionStatus;
  public short genre;
  public boolean isScrambled;
  public String name;
  public int originalStartTime;
  public short parentalRating;
  public int startTime;

  public EpgEventInfo()
  {
  }

  public EpgEventInfo(Parcel paramParcel)
  {
  }

  public int describeContents()
  {
    throw new RuntimeException("stub");
  }

  public EnumEpgFunctionStatus getEpgFunctionStatus()
    throws TvOutOfBoundException
  {
    throw new RuntimeException("stub");
  }

  public void setEpgFunctionStatus(EnumEpgFunctionStatus paramEnumEpgFunctionStatus)
  {
    throw new RuntimeException("stub");
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    throw new RuntimeException("stub");
  }

  public static enum EnumEpgFunctionStatus
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
 * Qualified Name:     com.mstar.android.tvapi.dtv.vo.EpgEventInfo
 * JD-Core Version:    0.6.2
 */