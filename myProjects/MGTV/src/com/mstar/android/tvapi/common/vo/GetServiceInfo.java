package com.mstar.android.tvapi.common.vo;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class GetServiceInfo
  implements Parcelable
{
  public static final Parcelable.Creator<GetServiceInfo> CREATOR;
  public short favorite;
  public boolean isDelete;
  public boolean isHide;
  public boolean isLock;
  public boolean isScramble;
  public boolean isSkip;
  public boolean isVisible;
  public short majorNum;
  public short minorNum;
  public int number;
  public short nvodTimeShiftServiceNum;
  public int progId;
  public int queryIndex;
  public int screenMuteStatus;
  public String serviceName;
  public short serviceType;

  public GetServiceInfo()
  {
  }

  public GetServiceInfo(Parcel paramParcel)
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
 * Qualified Name:     com.mstar.android.tvapi.common.vo.GetServiceInfo
 * JD-Core Version:    0.6.2
 */