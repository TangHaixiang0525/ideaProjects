package com.mstar.android.tvapi.common.vo;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class EpgEventTimerInfo
  implements Parcelable
{
  public static final Parcelable.Creator<EpgEventTimerInfo> CREATOR;
  public int checkSum;
  public int durationTime;
  public short enRepeatMode;
  public short enTimerType;
  public int eventID;
  public boolean isEndTimeBeforeStart;
  public int majorNumber;
  public int minorNumber;
  public int serviceNumber;
  public int serviceType;
  public int startTime;

  public EpgEventTimerInfo()
  {
  }

  public EpgEventTimerInfo(Parcel paramParcel)
  {
  }

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
 * Qualified Name:     com.mstar.android.tvapi.common.vo.EpgEventTimerInfo
 * JD-Core Version:    0.6.2
 */