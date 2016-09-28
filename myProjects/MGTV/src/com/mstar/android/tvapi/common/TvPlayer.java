package com.mstar.android.tvapi.common;

import android.view.SurfaceHolder;
import com.mstar.android.tvapi.common.exception.TvCommonException;
import com.mstar.android.tvapi.common.listener.OnTvPlayerEventListener;
import com.mstar.android.tvapi.common.vo.EnumAvdVideoStandardType;
import com.mstar.android.tvapi.common.vo.EnumStdDetectionState;
import com.mstar.android.tvapi.common.vo.EnumTeletextCommand;
import com.mstar.android.tvapi.common.vo.EnumTeletextMode;
import com.mstar.android.tvapi.common.vo.TvOsType.EnumInputSource;
import com.mstar.android.tvapi.common.vo.VideoArcInfo;
import com.mstar.android.tvapi.common.vo.VideoInfo;
import com.mstar.android.tvapi.dtv.dvb.dvbc.vo.EnumChinaDvbcRegion;

public abstract interface TvPlayer
{
  public abstract boolean closeTeletext()
    throws TvCommonException;

  public abstract boolean detectInputSource(TvOsType.EnumInputSource paramEnumInputSource)
    throws TvCommonException;

  public abstract void forceVideoStandard(EnumAvdVideoStandardType paramEnumAvdVideoStandardType)
    throws TvCommonException;

  public abstract VideoArcInfo getAspectRatio()
    throws TvCommonException;

  public abstract EnumChinaDvbcRegion getChinaDvbcRegion()
    throws TvCommonException;

  public abstract String getMheg5PfgContent()
    throws TvCommonException;

  public abstract int[] getNitFrequencyByDtvRegion(EnumChinaDvbcRegion paramEnumChinaDvbcRegion)
    throws TvCommonException;

  public abstract int getPhaseRange()
    throws TvCommonException;

  public abstract VideoInfo getVideoInfo()
    throws TvCommonException;

  public abstract EnumAvdVideoStandardType getVideoStandard()
    throws TvCommonException;

  public abstract EnumStdDetectionState getVideoStandardDetectionState()
    throws TvCommonException;

  public abstract boolean hasTeletextClockSignal()
    throws TvCommonException;

  public abstract boolean hasTeletextSignal()
    throws TvCommonException;

  public abstract void initOfflineDetection()
    throws TvCommonException;

  public abstract boolean isHdmiMode();

  public abstract boolean isSignalStable()
    throws TvCommonException;

  public abstract boolean isTeletextDisplayed()
    throws TvCommonException;

  public abstract boolean isTeletextSubtitleChannel()
    throws TvCommonException;

  public abstract boolean openPAT(EnumTeletextCommand paramEnumTeletextCommand)
    throws TvCommonException;

  public abstract boolean openTeletext(EnumTeletextMode paramEnumTeletextMode)
    throws TvCommonException;

  public abstract void release()
    throws Throwable;

  public abstract boolean sendMheg5Command(short paramShort)
    throws TvCommonException;

  public abstract boolean sendMheg5IcsCommand(int paramInt, short paramShort)
    throws TvCommonException;

  public abstract boolean sendTeletextCommand(EnumTeletextCommand paramEnumTeletextCommand)
    throws TvCommonException;

  public abstract void setChinaDvbcRegion(EnumChinaDvbcRegion paramEnumChinaDvbcRegion)
    throws TvCommonException;

  public abstract void setDebugMode(boolean paramBoolean)
    throws TvCommonException;

  public abstract void setDisplay(SurfaceHolder paramSurfaceHolder)
    throws TvCommonException;

  public abstract boolean setHPosition(int paramInt)
    throws TvCommonException;

  public abstract boolean setHdmiGpio(int[] paramArrayOfInt)
    throws TvCommonException;

  public abstract boolean setMirror(boolean paramBoolean)
    throws TvCommonException;

  public abstract void setOnTvPlayerEventListener(OnTvPlayerEventListener paramOnTvPlayerEventListener);

  public abstract boolean setPhase(int paramInt)
    throws TvCommonException;

  public abstract boolean setSize(int paramInt)
    throws TvCommonException;

  public abstract boolean setVPosition(int paramInt)
    throws TvCommonException;

  public abstract void startAutoStandardDetection()
    throws TvCommonException;

  public abstract boolean startPcModeAtuoTune()
    throws TvCommonException;
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.tvapi.common.TvPlayer
 * JD-Core Version:    0.6.2
 */