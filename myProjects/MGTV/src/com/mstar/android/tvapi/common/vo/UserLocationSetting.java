package com.mstar.android.tvapi.common.vo;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class UserLocationSetting
  implements Parcelable
{
  public static final Parcelable.Creator<UserLocationSetting> CREATOR;
  public int locationNo;
  public short manualLatitude;
  public short manualLongitude;

  public UserLocationSetting()
  {
  }

  public UserLocationSetting(Parcel paramParcel)
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
 * Qualified Name:     com.mstar.android.tvapi.common.vo.UserLocationSetting
 * JD-Core Version:    0.6.2
 */