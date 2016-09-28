package com.mstar.android.tvapi.dtv.atsc.vo;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class UsaTvRatingInformation
  implements Parcelable
{
  public static final Parcelable.Creator<UsaTvRatingInformation> CREATOR;
  public boolean bTV_14_ALL_Lock;
  public boolean bTV_14_D_Lock;
  public boolean bTV_14_L_Lock;
  public boolean bTV_14_S_Lock;
  public boolean bTV_14_V_Lock;
  public boolean bTV_G_ALL_Lock;
  public boolean bTV_G_D_Lock;
  public boolean bTV_G_L_Lock;
  public boolean bTV_G_S_Lock;
  public boolean bTV_G_V_Lock;
  public boolean bTV_MA_ALL_Lock;
  public boolean bTV_MA_D_Lock;
  public boolean bTV_MA_L_Lock;
  public boolean bTV_MA_S_Lock;
  public boolean bTV_MA_V_Lock;
  public boolean bTV_PG_ALL_Lock;
  public boolean bTV_PG_D_Lock;
  public boolean bTV_PG_L_Lock;
  public boolean bTV_PG_S_Lock;
  public boolean bTV_PG_V_Lock;
  public boolean bTV_Y7_ALL_Lock;
  public boolean bTV_Y7_FV_Lock;
  public boolean bTV_Y_ALL_Lock;

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
 * Qualified Name:     com.mstar.android.tvapi.dtv.atsc.vo.UsaTvRatingInformation
 * JD-Core Version:    0.6.2
 */