package com.mstar.android.tvapi.dtv.atsc.vo;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class AtscEpgEventInfo
  implements Parcelable
{
  public static final Parcelable.Creator<AtscEpgEventInfo> CREATOR;
  public boolean bHasCCInfo;
  public int durationTime;
  public EnumAtscEpgFunctionStatus enStrStatus;
  public int endTime;
  public String sExtendedText;
  public String sName;
  public AtscEpgRating stRating;
  public int startTime;

  public AtscEpgEventInfo()
  {
  }

  public AtscEpgEventInfo(Parcel paramParcel)
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

  public class AtscEpgRating
  {
    public byte caEngFlag;
    public byte caEngRatingD0;
    public byte caFreFlag;
    public byte caFreRatingD1;
    public byte dialog;
    public byte fantasyViolence;
    public byte language;
    public byte mpaaFlag;
    public byte mpaaRatingD2;
    public byte ratingRxIsOK;
    public byte sexualContent;
    public byte tvRatingForChild;
    public byte tvRatingForEntire;
    public byte violence;

    public AtscEpgRating()
    {
    }
  }

  public static enum EnumAtscEpgFunctionStatus
  {
    public static int getOrdinalThroughValue(int paramInt)
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
 * Qualified Name:     com.mstar.android.tvapi.dtv.atsc.vo.AtscEpgEventInfo
 * JD-Core Version:    0.6.2
 */