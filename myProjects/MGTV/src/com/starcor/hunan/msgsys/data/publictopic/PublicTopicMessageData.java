package com.starcor.hunan.msgsys.data.publictopic;

import com.starcor.hunan.msgsys.data.TopicMessageData;

public class PublicTopicMessageData extends TopicMessageData
{
  private static final String TAG = PublicTopicMessageData.class.getSimpleName();

  public PublicTopicMessageData()
  {
    this.mSubClassTag = TAG;
  }

  public String toString()
  {
    return TAG + "[publish_time=" + this.mPublish_time + " message_id=" + this.mMessage_id + " rawBody=" + this.mMessage + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.msgsys.data.publictopic.PublicTopicMessageData
 * JD-Core Version:    0.6.2
 */