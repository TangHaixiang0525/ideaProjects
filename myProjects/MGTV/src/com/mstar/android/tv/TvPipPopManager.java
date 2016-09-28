package com.mstar.android.tv;

import com.mstar.android.tvapi.common.vo.EnumPipModes;
import com.mstar.android.tvapi.common.vo.EnumPipPosition;
import com.mstar.android.tvapi.common.vo.EnumPipReturn;
import com.mstar.android.tvapi.common.vo.EnumScalerWindow;
import com.mstar.android.tvapi.common.vo.TvOsType.EnumInputSource;
import com.mstar.android.tvapi.common.vo.VideoWindowType;

public class TvPipPopManager
{
  public static TvPipPopManager getInstance()
  {
    throw new RuntimeException("stub");
  }

  public boolean checkPipSupport(TvOsType.EnumInputSource paramEnumInputSource1, TvOsType.EnumInputSource paramEnumInputSource2)
  {
    throw new RuntimeException("stub");
  }

  public boolean checkPipSupportOnSubSrc(TvOsType.EnumInputSource paramEnumInputSource)
  {
    throw new RuntimeException("stub");
  }

  public boolean checkPopSupport(TvOsType.EnumInputSource paramEnumInputSource1, TvOsType.EnumInputSource paramEnumInputSource2)
  {
    throw new RuntimeException("stub");
  }

  public boolean checkTravelingModeSupport(TvOsType.EnumInputSource paramEnumInputSource1, TvOsType.EnumInputSource paramEnumInputSource2)
  {
    throw new RuntimeException("stub");
  }

  public boolean disable3dDualView()
  {
    throw new RuntimeException("stub");
  }

  public boolean disablePip()
  {
    throw new RuntimeException("stub");
  }

  public boolean disablePop()
  {
    throw new RuntimeException("stub");
  }

  public boolean disableTravelingMode()
  {
    throw new RuntimeException("stub");
  }

  public boolean enable3dDualView(TvOsType.EnumInputSource paramEnumInputSource1, TvOsType.EnumInputSource paramEnumInputSource2, VideoWindowType paramVideoWindowType1, VideoWindowType paramVideoWindowType2)
  {
    throw new RuntimeException("stub");
  }

  public EnumPipReturn enablePipMM(TvOsType.EnumInputSource paramEnumInputSource, VideoWindowType paramVideoWindowType)
  {
    throw new RuntimeException("stub");
  }

  public EnumPipReturn enablePipTV(TvOsType.EnumInputSource paramEnumInputSource1, TvOsType.EnumInputSource paramEnumInputSource2, VideoWindowType paramVideoWindowType)
  {
    throw new RuntimeException("stub");
  }

  public EnumPipReturn enablePopMM(TvOsType.EnumInputSource paramEnumInputSource)
  {
    throw new RuntimeException("stub");
  }

  public EnumPipReturn enablePopTV(TvOsType.EnumInputSource paramEnumInputSource1, TvOsType.EnumInputSource paramEnumInputSource2)
  {
    throw new RuntimeException("stub");
  }

  public EnumPipReturn enableTravelingModeMM(TvOsType.EnumInputSource paramEnumInputSource)
  {
    throw new RuntimeException("stub");
  }

  public EnumPipReturn enableTravelingModeTV(TvOsType.EnumInputSource paramEnumInputSource1, TvOsType.EnumInputSource paramEnumInputSource2)
  {
    throw new RuntimeException("stub");
  }

  public boolean getIsPipOn()
  {
    throw new RuntimeException("stub");
  }

  public int[] getMainWindowSourceList()
  {
    throw new RuntimeException("stub");
  }

  public EnumPipModes getPipMode()
  {
    throw new RuntimeException("stub");
  }

  public EnumPipPosition getPipPosition()
  {
    throw new RuntimeException("stub");
  }

  public int[] getSubWindowSourceList(boolean paramBoolean)
  {
    throw new RuntimeException("stub");
  }

  public boolean isPipModeEnabled()
  {
    throw new RuntimeException("stub");
  }

  public void setPipDisplayFocusWindow(EnumScalerWindow paramEnumScalerWindow)
  {
    throw new RuntimeException("stub");
  }

  public boolean setPipOnFlag(boolean paramBoolean)
  {
    throw new RuntimeException("stub");
  }

  public void setPipPosition(EnumPipPosition paramEnumPipPosition)
  {
    throw new RuntimeException("stub");
  }

  public boolean setPipSubwindow(VideoWindowType paramVideoWindowType)
  {
    throw new RuntimeException("stub");
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.tv.TvPipPopManager
 * JD-Core Version:    0.6.2
 */