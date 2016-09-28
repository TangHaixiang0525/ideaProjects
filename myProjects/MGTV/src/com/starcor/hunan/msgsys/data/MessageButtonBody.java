package com.starcor.hunan.msgsys.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class MessageButtonBody
  implements Parcelable
{
  public static final Parcelable.Creator<MessageButtonBody> CREATOR = new Parcelable.Creator()
  {
    public MessageButtonBody createFromParcel(Parcel paramAnonymousParcel)
    {
      MessageButtonBody localMessageButtonBody = new MessageButtonBody();
      localMessageButtonBody.setActions(paramAnonymousParcel.readString());
      localMessageButtonBody.setLabel(paramAnonymousParcel.readString());
      localMessageButtonBody.setArgs(paramAnonymousParcel.readString());
      return localMessageButtonBody;
    }

    public MessageButtonBody[] newArray(int paramAnonymousInt)
    {
      return new MessageButtonBody[paramAnonymousInt];
    }
  };
  private static final String TAG = MessageButtonBody.class.getSimpleName();
  private String mActions = "";
  private String mArgs = "";
  private String mLabel = "";

  public int describeContents()
  {
    return 0;
  }

  public String getActions()
  {
    return this.mActions;
  }

  public String getArgs()
  {
    return this.mArgs;
  }

  public String getLabel()
  {
    return this.mLabel;
  }

  public void setActions(String paramString)
  {
    this.mActions = paramString;
  }

  public void setArgs(String paramString)
  {
    this.mArgs = paramString;
  }

  public void setLabel(String paramString)
  {
    this.mLabel = paramString;
  }

  public String toString()
  {
    return TAG + "[" + "actions=" + this.mActions + " label=" + this.mLabel + " args=" + this.mArgs + "]";
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeString(this.mActions);
    paramParcel.writeString(this.mLabel);
    paramParcel.writeString(this.mArgs);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.msgsys.data.MessageButtonBody
 * JD-Core Version:    0.6.2
 */