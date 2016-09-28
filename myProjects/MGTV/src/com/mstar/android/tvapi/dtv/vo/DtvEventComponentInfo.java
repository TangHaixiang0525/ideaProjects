package com.mstar.android.tvapi.dtv.vo;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.mstar.android.tvapi.common.vo.EnumVideoType;

public class DtvEventComponentInfo
  implements Parcelable
{
  public static final Parcelable.Creator<DtvEventComponentInfo> CREATOR;
  public short audioTrackNum;
  public boolean ccService;
  public boolean isAd;
  public boolean mheg5Service;
  public short parentalRating;
  public short subtitleNum;
  public boolean subtitleService;
  public boolean teletextService;

  public DtvEventComponentInfo()
  {
  }

  public DtvEventComponentInfo(Parcel paramParcel)
  {
  }

  public int describeContents()
  {
    throw new RuntimeException("stub");
  }

  public DtvType.EnumAspectRatioCode getAspectRatioCode()
  {
    throw new RuntimeException("stub");
  }

  public DtvType.EnumDtvVideoQuality getDtvVideoQuality()
  {
    throw new RuntimeException("stub");
  }

  public DtvType.EnumEpgMainGenreType getGenreType()
  {
    throw new RuntimeException("stub");
  }

  public EnumVideoType getVideoType()
  {
    throw new RuntimeException("stub");
  }

  public void setAspectRatioCode(DtvType.EnumAspectRatioCode paramEnumAspectRatioCode)
  {
    throw new RuntimeException("stub");
  }

  public void setDtvVideoQuality(DtvType.EnumDtvVideoQuality paramEnumDtvVideoQuality)
  {
    throw new RuntimeException("stub");
  }

  public void setGenreType(DtvType.EnumEpgMainGenreType paramEnumEpgMainGenreType)
  {
    throw new RuntimeException("stub");
  }

  public void setVideoType(EnumVideoType paramEnumVideoType)
  {
    throw new RuntimeException("stub");
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    throw new RuntimeException("stub");
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.tvapi.dtv.vo.DtvEventComponentInfo
 * JD-Core Version:    0.6.2
 */