package com.mstar.android.tv;

import com.mstar.android.tvapi.atv.listener.OnAtvPlayerEventListener;
import com.mstar.android.tvapi.atv.vo.EnumAtvManualTuneMode;
import com.mstar.android.tvapi.atv.vo.EnumGetProgramCtrl;
import com.mstar.android.tvapi.atv.vo.EnumGetProgramInfo;
import com.mstar.android.tvapi.atv.vo.EnumSetProgramCtrl;
import com.mstar.android.tvapi.atv.vo.EnumSetProgramInfo;
import com.mstar.android.tvapi.common.listener.OnTvPlayerEventListener;
import com.mstar.android.tvapi.common.vo.AtvSystemStandard.EnumAtvSystemStandard;
import com.mstar.android.tvapi.common.vo.EnumAntennaType;
import com.mstar.android.tvapi.common.vo.EnumAtvAudioModeType;
import com.mstar.android.tvapi.common.vo.EnumAvdVideoStandardType;
import com.mstar.android.tvapi.common.vo.EnumChannelSwitchMode;
import com.mstar.android.tvapi.common.vo.EnumFavoriteId;
import com.mstar.android.tvapi.common.vo.EnumFirstServiceInputType;
import com.mstar.android.tvapi.common.vo.EnumFirstServiceType;
import com.mstar.android.tvapi.common.vo.EnumProgramAttribute;
import com.mstar.android.tvapi.common.vo.EnumProgramCountType;
import com.mstar.android.tvapi.common.vo.EnumProgramInfoType;
import com.mstar.android.tvapi.common.vo.EnumServiceType;
import com.mstar.android.tvapi.common.vo.ProgramInfo;
import com.mstar.android.tvapi.common.vo.ProgramInfoQueryCriteria;
import com.mstar.android.tvapi.common.vo.TvOsType.EnumCountry;
import com.mstar.android.tvapi.common.vo.TvOsType.EnumLanguage;
import com.mstar.android.tvapi.dtv.listener.OnDtvPlayerEventListener;
import com.mstar.android.tvapi.dtv.vo.DtvSubtitleInfo;
import com.mstar.android.tvapi.dtv.vo.RfInfo;
import com.mstar.android.tvapi.dtv.vo.RfInfo.EnumInfoType;

public class TvChannelManager
{
  public static TvChannelManager getInstance()
  {
    throw new RuntimeException("stub");
  }

  public void addProgramToFavorite(EnumFavoriteId paramEnumFavoriteId, int paramInt1, int paramInt2, int paramInt3)
  {
    throw new RuntimeException("stub");
  }

  public boolean changeToFirstService(EnumFirstServiceInputType paramEnumFirstServiceInputType, EnumFirstServiceType paramEnumFirstServiceType)
  {
    throw new RuntimeException("stub");
  }

  public boolean closeSubtitle()
  {
    throw new RuntimeException("stub");
  }

  public void deleteProgramFromFavorite(EnumFavoriteId paramEnumFavoriteId, int paramInt1, int paramInt2, int paramInt3)
  {
    throw new RuntimeException("stub");
  }

  public int getAtvCurrentFrequency()
  {
    throw new RuntimeException("stub");
  }

  public int getAtvProgramInfo(EnumGetProgramInfo paramEnumGetProgramInfo, int paramInt)
  {
    throw new RuntimeException("stub");
  }

  public AtvSystemStandard.EnumAtvSystemStandard getAtvSoundSystem()
  {
    throw new RuntimeException("stub");
  }

  public String getAtvStationName(int paramInt)
  {
    throw new RuntimeException("stub");
  }

  public EnumAvdVideoStandardType getAtvVideoSystem()
  {
    throw new RuntimeException("stub");
  }

  public EnumChannelSwitchMode getChannelSwitchMode()
  {
    throw new RuntimeException("stub");
  }

  public int getCurrentChannelNumber()
  {
    throw new RuntimeException("stub");
  }

  public TvOsType.EnumLanguage getCurrentLanguageIndex(String paramString)
  {
    throw new RuntimeException("stub");
  }

  public boolean getProgramAttribute(EnumProgramAttribute paramEnumProgramAttribute, int paramInt1, int paramInt2, int paramInt3)
  {
    throw new RuntimeException("stub");
  }

  public int getProgramCount(EnumProgramCountType paramEnumProgramCountType)
  {
    throw new RuntimeException("stub");
  }

  public int getProgramCtrl(EnumGetProgramCtrl paramEnumGetProgramCtrl, int paramInt1, int paramInt2)
  {
    throw new RuntimeException("stub");
  }

  public ProgramInfo getProgramInfo(ProgramInfoQueryCriteria paramProgramInfoQueryCriteria, EnumProgramInfoType paramEnumProgramInfoType)
  {
    throw new RuntimeException("stub");
  }

  public String getProgramName(int paramInt1, EnumServiceType paramEnumServiceType, int paramInt2)
  {
    throw new RuntimeException("stub");
  }

  public RfInfo getRfInfo(RfInfo.EnumInfoType paramEnumInfoType, int paramInt)
  {
    throw new RuntimeException("stub");
  }

  public EnumAtvAudioModeType getSIFMtsMode()
  {
    throw new RuntimeException("stub");
  }

  public DtvSubtitleInfo getSubtitleInfo()
  {
    throw new RuntimeException("stub");
  }

  public TvOsType.EnumCountry getSystemCountry()
  {
    throw new RuntimeException("stub");
  }

  public EnumAvdVideoStandardType getVideoStandard()
  {
    throw new RuntimeException("stub");
  }

  public boolean isSignalStabled()
  {
    throw new RuntimeException("stub");
  }

  public boolean openSubtitle(int paramInt)
  {
    throw new RuntimeException("stub");
  }

  public boolean pauseAtvAutoTuning()
  {
    throw new RuntimeException("stub");
  }

  public boolean pauseDtvScan()
  {
    throw new RuntimeException("stub");
  }

  public boolean playDtvCurrentProgram()
  {
    throw new RuntimeException("stub");
  }

  public boolean programDown()
  {
    throw new RuntimeException("stub");
  }

  public boolean programSel(int paramInt, EnumServiceType paramEnumServiceType)
  {
    throw new RuntimeException("stub");
  }

  public boolean programUp()
  {
    throw new RuntimeException("stub");
  }

  public boolean registerOnAtvPlayerEventListener(OnAtvPlayerEventListener paramOnAtvPlayerEventListener)
  {
    throw new RuntimeException("stub");
  }

  public boolean registerOnDtvPlayerEventListener(OnDtvPlayerEventListener paramOnDtvPlayerEventListener)
  {
    throw new RuntimeException("stub");
  }

  public boolean registerOnTvPlayerEventListener(OnTvPlayerEventListener paramOnTvPlayerEventListener)
  {
    throw new RuntimeException("stub");
  }

  public boolean resumeAtvAutoTuning()
  {
    throw new RuntimeException("stub");
  }

  public boolean resumeDtvScan()
  {
    throw new RuntimeException("stub");
  }

  public int setAtvChannel(int paramInt)
  {
    throw new RuntimeException("stub");
  }

  public boolean setAtvForceSoundSystem(AtvSystemStandard.EnumAtvSystemStandard paramEnumAtvSystemStandard)
  {
    throw new RuntimeException("stub");
  }

  public boolean setAtvForceVedioSystem(EnumAvdVideoStandardType paramEnumAvdVideoStandardType)
  {
    throw new RuntimeException("stub");
  }

  public int setAtvProgramInfo(EnumSetProgramInfo paramEnumSetProgramInfo, int paramInt1, int paramInt2)
  {
    throw new RuntimeException("stub");
  }

  public void setChannelChangeFreezeMode(boolean paramBoolean)
  {
    throw new RuntimeException("stub");
  }

  public boolean setChannelSwitchMode(EnumChannelSwitchMode paramEnumChannelSwitchMode)
  {
    throw new RuntimeException("stub");
  }

  public void setDtvAntennaType(EnumAntennaType paramEnumAntennaType)
  {
    throw new RuntimeException("stub");
  }

  public boolean setDtvManualScanByFreq(int paramInt)
  {
    throw new RuntimeException("stub");
  }

  public boolean setDtvManualScanByRF(int paramInt)
  {
    throw new RuntimeException("stub");
  }

  public void setProgramAttribute(EnumProgramAttribute paramEnumProgramAttribute, int paramInt1, int paramInt2, int paramInt3)
  {
    throw new RuntimeException("stub");
  }

  public int setProgramCtrl(EnumSetProgramCtrl paramEnumSetProgramCtrl, int paramInt1, int paramInt2)
  {
    throw new RuntimeException("stub");
  }

  public void setSystemCountry(TvOsType.EnumCountry paramEnumCountry)
  {
    throw new RuntimeException("stub");
  }

  public boolean startAtvAutoTuning(int paramInt1, int paramInt2, int paramInt3)
  {
    throw new RuntimeException("stub");
  }

  public boolean startAtvManualTuning(int paramInt1, int paramInt2, EnumAtvManualTuneMode paramEnumAtvManualTuneMode)
  {
    throw new RuntimeException("stub");
  }

  public boolean startDtvAutoScan()
  {
    throw new RuntimeException("stub");
  }

  public boolean startDtvFullScan()
  {
    throw new RuntimeException("stub");
  }

  public boolean startDtvManualScan()
  {
    throw new RuntimeException("stub");
  }

  public boolean stopAtvAutoTuning()
  {
    throw new RuntimeException("stub");
  }

  public void stopAtvManualTuning()
  {
    throw new RuntimeException("stub");
  }

  public boolean stopDtvScan()
  {
    throw new RuntimeException("stub");
  }

  public void switchAudioTrack(int paramInt)
  {
    throw new RuntimeException("stub");
  }

  public boolean switchMSrvDtvRouteCmd(short paramShort)
  {
    throw new RuntimeException("stub");
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.tv.TvChannelManager
 * JD-Core Version:    0.6.2
 */