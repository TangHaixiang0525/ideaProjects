package com.starcor.hunan.msgsys.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.starcor.core.utils.Logger;
import java.util.ArrayList;
import java.util.List;

public class MessageItemData
  implements Parcelable
{
  public static final Parcelable.Creator<MessageItemData> CREATOR = new Parcelable.Creator()
  {
    public MessageItemData createFromParcel(Parcel paramAnonymousParcel)
    {
      MessageItemData localMessageItemData = new MessageItemData(null);
      MessageItemData.access$102(localMessageItemData, paramAnonymousParcel.readString());
      MessageItemData.access$202(localMessageItemData, paramAnonymousParcel.readString());
      MessageItemData.access$302(localMessageItemData, paramAnonymousParcel.readString());
      MessageItemData.access$402(localMessageItemData, (MessageItemData.MessageType)paramAnonymousParcel.readValue(MessageItemData.MessageType.class.getClassLoader()));
      MessageItemData.access$502(localMessageItemData, (MessageItemData.MessageActionType)paramAnonymousParcel.readValue(MessageItemData.MessageActionType.class.getClassLoader()));
      boolean[] arrayOfBoolean = new boolean[1];
      paramAnonymousParcel.readBooleanArray(arrayOfBoolean);
      MessageItemData.access$602(localMessageItemData, arrayOfBoolean[0]);
      MessageItemData.access$702(localMessageItemData, paramAnonymousParcel.readString());
      MessageItemData.access$802(localMessageItemData, (MessageItemData.SecondaryMessageType)paramAnonymousParcel.readValue(MessageItemData.SecondaryMessageType.class.getClassLoader()));
      MessageItemData.access$902(localMessageItemData, paramAnonymousParcel.readString());
      MessageItemData.access$1002(localMessageItemData, paramAnonymousParcel.readString());
      MessageItemData.access$1102(localMessageItemData, paramAnonymousParcel.readString());
      MessageItemData.access$1202(localMessageItemData, paramAnonymousParcel.readString());
      MessageItemData.access$1302(localMessageItemData, paramAnonymousParcel.readString());
      MessageItemData.access$1402(localMessageItemData, paramAnonymousParcel.readString());
      ArrayList localArrayList = new ArrayList();
      paramAnonymousParcel.readList(localArrayList, MessageButtonBody.class.getClassLoader());
      localMessageItemData.setMessageButtonBodies((ArrayList)localArrayList);
      return localMessageItemData;
    }

    public MessageItemData[] newArray(int paramAnonymousInt)
    {
      return new MessageItemData[paramAnonymousInt];
    }
  };
  private static final String TAG = MessageItemData.class.getSimpleName();
  private MessageActionType mActionType = null;
  private String mArgs = null;
  private String mContent = null;
  private String mDate = null;
  private String mDialogWay = "";
  private String mDialogway_args = "";
  private String mExt = "";
  private List<MessageButtonBody> mMessageButtonBodies = null;
  private String mMessageId = null;
  private MessageType mMessageType = null;
  private String mMsgType = "";
  private String mPageUrl = null;
  private SecondaryMessageType mSecondaryMessageType = null;
  private String mTitle = null;
  private boolean mUnreadFlag = false;

  private MessageItemData()
  {
  }

  public MessageItemData(String paramString1, String paramString2, String paramString3, boolean paramBoolean, MessageType paramMessageType, SecondaryMessageType paramSecondaryMessageType, MessageActionType paramMessageActionType, String paramString4)
  {
    this.mTitle = paramString1;
    this.mContent = paramString2;
    this.mDate = paramString3;
    this.mMessageType = paramMessageType;
    this.mSecondaryMessageType = paramSecondaryMessageType;
    this.mActionType = paramMessageActionType;
    this.mUnreadFlag = paramBoolean;
    this.mMessageId = paramString4;
  }

  public int describeContents()
  {
    return 0;
  }

  public String getActionArgs()
  {
    return this.mArgs;
  }

  public MessageActionType getActionType()
  {
    return this.mActionType;
  }

  public String getContent()
  {
    return this.mContent;
  }

  public String getDate()
  {
    return this.mDate;
  }

  public String getDialogway()
  {
    return this.mDialogWay;
  }

  public String getDialogway_args()
  {
    return this.mDialogway_args;
  }

  public String getExt()
  {
    return this.mExt;
  }

  public List<MessageButtonBody> getMessageButtonBodies()
  {
    return this.mMessageButtonBodies;
  }

  public String getMessageId()
  {
    return this.mMessageId;
  }

  public MessageType getMessageType()
  {
    return this.mMessageType;
  }

  public String getMsgType()
  {
    return this.mMsgType;
  }

  public String getPageUrl()
  {
    return this.mPageUrl;
  }

  public SecondaryMessageType getSecondaryMessageType()
  {
    return this.mSecondaryMessageType;
  }

  public String getTitle()
  {
    return this.mTitle;
  }

  public boolean getUnreadFlag()
  {
    return this.mUnreadFlag;
  }

  public void setActionArgs(String paramString)
  {
    this.mArgs = paramString;
    Logger.i(TAG, "设置播放影片参数为" + this.mArgs);
  }

  public void setActionType(MessageActionType paramMessageActionType)
  {
    this.mActionType = paramMessageActionType;
  }

  public void setDialogway(String paramString)
  {
    this.mDialogWay = paramString;
  }

  public void setDialogway_args(String paramString)
  {
    this.mDialogway_args = paramString;
  }

  public void setExt(String paramString)
  {
    this.mExt = paramString;
  }

  public void setMessageButtonBodies(ArrayList<MessageButtonBody> paramArrayList)
  {
    this.mMessageButtonBodies = paramArrayList;
  }

  public void setMessageType(MessageType paramMessageType)
  {
    this.mMessageType = paramMessageType;
  }

  public void setMsgType(String paramString)
  {
    this.mMsgType = paramString;
  }

  public void setPageUrl(String paramString)
  {
    Logger.i(TAG, "设置浏览页链接为" + paramString);
    this.mPageUrl = paramString;
  }

  public String toString()
  {
    return "message id = " + this.mMessageId + " message title = " + this.mTitle + " date = " + this.mDate + " message content = " + this.mContent + " action = " + this.mActionType + " message type = " + this.mMessageType + " message secondary type = " + this.mSecondaryMessageType + " unread flag = " + this.mUnreadFlag + " action args = " + this.mArgs + " page url = " + this.mPageUrl + " ext=" + this.mExt + " messageType=" + this.mMessageType + " dialogway=" + this.mDialogWay + " dialogway_args=" + this.mDialogway_args + " messageButtonBodies=" + this.mMessageButtonBodies;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    boolean[] arrayOfBoolean = new boolean[1];
    arrayOfBoolean[0] = this.mUnreadFlag;
    paramParcel.writeString(this.mTitle);
    paramParcel.writeString(this.mContent);
    paramParcel.writeString(this.mDate);
    paramParcel.writeValue(this.mMessageType);
    paramParcel.writeValue(this.mActionType);
    paramParcel.writeBooleanArray(arrayOfBoolean);
    paramParcel.writeString(this.mMessageId);
    paramParcel.writeValue(this.mSecondaryMessageType);
    paramParcel.writeString(this.mArgs);
    paramParcel.writeString(this.mPageUrl);
    paramParcel.writeString(this.mExt);
    paramParcel.writeString(this.mMsgType);
    paramParcel.writeString(this.mDialogWay);
    paramParcel.writeString(this.mDialogway_args);
    paramParcel.writeList(this.mMessageButtonBodies);
  }

  public static enum MessageActionType
  {
    static
    {
      READ_MESSAGE = new MessageActionType("READ_MESSAGE", 2);
      DELETE_MESSAGE = new MessageActionType("DELETE_MESSAGE", 3);
      OPEN_PAGE = new MessageActionType("OPEN_PAGE", 4);
      SPECIAL_PAGE = new MessageActionType("SPECIAL_PAGE", 5);
      RESERVE_PAGE = new MessageActionType("RESERVE_PAGE", 6);
      INVALID = new MessageActionType("INVALID", 7);
      MessageActionType[] arrayOfMessageActionType = new MessageActionType[8];
      arrayOfMessageActionType[0] = PLAY_VIDEO;
      arrayOfMessageActionType[1] = VIEW_DETAIL;
      arrayOfMessageActionType[2] = READ_MESSAGE;
      arrayOfMessageActionType[3] = DELETE_MESSAGE;
      arrayOfMessageActionType[4] = OPEN_PAGE;
      arrayOfMessageActionType[5] = SPECIAL_PAGE;
      arrayOfMessageActionType[6] = RESERVE_PAGE;
      arrayOfMessageActionType[7] = INVALID;
    }
  }

  public static enum MessageType
  {
    static
    {
      MessageType[] arrayOfMessageType = new MessageType[2];
      arrayOfMessageType[0] = MY_MESSAGE;
      arrayOfMessageType[1] = SYSTEM_MESSAGE;
    }
  }

  public static enum SecondaryMessageType
  {
    static
    {
      PRIVATE_TOPOIC_MESSAGE = new SecondaryMessageType("PRIVATE_TOPOIC_MESSAGE", 1);
      ADMIN_TOPOIC_MESSAGE = new SecondaryMessageType("ADMIN_TOPOIC_MESSAGE", 2);
      RESERVE_TOPIC_MESSAGE = new SecondaryMessageType("RESERVE_TOPIC_MESSAGE", 3);
      INVALID = new SecondaryMessageType("INVALID", 4);
      SecondaryMessageType[] arrayOfSecondaryMessageType = new SecondaryMessageType[5];
      arrayOfSecondaryMessageType[0] = PUBLIC_TOPIC_MESSAAGE;
      arrayOfSecondaryMessageType[1] = PRIVATE_TOPOIC_MESSAGE;
      arrayOfSecondaryMessageType[2] = ADMIN_TOPOIC_MESSAGE;
      arrayOfSecondaryMessageType[3] = RESERVE_TOPIC_MESSAGE;
      arrayOfSecondaryMessageType[4] = INVALID;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.msgsys.data.MessageItemData
 * JD-Core Version:    0.6.2
 */