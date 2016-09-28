package com.starcor.core.report.enums;

public enum BufferEnum
{
  private int value = 0;

  static
  {
    BUFFER_COMMON = new BufferEnum("BUFFER_COMMON", 2, 2);
    BUFFER_DRAG_EXCEPTION = new BufferEnum("BUFFER_DRAG_EXCEPTION", 3, 4);
    BufferEnum[] arrayOfBufferEnum = new BufferEnum[4];
    arrayOfBufferEnum[0] = BUFFER_AUTH;
    arrayOfBufferEnum[1] = BUFFER_DRAG;
    arrayOfBufferEnum[2] = BUFFER_COMMON;
    arrayOfBufferEnum[3] = BUFFER_DRAG_EXCEPTION;
  }

  private BufferEnum(int paramInt)
  {
    this.value = paramInt;
  }

  public int getValue()
  {
    return this.value;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.report.enums.BufferEnum
 * JD-Core Version:    0.6.2
 */