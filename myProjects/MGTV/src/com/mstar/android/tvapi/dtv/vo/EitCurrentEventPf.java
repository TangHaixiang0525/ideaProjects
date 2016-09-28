package com.mstar.android.tvapi.dtv.vo;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.format.Time;

public class EitCurrentEventPf
  implements Parcelable
{
  public static final Parcelable.Creator<EitCurrentEventPf> CREATOR;
  public short contentNibbleLevel1;
  public short contentNibbleLevel2;
  public int durationInSeconds;
  public String eventName;
  public String extendedEventItem;
  public String extendedEventText;
  public boolean isEndTimeDayLightTime;
  public boolean isScrambled;
  public boolean isStartTimeDayLightTime;
  public short parentalControl;
  public short parentalObjectiveContent;
  public String shortEventText;
  public Time stEndTime;
  public Time stStartTime;

  public EitCurrentEventPf()
  {
  }

  public EitCurrentEventPf(Parcel paramParcel)
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
 * Qualified Name:     com.mstar.android.tvapi.dtv.vo.EitCurrentEventPf
 * JD-Core Version:    0.6.2
 */