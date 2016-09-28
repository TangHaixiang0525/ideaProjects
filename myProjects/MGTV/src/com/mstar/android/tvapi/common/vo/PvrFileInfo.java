package com.mstar.android.tvapi.common.vo;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class PvrFileInfo
  implements Parcelable
{
  public static final Parcelable.Creator<PvrFileInfo> CREATOR;
  public String filename;
  public boolean isRecording;

  public PvrFileInfo()
  {
  }

  public PvrFileInfo(Parcel paramParcel)
  {
  }

  public int describeContents()
  {
    throw new RuntimeException("stub");
  }

  public EnumPvrFileInfoSortKey getPvrFileInfoSortKey()
  {
    throw new RuntimeException("stub");
  }

  public void getPvrFileInfoSortKey(EnumPvrFileInfoSortKey paramEnumPvrFileInfoSortKey)
  {
    throw new RuntimeException("stub");
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    throw new RuntimeException("stub");
  }

  public static enum EnumPvrFileInfoSortKey
  {
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.tvapi.common.vo.PvrFileInfo
 * JD-Core Version:    0.6.2
 */