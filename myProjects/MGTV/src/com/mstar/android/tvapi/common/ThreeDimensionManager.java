package com.mstar.android.tvapi.common;

import com.mstar.android.tvapi.common.exception.TvCommonException;
import com.mstar.android.tvapi.common.vo.Detect3dFormatParameter;
import com.mstar.android.tvapi.common.vo.Enum3dAspectRatioType;
import com.mstar.android.tvapi.common.vo.Enum3dItemType;
import com.mstar.android.tvapi.common.vo.Enum3dType;
import com.mstar.android.tvapi.common.vo.EnumScalerWindow;
import com.mstar.android.tvapi.common.vo.TvOsType.EnumInputSource;
import com.mstar.android.tvapi.common.vo.VideoWindowType;

public final class ThreeDimensionManager
{
  protected static ThreeDimensionManager getInstance()
  {
    throw new RuntimeException("stub");
  }

  public final Enum3dType detect3dFormat(EnumScalerWindow paramEnumScalerWindow)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public native boolean disable3dDualView()
    throws TvCommonException;

  public final native boolean disable3dLrSwitch()
    throws TvCommonException;

  public native void disableLow3dQuality()
    throws TvCommonException;

  public final boolean enable3d(Enum3dType paramEnum3dType)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public boolean enable3dDualView(TvOsType.EnumInputSource paramEnumInputSource1, TvOsType.EnumInputSource paramEnumInputSource2, VideoWindowType paramVideoWindowType1, VideoWindowType paramVideoWindowType2)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final native boolean enable3dLrSwitch()
    throws TvCommonException;

  public final boolean enable3dTo2d(Enum3dType paramEnum3dType)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public native void enableLow3dQuality()
    throws TvCommonException;

  protected void finalize()
    throws Throwable
  {
    throw new RuntimeException("stub");
  }

  public native boolean generateMvopTiming(int paramInt1, int paramInt2, int paramInt3)
    throws TvCommonException;

  public final Enum3dAspectRatioType get3dArc()
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final native boolean get3dFormatDetectFlag()
    throws TvCommonException;

  public final native int get3dGain()
    throws TvCommonException;

  public final native int get3dOffset()
    throws TvCommonException;

  public final Enum3dType getCurrent3dFormat()
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final native Detect3dFormatParameter getDetect3dFormatParameters()
    throws TvCommonException;

  public final native boolean is3dLrSwitched()
    throws TvCommonException;

  public final boolean query3dCapability(Enum3dItemType paramEnum3dItemType)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  protected void release()
    throws Throwable
  {
    throw new RuntimeException("stub");
  }

  public final boolean set3dArc(Enum3dAspectRatioType paramEnum3dAspectRatioType)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final native boolean set3dFormatDetectFlag(boolean paramBoolean)
    throws TvCommonException;

  public final native boolean set3dGain(int paramInt)
    throws TvCommonException;

  public final native boolean set3dOffset(int paramInt)
    throws TvCommonException;

  public native void setDebugMode(boolean paramBoolean);

  public final native boolean setDetect3dFormatParameters(Detect3dFormatParameter paramDetect3dFormatParameter)
    throws TvCommonException;
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.tvapi.common.ThreeDimensionManager
 * JD-Core Version:    0.6.2
 */