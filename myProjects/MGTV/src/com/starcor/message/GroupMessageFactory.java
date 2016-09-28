package com.starcor.message;

public class GroupMessageFactory
{
  private static int Main_Id_Index = 1;
  private int mMainId;
  Class mSrc;
  private int mSubId = 1;

  public GroupMessageFactory()
  {
    int i = Main_Id_Index;
    Main_Id_Index = i + 1;
    this.mMainId = i;
  }

  public Message obtainGroupMessage()
  {
    Message localMessage = new Message();
    localMessage.setSourceClass(this.mSrc);
    localMessage.setSourceName(this.mSrc.getSimpleName());
    localMessage.setGroupId(this.mMainId);
    int i = this.mSubId;
    this.mSubId = (i + 1);
    localMessage.setId(i);
    return localMessage;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.message.GroupMessageFactory
 * JD-Core Version:    0.6.2
 */