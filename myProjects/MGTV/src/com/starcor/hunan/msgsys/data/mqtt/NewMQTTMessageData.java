package com.starcor.hunan.msgsys.data.mqtt;

import org.json.JSONObject;

public class NewMQTTMessageData
{
  private JSONObject mData = null;
  private MessageType mType = null;

  public NewMQTTMessageData(MessageType paramMessageType, JSONObject paramJSONObject)
  {
    this.mType = paramMessageType;
    this.mData = paramJSONObject;
  }

  public JSONObject getMessageData()
  {
    return this.mData;
  }

  public MessageType getType()
  {
    return this.mType;
  }

  public String toString()
  {
    return "消息类型" + this.mType + " 数据" + this.mData.toString();
  }

  public static enum MessageType
  {
    static
    {
      PRIVATE_TOPIC_MESSAGE = new MessageType("PRIVATE_TOPIC_MESSAGE", 1);
      ADMIN_TOPIC_MESSAGE = new MessageType("ADMIN_TOPIC_MESSAGE", 2);
      HTTP_POST_MESSAGE = new MessageType("HTTP_POST_MESSAGE", 3);
      MessageType[] arrayOfMessageType = new MessageType[4];
      arrayOfMessageType[0] = PUBLIC_TOPIC_MESSAGE;
      arrayOfMessageType[1] = PRIVATE_TOPIC_MESSAGE;
      arrayOfMessageType[2] = ADMIN_TOPIC_MESSAGE;
      arrayOfMessageType[3] = HTTP_POST_MESSAGE;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.msgsys.data.mqtt.NewMQTTMessageData
 * JD-Core Version:    0.6.2
 */