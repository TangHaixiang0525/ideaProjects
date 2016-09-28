package com.mstar.android.tvapi.common;

import com.mstar.android.tvapi.common.exception.TvCommonException;
import com.mstar.android.tvapi.common.listener.OnAudioEventListener;
import com.mstar.android.tvapi.common.vo.AdvancedSoundParameter;
import com.mstar.android.tvapi.common.vo.AtvSystemStandard.EnumAtvSystemStandard;
import com.mstar.android.tvapi.common.vo.AudioCommonInfoType.EnumAudioCommonInfoType;
import com.mstar.android.tvapi.common.vo.AudioOutParameter;
import com.mstar.android.tvapi.common.vo.DtvSoundEffect;
import com.mstar.android.tvapi.common.vo.EnumAdvancedSoundParameterType;
import com.mstar.android.tvapi.common.vo.EnumAdvancedSoundSubProcessType;
import com.mstar.android.tvapi.common.vo.EnumAdvancedSoundType;
import com.mstar.android.tvapi.common.vo.EnumAtvAudioModeType;
import com.mstar.android.tvapi.common.vo.EnumAtvInfoType;
import com.mstar.android.tvapi.common.vo.EnumAudioInputLevelSourceType;
import com.mstar.android.tvapi.common.vo.EnumAudioOutType;
import com.mstar.android.tvapi.common.vo.EnumAudioProcessorType;
import com.mstar.android.tvapi.common.vo.EnumAudioReturn;
import com.mstar.android.tvapi.common.vo.EnumAudioVolumeSourceType;
import com.mstar.android.tvapi.common.vo.EnumAuidoCaptureDeviceType;
import com.mstar.android.tvapi.common.vo.EnumAuidoCaptureSource;
import com.mstar.android.tvapi.common.vo.EnumDtvSoundMode;
import com.mstar.android.tvapi.common.vo.EnumEqualizerType;
import com.mstar.android.tvapi.common.vo.EnumKtvAudioMpegSoundMode;
import com.mstar.android.tvapi.common.vo.EnumKtvMixVolumeType;
import com.mstar.android.tvapi.common.vo.EnumMuteStatusType;
import com.mstar.android.tvapi.common.vo.EnumSoundEffectType;
import com.mstar.android.tvapi.common.vo.EnumSoundGetParameterType;
import com.mstar.android.tvapi.common.vo.EnumSoundHidevMode;
import com.mstar.android.tvapi.common.vo.EnumSoundSetParamType;
import com.mstar.android.tvapi.common.vo.EnumSpdifType;
import com.mstar.android.tvapi.common.vo.KtvInfoType.EnumKtvInfoType;
import com.mstar.android.tvapi.common.vo.MuteType.EnumMuteType;
import com.mstar.android.tvapi.common.vo.TvOsType.EnumInputSource;
import com.mstar.android.tvapi.common.vo.TvOsType.EnumLanguage;

public final class AudioManager
{
  public static final int E_ATVPLAYER_AUTO_TUNING_RECEIVE_EVENT_INTERVAL = 800000;

  protected static AudioManager getInstance()
  {
    throw new RuntimeException("stub");
  }

  public EnumAudioReturn checkAtvSoundSystem()
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final short disableKtvMixModeMute(EnumKtvMixVolumeType paramEnumKtvMixVolumeType)
  {
    throw new RuntimeException("stub");
  }

  public EnumAudioReturn disableMute(MuteType.EnumMuteType paramEnumMuteType)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public EnumAudioReturn enableAdvancedSoundEffect(EnumAdvancedSoundType paramEnumAdvancedSoundType, EnumAdvancedSoundSubProcessType paramEnumAdvancedSoundSubProcessType)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public EnumAudioReturn enableBasicSoundEffect(EnumSoundEffectType paramEnumSoundEffectType, boolean paramBoolean)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final short enableKtvMixModeMute(EnumKtvMixVolumeType paramEnumKtvMixVolumeType)
  {
    throw new RuntimeException("stub");
  }

  public EnumAudioReturn enableMute(MuteType.EnumMuteType paramEnumMuteType)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final native short executeAmplifierExtendedCommand(short paramShort, int paramInt1, int paramInt2, int[] paramArrayOfInt)
    throws TvCommonException;

  protected void finalize()
    throws Throwable
  {
    throw new RuntimeException("stub");
  }

  public final int getAdvancedSoundEffect(EnumAdvancedSoundParameterType paramEnumAdvancedSoundParameterType)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public EnumAtvInfoType getAtvInfo()
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public EnumAtvAudioModeType getAtvMtsMode()
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public EnumAtvAudioModeType getAtvSoundMode()
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public AtvSystemStandard.EnumAtvSystemStandard getAtvSoundSystem()
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public TvOsType.EnumLanguage getAudioPrimaryLanguage()
  {
    throw new RuntimeException("stub");
  }

  public TvOsType.EnumLanguage getAudioSecondaryLanguage()
  {
    throw new RuntimeException("stub");
  }

  public final byte getAudioVolume(EnumAudioVolumeSourceType paramEnumAudioVolumeSourceType)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final int getBasicSoundEffect(EnumSoundGetParameterType paramEnumSoundGetParameterType)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public EnumDtvSoundMode getDtvOutputMode()
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public short getInputLevel(EnumAudioInputLevelSourceType paramEnumAudioInputLevelSourceType)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public TvOsType.EnumInputSource getInputSource()
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final int getKtvSoundInfo(KtvInfoType.EnumKtvInfoType paramEnumKtvInfoType)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final int getSoundParameter(EnumSoundSetParamType paramEnumSoundSetParamType, int paramInt)
  {
    throw new RuntimeException("stub");
  }

  public final boolean isMuteEnabled(EnumMuteStatusType paramEnumMuteStatusType)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  protected void release()
    throws Throwable
  {
    throw new RuntimeException("stub");
  }

  public void setADAbsoluteVolume(int paramInt)
  {
    throw new RuntimeException("stub");
  }

  public void setADEnable(boolean paramBoolean)
  {
    throw new RuntimeException("stub");
  }

  public final EnumAudioReturn setAdvancedSoundEffect(EnumAdvancedSoundParameterType paramEnumAdvancedSoundParameterType, AdvancedSoundParameter paramAdvancedSoundParameter)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final void setAmplifierEqualizerByMode(EnumEqualizerType paramEnumEqualizerType)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final native boolean setAmplifierMute(boolean paramBoolean)
    throws TvCommonException;

  public EnumAudioReturn setAtvInfo(EnumAtvInfoType paramEnumAtvInfoType, EnumSoundHidevMode paramEnumSoundHidevMode)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public EnumAudioReturn setAtvMtsMode(EnumAtvAudioModeType paramEnumAtvAudioModeType)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public boolean setAtvSoundSystem(AtvSystemStandard.EnumAtvSystemStandard paramEnumAtvSystemStandard)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final EnumAudioReturn setAudioCaptureSource(EnumAuidoCaptureDeviceType paramEnumAuidoCaptureDeviceType, EnumAuidoCaptureSource paramEnumAuidoCaptureSource)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public EnumAudioReturn setAudioOutput(EnumAudioOutType paramEnumAudioOutType, AudioOutParameter paramAudioOutParameter)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public void setAudioPrimaryLanguage(TvOsType.EnumLanguage paramEnumLanguage)
  {
    throw new RuntimeException("stub");
  }

  public void setAudioSecondaryLanguage(TvOsType.EnumLanguage paramEnumLanguage)
  {
    throw new RuntimeException("stub");
  }

  public int setAudioSource(TvOsType.EnumInputSource paramEnumInputSource, EnumAudioProcessorType paramEnumAudioProcessorType)
  {
    throw new RuntimeException("stub");
  }

  public void setAudioVolume(EnumAudioVolumeSourceType paramEnumAudioVolumeSourceType, byte paramByte)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public void setAutoHOHEnable(boolean paramBoolean)
  {
    throw new RuntimeException("stub");
  }

  public final EnumAudioReturn setBasicSoundEffect(EnumSoundEffectType paramEnumSoundEffectType, DtvSoundEffect paramDtvSoundEffect)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public boolean setCommonAudioInfo(AudioCommonInfoType.EnumAudioCommonInfoType paramEnumAudioCommonInfoType, int paramInt1, int paramInt2)
  {
    throw new RuntimeException("stub");
  }

  public final native void setDebugMode(boolean paramBoolean)
    throws TvCommonException;

  public void setDigitalOut(EnumSpdifType paramEnumSpdifType)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public void setDtvOutputMode(EnumDtvSoundMode paramEnumDtvSoundMode)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public void setInputLevel(EnumAudioInputLevelSourceType paramEnumAudioInputLevelSourceType, short paramShort)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public void setInputSource(TvOsType.EnumInputSource paramEnumInputSource)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final short setKtvMixModeVolume(EnumKtvMixVolumeType paramEnumKtvMixVolumeType, short paramShort1, short paramShort2)
  {
    throw new RuntimeException("stub");
  }

  public final short setKtvSoundInfo(KtvInfoType.EnumKtvInfoType paramEnumKtvInfoType, int paramInt1, int paramInt2)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public int setKtvSoundTrack(EnumKtvAudioMpegSoundMode paramEnumKtvAudioMpegSoundMode)
  {
    throw new RuntimeException("stub");
  }

  public final boolean setMuteStatus(int paramInt, TvOsType.EnumInputSource paramEnumInputSource)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public void setOnAudioEventListener(OnAudioEventListener paramOnAudioEventListener)
  {
    throw new RuntimeException("stub");
  }

  public final short setSoundParameter(EnumSoundSetParamType paramEnumSoundSetParamType, int paramInt1, int paramInt2)
  {
    throw new RuntimeException("stub");
  }

  public final native short setSoundSpdifDelay(int paramInt);

  public final native short setSoundSpeakerDelay(int paramInt);

  public final native short setSubWooferVolume(boolean paramBoolean, short paramShort)
    throws TvCommonException;

  public EnumAudioReturn setToNextAtvMtsMode()
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  protected static enum EVENT
  {
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.tvapi.common.AudioManager
 * JD-Core Version:    0.6.2
 */