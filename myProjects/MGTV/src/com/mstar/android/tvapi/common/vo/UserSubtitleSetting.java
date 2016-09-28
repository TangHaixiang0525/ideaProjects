package com.mstar.android.tvapi.common.vo;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class UserSubtitleSetting
  implements Parcelable
{
  public static final Parcelable.Creator<UserSubtitleSetting> CREATOR;
  public int enableSubTitle;
  public int hardOfHearing;
  public int reserved;
  public TvOsType.EnumLanguage subtitleDefaultLanguage1;
  public TvOsType.EnumLanguage subtitleDefaultLanguage2;

  public UserSubtitleSetting(Parcel paramParcel)
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
 * Qualified Name:     com.mstar.android.tvapi.common.vo.UserSubtitleSetting
 * JD-Core Version:    0.6.2
 */