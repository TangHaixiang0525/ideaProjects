package com.mstar.android.tvapi.dtv.atsc.vo;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class AtscMainListChannelInformation
  implements Parcelable
{
  public static final Parcelable.Creator<AtscMainListChannelInformation> CREATOR;
  public int id;
  public int majorNumber;
  public int minorNumber;
  public int progId;
  public short rfCh;

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
 * Qualified Name:     com.mstar.android.tvapi.dtv.atsc.vo.AtscMainListChannelInformation
 * JD-Core Version:    0.6.2
 */