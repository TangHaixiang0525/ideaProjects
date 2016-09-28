package com.mstar.android.tvapi.dtv.dvb.dvbs.vo;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class ScifConfigDb
  implements Parcelable
{
  public static final Parcelable.Creator<ScifConfigDb> CREATOR;
  public static final int MAX_NUM_UBAND = 8;
  public short[] frequencies;
  public short hiLOF;
  public short lowLOF;
  public ScifConfig scifConfig;

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
 * Qualified Name:     com.mstar.android.tvapi.dtv.dvb.dvbs.vo.ScifConfigDb
 * JD-Core Version:    0.6.2
 */