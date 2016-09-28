package com.mstar.android.tvapi.common;

import com.mstar.android.tvapi.common.exception.TvCommonException;
import com.mstar.android.tvapi.common.vo.EnumPipMode;
import com.mstar.android.tvapi.common.vo.EnumPipModes;
import com.mstar.android.tvapi.common.vo.EnumPipReturn;
import com.mstar.android.tvapi.common.vo.EnumScalerWindow;
import com.mstar.android.tvapi.common.vo.TvOsType.EnumInputSource;
import com.mstar.android.tvapi.common.vo.TvOsType.EnumTravelingEngineType;
import com.mstar.android.tvapi.common.vo.VideoWindowType;

public final class PipManager
{
  protected static PipManager getInstance()
  {
    throw new RuntimeException("stub");
  }

  public final native void clearFrame()
    throws TvCommonException;

  public final boolean disablePip(EnumScalerWindow paramEnumScalerWindow)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final boolean disablePop(EnumScalerWindow paramEnumScalerWindow)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final EnumPipReturn enablePipMm(TvOsType.EnumInputSource paramEnumInputSource, VideoWindowType paramVideoWindowType)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final EnumPipReturn enablePipTv(TvOsType.EnumInputSource paramEnumInputSource1, TvOsType.EnumInputSource paramEnumInputSource2, VideoWindowType paramVideoWindowType)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final EnumPipReturn enablePopMm(TvOsType.EnumInputSource paramEnumInputSource)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final EnumPipReturn enablePopTv(TvOsType.EnumInputSource paramEnumInputSource1, TvOsType.EnumInputSource paramEnumInputSource2)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  protected void finalize()
    throws Throwable
  {
    throw new RuntimeException("stub");
  }

  public final EnumPipModes getPipMode()
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final boolean[] getPipSupportedSubInputSource(EnumPipMode paramEnumPipMode)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final native boolean isPipModeEnabled()
    throws TvCommonException;

  public final boolean isPipSupported(TvOsType.EnumInputSource paramEnumInputSource1, TvOsType.EnumInputSource paramEnumInputSource2)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final boolean isPopSupported(TvOsType.EnumInputSource paramEnumInputSource1, TvOsType.EnumInputSource paramEnumInputSource2)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final boolean isTravelingModeSupported(TvOsType.EnumInputSource paramEnumInputSource1, TvOsType.EnumInputSource paramEnumInputSource2)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final boolean isTravelingModeSupported(TvOsType.EnumInputSource paramEnumInputSource1, TvOsType.EnumInputSource paramEnumInputSource2, TvOsType.EnumTravelingEngineType paramEnumTravelingEngineType)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  protected void release()
    throws Throwable
  {
    throw new RuntimeException("stub");
  }

  public native void setDebugMode(boolean paramBoolean)
    throws TvCommonException;

  public final void setPipDisplayFocusWindow(EnumScalerWindow paramEnumScalerWindow)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final native boolean setPipSubWindow(VideoWindowType paramVideoWindowType)
    throws TvCommonException;
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.tvapi.common.PipManager
 * JD-Core Version:    0.6.2
 */