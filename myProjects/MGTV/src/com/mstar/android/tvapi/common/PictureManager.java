package com.mstar.android.tvapi.common;

import com.mstar.android.tvapi.common.exception.TvCommonException;
import com.mstar.android.tvapi.common.vo.ColorTemperature;
import com.mstar.android.tvapi.common.vo.EnumLocalDimmingMode;
import com.mstar.android.tvapi.common.vo.EnumMfcMode;
import com.mstar.android.tvapi.common.vo.EnumMfcOsdWindow;
import com.mstar.android.tvapi.common.vo.EnumScalerWindow;
import com.mstar.android.tvapi.common.vo.EnumVideoArcType;
import com.mstar.android.tvapi.common.vo.Film.EnumFilm;
import com.mstar.android.tvapi.common.vo.GetPixelRgbStage.EnumGetPixelRgbStage;
import com.mstar.android.tvapi.common.vo.MpegNoiseReduction.EnumMpegNoiseReduction;
import com.mstar.android.tvapi.common.vo.MweType.EnumMweType;
import com.mstar.android.tvapi.common.vo.NoiseReduction.EnumNoiseReduction;
import com.mstar.android.tvapi.common.vo.PanelProperty;
import com.mstar.android.tvapi.common.vo.Rgb_Data;
import com.mstar.android.tvapi.common.vo.ScreenPixelInfo;
import com.mstar.android.tvapi.common.vo.SetLocationType.EnumSetLocationType;
import com.mstar.android.tvapi.common.vo.VideoWindowType;

public final class PictureManager
{
  protected static PictureManager getInstance()
  {
    throw new RuntimeException("stub");
  }

  public final native boolean Is4K2KMode()
    throws TvCommonException;

  public native boolean autoHDMIColorRange()
    throws TvCommonException;

  public final boolean disableAllOsdWindow()
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public native void disableBacklight()
    throws TvCommonException;

  public final native void disableDlc()
    throws TvCommonException;

  public final boolean disableOsdWindow(EnumMfcOsdWindow paramEnumMfcOsdWindow)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final native void disableOverScan()
    throws TvCommonException;

  public native void enableBacklight()
    throws TvCommonException;

  public final native void enableDlc()
    throws TvCommonException;

  public final native void enableOverScan()
    throws TvCommonException;

  public native boolean enter4K2KMode(boolean paramBoolean)
    throws TvCommonException;

  protected void finalize()
    throws Throwable
  {
    throw new RuntimeException("stub");
  }

  public native boolean forceFreerun(boolean paramBoolean1, boolean paramBoolean2)
    throws TvCommonException;

  public final native boolean freezeImage()
    throws TvCommonException;

  public final native int getBacklight()
    throws TvCommonException;

  public final native int getBacklightMaxValue()
    throws TvCommonException;

  public final native int getBacklightMinValue()
    throws TvCommonException;

  public native int getCustomerPqRuleNumber()
    throws TvCommonException;

  public final MweType.EnumMweType getDemoMode()
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public native short getDlcAverageLuma()
    throws TvCommonException;

  public native short getDlcHistogramMax()
    throws TvCommonException;

  public native short getDlcHistogramMin()
    throws TvCommonException;

  public native int[] getDlcLumArray(int paramInt)
    throws TvCommonException;

  public native int getDlcLumAverageTemporary()
    throws TvCommonException;

  public native int getDlcLumTotalCount()
    throws TvCommonException;

  public final native int[] getDynamicContrastCurve()
    throws TvCommonException;

  public final native PanelProperty getPanelWidthHeight()
    throws TvCommonException;

  public ScreenPixelInfo getPixelInfo(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public Rgb_Data getPixelRgb(GetPixelRgbStage.EnumGetPixelRgbStage paramEnumGetPixelRgbStage, short paramShort1, short paramShort2, EnumScalerWindow paramEnumScalerWindow)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final int getReproduceRate()
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final byte getResolution()
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public native int getStatusNumberByCustomerPqRule(int paramInt)
    throws TvCommonException;

  public final native boolean isImageFreezed()
    throws TvCommonException;

  public final native boolean isOverscanEnabled()
    throws TvCommonException;

  public native boolean moveWindow()
    throws TvCommonException;

  protected void release()
    throws Throwable
  {
    throw new RuntimeException("stub");
  }

  public final native boolean scaleWindow()
    throws TvCommonException;

  public boolean selectWindow(EnumScalerWindow paramEnumScalerWindow)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final void setAspectRatio(EnumVideoArcType paramEnumVideoArcType)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final native void setBacklight(int paramInt)
    throws TvCommonException;

  public native void setColorRange(boolean paramBoolean)
    throws TvCommonException;

  public final native void setColorTemperature(ColorTemperature paramColorTemperature)
    throws TvCommonException;

  public final native void setCropWindow(VideoWindowType paramVideoWindowType)
    throws TvCommonException;

  public native void setDebugMode(boolean paramBoolean)
    throws TvCommonException;

  public void setDemoMode(MweType.EnumMweType paramEnumMweType)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final native void setDisplayWindow(VideoWindowType paramVideoWindowType)
    throws TvCommonException;

  public final native void setDynamicContrastCurve(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[] paramArrayOfInt3)
    throws TvCommonException;

  public void setFilm(Film.EnumFilm paramEnumFilm)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public native boolean setLocalDimmingBrightLevel(short paramShort)
    throws TvCommonException;

  public boolean setLocalDimmingMode(EnumLocalDimmingMode paramEnumLocalDimmingMode)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final void setMfc(EnumMfcMode paramEnumMfcMode)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final boolean setMpegNoiseReduction(MpegNoiseReduction.EnumMpegNoiseReduction paramEnumMpegNoiseReduction)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final boolean setNoiseReduction(NoiseReduction.EnumNoiseReduction paramEnumNoiseReduction)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final boolean setOsdWindow(EnumMfcOsdWindow paramEnumMfcOsdWindow, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final native void setOutputPattern(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3)
    throws TvCommonException;

  public final native void setOverscan(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws TvCommonException;

  public final void setPictureModeBrightness(SetLocationType.EnumSetLocationType paramEnumSetLocationType, int paramInt)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final native void setPictureModeBrightness(short paramShort)
    throws TvCommonException;

  public final native void setPictureModeColor(short paramShort)
    throws TvCommonException;

  public final native void setPictureModeContrast(short paramShort)
    throws TvCommonException;

  public final native void setPictureModeSharpness(short paramShort)
    throws TvCommonException;

  public final native void setPictureModeTint(short paramShort)
    throws TvCommonException;

  public void setReproduceRate(int paramInt)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public void setResolution(byte paramByte)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public native boolean setStatusByCustomerPqRule(int paramInt1, int paramInt2)
    throws TvCommonException;

  public native boolean setSwingLevel(short paramShort)
    throws TvCommonException;

  public native boolean setTurnOffLocalDimmingBacklight(boolean paramBoolean)
    throws TvCommonException;

  public native boolean setUltraClear(boolean paramBoolean)
    throws TvCommonException;

  public final native void setWindowInvisible()
    throws TvCommonException;

  public final native void setWindowVisible()
    throws TvCommonException;

  public native boolean switchDlcCurve(short paramShort)
    throws TvCommonException;

  public final native boolean unFreezeImage()
    throws TvCommonException;
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.tvapi.common.PictureManager
 * JD-Core Version:    0.6.2
 */