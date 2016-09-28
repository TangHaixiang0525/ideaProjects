package com.mstar.android.tvapi.dtv.atsc.vo;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class Region5RatingInformation
  implements Parcelable
{
  public static final Parcelable.Creator<Region5RatingInformation> CREATOR;
  public static final int RRT5_DIMENSIONS = 256;
  public static final int RRT5_DIMNAME_LENGTH = 24;
  public static final int RRT5_LEVELS = 15;
  public static final int RRT5_REGNAME_LENGTH = 36;
  public static final int RRT5_TEXT_LENGTH = 16;
  public short dimensionNo;
  public Region5DimensionInformation[] regin5Dimensions;
  public short[] region5Name;

  public int describeContents()
  {
    throw new RuntimeException("stub");
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    throw new RuntimeException("stub");
  }

  public class Region5DimensionInformation
  {
    public String[] abbRatingText;
    public short[] dimensionName;
    public char graduatedScale;
    public short valuesDefined;

    public Region5DimensionInformation(Parcel arg2)
    {
    }

    public void writeToParcel(Parcel paramParcel)
    {
      throw new RuntimeException("stub");
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.tvapi.dtv.atsc.vo.Region5RatingInformation
 * JD-Core Version:    0.6.2
 */