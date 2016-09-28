package com.starcor.core.domain;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class ServerMessage
  implements Parcelable
{
  public static final Parcelable.Creator CREATOR = new Parcelable.Creator()
  {
    public ServerMessage createFromParcel(Parcel paramAnonymousParcel)
    {
      ServerMessage localServerMessage = new ServerMessage();
      localServerMessage.type = paramAnonymousParcel.readString();
      localServerMessage.id = paramAnonymousParcel.readString();
      localServerMessage.message_type = paramAnonymousParcel.readString();
      localServerMessage.user_cluster_id = paramAnonymousParcel.readString();
      localServerMessage.title = paramAnonymousParcel.readString();
      localServerMessage.content = paramAnonymousParcel.readString();
      localServerMessage.date = paramAnonymousParcel.readString();
      paramAnonymousParcel.readBooleanArray(new boolean[1]);
      return localServerMessage;
    }

    public ServerMessage[] newArray(int paramAnonymousInt)
    {
      return new ServerMessage[paramAnonymousInt];
    }
  };
  public String content;
  public String date;
  public String id;
  public boolean isRead = false;
  public String message_type;
  public String title;
  public String type;
  public String user_cluster_id;

  public int describeContents()
  {
    return 0;
  }

  public String getMessageId()
  {
    return this.id;
  }

  public boolean getReadFlag()
  {
    return this.isRead;
  }

  public void setReadFlag(boolean paramBoolean)
  {
    this.isRead = paramBoolean;
  }

  public String toString()
  {
    return "SysMessage [type=" + this.type + ", id=" + this.id + ", message_type=" + this.message_type + ", user_cluster_id=" + this.user_cluster_id + ", title=" + this.title + ", content=" + this.content + "]";
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeString(this.type);
    paramParcel.writeString(this.id);
    paramParcel.writeString(this.message_type);
    paramParcel.writeString(this.user_cluster_id);
    paramParcel.writeString(this.title);
    paramParcel.writeString(this.content);
    paramParcel.writeString(this.date);
    boolean[] arrayOfBoolean = new boolean[1];
    arrayOfBoolean[0] = this.isRead;
    paramParcel.writeBooleanArray(arrayOfBoolean);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.ServerMessage
 * JD-Core Version:    0.6.2
 */