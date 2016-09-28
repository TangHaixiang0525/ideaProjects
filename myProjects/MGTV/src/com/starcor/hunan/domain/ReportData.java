package com.starcor.hunan.domain;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class ReportData
  implements Parcelable
{
  public static Parcelable.Creator<ReportData> CREATOR = new Parcelable.Creator()
  {
    public ReportData createFromParcel(Parcel paramAnonymousParcel)
    {
      return new ReportData(paramAnonymousParcel, null);
    }

    public ReportData[] newArray(int paramAnonymousInt)
    {
      return new ReportData[paramAnonymousInt];
    }
  };
  public String isfrom_special = "0";
  public String recommend_type = "";
  public String special_id = "";

  public ReportData()
  {
  }

  private ReportData(Parcel paramParcel)
  {
    this.recommend_type = paramParcel.readString();
    this.isfrom_special = paramParcel.readString();
    this.special_id = paramParcel.readString();
  }

  public int describeContents()
  {
    return 0;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeString(this.recommend_type);
    paramParcel.writeString(this.isfrom_special);
    paramParcel.writeString(this.special_id);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.domain.ReportData
 * JD-Core Version:    0.6.2
 */