package com.mstar.android.tvapi.dtv.common;

import com.mstar.android.tvapi.common.TvPlayer;
import com.mstar.android.tvapi.common.exception.TvCommonException;
import com.mstar.android.tvapi.dtv.listener.OnDtvPlayerEventListener;
import com.mstar.android.tvapi.dtv.vo.DtvAudioInfo;
import com.mstar.android.tvapi.dtv.vo.DtvType.EnumDtvSetAudioMode;
import com.mstar.android.tvapi.dtv.vo.RfInfo;
import com.mstar.android.tvapi.dtv.vo.RfInfo.EnumInfoType;

public abstract interface DtvPlayer extends TvPlayer
{
  public abstract boolean autostartApplication()
    throws TvCommonException;

  public abstract boolean disableGigna()
    throws TvCommonException;

  public abstract boolean enableGinga()
    throws TvCommonException;

  public abstract DtvAudioInfo getAudioInfo()
    throws TvCommonException;

  public abstract RfInfo getRfInfo(RfInfo.EnumInfoType paramEnumInfoType, int paramInt)
    throws TvCommonException;

  public abstract boolean isGingaEnabled()
    throws TvCommonException;

  public abstract boolean isGingaRunning()
    throws TvCommonException;

  public abstract boolean playCurrentProgram()
    throws TvCommonException;

  public abstract boolean processKey(int paramInt, boolean paramBoolean)
    throws TvCommonException;

  public abstract boolean setAudioMode(DtvType.EnumDtvSetAudioMode paramEnumDtvSetAudioMode)
    throws TvCommonException;

  public abstract boolean setManualTuneByFreq(int paramInt)
    throws TvCommonException;

  public abstract boolean setManualTuneByRf(short paramShort)
    throws TvCommonException;

  public abstract void setOnDtvPlayerEventListener(OnDtvPlayerEventListener paramOnDtvPlayerEventListener);

  public abstract boolean startApplication(long paramLong1, long paramLong2)
    throws TvCommonException;

  public abstract boolean stopApplication()
    throws TvCommonException;

  public abstract void switchAudioTrack(int paramInt)
    throws TvCommonException;
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.tvapi.dtv.common.DtvPlayer
 * JD-Core Version:    0.6.2
 */