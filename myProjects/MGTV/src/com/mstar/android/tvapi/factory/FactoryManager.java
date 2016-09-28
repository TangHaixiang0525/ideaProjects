package com.mstar.android.tvapi.factory;

import com.mstar.android.tvapi.common.exception.TvCommonException;
import com.mstar.android.tvapi.common.vo.EnumColorTemperature;
import com.mstar.android.tvapi.common.vo.EnumScalerWindow;
import com.mstar.android.tvapi.common.vo.TvOsType.EnumInputSource;
import com.mstar.android.tvapi.factory.vo.DisplayResolutionType.EnumDisplayResolutionType;
import com.mstar.android.tvapi.factory.vo.EnumAcOnPowerOnMode;
import com.mstar.android.tvapi.factory.vo.EnumAdcSetIndexType;
import com.mstar.android.tvapi.factory.vo.EnumFwType;
import com.mstar.android.tvapi.factory.vo.EnumPqUpdateFile;
import com.mstar.android.tvapi.factory.vo.EnumScreenMute;
import com.mstar.android.tvapi.factory.vo.FactoryNsVdSet;
import com.mstar.android.tvapi.factory.vo.PictureModeValue;
import com.mstar.android.tvapi.factory.vo.PqlCalibrationData;
import com.mstar.android.tvapi.factory.vo.WbGainOffset;
import com.mstar.android.tvapi.factory.vo.WbGainOffsetEx;

public class FactoryManager
{
  protected static FactoryManager getInstance(Object paramObject)
  {
    throw new RuntimeException("stub");
  }

  public final native boolean autoAdc()
    throws TvCommonException;

  public final native void copySubColorDataToAllSource()
    throws TvCommonException;

  public final native void copyWhiteBalanceSettingToAllSource()
    throws TvCommonException;

  public final native boolean disablePVRRecordAll()
    throws TvCommonException;

  public final native boolean disableUart()
    throws TvCommonException;

  public final native boolean disableWdt()
    throws TvCommonException;

  public final native boolean enablePVRRecordAll()
    throws TvCommonException;

  public final native boolean enableUart()
    throws TvCommonException;

  public native boolean enableUartDebug()
    throws TvCommonException;

  public final native boolean enableWdt()
    throws TvCommonException;

  public void finalize()
    throws Throwable
  {
    throw new RuntimeException("stub");
  }

  public final PqlCalibrationData getAdcGainOffset(EnumScalerWindow paramEnumScalerWindow, EnumAdcSetIndexType paramEnumAdcSetIndexType)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final DisplayResolutionType.EnumDisplayResolutionType getDisplayResolution()
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public EnumAcOnPowerOnMode getEnvironmentPowerMode()
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public native short getEnvironmentPowerOnMusicVolume()
    throws TvCommonException;

  public int getFwVersion(EnumFwType paramEnumFwType)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final String getPQVersion(EnumScalerWindow paramEnumScalerWindow)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final native PictureModeValue getPictureModeValue()
    throws TvCommonException;

  public final native int getQmapCurrentTableIdx(short paramShort)
    throws TvCommonException;

  public final native String getQmapIpName(short paramShort)
    throws TvCommonException;

  public final native int getQmapIpNum()
    throws TvCommonException;

  public final native String getQmapTableName(short paramShort1, short paramShort2)
    throws TvCommonException;

  public final native int getQmapTableNum(short paramShort)
    throws TvCommonException;

  public short getResolutionMappingIndex(TvOsType.EnumInputSource paramEnumInputSource)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public native String getSoftwareVersion()
    throws TvCommonException;

  public final native boolean getUartEnv()
    throws TvCommonException;

  public final String getUpdatePqFilePath(EnumPqUpdateFile paramEnumPqUpdateFile)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final WbGainOffset getWbGainOffset(EnumColorTemperature paramEnumColorTemperature)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final WbGainOffsetEx getWbGainOffsetEx(EnumColorTemperature paramEnumColorTemperature, int paramInt)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final native boolean isAgingModeOn()
    throws TvCommonException;

  public final native boolean isPVRRecordAllOn()
    throws TvCommonException;

  public final native boolean isUartOn()
    throws TvCommonException;

  public final native boolean isWdtOn()
    throws TvCommonException;

  public final native void loadPqTable(int paramInt1, int paramInt2)
    throws TvCommonException;

  public final native String native_getPQVersion(int paramInt)
    throws TvCommonException;

  public native short[] readBytesFromI2C(int paramInt, short[] paramArrayOfShort, short paramShort)
    throws TvCommonException;

  public void release()
    throws Throwable
  {
    throw new RuntimeException("stub");
  }

  public final native boolean resetDisplayResolution()
    throws TvCommonException;

  public final native boolean restoreDbFromUsb()
    throws TvCommonException;

  public native void restoreFactoryAtvProgramTable(short paramShort)
    throws TvCommonException;

  public native void restoreFactoryDtvProgramTable(short paramShort)
    throws TvCommonException;

  public final void setAdcGainOffset(EnumScalerWindow paramEnumScalerWindow, EnumAdcSetIndexType paramEnumAdcSetIndexType, PqlCalibrationData paramPqlCalibrationData)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final native boolean setBrightness(short paramShort)
    throws TvCommonException;

  public final native boolean setContrast(short paramShort)
    throws TvCommonException;

  public native void setDebugMode(boolean paramBoolean)
    throws TvCommonException;

  public boolean setEnvironmentPowerMode(EnumAcOnPowerOnMode paramEnumAcOnPowerOnMode)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public native boolean setEnvironmentPowerOnMusicVolume(short paramShort)
    throws TvCommonException;

  public final native void setFactoryVdInitParameter(FactoryNsVdSet paramFactoryNsVdSet)
    throws TvCommonException;

  public final native void setFactoryVdParameter(FactoryNsVdSet paramFactoryNsVdSet)
    throws TvCommonException;

  public final native boolean setHue(short paramShort)
    throws TvCommonException;

  public native void setPQParameterViaUsbKey()
    throws TvCommonException;

  public final native boolean setSaturation(short paramShort)
    throws TvCommonException;

  public final native boolean setSharpness(short paramShort)
    throws TvCommonException;

  public final native void setUartEnv(boolean paramBoolean)
    throws TvCommonException;

  public final void setVideoTestPattern(EnumScreenMute paramEnumScreenMute)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final void setWbGainOffset(EnumColorTemperature paramEnumColorTemperature, short paramShort1, short paramShort2, short paramShort3, short paramShort4, short paramShort5, short paramShort6)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final void setWbGainOffsetEx(EnumColorTemperature paramEnumColorTemperature, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, TvOsType.EnumInputSource paramEnumInputSource)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public native void stopTvService()
    throws TvCommonException;

  public final native boolean storeDbToUsb()
    throws TvCommonException;

  public native boolean switchUart()
    throws TvCommonException;

  public final native void updatePqIniFiles()
    throws TvCommonException;

  public final native boolean updateSscParameter()
    throws TvCommonException;

  public native boolean writeBytesToI2C(int paramInt, short[] paramArrayOfShort1, short[] paramArrayOfShort2)
    throws TvCommonException;
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.tvapi.factory.FactoryManager
 * JD-Core Version:    0.6.2
 */