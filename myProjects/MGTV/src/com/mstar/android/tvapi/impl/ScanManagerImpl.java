package com.mstar.android.tvapi.impl;

import com.mstar.android.tvapi.atv.AtvScanManager;
import com.mstar.android.tvapi.atv.vo.EnumAtvManualTuneMode;
import com.mstar.android.tvapi.atv.vo.EnumAutoScanState;
import com.mstar.android.tvapi.atv.vo.EnumGetProgramCtrl;
import com.mstar.android.tvapi.atv.vo.EnumGetProgramInfo;
import com.mstar.android.tvapi.atv.vo.EnumSetProgramCtrl;
import com.mstar.android.tvapi.atv.vo.EnumSetProgramInfo;
import com.mstar.android.tvapi.common.exception.TvCommonException;
import com.mstar.android.tvapi.common.vo.AtvProgramData;
import com.mstar.android.tvapi.common.vo.EnumCableOperator;
import com.mstar.android.tvapi.common.vo.EnumMedium;
import com.mstar.android.tvapi.dtv.dvb.dvbc.DvbcScanManager;
import com.mstar.android.tvapi.dtv.dvb.dvbc.vo.EnumCabConstelType;
import com.mstar.android.tvapi.dtv.dvb.dvbt.DvbtScanManager;
import com.mstar.android.tvapi.dtv.dvb.vo.DvbTargetRegionInfo;
import com.mstar.android.tvapi.dtv.vo.DtvNetworkRegionInfo;
import com.mstar.android.tvapi.dtv.vo.EnumRfChannelBandwidth;

public class ScanManagerImpl
  implements AtvScanManager, DvbtScanManager, DvbcScanManager
{
  protected static ScanManagerImpl getInstance(Object paramObject)
  {
    throw new RuntimeException("stub");
  }

  public void finalize()
    throws Throwable
  {
    throw new RuntimeException("stub");
  }

  public int getAtvProgramInfo(EnumGetProgramInfo paramEnumGetProgramInfo, int paramInt)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final native AtvProgramData getAtvProgramMiscInfo(int paramInt)
    throws TvCommonException;

  public final native String getAtvStationName(int paramInt)
    throws TvCommonException;

  public final native int getCurrentFrequency()
    throws TvCommonException;

  public final native int getDefaultHomingChannelFrequency()
    throws TvCommonException;

  public final native int getDefaultNetworkId()
    throws TvCommonException;

  public EnumMedium getNtscAntenna()
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public int getProgramControl(EnumGetProgramCtrl paramEnumGetProgramCtrl, int paramInt1, int paramInt2)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final DvbTargetRegionInfo getRegionInfo()
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final native DtvNetworkRegionInfo getRegionNetworks()
    throws TvCommonException;

  public final native boolean getSmartScanMode()
    throws TvCommonException;

  public final native boolean isScanning()
    throws TvCommonException;

  public final native boolean pauseScan()
    throws TvCommonException;

  public void release()
    throws Throwable
  {
    throw new RuntimeException("stub");
  }

  public final native boolean resolveConflictLcn()
    throws TvCommonException;

  public final native boolean resumeScan()
    throws TvCommonException;

  public final boolean setAtvProgramInfo(EnumSetProgramInfo paramEnumSetProgramInfo, int paramInt1, int paramInt2)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final native boolean setAtvProgramMiscInfo(int paramInt, AtvProgramData paramAtvProgramData)
    throws TvCommonException;

  public final native boolean setAtvStationName(int paramInt, String paramString)
    throws TvCommonException;

  public final native boolean setAutoTuningEnd()
    throws TvCommonException;

  public final native boolean setAutoTuningPause()
    throws TvCommonException;

  public final native boolean setAutoTuningResume()
    throws TvCommonException;

  public final boolean setAutoTuningStart(int paramInt1, int paramInt2, int paramInt3, EnumAutoScanState paramEnumAutoScanState)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public boolean setBandwidth(EnumRfChannelBandwidth paramEnumRfChannelBandwidth)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public void setCableOperator(EnumCableOperator paramEnumCableOperator)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final native void setDebugMode(boolean paramBoolean)
    throws TvCommonException;

  public final native void setManualTuningEnd()
    throws TvCommonException;

  public final boolean setManualTuningStart(int paramInt1, int paramInt2, EnumAtvManualTuneMode paramEnumAtvManualTuneMode)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public boolean setNtscAntenna(EnumMedium paramEnumMedium)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public boolean setProgramControl(EnumSetProgramCtrl paramEnumSetProgramCtrl, int paramInt1, int paramInt2)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final native boolean setRegion(String paramString, short paramShort1, short paramShort2, int paramInt)
    throws TvCommonException;

  public boolean setScanParam(short paramShort1, EnumCabConstelType paramEnumCabConstelType, int paramInt1, int paramInt2, short paramShort2, boolean paramBoolean)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final native void setSmartScanMode(boolean paramBoolean)
    throws TvCommonException;

  public final native void startAutoScan()
    throws TvCommonException;

  public final native void startAutoUpdateScan()
    throws TvCommonException;

  public final native boolean startFullScan()
    throws TvCommonException;

  public final native boolean startManualScan()
    throws TvCommonException;

  public final native void startNtscDirectTune(int paramInt1, int paramInt2)
    throws TvCommonException;

  public final native boolean startQuickScan()
    throws TvCommonException;

  public final native void startStandbyScan()
    throws TvCommonException;

  public final native boolean stopScan()
    throws TvCommonException;
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.tvapi.impl.ScanManagerImpl
 * JD-Core Version:    0.6.2
 */