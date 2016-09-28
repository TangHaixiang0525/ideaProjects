package com.mstar.android.tvapi.dtv.dvb.dvbc;

import com.mstar.android.tvapi.common.exception.TvCommonException;
import com.mstar.android.tvapi.common.vo.EnumCableOperator;
import com.mstar.android.tvapi.dtv.common.DtvScanManager;
import com.mstar.android.tvapi.dtv.dvb.dvbc.vo.EnumCabConstelType;

public abstract interface DvbcScanManager extends DtvScanManager
{
  public abstract int getDefaultHomingChannelFrequency()
    throws TvCommonException;

  public abstract int getDefaultNetworkId()
    throws TvCommonException;

  public abstract void setCableOperator(EnumCableOperator paramEnumCableOperator)
    throws TvCommonException;

  public abstract boolean setScanParam(short paramShort1, EnumCabConstelType paramEnumCabConstelType, int paramInt1, int paramInt2, short paramShort2, boolean paramBoolean)
    throws TvCommonException;

  public abstract boolean startQuickScan()
    throws TvCommonException;
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.tvapi.dtv.dvb.dvbc.DvbcScanManager
 * JD-Core Version:    0.6.2
 */