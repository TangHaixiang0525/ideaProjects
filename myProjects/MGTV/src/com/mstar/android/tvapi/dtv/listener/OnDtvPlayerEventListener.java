package com.mstar.android.tvapi.dtv.listener;

import com.mstar.android.tvapi.dtv.vo.DtvEventScan;

public abstract interface OnDtvPlayerEventListener
{
  public abstract boolean onAudioModeChange(int paramInt, boolean paramBoolean);

  public abstract boolean onChangeTtxStatus(int paramInt, boolean paramBoolean);

  public abstract boolean onCiLoadCredentialFail(int paramInt);

  public abstract boolean onDtvAutoTuningScanInfo(int paramInt, DtvEventScan paramDtvEventScan);

  public abstract boolean onDtvAutoUpdateScan(int paramInt);

  public abstract boolean onDtvChannelNameReady(int paramInt);

  public abstract boolean onDtvPriComponentMissing(int paramInt);

  public abstract boolean onDtvProgramInfoReady(int paramInt);

  public abstract boolean onEpgTimerSimulcast(int paramInt1, int paramInt2);

  public abstract boolean onGingaStatusMode(int paramInt, boolean paramBoolean);

  public abstract boolean onHbbtvStatusMode(int paramInt, boolean paramBoolean);

  public abstract boolean onMheg5EventHandler(int paramInt1, int paramInt2);

  public abstract boolean onMheg5ReturnKey(int paramInt1, int paramInt2);

  public abstract boolean onMheg5StatusMode(int paramInt1, int paramInt2);

  public abstract boolean onOadDownload(int paramInt1, int paramInt2);

  public abstract boolean onOadHandler(int paramInt1, int paramInt2, int paramInt3);

  public abstract boolean onOadTimeout(int paramInt1, int paramInt2);

  public abstract boolean onPopupScanDialogFrequencyChange(int paramInt);

  public abstract boolean onPopupScanDialogLossSignal(int paramInt);

  public abstract boolean onPopupScanDialogNewMultiplex(int paramInt);

  public abstract boolean onRctPresence(int paramInt);

  public abstract boolean onSignalLock(int paramInt);

  public abstract boolean onSignalUnLock(int paramInt);

  public abstract boolean onTsChange(int paramInt);
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.tvapi.dtv.listener.OnDtvPlayerEventListener
 * JD-Core Version:    0.6.2
 */