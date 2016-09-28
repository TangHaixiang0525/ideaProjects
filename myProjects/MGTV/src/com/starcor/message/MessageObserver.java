package com.starcor.message;

public abstract class MessageObserver
{
  private int mCurrentFlags;
  private int mFlags;

  public void addFlags(int paramInt)
  {
    this.mFlags = (paramInt | this.mFlags);
  }

  public void clear()
  {
    this.mCurrentFlags = 0;
  }

  public boolean containsFlags(int paramInt)
  {
    return (paramInt & this.mFlags) != 0;
  }

  public abstract void doNotify(Message paramMessage);

  public boolean equalsFlags(int paramInt)
  {
    return (paramInt & this.mFlags) == paramInt;
  }

  int getCommonFlags(int paramInt)
  {
    return paramInt & this.mFlags;
  }

  public int getCurFlags()
  {
    return this.mCurrentFlags;
  }

  public int getFlags()
  {
    return this.mFlags;
  }

  void setCurFlags(int paramInt)
  {
    this.mCurrentFlags = paramInt;
  }

  public void setFlags(int paramInt)
  {
    this.mFlags = paramInt;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.message.MessageObserver
 * JD-Core Version:    0.6.2
 */