package com.mstar.android.tvapi.dtv.common;

import android.text.format.Time;
import com.mstar.android.tvapi.common.exception.TvCommonException;
import com.mstar.android.tvapi.dtv.vo.EnumOadVersionType;

public final class OadManager
{
  protected static OadManager getInstance()
  {
    throw new RuntimeException("stub");
  }

  public static native boolean resetForOadUpgrade()
    throws TvCommonException;

  public static native boolean standbyForOadUpgrade()
    throws TvCommonException;

  protected void finalize()
    throws Throwable
  {
    throw new RuntimeException("stub");
  }

  protected Time getOadBroadcastEndTime()
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  protected Time getOadBroadcastStartTime()
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  protected final native short getOadDownloadProgress()
    throws TvCommonException;

  protected final native short getOadUpdatedServiceNumber()
    throws TvCommonException;

  protected final int getOadVersion(EnumOadVersionType paramEnumOadVersionType)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  protected final native void setOadOff()
    throws TvCommonException;

  protected final native void setOadOn()
    throws TvCommonException;

  protected final native void startAutoOadScan()
    throws TvCommonException;

  protected final native void startOad()
    throws TvCommonException;

  protected final native boolean startOadInStandby()
    throws TvCommonException;

  protected final native boolean startOadInStandy()
    throws TvCommonException;

  protected final native void stopOad(boolean paramBoolean)
    throws TvCommonException;
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.tvapi.dtv.common.OadManager
 * JD-Core Version:    0.6.2
 */