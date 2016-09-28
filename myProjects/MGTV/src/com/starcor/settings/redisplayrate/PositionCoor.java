package com.starcor.settings.redisplayrate;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class PositionCoor
  implements Parcelable
{
  public static final Parcelable.Creator<PositionCoor> CREATOR = new Parcelable.Creator()
  {
    public PositionCoor createFromParcel(Parcel paramAnonymousParcel)
    {
      return new PositionCoor(paramAnonymousParcel, null);
    }

    public PositionCoor[] newArray(int paramAnonymousInt)
    {
      return new PositionCoor[paramAnonymousInt];
    }
  };
  public int bottom;
  public int height;
  public int left;
  public int right;
  public int top;
  public int width;

  public PositionCoor()
  {
  }

  private PositionCoor(Parcel paramParcel)
  {
    this.left = paramParcel.readInt();
    this.top = paramParcel.readInt();
    this.right = paramParcel.readInt();
    this.bottom = paramParcel.readInt();
    this.width = paramParcel.readInt();
    this.height = paramParcel.readInt();
  }

  public PositionCoor(PositionCoor paramPositionCoor)
  {
    this.left = paramPositionCoor.left;
    this.top = paramPositionCoor.top;
    this.right = paramPositionCoor.right;
    this.bottom = paramPositionCoor.bottom;
    this.width = paramPositionCoor.width;
    this.height = paramPositionCoor.height;
  }

  public int describeContents()
  {
    return 0;
  }

  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof PositionCoor));
    PositionCoor localPositionCoor;
    do
    {
      return false;
      localPositionCoor = (PositionCoor)paramObject;
    }
    while ((this.left != localPositionCoor.left) || (this.top != localPositionCoor.top) || (this.right != localPositionCoor.right) || (this.bottom != localPositionCoor.bottom) || (this.width != localPositionCoor.width) || (this.height != localPositionCoor.height));
    return true;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeInt(this.left);
    paramParcel.writeInt(this.top);
    paramParcel.writeInt(this.right);
    paramParcel.writeInt(this.bottom);
    paramParcel.writeInt(this.width);
    paramParcel.writeInt(this.height);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.settings.redisplayrate.PositionCoor
 * JD-Core Version:    0.6.2
 */