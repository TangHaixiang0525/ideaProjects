package com.mstar.android.tvapi.dtv.atsc.vo;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class AtscProgramInfo
  implements Parcelable
{
  public static final Parcelable.Creator<AtscProgramInfo> CREATOR;
  public static final int MAX_AUD_LANG_NUM = 16;
  public static final int MAX_SERVICE_NAME = 8;
  public short audLangNum;
  public int id;
  public int muxTableId;
  public int pcrPid;
  public int pmtPID;
  public int programNumber;
  public String serviceName;
  public int sourceId;
  public int videoPID;

  public int describeContents()
  {
    throw new RuntimeException("stub");
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    throw new RuntimeException("stub");
  }

  public class AtscChannelAttribute
  {
    public byte favorite;
    public byte isHide;
    public byte isLock;
    public byte isRenamed;
    public byte isScramble;
    public byte isSkipped;
    public byte scrambleChStatus;
    public short serviceType;

    public AtscChannelAttribute()
    {
    }
  }

  public class AtscTableVersion
  {
    public short mgtVer;
    public short patVer;
    public short pmtVer;
    public short rrtVer;
    public short vctVer;

    public AtscTableVersion()
    {
    }
  }

  public class AtscVirtualChannelNumber
  {
    public int majorNumber;
    public int minorNumber;

    public AtscVirtualChannelNumber()
    {
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.tvapi.dtv.atsc.vo.AtscProgramInfo
 * JD-Core Version:    0.6.2
 */