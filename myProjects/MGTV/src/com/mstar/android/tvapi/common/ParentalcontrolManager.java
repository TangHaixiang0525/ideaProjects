package com.mstar.android.tvapi.common;

public final class ParentalcontrolManager
{
  protected static ParentalcontrolManager getInstance()
  {
    throw new RuntimeException("stub");
  }

  public final native int GetParentalControlRating();

  public final native int GetParentalPassword();

  protected void finalize()
    throws Throwable
  {
    throw new RuntimeException("stub");
  }

  public final native boolean isSystemLock();

  protected void release()
    throws Throwable
  {
    throw new RuntimeException("stub");
  }

  public final native void setParentalControlRating(int paramInt);

  public final native void setParentalPassword(int paramInt);

  public final native void setSystemLock(boolean paramBoolean);
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.tvapi.common.ParentalcontrolManager
 * JD-Core Version:    0.6.2
 */