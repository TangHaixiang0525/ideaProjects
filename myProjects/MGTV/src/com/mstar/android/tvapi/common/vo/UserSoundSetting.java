package com.mstar.android.tvapi.common.vo;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class UserSoundSetting
  implements Parcelable
{
  public static final Parcelable.Creator<UserSoundSetting> CREATOR;
  public EnumSoundAdOutput adOutput;
  public short adVolume;
  public SoundModeSetting[] astSoundModeSettings;
  public EnumAudysseyDynamicVolumeMode audysseyDynamicVolume;
  public EnumAudysseyEqMode audysseyEq;
  public short balance;
  public int checkSum;
  public boolean enableAVC;
  public boolean enableAd;
  public boolean enableHi;
  public short headPhonePreScale;
  public short headphoneVolume;
  public short lineOutPreScale;
  public short muteFlag;
  public short primaryFlag;
  public short scart1PreScale;
  public short scart2PreScale;
  public EnumAudioMode soundAudioChannel;
  public TvOsType.EnumLanguage soundAudioLanguage1;
  public TvOsType.EnumLanguage soundAudioLanguage2;
  public EnumSoundMode soundMode;
  public short spdifDelay;
  public short speakerPreScale;
  public short speakerdelay;
  public EnumSurroundType surround;
  public EnumSurroundSystemType surroundSoundMode;
  public short volume;

  public UserSoundSetting()
  {
  }

  public UserSoundSetting(Parcel paramParcel)
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
 * Qualified Name:     com.mstar.android.tvapi.common.vo.UserSoundSetting
 * JD-Core Version:    0.6.2
 */