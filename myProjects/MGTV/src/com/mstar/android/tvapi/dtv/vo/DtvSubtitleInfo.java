package com.mstar.android.tvapi.dtv.vo;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class DtvSubtitleInfo
  implements Parcelable
{
  public static final Parcelable.Creator<DtvSubtitleInfo> CREATOR;
  public static final int MAX_MENUSUBTITLESERVICE_COUNT = 24;
  public short currentSubtitleIndex;
  public boolean subtitleOn;
  public short subtitleServiceNumber;
  public MenuSubtitleService[] subtitleServices;

  public DtvSubtitleInfo()
  {
  }

  public DtvSubtitleInfo(MenuSubtitleService[] paramArrayOfMenuSubtitleService, short paramShort1, short paramShort2, boolean paramBoolean)
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
 * Qualified Name:     com.mstar.android.tvapi.dtv.vo.DtvSubtitleInfo
 * JD-Core Version:    0.6.2
 */