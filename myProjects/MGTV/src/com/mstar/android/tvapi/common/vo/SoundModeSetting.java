package com.mstar.android.tvapi.common.vo;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class SoundModeSetting
  implements Parcelable
{
  public static final Parcelable.Creator<SoundModeSetting> CREATOR;
  public short balance;
  public short bass;
  public EnumAudioMode enSoundAudioChannel;
  public short eqBand1;
  public short eqBand2;
  public short eqBand3;
  public short eqBand4;
  public short eqBand5;
  public short eqBand6;
  public short eqBand7;
  public short treble;
  public boolean userMode;

  public SoundModeSetting()
  {
  }

  public SoundModeSetting(Parcel paramParcel)
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
 * Qualified Name:     com.mstar.android.tvapi.common.vo.SoundModeSetting
 * JD-Core Version:    0.6.2
 */