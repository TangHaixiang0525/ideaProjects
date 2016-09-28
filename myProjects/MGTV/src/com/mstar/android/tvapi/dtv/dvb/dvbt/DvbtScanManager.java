package com.mstar.android.tvapi.dtv.dvb.dvbt;

import com.mstar.android.tvapi.common.exception.TvCommonException;
import com.mstar.android.tvapi.dtv.common.DtvScanManager;

public abstract interface DvbtScanManager extends DtvScanManager
{
  public abstract boolean resolveConflictLcn()
    throws TvCommonException;

  public abstract boolean setRegion(String paramString, short paramShort1, short paramShort2, int paramInt)
    throws TvCommonException;
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.tvapi.dtv.dvb.dvbt.DvbtScanManager
 * JD-Core Version:    0.6.2
 */