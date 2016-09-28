package com.mstar.android.tvapi.impl;

import android.view.SurfaceHolder;
import com.mstar.android.tvapi.atv.AtvPlayer;
import com.mstar.android.tvapi.atv.listener.OnAtvPlayerEventListener;
import com.mstar.android.tvapi.common.exception.TvCommonException;
import com.mstar.android.tvapi.common.listener.OnTvPlayerEventListener;
import com.mstar.android.tvapi.common.vo.DtvProgramSignalInfo;
import com.mstar.android.tvapi.common.vo.EnumAntennaType;
import com.mstar.android.tvapi.common.vo.EnumAvdVideoStandardType;
import com.mstar.android.tvapi.common.vo.EnumStdDetectionState;
import com.mstar.android.tvapi.common.vo.EnumTeletextCommand;
import com.mstar.android.tvapi.common.vo.EnumTeletextMode;
import com.mstar.android.tvapi.common.vo.TvOsType.EnumCountry;
import com.mstar.android.tvapi.common.vo.TvOsType.EnumInputSource;
import com.mstar.android.tvapi.common.vo.TvOsType.EnumTimeZone;
import com.mstar.android.tvapi.common.vo.VideoArcInfo;
import com.mstar.android.tvapi.common.vo.VideoInfo;
import com.mstar.android.tvapi.dtv.atsc.AtscPlayer;
import com.mstar.android.tvapi.dtv.atsc.vo.AudioMuteType.EnumAudioMuteType;
import com.mstar.android.tvapi.dtv.atsc.vo.EnumCanadaEngRatingType;
import com.mstar.android.tvapi.dtv.atsc.vo.EnumCanadaFreRatingType;
import com.mstar.android.tvapi.dtv.atsc.vo.EnumUsaTvRatingType;
import com.mstar.android.tvapi.dtv.atsc.vo.Region5RatingInformation;
import com.mstar.android.tvapi.dtv.atsc.vo.UsaMpaaRatingType.EnumUsaMpaaRatingType;
import com.mstar.android.tvapi.dtv.dvb.DvbPlayer;
import com.mstar.android.tvapi.dtv.dvb.dvbc.DtvDemodDvbcInfo;
import com.mstar.android.tvapi.dtv.dvb.dvbc.vo.EnumChinaDvbcRegion;
import com.mstar.android.tvapi.dtv.dvb.dvbt.DtvDemodDvbtInfo;
import com.mstar.android.tvapi.dtv.dvb.vo.DvbMuxInfo;
import com.mstar.android.tvapi.dtv.listener.OnDtvPlayerEventListener;
import com.mstar.android.tvapi.dtv.vo.DtvAudioInfo;
import com.mstar.android.tvapi.dtv.vo.DtvDemodType;
import com.mstar.android.tvapi.dtv.vo.DtvDemodVersion;
import com.mstar.android.tvapi.dtv.vo.DtvType.EnumDtvSetAudioMode;
import com.mstar.android.tvapi.dtv.vo.EnumParentalRating;
import com.mstar.android.tvapi.dtv.vo.RfInfo;
import com.mstar.android.tvapi.dtv.vo.RfInfo.EnumInfoType;

public class PlayerImpl
  implements AtvPlayer, DvbPlayer, AtscPlayer
{
  protected static PlayerImpl getInstance(Object paramObject)
  {
    throw new RuntimeException("stub");
  }

  public final native boolean autostartApplication()
    throws TvCommonException;

  public final native boolean closeTeletext()
    throws TvCommonException;

  public boolean detectInputSource(TvOsType.EnumInputSource paramEnumInputSource)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final native boolean disableAft()
    throws TvCommonException;

  public final native void disableAutoClock()
    throws TvCommonException;

  public final native boolean disableAutoScartOut()
    throws TvCommonException;

  public final native boolean disableGigna()
    throws TvCommonException;

  public final native boolean doesCcExist()
    throws TvCommonException;

  public final native boolean enableAft()
    throws TvCommonException;

  public final native void enableAutoClock()
    throws TvCommonException;

  public final native boolean enableAutoScartOut()
    throws TvCommonException;

  public final native boolean enableGinga()
    throws TvCommonException;

  public native boolean enterPassToUnlockByUser(boolean paramBoolean)
    throws TvCommonException;

  public native boolean enterPassToUnlockUnratedByUser(boolean paramBoolean)
    throws TvCommonException;

  public void finalize()
    throws Throwable
  {
    throw new RuntimeException("stub");
  }

  public void forceVideoStandard(EnumAvdVideoStandardType paramEnumAvdVideoStandardType)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final native int getAntennaType()
    throws TvCommonException;

  public final VideoArcInfo getAspectRatio()
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final native DtvAudioInfo getAudioInfo()
    throws TvCommonException;

  public final native int getCcMode()
    throws TvCommonException;

  public EnumChinaDvbcRegion getChinaDvbcRegion()
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public native String getCountryCode()
    throws TvCommonException;

  public final native DvbMuxInfo getCurrentMuxInfo()
    throws TvCommonException;

  public native String getCurrentRatingInformation()
    throws TvCommonException;

  public final native DtvProgramSignalInfo getCurrentSignalInformation()
    throws TvCommonException;

  public native boolean getCurrentVChipBlockStatus()
    throws TvCommonException;

  public DtvDemodVersion getDTVDemodVersion(DtvDemodType paramDtvDemodType)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public native DtvDemodDvbcInfo getDemodDVBCInfo()
    throws TvCommonException;

  public native DtvDemodDvbtInfo getDemodDVBTInfo()
    throws TvCommonException;

  public final native int getDtvRouteCount()
    throws TvCommonException;

  public final native String getLanguageCode()
    throws TvCommonException;

  public final native String getMheg5PfgContent()
    throws TvCommonException;

  public final native DvbMuxInfo getMuxInfoByProgramNumber(int paramInt, short paramShort)
    throws TvCommonException;

  public final int[] getNitFrequencyByDtvRegion(EnumChinaDvbcRegion paramEnumChinaDvbcRegion)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final native int getPhaseRange()
    throws TvCommonException;

  public native Region5RatingInformation getRRTInformation()
    throws TvCommonException;

  public RfInfo getRfInfo(RfInfo.EnumInfoType paramEnumInfoType, int paramInt)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final native VideoInfo getVideoInfo()
    throws TvCommonException;

  public EnumAvdVideoStandardType getVideoStandard()
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public EnumStdDetectionState getVideoStandardDetectionState()
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final native boolean hasTeletextClockSignal()
    throws TvCommonException;

  public final native boolean hasTeletextSignal()
    throws TvCommonException;

  public final native void initAtvVif()
    throws TvCommonException;

  public final native void initOfflineDetection()
    throws TvCommonException;

  public final native boolean isAftEnabled()
    throws TvCommonException;

  public final native boolean isGingaEnabled()
    throws TvCommonException;

  public final native boolean isGingaRunning()
    throws TvCommonException;

  public final native boolean isHdmiMode();

  public final native boolean isSignalStable()
    throws TvCommonException;

  public final native boolean isTeletextDisplayed()
    throws TvCommonException;

  public final native boolean isTeletextSubtitleChannel()
    throws TvCommonException;

  public native DtvDemodVersion native_getDTVDemodVersion(int paramInt)
    throws TvCommonException;

  public final boolean openPAT(EnumTeletextCommand paramEnumTeletextCommand)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public boolean openTeletext(EnumTeletextMode paramEnumTeletextMode)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final native boolean playCurrentProgram()
    throws TvCommonException;

  public final native boolean processKey(int paramInt, boolean paramBoolean)
    throws TvCommonException;

  public void release()
    throws Throwable
  {
    throw new RuntimeException("stub");
  }

  public final native boolean saveAtvProgram(int paramInt)
    throws TvCommonException;

  public final native boolean sendMheg5Command(short paramShort)
    throws TvCommonException;

  public final native boolean sendMheg5IcsCommand(int paramInt, short paramShort)
    throws TvCommonException;

  public final boolean sendTeletextCommand(EnumTeletextCommand paramEnumTeletextCommand)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public void setAntennaType(EnumAntennaType paramEnumAntennaType)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public boolean setAudioMode(DtvType.EnumDtvSetAudioMode paramEnumDtvSetAudioMode)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public boolean setAudioMute(AudioMuteType.EnumAudioMuteType paramEnumAudioMuteType, TvOsType.EnumInputSource paramEnumInputSource)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final boolean setCanadaEngGuideline(EnumCanadaEngRatingType paramEnumCanadaEngRatingType)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final boolean setCanadaFreGuideline(EnumCanadaFreRatingType paramEnumCanadaFreRatingType)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final native void setCcMode(int paramInt)
    throws TvCommonException;

  public void setChannelChangeFreezeMode(boolean paramBoolean)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final void setChinaDvbcRegion(EnumChinaDvbcRegion paramEnumChinaDvbcRegion)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public boolean setCountry(TvOsType.EnumCountry paramEnumCountry)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final native void setDebugMode(boolean paramBoolean)
    throws TvCommonException;

  public void setDisplay(SurfaceHolder paramSurfaceHolder)
  {
    throw new RuntimeException("stub");
  }

  public final native void setDtvRoute(short paramShort)
    throws TvCommonException;

  public final boolean setDynamicGuideline(short paramShort1, short paramShort2, short paramShort3)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final native void setFavoriteRegion(int paramInt)
    throws TvCommonException;

  public final native boolean setHPosition(int paramInt)
    throws TvCommonException;

  public final native boolean setHdmiGpio(int[] paramArrayOfInt)
    throws TvCommonException;

  public final native boolean setManualTuneByFreq(int paramInt)
    throws TvCommonException;

  public final native boolean setManualTuneByRf(short paramShort)
    throws TvCommonException;

  public final native boolean setMirror(boolean paramBoolean)
    throws TvCommonException;

  public void setOnAtvPlayerEventListener(OnAtvPlayerEventListener paramOnAtvPlayerEventListener)
  {
    throw new RuntimeException("stub");
  }

  public void setOnDtvPlayerEventListener(OnDtvPlayerEventListener paramOnDtvPlayerEventListener)
  {
    throw new RuntimeException("stub");
  }

  public void setOnTvPlayerEventListener(OnTvPlayerEventListener paramOnTvPlayerEventListener)
  {
    throw new RuntimeException("stub");
  }

  public void setParental(EnumParentalRating paramEnumParentalRating)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final native boolean setPhase(int paramInt)
    throws TvCommonException;

  public final native boolean setSize(int paramInt)
    throws TvCommonException;

  public void setTimeZone(TvOsType.EnumTimeZone paramEnumTimeZone)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final boolean setUsaMpaaGuideline(UsaMpaaRatingType.EnumUsaMpaaRatingType paramEnumUsaMpaaRatingType, boolean paramBoolean)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final boolean setUsaTvGuideline(EnumUsaTvRatingType paramEnumUsaTvRatingType, short paramShort)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public native boolean setVChipGuideline(short paramShort1, short paramShort2, short paramShort3, short paramShort4)
    throws TvCommonException;

  public final native boolean setVPosition(int paramInt)
    throws TvCommonException;

  public final native boolean startApplication(long paramLong1, long paramLong2)
    throws TvCommonException;

  public native void startAutoStandardDetection()
    throws TvCommonException;

  public final native boolean startCc()
    throws TvCommonException;

  public final native boolean startPcModeAtuoTune()
    throws TvCommonException;

  public final native boolean stopApplication()
    throws TvCommonException;

  public final native boolean stopCc()
    throws TvCommonException;

  public final native void switchAudioTrack(int paramInt)
    throws TvCommonException;

  public final native boolean switchDtvRoute(short paramShort)
    throws TvCommonException;

  public final native boolean unlockChannel()
    throws TvCommonException;

  protected static enum EVENT
  {
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.tvapi.impl.PlayerImpl
 * JD-Core Version:    0.6.2
 */