package com.mstar.android.tvapi.common.vo;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class UserMmSetting
  implements Parcelable
{
  public static final Parcelable.Creator<UserMmSetting> CREATOR;
  public byte previewOn;
  public byte reserved;
  public byte resumePlay;
  public short slideShowMode;
  public short slideShowTime;
  public short subtitleBgColor;
  public short subtitleFontColor;
  public short subtitleSpecific;

  public UserMmSetting()
  {
  }

  public UserMmSetting(Parcel paramParcel)
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
 * Qualified Name:     com.mstar.android.tvapi.common.vo.UserMmSetting
 * JD-Core Version:    0.6.2
 */