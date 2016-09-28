package com.starcor.message;

import java.util.concurrent.atomic.AtomicInteger;

public class Message
{
  public static final int EXCUTE_MODE_ASYNC = 1;
  public static final int EXCUTE_MODE_SYNC = 2;
  private static AtomicInteger INDEX = new AtomicInteger();
  public int index = INDEX.getAndIncrement();
  private int mExcuteMode = 2;
  public Object mExtData;
  private int mFlags;
  private int mGroupId;
  private int mId = this.index;
  private Class<?> mSourceClass;
  private String mSourceName;
  public int what;

  public Message()
  {
  }

  public Message(Class<?> paramClass)
  {
    this();
    setSourceClass(paramClass);
    setSourceName(paramClass.getSimpleName());
  }

  public static boolean isInSameSeq(Message paramMessage1, Message paramMessage2)
  {
    if ((paramMessage1 == null) || (paramMessage2 == null) || (paramMessage1.mGroupId == 0));
    while (paramMessage1.mGroupId != paramMessage2.mGroupId)
      return false;
    return true;
  }

  public void addFlags(int paramInt)
  {
    this.mFlags = (paramInt | this.mFlags);
  }

  public int getExcuteMode()
  {
    return this.mExcuteMode;
  }

  public int getFlags()
  {
    return this.mFlags;
  }

  public int getGroupId()
  {
    return this.mGroupId;
  }

  public int getId()
  {
    return this.mId;
  }

  public Class getSourceClass()
  {
    return this.mSourceClass;
  }

  public String getSourceName()
  {
    return this.mSourceName;
  }

  public void setExcuteMode(int paramInt)
  {
    this.mExcuteMode = paramInt;
  }

  public void setFlags(int paramInt)
  {
    this.mFlags = paramInt;
  }

  public void setGroupId(int paramInt)
  {
    this.mGroupId = paramInt;
  }

  public void setId(int paramInt)
  {
    this.mId = paramInt;
  }

  public void setSourceClass(Class<?> paramClass)
  {
    this.mSourceClass = paramClass;
  }

  public void setSourceName(String paramString)
  {
    this.mSourceName = paramString;
  }

  public String toString()
  {
    Object[] arrayOfObject = new Object[8];
    arrayOfObject[0] = Integer.valueOf(this.mFlags);
    arrayOfObject[1] = this.mSourceName;
    arrayOfObject[2] = Integer.valueOf(this.mGroupId);
    arrayOfObject[3] = Integer.valueOf(this.mId);
    arrayOfObject[4] = Integer.valueOf(this.mExcuteMode);
    arrayOfObject[5] = this.mExtData;
    arrayOfObject[6] = Integer.valueOf(this.what);
    arrayOfObject[7] = Integer.valueOf(this.index);
    return String.format("the flag is: %s, \nthe sourceName is: %s, \nthe gourp id is: %s, \nthe id is: %s, \nthe excute is: %s, \nthe ext data is: %s, \nthe what is: %s,\n the index is: %s, \n", arrayOfObject);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.message.Message
 * JD-Core Version:    0.6.2
 */