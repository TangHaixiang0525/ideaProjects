package com.mstar.android.tvapi.common.vo;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class NetworkSetting
  implements Parcelable
{
  public static final Parcelable.Creator<NetworkSetting> CREATOR;
  public boolean bnetSelected;
  public short dns0;
  public short dns1;
  public short dns2;
  public short dns3;
  public short gateway0;
  public short gateway1;
  public short gateway2;
  public short gateway3;
  public char[] ip;
  public short ipAddr0;
  public short ipAddr1;
  public short ipAddr2;
  public short ipAddr3;
  public char[] ipName;
  public short netMask0;
  public short netMask1;
  public short netMask2;
  public short netMask3;
  public char[] netPassword;
  public char[] netUserName;
  public EnumNetConfigurationType netconfig;

  public NetworkSetting()
  {
  }

  public NetworkSetting(Parcel paramParcel)
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
 * Qualified Name:     com.mstar.android.tvapi.common.vo.NetworkSetting
 * JD-Core Version:    0.6.2
 */