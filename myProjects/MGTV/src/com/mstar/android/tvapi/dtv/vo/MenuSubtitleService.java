package com.mstar.android.tvapi.dtv.vo;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.mstar.android.tvapi.common.vo.TvOsType.EnumLanguage;

public class MenuSubtitleService
  implements Parcelable
{
  public static final Parcelable.Creator<MenuSubtitleService> CREATOR;
  public static final int MAX_STRINGCODE_COUNT = 4;
  public int eLanguage;
  public int enSubtitleType;
  public short refCount;
  public char[] stringCodes;

  public MenuSubtitleService()
  {
  }

  public MenuSubtitleService(int paramInt1, int paramInt2, short paramShort, char[] paramArrayOfChar)
  {
  }

  public int describeContents()
  {
    throw new RuntimeException("stub");
  }

  public TvOsType.EnumLanguage getLanguage()
  {
    throw new RuntimeException("stub");
  }

  public void setLanguage(TvOsType.EnumLanguage paramEnumLanguage)
  {
    throw new RuntimeException("stub");
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    throw new RuntimeException("stub");
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.tvapi.dtv.vo.MenuSubtitleService
 * JD-Core Version:    0.6.2
 */