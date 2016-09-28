package com.mstar.android.tvapi.common.listener;

import com.mstar.android.tvapi.common.vo.HbbtvEventInfo;

public abstract interface OnTvPlayerEventListener
{
  public abstract boolean onEpgUpdateList(int paramInt1, int paramInt2);

  public abstract boolean onHbbtvUiEvent(int paramInt, HbbtvEventInfo paramHbbtvEventInfo);

  public abstract boolean onPopupDialog(int paramInt1, int paramInt2, int paramInt3);

  public abstract boolean onPvrNotifyAlwaysTimeShiftProgramNotReady(int paramInt);

  public abstract boolean onPvrNotifyAlwaysTimeShiftProgramReady(int paramInt);

  public abstract boolean onPvrNotifyCiPlusProtection(int paramInt);

  public abstract boolean onPvrNotifyCiPlusRetentionLimitUpdate(int paramInt1, int paramInt2);

  public abstract boolean onPvrNotifyOverRun(int paramInt);

  public abstract boolean onPvrNotifyParentalControl(int paramInt1, int paramInt2);

  public abstract boolean onPvrNotifyPlaybackBegin(int paramInt);

  public abstract boolean onPvrNotifyPlaybackSpeedChange(int paramInt);

  public abstract boolean onPvrNotifyPlaybackStop(int paramInt);

  public abstract boolean onPvrNotifyPlaybackTime(int paramInt1, int paramInt2);

  public abstract boolean onPvrNotifyRecordSize(int paramInt1, int paramInt2);

  public abstract boolean onPvrNotifyRecordStop(int paramInt);

  public abstract boolean onPvrNotifyRecordTime(int paramInt1, int paramInt2);

  public abstract boolean onPvrNotifyTimeShiftOverwritesAfter(int paramInt1, int paramInt2);

  public abstract boolean onPvrNotifyTimeShiftOverwritesBefore(int paramInt1, int paramInt2);

  public abstract boolean onPvrNotifyUsbRemoved(int paramInt1, int paramInt2);

  public abstract boolean onScreenSaverMode(int paramInt1, int paramInt2);

  public abstract boolean onSignalLock(int paramInt);

  public abstract boolean onSignalUnLock(int paramInt);

  public abstract boolean onTvProgramInfoReady(int paramInt);
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.tvapi.common.listener.OnTvPlayerEventListener
 * JD-Core Version:    0.6.2
 */