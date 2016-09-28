package com.mstar.android.tvapi.atv;

import com.mstar.android.tvapi.atv.vo.EnumAtvManualTuneMode;
import com.mstar.android.tvapi.atv.vo.EnumAutoScanState;
import com.mstar.android.tvapi.atv.vo.EnumGetProgramCtrl;
import com.mstar.android.tvapi.atv.vo.EnumGetProgramInfo;
import com.mstar.android.tvapi.atv.vo.EnumSetProgramCtrl;
import com.mstar.android.tvapi.atv.vo.EnumSetProgramInfo;
import com.mstar.android.tvapi.common.ScanManager;
import com.mstar.android.tvapi.common.exception.TvCommonException;
import com.mstar.android.tvapi.common.vo.AtvProgramData;

public abstract interface AtvScanManager extends ScanManager
{
  public abstract int getAtvProgramInfo(EnumGetProgramInfo paramEnumGetProgramInfo, int paramInt)
    throws TvCommonException;

  public abstract AtvProgramData getAtvProgramMiscInfo(int paramInt)
    throws TvCommonException;

  public abstract String getAtvStationName(int paramInt)
    throws TvCommonException;

  public abstract int getCurrentFrequency()
    throws TvCommonException;

  public abstract int getProgramControl(EnumGetProgramCtrl paramEnumGetProgramCtrl, int paramInt1, int paramInt2)
    throws TvCommonException;

  public abstract boolean setAtvProgramInfo(EnumSetProgramInfo paramEnumSetProgramInfo, int paramInt1, int paramInt2)
    throws TvCommonException;

  public abstract boolean setAtvProgramMiscInfo(int paramInt, AtvProgramData paramAtvProgramData)
    throws TvCommonException;

  public abstract boolean setAtvStationName(int paramInt, String paramString)
    throws TvCommonException;

  public abstract boolean setAutoTuningEnd()
    throws TvCommonException;

  public abstract boolean setAutoTuningPause()
    throws TvCommonException;

  public abstract boolean setAutoTuningResume()
    throws TvCommonException;

  public abstract boolean setAutoTuningStart(int paramInt1, int paramInt2, int paramInt3, EnumAutoScanState paramEnumAutoScanState)
    throws TvCommonException;

  public abstract void setDebugMode(boolean paramBoolean)
    throws TvCommonException;

  public abstract void setManualTuningEnd()
    throws TvCommonException;

  public abstract boolean setManualTuningStart(int paramInt1, int paramInt2, EnumAtvManualTuneMode paramEnumAtvManualTuneMode)
    throws TvCommonException;

  public abstract boolean setProgramControl(EnumSetProgramCtrl paramEnumSetProgramCtrl, int paramInt1, int paramInt2)
    throws TvCommonException;
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.tvapi.atv.AtvScanManager
 * JD-Core Version:    0.6.2
 */