package com.mstar.android.tvapi.dtv.dvb;

import com.mstar.android.tvapi.common.exception.TvCommonException;
import com.mstar.android.tvapi.common.vo.DtvProgramSignalInfo;
import com.mstar.android.tvapi.common.vo.TvOsType.EnumCountry;
import com.mstar.android.tvapi.common.vo.TvOsType.EnumTimeZone;
import com.mstar.android.tvapi.dtv.common.DtvPlayer;
import com.mstar.android.tvapi.dtv.dvb.vo.DvbMuxInfo;
import com.mstar.android.tvapi.dtv.vo.DtvDemodType;
import com.mstar.android.tvapi.dtv.vo.DtvDemodVersion;
import com.mstar.android.tvapi.dtv.vo.EnumParentalRating;

public abstract interface DvbPlayer extends DtvPlayer
{
  public abstract void disableAutoClock()
    throws TvCommonException;

  public abstract void enableAutoClock()
    throws TvCommonException;

  public abstract int getAntennaType()
    throws TvCommonException;

  public abstract String getCountryCode()
    throws TvCommonException;

  public abstract DvbMuxInfo getCurrentMuxInfo()
    throws TvCommonException;

  public abstract DtvProgramSignalInfo getCurrentSignalInformation()
    throws TvCommonException;

  public abstract DtvDemodVersion getDTVDemodVersion(DtvDemodType paramDtvDemodType)
    throws TvCommonException;

  public abstract int getDtvRouteCount()
    throws TvCommonException;

  public abstract String getLanguageCode()
    throws TvCommonException;

  public abstract DvbMuxInfo getMuxInfoByProgramNumber(int paramInt, short paramShort)
    throws TvCommonException;

  public abstract boolean setCountry(TvOsType.EnumCountry paramEnumCountry)
    throws TvCommonException;

  public abstract void setDtvRoute(short paramShort)
    throws TvCommonException;

  public abstract void setFavoriteRegion(int paramInt)
    throws TvCommonException;

  public abstract void setParental(EnumParentalRating paramEnumParentalRating)
    throws TvCommonException;

  public abstract void setTimeZone(TvOsType.EnumTimeZone paramEnumTimeZone)
    throws TvCommonException;

  public abstract boolean switchDtvRoute(short paramShort)
    throws TvCommonException;

  public abstract boolean unlockChannel()
    throws TvCommonException;
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.tvapi.dtv.dvb.DvbPlayer
 * JD-Core Version:    0.6.2
 */