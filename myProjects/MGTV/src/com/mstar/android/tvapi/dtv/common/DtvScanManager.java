package com.mstar.android.tvapi.dtv.common;

import com.mstar.android.tvapi.common.ScanManager;
import com.mstar.android.tvapi.common.exception.TvCommonException;
import com.mstar.android.tvapi.dtv.dvb.vo.DvbTargetRegionInfo;
import com.mstar.android.tvapi.dtv.vo.DtvNetworkRegionInfo;
import com.mstar.android.tvapi.dtv.vo.EnumRfChannelBandwidth;

public abstract interface DtvScanManager extends ScanManager
{
  public abstract DvbTargetRegionInfo getRegionInfo()
    throws TvCommonException;

  public abstract DtvNetworkRegionInfo getRegionNetworks()
    throws TvCommonException;

  public abstract boolean pauseScan()
    throws TvCommonException;

  public abstract boolean resumeScan()
    throws TvCommonException;

  public abstract boolean setBandwidth(EnumRfChannelBandwidth paramEnumRfChannelBandwidth)
    throws TvCommonException;

  public abstract void startAutoScan()
    throws TvCommonException;

  public abstract void startAutoUpdateScan()
    throws TvCommonException;

  public abstract boolean startFullScan()
    throws TvCommonException;

  public abstract boolean startManualScan()
    throws TvCommonException;

  public abstract void startStandbyScan()
    throws TvCommonException;

  public abstract boolean stopScan()
    throws TvCommonException;
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.tvapi.dtv.common.DtvScanManager
 * JD-Core Version:    0.6.2
 */