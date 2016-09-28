package com.starcor.hunan.msgsys.data;

public class TopicMessageData
{
  protected TopicMessageRawBody mMessage = null;
  protected String mMessage_id = "";
  protected String mPublish_time = "";
  protected String mSubClassTag = "";

  public TopicMessageRawBody getMessage()
  {
    return this.mMessage;
  }

  public String getMessage_id()
  {
    return this.mMessage_id;
  }

  public String getPublish_time()
  {
    return this.mPublish_time;
  }

  public String getSubClassTag()
  {
    return this.mSubClassTag;
  }

  public void setMessage(TopicMessageRawBody paramTopicMessageRawBody)
  {
    this.mMessage = paramTopicMessageRawBody;
  }

  public void setMessage_id(String paramString)
  {
    this.mMessage_id = paramString;
  }

  public void setPublish_time(String paramString)
  {
    this.mPublish_time = paramString;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.msgsys.data.TopicMessageData
 * JD-Core Version:    0.6.2
 */