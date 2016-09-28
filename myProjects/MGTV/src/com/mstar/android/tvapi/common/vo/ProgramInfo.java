package com.mstar.android.tvapi.common.vo;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class ProgramInfo
  implements Parcelable
{
  public static final Parcelable.Creator<ProgramInfo> CREATOR;
  public int antennaType;
  public short favorite;
  public int frequency;
  public boolean isDelete;
  public boolean isHide;
  public boolean isLock;
  public boolean isScramble;
  public boolean isSkip;
  public boolean isVisible;
  public short majorNum;
  public short minorNum;
  public int number;
  public int progId;
  public int queryIndex;
  public int screenMuteStatus;
  public int serviceId;
  public String serviceName;
  public short serviceType;
  public int transportStreamId;

  public ProgramInfo()
  {
  }

  public ProgramInfo(int paramInt)
  {
  }

  public ProgramInfo(int paramInt1, int paramInt2, short paramShort1, short paramShort2, int paramInt3, short paramShort3, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5, boolean paramBoolean6, short paramShort4, int paramInt4, String paramString, int paramInt5, int paramInt6, int paramInt7, int paramInt8)
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
 * Qualified Name:     com.mstar.android.tvapi.common.vo.ProgramInfo
 * JD-Core Version:    0.6.2
 */