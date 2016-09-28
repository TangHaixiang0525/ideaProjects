package com.mstar.android.tvapi.common;

import com.mstar.android.tvapi.common.exception.TvCommonException;
import com.mstar.android.tvapi.common.listener.OnTvEventListener;
import com.mstar.android.tvapi.common.vo.DivxDrmRegistrationInfo;
import com.mstar.android.tvapi.common.vo.EnumDrmOpMode;
import com.mstar.android.tvapi.common.vo.EnumPowerOnLogoMode;
import com.mstar.android.tvapi.common.vo.EnumPowerOnMusicMode;
import com.mstar.android.tvapi.common.vo.EnumScreenMuteType;
import com.mstar.android.tvapi.common.vo.EnumUrsaUpgradeStatus;
import com.mstar.android.tvapi.common.vo.TvOsType.EnumInputSource;
import com.mstar.android.tvapi.common.vo.TvOsType.EnumLanguage;
import com.mstar.android.tvapi.common.vo.TvTypeInfo;
import com.mstar.android.tvapi.common.vo.UsbUpgradeCfg;
import com.mstar.android.tvapi.dtv.dvb.vo.EnumDvbSystemType;
import com.mstar.android.tvapi.factory.FactoryManager;

public class TvManager
{
  public static TvManager getInstance()
  {
    throw new RuntimeException("stub");
  }

  public native void disableI2c(int paramInt)
    throws TvCommonException;

  public native boolean disablePowerOnLogo()
    throws TvCommonException;

  public native boolean disablePowerOnMusic()
    throws TvCommonException;

  protected native void disableScartOutRgb()
    throws TvCommonException;

  public native void disableTvosIr()
    throws TvCommonException;

  public native void enableI2c(int paramInt)
    throws TvCommonException;

  public boolean enablePowerOnLogo(EnumPowerOnLogoMode paramEnumPowerOnLogoMode)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public native boolean enablePowerOnMusic()
    throws TvCommonException;

  protected native void enableScartOutRgb()
    throws TvCommonException;

  public native void enterSleepMode(boolean paramBoolean1, boolean paramBoolean2)
    throws TvCommonException;

  public native void enterStrMode()
    throws TvCommonException;

  protected void finalize()
    throws Throwable
  {
    throw new RuntimeException("stub");
  }

  public void finalizeAllManager()
    throws Throwable
  {
    throw new RuntimeException("stub");
  }

  public AudioManager getAudioManager()
  {
    throw new RuntimeException("stub");
  }

  public CecManager getCecManager()
  {
    throw new RuntimeException("stub");
  }

  public ChannelManager getChannelManager()
  {
    throw new RuntimeException("stub");
  }

  public native int getCurrentDtvRoute()
    throws TvCommonException;

  public TvOsType.EnumInputSource getCurrentInputSource()
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public TvOsType.EnumLanguage getCurrentLanguageIndex(String paramString)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public TvOsType.EnumInputSource getCurrentMainInputSource()
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public TvOsType.EnumInputSource getCurrentSubInputSource()
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public DatabaseManager getDatabaseManager()
  {
    throw new RuntimeException("stub");
  }

  public native short getDefaultDisplay()
    throws TvCommonException;

  public DivxDrmRegistrationInfo getDivxDrmRegistrationInformation(EnumDrmOpMode paramEnumDrmOpMode)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public boolean getEnableHomeKey()
  {
    throw new RuntimeException("stub");
  }

  public native String getEnvironment(String paramString)
    throws TvCommonException;

  public EnumPowerOnLogoMode getEnvironmentPowerOnLogoMode()
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public EnumPowerOnMusicMode getEnvironmentPowerOnMusicMode()
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public FactoryManager getFactoryManager()
  {
    throw new RuntimeException("stub");
  }

  public native int getFrcVersion()
    throws TvCommonException;

  public native int getGpioDeviceStatus(int paramInt)
    throws TvCommonException;

  public LogoManager getLogoManager()
  {
    throw new RuntimeException("stub");
  }

  public MhlManager getMhlManager()
  {
    throw new RuntimeException("stub");
  }

  public ParentalcontrolManager getParentalcontrolManager()
  {
    throw new RuntimeException("stub");
  }

  public PictureManager getPictureManager()
  {
    throw new RuntimeException("stub");
  }

  public PipManager getPipManager()
  {
    throw new RuntimeException("stub");
  }

  public TvPlayer getPlayerManager()
  {
    throw new RuntimeException("stub");
  }

  public PvrManager getPvrManager()
  {
    throw new RuntimeException("stub");
  }

  public EnumDvbSystemType getRoutePathDtvType(short paramShort)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public native short getSarAdcLevel(short paramShort)
    throws TvCommonException;

  public ScanManager getScanManager()
  {
    throw new RuntimeException("stub");
  }

  public native int[] getSourceList()
    throws TvCommonException;

  public native String getSystemBoardName()
    throws TvCommonException;

  public native int getSystemCurrentGammaTableNo()
    throws TvCommonException;

  public native String getSystemPanelName()
    throws TvCommonException;

  public native String getSystemSoftwareVersion()
    throws TvCommonException;

  public native int getSystemTotalGammaTableNo()
    throws TvCommonException;

  public ThreeDimensionManager getThreeDimensionManager()
  {
    throw new RuntimeException("stub");
  }

  public TimerManager getTimerManager()
  {
    throw new RuntimeException("stub");
  }

  public native TvTypeInfo getTvInfo()
    throws TvCommonException;

  public native String getTvosInterfaceCommand()
    throws TvCommonException;

  public native boolean isSignalStable(int paramInt)
    throws TvCommonException;

  public native boolean isTvBootFinished()
    throws TvCommonException;

  public native boolean isUsbUpgradeFileValid()
    throws TvCommonException;

  public native boolean isUsbUpgradeFileValid(String paramString)
    throws TvCommonException;

  public native void playPowerOffMusic(String paramString, int paramInt)
    throws TvCommonException;

  public native short[] readFromEeprom(short paramShort, int paramInt)
    throws TvCommonException;

  public native short[] readFromSpiFlashByAddress(int paramInt1, int paramInt2)
    throws TvCommonException;

  public native short[] readFromSpiFlashByBank(int paramInt1, int paramInt2)
    throws TvCommonException;

  public native void rebootSystem()
    throws TvCommonException;

  public native boolean resetForMbootUpgrade(String paramString)
    throws TvCommonException;

  public native boolean resetForNandUpgrade()
    throws TvCommonException;

  public native boolean resetForNetworkUpgrade()
    throws TvCommonException;

  public native boolean resetForUsbUpgrade(UsbUpgradeCfg paramUsbUpgradeCfg)
    throws TvCommonException;

  public native void resetPostEvent()
    throws TvCommonException;

  public native boolean resetToFactoryDefault()
    throws TvCommonException;

  public native int searchFileInUsb(String paramString1, String paramString2)
    throws TvCommonException;

  public native void setDebugMode(boolean paramBoolean)
    throws TvCommonException;

  public native boolean setDefaultDisplay(short paramShort)
    throws TvCommonException;

  public void setEnableHomeKey(boolean paramBoolean)
  {
    throw new RuntimeException("stub");
  }

  public native boolean setEnvironment(String paramString1, String paramString2)
    throws TvCommonException;

  public boolean setEnvironmentPowerOnLogoMode(EnumPowerOnLogoMode paramEnumPowerOnLogoMode)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public boolean setEnvironmentPowerOnMusicMode(EnumPowerOnMusicMode paramEnumPowerOnMusicMode)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public native boolean setGpioDeviceStatus(int paramInt, boolean paramBoolean)
    throws TvCommonException;

  public boolean setInputSource(TvOsType.EnumInputSource paramEnumInputSource)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public boolean setInputSource(TvOsType.EnumInputSource paramEnumInputSource, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public void setLanguage(TvOsType.EnumLanguage paramEnumLanguage)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public void setOnTvEventListener(OnTvEventListener paramOnTvEventListener)
  {
    throw new RuntimeException("stub");
  }

  public native short[] setTvosCommonCommand(String paramString)
    throws TvCommonException;

  public native boolean setTvosInterfaceCommand(String paramString)
    throws TvCommonException;

  public boolean setVideoMute(boolean paramBoolean, EnumScreenMuteType paramEnumScreenMuteType, int paramInt, TvOsType.EnumInputSource paramEnumInputSource)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public EnumUrsaUpgradeStatus startUrsaFirmwareUpgrade(String paramString)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public native boolean updateCustomerIniFile(String paramString1, String paramString2)
    throws TvCommonException;

  public native boolean updatePanelIniFile(String paramString1, String paramString2)
    throws TvCommonException;

  public native boolean writeToEeprom(short paramShort, short[] paramArrayOfShort)
    throws TvCommonException;

  public native boolean writeToSpiFlashByAddress(int paramInt, short[] paramArrayOfShort)
    throws TvCommonException;

  public native boolean writeToSpiFlashByBank(int paramInt, short[] paramArrayOfShort)
    throws TvCommonException;

  protected static enum EVENT
  {
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.tvapi.common.TvManager
 * JD-Core Version:    0.6.2
 */