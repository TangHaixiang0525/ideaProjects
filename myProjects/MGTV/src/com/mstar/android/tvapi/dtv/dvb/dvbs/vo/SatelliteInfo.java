package com.mstar.android.tvapi.dtv.dvb.dvbs.vo;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class SatelliteInfo
  implements Parcelable
{
  public static final Parcelable.Creator<SatelliteInfo> CREATOR;
  public int angle;
  public short channelId;
  public short diseqcLevel;
  public short e22KOnOff;
  public int frequency;
  public int hiLOF;
  public short lnbPwrOnOff;
  public int lowLOF;
  public int numberOfTp;
  public short ov12VOnOff;
  public short position;
  public String satName;
  public short satelliteId;
  public short swt10Port;
  public short swt11Port;
  public short toneburstType;

  public int describeContents()
  {
    throw new RuntimeException("stub");
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    throw new RuntimeException("stub");
  }

  public static enum EnumLnbType
  {
  }

  public static enum EnumLnbTypeReal
  {
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.tvapi.dtv.dvb.dvbs.vo.SatelliteInfo
 * JD-Core Version:    0.6.2
 */