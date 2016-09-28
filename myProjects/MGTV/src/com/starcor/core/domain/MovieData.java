package com.starcor.core.domain;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class MovieData
  implements Parcelable
{
  public static final Parcelable.Creator<MovieData> CREATOR = new Parcelable.Creator()
  {
    public MovieData createFromParcel(Parcel paramAnonymousParcel)
    {
      return new MovieData(paramAnonymousParcel);
    }

    public MovieData[] newArray(int paramAnonymousInt)
    {
      return new MovieData[paramAnonymousInt];
    }
  };
  public String categoryId = "";
  public String img_v = "";
  public String indexUIStyle = "";
  public String name = "";
  public String packageId = "";
  public boolean updateIndex = false;
  public String videoId = "";
  public int videoIndex = -1;
  public int videoType;
  public int viewType = -1;

  public MovieData()
  {
  }

  protected MovieData(Parcel paramParcel)
  {
    this.packageId = paramParcel.readString();
    this.categoryId = paramParcel.readString();
    this.videoId = paramParcel.readString();
    this.videoType = paramParcel.readInt();
    this.viewType = paramParcel.readInt();
    this.videoIndex = paramParcel.readInt();
    this.name = paramParcel.readString();
    this.img_v = paramParcel.readString();
    this.indexUIStyle = paramParcel.readString();
    int i = paramParcel.readByte();
    boolean bool = false;
    if (i != 0)
      bool = true;
    this.updateIndex = bool;
  }

  public int describeContents()
  {
    return 0;
  }

  public String toString()
  {
    return "MovieData{packageId='" + this.packageId + '\'' + ", categoryId='" + this.categoryId + '\'' + ", videoId='" + this.videoId + '\'' + ", videoType=" + this.videoType + ", viewType=" + this.viewType + ", videoIndex=" + this.videoIndex + ", name='" + this.name + '\'' + ", img_v='" + this.img_v + '\'' + ", indexUIStyle='" + this.indexUIStyle + '\'' + ", updateIndex=" + this.updateIndex + '}';
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeString(this.packageId);
    paramParcel.writeString(this.categoryId);
    paramParcel.writeString(this.videoId);
    paramParcel.writeInt(this.videoType);
    paramParcel.writeInt(this.viewType);
    paramParcel.writeInt(this.videoIndex);
    paramParcel.writeString(this.name);
    paramParcel.writeString(this.img_v);
    paramParcel.writeString(this.indexUIStyle);
    if (this.updateIndex);
    for (byte b = 1; ; b = 0)
    {
      paramParcel.writeByte(b);
      return;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.MovieData
 * JD-Core Version:    0.6.2
 */