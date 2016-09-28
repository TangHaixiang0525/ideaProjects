package com.starcor.hunan.msgsys.data.privatetopic;

import com.starcor.hunan.msgsys.data.TopicMessageRawBody;

public class PrivateTopicMessageRawBody extends TopicMessageRawBody
{
  private static final String TAG = PrivateTopicMessageRawBody.class.getSimpleName();

  public String toString()
  {
    return TAG + "[" + "ext=" + this.mExt + " title=" + this.mTitle + " message_type=" + this.mMessage_type + " body=" + this.mBody + " alter=" + this.mAlter + " dialogway=" + this.mDialogway + " dialogway_args=" + this.mDialogway_args + " buttons=" + this.mMessageButtonBodies + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.msgsys.data.privatetopic.PrivateTopicMessageRawBody
 * JD-Core Version:    0.6.2
 */