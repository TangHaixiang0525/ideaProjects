package com.mstar.android.tvapi.common;

import android.text.format.Time;
import com.mstar.android.tvapi.common.exception.TvCommonException;
import com.mstar.android.tvapi.common.vo.EnumEpgTimerActType;
import com.mstar.android.tvapi.common.vo.EnumOffTimerMode;
import com.mstar.android.tvapi.common.vo.EnumSleepTimeState;
import com.mstar.android.tvapi.common.vo.EpgEventTimerInfo;
import com.mstar.android.tvapi.common.vo.TimerPowerOffModeStatus;
import com.mstar.android.tvapi.common.vo.TimerPowerOn;
import com.mstar.android.tvapi.common.vo.TvOsType.EnumTimeZone;
import com.mstar.android.tvapi.dtv.vo.EnumEpgTimerCheck;

public final class TimerManager
{
  protected static TimerManager getInstance()
  {
    throw new RuntimeException("stub");
  }

  public EnumEpgTimerCheck addEpgEvent(EpgEventTimerInfo paramEpgEventTimerInfo)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final native void cancelEpgTimerEvent(int paramInt, boolean paramBoolean)
    throws TvCommonException;

  public final native Time convertSeconds2StTime(int paramInt)
    throws TvCommonException;

  public final native int convertStTime2Seconds(Time paramTime)
    throws TvCommonException;

  public final native boolean delAllEpgEvent()
    throws TvCommonException;

  public final native boolean delEpgEvent(int paramInt)
    throws TvCommonException;

  public final native boolean deletePastEpgTimer()
    throws TvCommonException;

  public final void disablePowerOffMode(EnumOffTimerMode paramEnumOffTimerMode)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final native boolean execEpgTimerAction()
    throws TvCommonException;

  protected void finalize()
    throws Throwable
  {
    throw new RuntimeException("stub");
  }

  public final native Time getClkTime()
    throws TvCommonException;

  public native int getClockOffset()
    throws TvCommonException;

  public final native EpgEventTimerInfo getEpgTimerEventByIndex(int paramInt)
    throws TvCommonException;

  public final native int getEpgTimerEventCount()
    throws TvCommonException;

  public final native EpgEventTimerInfo getEpgTimerRecordingProgram()
    throws TvCommonException;

  public final native int getNextNDayClkTimeInSeconds(short paramShort)
    throws TvCommonException;

  public final native TimerPowerOffModeStatus getOffModeStatus()
    throws TvCommonException;

  public final native TimerPowerOn getOnTime()
    throws TvCommonException;

  public native int getRtcClock()
    throws TvCommonException;

  public final native short getSleepModeTime()
    throws TvCommonException;

  public EnumSleepTimeState getSleeperState()
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final native Time getStClkTime()
    throws TvCommonException;

  public final native Time getStOnTime()
    throws TvCommonException;

  public final TvOsType.EnumTimeZone getTimeZone()
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final native boolean isEpgScheduleRecordRemiderExist(int paramInt)
    throws TvCommonException;

  public EnumEpgTimerCheck isEpgTimerSettingValid(EpgEventTimerInfo paramEpgEventTimerInfo)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final native boolean isTimeFormat12HRs()
    throws TvCommonException;

  protected void release()
    throws Throwable
  {
    throw new RuntimeException("stub");
  }

  public final native void setClkTime(Time paramTime, boolean paramBoolean)
    throws TvCommonException;

  public final native void setDebugMode(boolean paramBoolean)
    throws TvCommonException;

  public final native void setOffModeStatus(TimerPowerOffModeStatus paramTimerPowerOffModeStatus, boolean paramBoolean)
    throws TvCommonException;

  public final void setOnTime(TimerPowerOn paramTimerPowerOn, boolean paramBoolean1, boolean paramBoolean2, EnumEpgTimerActType paramEnumEpgTimerActType)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public void setOnTimerEventListener(OnTimerEventListener paramOnTimerEventListener)
  {
    throw new RuntimeException("stub");
  }

  public void setSleepModeTime(EnumSleepTimeState paramEnumSleepTimeState)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public native void setSleepTime(int paramInt)
    throws TvCommonException;

  public final native void setTimeFormat12HRs()
    throws TvCommonException;

  public final native void setTimeFormat24HRs()
    throws TvCommonException;

  public final void setTimeZone(TvOsType.EnumTimeZone paramEnumTimeZone, boolean paramBoolean)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  protected static enum EVENT
  {
  }

  public static abstract interface OnTimerEventListener
  {
    public abstract boolean onDestroyCountDown(TimerManager paramTimerManager, int paramInt1, int paramInt2, int paramInt3);

    public abstract boolean onEpgTimeUp(TimerManager paramTimerManager, int paramInt1, int paramInt2, int paramInt3);

    public abstract boolean onEpgTimerCountDown(TimerManager paramTimerManager, int paramInt1, int paramInt2, int paramInt3);

    public abstract boolean onEpgTimerRecordStart(TimerManager paramTimerManager, int paramInt1, int paramInt2, int paramInt3);

    public abstract boolean onLastMinuteWarn(TimerManager paramTimerManager, int paramInt1, int paramInt2, int paramInt3);

    public abstract boolean onOadTimeScan(TimerManager paramTimerManager, int paramInt1, int paramInt2, int paramInt3);

    public abstract boolean onOneSecondBeat(TimerManager paramTimerManager, int paramInt1, int paramInt2, int paramInt3);

    public abstract boolean onPowerDownTime(TimerManager paramTimerManager, int paramInt1, int paramInt2, int paramInt3);

    public abstract boolean onPvrNotifyRecordStop(TimerManager paramTimerManager, int paramInt1, int paramInt2, int paramInt3);

    public abstract boolean onSignalLock(TimerManager paramTimerManager, int paramInt1, int paramInt2, int paramInt3);

    public abstract boolean onSystemClkChg(TimerManager paramTimerManager, int paramInt1, int paramInt2, int paramInt3);

    public abstract boolean onUpdateLastMinute(TimerManager paramTimerManager, int paramInt1, int paramInt2, int paramInt3);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.tvapi.common.TimerManager
 * JD-Core Version:    0.6.2
 */