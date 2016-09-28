package com.mstar.android.tvapi.common;

import com.mstar.android.tvapi.common.exception.TvCommonException;

public abstract interface ScanManager
{
  public abstract boolean getSmartScanMode()
    throws TvCommonException;

  public abstract boolean isScanning()
    throws TvCommonException;

  public abstract void release()
    throws Throwable;

  public abstract void setSmartScanMode(boolean paramBoolean)
    throws TvCommonException;
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.tvapi.common.ScanManager
 * JD-Core Version:    0.6.2
 */