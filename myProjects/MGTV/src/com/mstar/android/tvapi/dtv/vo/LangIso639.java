package com.mstar.android.tvapi.dtv.vo;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class LangIso639
  implements Parcelable
{
  public static final Parcelable.Creator<LangIso639> CREATOR;
  public short audioMode;
  public short audioType;
  public boolean isValid;
  public char[] isoLangInfo;

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
 * Qualified Name:     com.mstar.android.tvapi.dtv.vo.LangIso639
 * JD-Core Version:    0.6.2
 */