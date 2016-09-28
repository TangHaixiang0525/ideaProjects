package com.mstar.android.tvapi.dtv.common;

import com.mstar.android.tvapi.common.TvManager;
import com.mstar.android.tvapi.common.exception.TvCommonException;
import com.mstar.android.tvapi.dtv.atsc.AtscPlayer;
import com.mstar.android.tvapi.dtv.dvb.DvbPlayer;
import com.mstar.android.tvapi.dtv.dvb.dvbc.DvbcScanManager;
import com.mstar.android.tvapi.dtv.dvb.dvbt.DvbtScanManager;

public final class DtvManager extends TvManager
{
  public static AtscPlayer getAtscPlayerManager()
  {
    throw new RuntimeException("stub");
  }

  public static CiManager getCiManager()
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public static DtvScanManager getDtvScanManager()
  {
    throw new RuntimeException("stub");
  }

  public static DvbPlayer getDvbPlayerManager()
  {
    throw new RuntimeException("stub");
  }

  public static DvbcScanManager getDvbcScanManager()
  {
    throw new RuntimeException("stub");
  }

  public static DvbtScanManager getDvbtScanManager()
  {
    throw new RuntimeException("stub");
  }

  public static EpgManager getEpgManager()
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public static OadManager getOadManager()
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public static SubtitleManager getSubtitleManager()
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.tvapi.dtv.common.DtvManager
 * JD-Core Version:    0.6.2
 */