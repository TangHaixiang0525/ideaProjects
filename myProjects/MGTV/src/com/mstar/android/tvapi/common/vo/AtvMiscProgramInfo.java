package com.mstar.android.tvapi.common.vo;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class AtvMiscProgramInfo
  implements Parcelable
{
  public static final Parcelable.Creator<AtvMiscProgramInfo> CREATOR;
  public boolean bIsAutoColorSystem;
  public byte eAudioMode;
  public byte eAudioStandard;
  public boolean eMedium;
  public byte eVideoStandard;
  public byte eVolumeCompensation;
  public boolean isAutoFrequencyTuning;
  public boolean isDirectTuned;
  public byte isDualAudioSelected;
  public boolean isHide;
  public boolean isLock;
  public boolean isRealtimeAudioDetectionEnabled;
  public boolean isSkip;
  public short u8AutoFrequencyTuningOffset;
  public short u8ChannelNumber;
  public byte u8Favorite;
  public byte unused;

  public AtvMiscProgramInfo()
  {
  }

  public AtvMiscProgramInfo(Parcel paramParcel)
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
 * Qualified Name:     com.mstar.android.tvapi.common.vo.AtvMiscProgramInfo
 * JD-Core Version:    0.6.2
 */