package com.mstar.android.tvapi.dtv.vo;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class AudioInfo
  implements Parcelable
{
  public static final Parcelable.Creator<AudioInfo> CREATOR;
  public short aacProfileAndLevel;
  public short aacType;
  public int audioPid;
  public short audioType;
  public boolean broadcastMixAd;
  public LangIso639 isoLangInfo;

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
 * Qualified Name:     com.mstar.android.tvapi.dtv.vo.AudioInfo
 * JD-Core Version:    0.6.2
 */