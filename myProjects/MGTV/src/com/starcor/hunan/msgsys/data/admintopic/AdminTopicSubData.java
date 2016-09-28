package com.starcor.hunan.msgsys.data.admintopic;

public class AdminTopicSubData
{
  private int mDetail;
  private int mMessage;
  private String[] mMessage_ids = null;
  private int mNext_connect_time = 0;
  private int mPage;
  private int mTopic;
  private int mVideo;
  private String mWhen = null;

  public int getDetail()
  {
    return this.mDetail;
  }

  public int getMessage()
  {
    return this.mMessage;
  }

  public String[] getMessage_ids()
  {
    return this.mMessage_ids;
  }

  public int getNext_connect_time()
  {
    return this.mNext_connect_time;
  }

  public int getPage()
  {
    return this.mPage;
  }

  public int getTopic()
  {
    return this.mTopic;
  }

  public int getVideo()
  {
    return this.mVideo;
  }

  public String getWhen()
  {
    return this.mWhen;
  }

  public void setDetail(int paramInt)
  {
    this.mDetail = paramInt;
  }

  public void setMessage(int paramInt)
  {
    this.mMessage = paramInt;
  }

  public void setMessage_ids(String[] paramArrayOfString)
  {
    this.mMessage_ids = paramArrayOfString;
  }

  public void setNext_connect_time(int paramInt)
  {
    this.mNext_connect_time = paramInt;
  }

  public void setPage(int paramInt)
  {
    this.mPage = paramInt;
  }

  public void setTopic(int paramInt)
  {
    this.mTopic = paramInt;
  }

  public void setVideo(int paramInt)
  {
    this.mVideo = paramInt;
  }

  public void setWhen(String paramString)
  {
    this.mWhen = paramString;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.msgsys.data.admintopic.AdminTopicSubData
 * JD-Core Version:    0.6.2
 */