package com.mstar.android.tvapi.common;

import com.mstar.android.tvapi.common.exception.TvCommonException;

public final class LogoManager
{
  protected static LogoManager getInstance()
  {
    throw new RuntimeException("stub");
  }

  protected void finalize()
    throws Throwable
  {
    throw new RuntimeException("stub");
  }

  public final native void hideBusyAnimation()
    throws TvCommonException;

  protected void release()
    throws Throwable
  {
    throw new RuntimeException("stub");
  }

  public final native void showBusyAnimation()
    throws TvCommonException;
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.tvapi.common.LogoManager
 * JD-Core Version:    0.6.2
 */