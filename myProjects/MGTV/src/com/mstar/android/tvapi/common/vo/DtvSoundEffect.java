package com.mstar.android.tvapi.common.vo;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class DtvSoundEffect
  implements Parcelable
{
  public static final Parcelable.Creator<DtvSoundEffect> CREATOR;
  public static final int MAX_EQ_BAND_NUM = 5;
  public static final int MAX_PEQ_BAND_NUM = 5;
  public int avcAttachTime;
  public int avcReleaseTime;
  public int avcThreshold;
  public int balance;
  public int bass;
  public int echoTime;
  public short eqBandNumber;
  public int noiseReductionThreshold;
  public short peqBandNumber;
  public int preScale;
  public int soundDrcThreshold;
  public SoundParameterEq[] soundParameterEqs;
  public SoundParameterPeq[] soundParameterPeqs;
  public int surroundXaValue;
  public int surroundXbValue;
  public int surroundXkValue;
  public int treble;

  public DtvSoundEffect()
  {
  }

  public DtvSoundEffect(Parcel paramParcel)
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
 * Qualified Name:     com.mstar.android.tvapi.common.vo.DtvSoundEffect
 * JD-Core Version:    0.6.2
 */