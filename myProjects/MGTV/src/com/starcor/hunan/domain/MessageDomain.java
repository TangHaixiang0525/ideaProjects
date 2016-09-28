package com.starcor.hunan.domain;

import java.io.Serializable;

public class MessageDomain
  implements Comparable<MessageDomain>, Serializable
{
  private static final long serialVersionUID = 1L;
  private boolean hasShow;
  private String messageDesc = "";
  private int messageId;
  private String messageName = "";
  private int messageType;
  private String msgId = "";
  public VideoDetail mvideoDetail = new VideoDetail();
  public VideoId mvideoId = new VideoId();
  private String videoId = "";
  private String videoType = "";

  public int compareTo(MessageDomain paramMessageDomain)
  {
    if (paramMessageDomain == null);
    return -1;
  }

  public boolean equals(Object paramObject)
  {
    return false;
  }

  public boolean getHasShow()
  {
    return this.hasShow;
  }

  public String getMessageDesc()
  {
    return this.messageDesc;
  }

  public int getMessageId()
  {
    return this.messageId;
  }

  public String getMessageName()
  {
    return this.messageName;
  }

  public int getMessageType()
  {
    return this.messageType;
  }

  public String getMsgId()
  {
    return this.msgId;
  }

  public String getVideoId()
  {
    return this.videoId;
  }

  public String getVideoType()
  {
    return this.videoType;
  }

  public void setHasShow(boolean paramBoolean)
  {
    this.hasShow = paramBoolean;
  }

  public void setMessageDesc(String paramString)
  {
    this.messageDesc = paramString;
  }

  public void setMessageId(int paramInt)
  {
    this.messageId = paramInt;
  }

  public void setMessageName(String paramString)
  {
    this.messageName = paramString;
  }

  public void setMessageType(int paramInt)
  {
    this.messageType = paramInt;
  }

  public void setMsgId(String paramString)
  {
    this.msgId = paramString;
  }

  public void setVideoId(String paramString)
  {
    this.videoId = paramString;
  }

  public void setVideoType(String paramString)
  {
    this.videoType = paramString;
  }

  public String toString()
  {
    return "MessageDomain [videoId=" + this.videoId + "messageType=" + this.messageType + ", videoType=" + this.videoType + ", messageId=" + this.messageId + ", messageName=" + this.messageName + ", messageDesc=" + this.messageDesc + ", hasShow=" + this.hasShow + "msgId=" + this.msgId + "]";
  }

  public class VideoDetail
    implements Serializable
  {
    public String assetId = "";
    public String assetsCategory = "";

    public VideoDetail()
    {
    }
  }

  public class VideoId
    implements Serializable
  {
    public String assetId = "";
    public String clipId = "";
    public String fileId = "";

    public VideoId()
    {
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.domain.MessageDomain
 * JD-Core Version:    0.6.2
 */