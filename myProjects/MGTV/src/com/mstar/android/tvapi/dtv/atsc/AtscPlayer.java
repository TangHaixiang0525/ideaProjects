package com.mstar.android.tvapi.dtv.atsc;

import com.mstar.android.tvapi.common.exception.TvCommonException;
import com.mstar.android.tvapi.common.vo.EnumAntennaType;
import com.mstar.android.tvapi.common.vo.TvOsType.EnumInputSource;
import com.mstar.android.tvapi.dtv.atsc.vo.AudioMuteType.EnumAudioMuteType;
import com.mstar.android.tvapi.dtv.atsc.vo.EnumCanadaEngRatingType;
import com.mstar.android.tvapi.dtv.atsc.vo.EnumCanadaFreRatingType;
import com.mstar.android.tvapi.dtv.atsc.vo.EnumUsaTvRatingType;
import com.mstar.android.tvapi.dtv.atsc.vo.Region5RatingInformation;
import com.mstar.android.tvapi.dtv.atsc.vo.UsaMpaaRatingType.EnumUsaMpaaRatingType;
import com.mstar.android.tvapi.dtv.common.DtvPlayer;

public abstract interface AtscPlayer extends DtvPlayer
{
  public abstract boolean enterPassToUnlockByUser(boolean paramBoolean)
    throws TvCommonException;

  public abstract boolean enterPassToUnlockUnratedByUser(boolean paramBoolean)
    throws TvCommonException;

  public abstract String getCurrentRatingInformation()
    throws TvCommonException;

  public abstract boolean getCurrentVChipBlockStatus()
    throws TvCommonException;

  public abstract Region5RatingInformation getRRTInformation()
    throws TvCommonException;

  public abstract void setAntennaType(EnumAntennaType paramEnumAntennaType)
    throws TvCommonException;

  public abstract boolean setAudioMute(AudioMuteType.EnumAudioMuteType paramEnumAudioMuteType, TvOsType.EnumInputSource paramEnumInputSource)
    throws TvCommonException;

  public abstract boolean setCanadaEngGuideline(EnumCanadaEngRatingType paramEnumCanadaEngRatingType)
    throws TvCommonException;

  public abstract boolean setCanadaFreGuideline(EnumCanadaFreRatingType paramEnumCanadaFreRatingType)
    throws TvCommonException;

  public abstract boolean setDynamicGuideline(short paramShort1, short paramShort2, short paramShort3)
    throws TvCommonException;

  public abstract boolean setUsaMpaaGuideline(UsaMpaaRatingType.EnumUsaMpaaRatingType paramEnumUsaMpaaRatingType, boolean paramBoolean)
    throws TvCommonException;

  public abstract boolean setUsaTvGuideline(EnumUsaTvRatingType paramEnumUsaTvRatingType, short paramShort)
    throws TvCommonException;

  public abstract boolean setVChipGuideline(short paramShort1, short paramShort2, short paramShort3, short paramShort4)
    throws TvCommonException;
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.tvapi.dtv.atsc.AtscPlayer
 * JD-Core Version:    0.6.2
 */