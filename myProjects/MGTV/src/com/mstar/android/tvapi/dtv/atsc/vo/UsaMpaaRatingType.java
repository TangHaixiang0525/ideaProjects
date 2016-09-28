package com.mstar.android.tvapi.dtv.atsc.vo;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class UsaMpaaRatingType
  implements Parcelable
{
  public static final Parcelable.Creator<UsaMpaaRatingType> CREATOR;
  public EnumUsaMpaaRatingType enUaMpaaRatingType;
  public boolean isNr;

  public int describeContents()
  {
    throw new RuntimeException("stub");
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    throw new RuntimeException("stub");
  }

  public static enum EnumUsaMpaaRatingType
  {
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.tvapi.dtv.atsc.vo.UsaMpaaRatingType
 * JD-Core Version:    0.6.2
 */