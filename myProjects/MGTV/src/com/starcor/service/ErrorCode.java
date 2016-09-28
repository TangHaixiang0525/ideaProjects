package com.starcor.service;

import java.io.Serializable;

public class ErrorCode
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private int errorCode;
  private String errorMsg;

  public ErrorCode(int paramInt, String paramString)
  {
    this.errorCode = paramInt;
    this.errorMsg = paramString;
  }

  public int getErrorCode()
  {
    return this.errorCode;
  }

  public String getErrorMsg()
  {
    return this.errorMsg;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.service.ErrorCode
 * JD-Core Version:    0.6.2
 */