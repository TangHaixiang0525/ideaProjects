package com.starcor.hunan.msgsys.data;

public class LiveTopicMessageData
  implements Cloneable
{
  public Message message;
  public String message_id = "";
  public String publish_time = "";

  public LiveTopicMessageData clone()
  {
    LiveTopicMessageData localLiveTopicMessageData = null;
    try
    {
      localLiveTopicMessageData = (LiveTopicMessageData)super.clone();
      localLiveTopicMessageData.message = this.message.clone();
      return localLiveTopicMessageData;
    }
    catch (CloneNotSupportedException localCloneNotSupportedException)
    {
      localCloneNotSupportedException.printStackTrace();
    }
    return localLiveTopicMessageData;
  }

  public String toString()
  {
    return "LiveTopicMessageData{publish_time='" + this.publish_time + '\'' + ", message_id='" + this.message_id + '\'' + ", message=" + this.message + '}';
  }

  public static class Message
    implements Cloneable
  {
    public String alter;
    public String body;
    public String dialogway;
    public String dialogway_args;
    public String ext;
    public String launch_image;
    public String message_type;
    public long show_second;
    public String title;

    public Message clone()
    {
      try
      {
        Message localMessage = (Message)super.clone();
        return localMessage;
      }
      catch (CloneNotSupportedException localCloneNotSupportedException)
      {
        localCloneNotSupportedException.printStackTrace();
      }
      return null;
    }

    public String toString()
    {
      return "Message{ext='" + this.ext + '\'' + ", title='" + this.title + '\'' + ", message_type='" + this.message_type + '\'' + ", launch_image='" + this.launch_image + '\'' + ", alter='" + this.alter + '\'' + ", dialogway='" + this.dialogway + '\'' + ", dialogway_args='" + this.dialogway_args + '\'' + ", body='" + this.body + '\'' + ", show_second='" + this.show_second + '\'' + '}';
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.msgsys.data.LiveTopicMessageData
 * JD-Core Version:    0.6.2
 */