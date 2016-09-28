package com.intertrust.wasabi;

import com.intertrust.wasabi.jni.ErrorCodeHelper;

public class ErrorCodeException extends Exception
{
  private static final long serialVersionUID = 5087550287280260960L;
  private int errorCode;

  protected ErrorCodeException(int paramInt)
  {
    super("Wasabi call failed with error " + Integer.toString(paramInt));
    this.errorCode = paramInt;
  }

  public static void checkResult(int paramInt)
    throws ErrorCodeException
  {
    if (paramInt != 0)
      throw new ErrorCodeException(paramInt);
  }

  public int getErrorCode()
  {
    return this.errorCode;
  }

  public ErrorCodeExplanation getExplanation()
  {
    return ErrorCodeHelper.explainErrorCode(this.errorCode);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.intertrust.wasabi.ErrorCodeException
 * JD-Core Version:    0.6.2
 */