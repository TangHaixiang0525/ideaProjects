package com.mstar.android.tvapi.common.vo;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class UsbUpgradeCfg
  implements Parcelable
{
  public static final Parcelable.Creator<UsbUpgradeCfg> CREATOR;
  public boolean IsUpgradeApplication;
  public boolean IsUpgradeConfig;
  public boolean IsUpgradeKernel;
  public boolean IsUpgradeMslib;
  public boolean IsUpgradeRootfs;
  public String acPath;

  public UsbUpgradeCfg()
  {
  }

  public UsbUpgradeCfg(Parcel paramParcel)
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
 * Qualified Name:     com.mstar.android.tvapi.common.vo.UsbUpgradeCfg
 * JD-Core Version:    0.6.2
 */